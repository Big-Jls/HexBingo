param(
    [string]$Version = "1.20.1-1.0.0-beta",
    [string]$MinecraftDir = "D:\minecraft\.minecraft",
    [string]$MappedDir = "D:\bingo\hexbingo-1.20.1\tools\mapped",
    [string]$FartJar = "D:\minecraft\.minecraft\libraries\net\minecraftforge\ForgeAutoRenamingTool\0.1.22\ForgeAutoRenamingTool-0.1.22-all.jar",
    [string]$Jdk = "D:\bingo\hexbingo-26.2\tools\jdk\jdk-25.0.4.1+1"
)

$ErrorActionPreference = "Stop"
$root = $PSScriptRoot
$javac = Join-Path $Jdk "bin\javac.exe"
$jarTool = Join-Path $Jdk "bin\jar.exe"
$java = Join-Path $Jdk "bin\java.exe"

$officialClient = Join-Path $MappedDir "client-official.jar"
$officialForgeClient = Join-Path $MappedDir "forge-client-official.jar"
$officialForgeUniversal = Join-Path $MappedDir "forge-universal-official.jar"
$officialBongo = Join-Path $MappedDir "Bongo-official.jar"
$officialLibX = Join-Path $MappedDir "LibX-official.jar"
foreach ($p in @($officialClient, $officialForgeClient, $officialForgeUniversal, $officialBongo, $officialLibX, $FartJar)) {
    if (-not (Test-Path $p)) { throw "Missing: $p" }
}

# 1.20.1 libraries (official-mapped MC jars first so Forge AT-patched classes win)
$cp = New-Object System.Collections.Generic.List[string]
$cp.Add($officialForgeClient)
$cp.Add($officialClient)
$cp.Add($officialForgeUniversal)
$vjPath = Join-Path $MinecraftDir "versions\1.20.1-Forge_47.4.23\1.20.1-Forge_47.4.23.json"
if (Test-Path $vjPath) {
    $vj = Get-Content $vjPath -Raw | ConvertFrom-Json
    foreach ($l in $vj.libraries) {
        $p = $l.downloads.artifact.path
        if ($p) {
            $f = Join-Path (Join-Path $MinecraftDir "libraries") $p
            if ((Test-Path $f) -and ($f -notlike "*\net\minecraft\client\*")) { $cp.Add($f) }
        }
    }
}
# FML / language providers are not listed with artifact paths in the version json
$forgeVer = "1.20.1-47.4.23"
foreach ($fml in @("fmlcore", "fmlloader", "javafmllanguage", "mclanguage", "lowcodelanguage", "fmlearlydisplay")) {
    $p = Join-Path $MinecraftDir ("libraries\net\minecraftforge\" + $fml + "\" + $forgeVer + "\" + $fml + "-" + $forgeVer + ".jar")
    if (Test-Path $p) { $cp.Add($p) }
}
$cp.Add($officialBongo)
$cp.Add($officialLibX)

# sources
$sources = Get-ChildItem "$root\src" -Recurse -Filter *.java | ForEach-Object {
    $_.FullName.Replace($root + "\", "") -replace "\\", "/"
}
$build = "$root\build-1.20.1"
Remove-Item $build -Recurse -Force -ErrorAction SilentlyContinue
New-Item -ItemType Directory -Force -Path $build | Out-Null

$enc = New-Object System.Text.UTF8Encoding($false)
$argsFile = "$root\build-1.20.1.args"
$srcFile = "$root\build-1.20.1.sources"
$cpArgs = ($cp | ForEach-Object { $_ -replace '\\', '/' }) -join ";"
$outDir = $build -replace '\\', '/'
[IO.File]::WriteAllText($argsFile, ('-cp "' + $cpArgs + '"' + "`r`n--release 17`r`n-encoding UTF-8`r`n-d " + $outDir + "`r`n"), $enc)
[IO.File]::WriteAllText($srcFile, (($sources -join "`r`n") + "`r`n"), $enc)

Write-Host "Compiling ($Version) ..."
Push-Location $root
$prevEap = $ErrorActionPreference
$ErrorActionPreference = 'Continue'
& $javac '-J-Duser.language=en' '-J-Duser.country=US' "@$argsFile" "-Xmaxerrs" "500" "@$srcFile"
$code = $LASTEXITCODE
$ErrorActionPreference = $prevEap
Pop-Location
if ($code -ne 0) { throw "compile failed ($code)" }

# Reobfuscate official member names back to SRG for Forge 1.20.1 runtime
Write-Host "Reobfuscating ..."
$tmp = "$root\build-tmp"
Remove-Item $tmp -Recurse -Force -ErrorAction SilentlyContinue
New-Item -ItemType Directory -Force -Path $tmp | Out-Null
$rawJar = "$tmp\classes-official.jar"
$reobfJar = "$tmp\classes-srg.jar"
& $jarTool --create --file $rawJar -C $build .
$reobfMap = "D:\bingo\hexbingo-1.20.1\tools\mappings\official2srg.tsrg"
$fartArgs = New-Object System.Collections.Generic.List[string]
$fartArgs.Add("-jar"); $fartArgs.Add($FartJar)
$fartArgs.Add("--input"); $fartArgs.Add($rawJar)
$fartArgs.Add("--output"); $fartArgs.Add($reobfJar)
$fartArgs.Add("--map"); $fartArgs.Add($reobfMap)
$fartArgs.Add("--threads"); $fartArgs.Add("8")
foreach ($l in $cp) { $fartArgs.Add("--lib"); $fartArgs.Add($l) }
$fartArgFile = "$tmp\fart.args"
[IO.File]::WriteAllText($fartArgFile, (($fartArgs -join "`r`n") + "`r`n"), (New-Object System.Text.UTF8Encoding($false)))
& $java "@$fartArgFile" *> "$tmp\fart.log"
if ($LASTEXITCODE -ne 0) { Get-Content "$tmp\fart.log" -Tail 20; throw "reobf failed" }

Write-Host "Packaging ..."
$stage = "$root\stage-1.20.1"
Remove-Item $stage -Recurse -Force -ErrorAction SilentlyContinue
New-Item -ItemType Directory -Force -Path $stage | Out-Null
Push-Location $stage
& $jarTool --extract --file $reobfJar
Pop-Location
Copy-Item "$root\res\*" $stage -Recurse -Force
New-Item -ItemType Directory -Force -Path "$stage\META-INF" | Out-Null
Copy-Item "$root\LICENSE" "$stage\META-INF\LICENSE" -Force
Copy-Item "$root\NOTICE" "$stage\META-INF\NOTICE" -Force
# remove non-1.20.1 metadata
$neo = "$stage\META-INF\neoforge.mods.toml"
if (Test-Path $neo) { Remove-Item $neo -Force }

# version stamp
$toml = "$stage\META-INF\mods.toml"
$tomlText = [IO.File]::ReadAllText($toml)
$tomlText = $tomlText -replace '(?m)^version = "[^"]*"', "version = `"$Version`""
[IO.File]::WriteAllText($toml, $tomlText, $enc)

New-Item -ItemType Directory -Force -Path "$root\dist-1.20.1" | Out-Null
$out = "$root\dist-1.20.1\HexBingo-$Version.jar"
Remove-Item $out -Force -ErrorAction SilentlyContinue
& $jarTool --create --file $out -C $stage .
if ($LASTEXITCODE -ne 0) { throw "jar failed" }

Remove-Item $argsFile, $srcFile -Force -ErrorAction SilentlyContinue
Write-Host "Built: $out"
