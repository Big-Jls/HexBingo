# Bingo: Hex Mayhem — Unofficial Bingo Hex Add-on

> 中文: [README.md](README.md)

> **Upstream projects**
> - Bongo — <https://github.com/noeppi-noeppi/Bongo>
> - BingoLobby — <https://github.com/noeppi-noeppi/BingoLobby>
> - LibX — <https://github.com/ModdingX/LibX>
>
> **Unofficial project**: this is a self-written add-on, not an official Bongo / BingoLobby project. It is not affiliated with **noeppi_noeppi** or **ModdingX**, and is not endorsed or sponsored by them.
>
> **License**: Apache License 2.0. The source is this repository. Audio assets are used with permission; see "Asset status".

## What is this

An add-on for [ReBongo](https://github.com/Big-Jls/rebongo) (the NeoForge 26.2 port of Bongo) that adds **150 hex augments**: 55 silver / 55 gold / 40 prismatic. It includes a pick-one-of-three screen, skill items, passive effects, and both task-based and combat-based augments.

## Gameplay

- During a match you are offered three hexes and pick one; it takes effect immediately.
- Three-choice offers and refreshes exclude hexes already owned by a teammate and the three candidates already offered to a teammate (even before they pick). Direct grants (Ascension, Chaos Shift, Pandora, free pick, Mirror Hex) may duplicate a teammate's hex.
- Default timing (adjustable in `config/hexbingo-common.toml`):
  - 1st: when you complete your first task (can be changed to the first completed Bingo line)
  - 2nd: 5 minutes into the match
  - 3rd: 10 minutes into the match
  - 4th: 15 minutes into the match, always prismatic
- Some hexes grant right-click skill items (for example Chaos Mind, You Cannot Touch Me, Rush Rush Rush). They have their own cooldowns and cannot be dropped or stored in containers.
- Rush Rush Rush is a 5-second automatic dash: no forward key needed, steer with your view and keep the cast height; blocks in a 3×3 area along the path drop, obsidian or bedrock stops it, then it enters a 30-second cooldown.
- The Ultimate Refresh button appears at the bottom of the selection screen only while you own that hex and still have charges. Each acquisition gives one use, it can be saved for a later selection, and it is cleared when the match ends.
- Hexes come in three tiers: silver leans toward basic stats/resources, gold toward tactical abilities, prismatic toward powerful or rule-changing effects.

## Requirements

| Dependency | Version |
|---|---|
| Minecraft | 26.2 |
| NeoForge | 26.2.0.93 or newer |
| Java | 25 |
| ReBongo (`modId = bongo`) | 26.2-1.0.0-port.4 or newer |
| LibX | 26.2-8.0.0 (official release) |

ReBongo: https://github.com/Big-Jls/rebongo

## Installation

1. Install NeoForge 26.2.0.93+ and official LibX 26.2-8.0.0.
2. Install ReBongo (`ReBongo-26.2-1.0.0-port.12.jar`, `modId` is `bongo`).
3. Put `HexBingo-26.2-1.49.27.jar` into the client and server `mods` folders.
4. Start a Hex Bingo match: `/bingo create bongo:<difficulty> bongo:mayhem`.
5. Pick hexes when prompted.

## Keys and commands

| Key | Action |
|---|---|
| **K** | Open the hex roster: see which hexes every team/player has picked (read-only), and the current Stack Dragon stacks |
| **V** | Toggle spider-like effects (after obtaining the matching hex) |
| **T** | Toggle the magnet item pickup (after obtaining the matching hex) |
| **R** | Toggle sword flight (after obtaining the matching hex) |
| **G** | Midas Touch: convert offhand items into gold ingots (after obtaining the matching hex) |

| Command | Permission | Description |
|---|---|---|
| `/bingo hex` | Everyone | Reopen the current hex selection screen (only while a pick is pending) |
| `/bingo hexes` | **OP** | Free pick: choose one hex you do not own yet |
| `/bingo rtp` | Everyone | Random teleport (requires the matching hex) |
| `/bingo boomerang` | Everyone | Boomerang (requires the matching hex) |
| `/bingo spy <teammate> <target>` | Everyone | Spy swap (requires the matching hex) |

## Hex list

### Silver (55)

| Name | id | Effect |
|---|---|---|
| Experience Mastery | `experience_mastery` | Gain 500 experience and 8-32 random lapis lazuli. |
| I See You | `i_see_you` | Every 2s, hostile mobs within 20 blocks glow. |
| Golden Ascension | `golden_shift` | Gain a random gold hex. |
| I Want It | `want_items` | Gain 5 random items (same weighted pool as the Minion Master pet). |
| Dead Wood Blooms | `random_wood` | Gain 5 random wood types and 5 random flower types. |
| Leg Day | `leg_day` | Permanent Speed I. |
| Strength | `strong` | Permanent Strength II. |
| Utility Scope | `scope_1` | Gain +2 block interaction range and +2 entity interaction range. Stacks with other scopes. |
| Homeguard | `homeguard` | Gain speed while running continuously; each 10s raises the speed level. Stop for 0.2s and it fades. Max Speed IV. |
| Stack Dragon | `stack_dragon` | Each mob kill adds a stack; every 15 stacks grants a loot chest. Higher stacks mean better loot. |
| Hook Dizzy | `hook_dizzy` | Gain 'Hook Dizzy': right-click to pull yourself toward the block you are looking at. 5s cooldown. |
| Pearl Stock | `pearl_stock` | Gain 8 ender pearls and 2 blaze powder. |
| Practice Day | `practice_day` | Permanent Haste I. |
| Breaking Into Hell | `nether_start` | Immediately sent to the Nether; /tp, /bp and /bingo tp are disabled until you return to the Overworld alive. Dying in the Nether sends you back. |
| Star Voyage | `star_voyage` | Teleported 256 blocks above your current height with a platform and a water bucket. You MUST land in water or you will die. Landing safely unlocks 1 random Bingo slot. |
| Perspective Swap | `swap_tasks` | Randomly swap the positions of two tasks on the current Bingo card. |
| Swift Scout | `mushroom_scout` | Random mushrooms (including nether fungi) grow in a 3 block radius around you. |
| Wormhole | `wormhole` | Instantly teleport to a random spot 1-1000 blocks away. |
| Flying Cat Cult | `cat_cult` | Summon every cat variant around you. |
| Dog Squad | `dog_squad` | Summon every wolf variant around you. |
| Out of Service | `jam_bp` | Gain Out of Service: right-click to disable enemy /bp for 3 minutes. 9 minute cooldown. |
| Gold Rush | `sand_gold` | Mining sand has a 3% chance to drop a gold nugget. |
| Eye Taxi | `eye_taxi` | Your thrown eyes of ender no longer shatter. |
| Flowers on the Way | `flowers_walk` | Flowers grow on grass blocks you walk over. |
| Gear Upgrade | `forge_master` | Replace the tools and weapons in your inventory with random tiers of the same type. |
| Whatever | `random_bingo` | Gives you a random bingo (completed ones included). |
| Tribute to Love | `love_tribute` | Teleport to the nearest shipwreck. |
| Nether Start | `ruined_portal` | Teleport to the nearest ruined portal. |
| Ancient Ruins | `ancient_ruins` | Randomly spawn 4-15 suspicious gravel or suspicious sand near you. |
| Sheep Sheep Sheep | `sheep_wool` | Killing a sheep additionally drops 2 wool of random colors. |
| Portable Workbench | `portable_bench` | Gain "Portable Workbench": right-click to open a 3x3 crafting grid without placing a crafting table. |
| Growth Spray | `bone_meal_spray` | Every 10 seconds, sprinkles bone meal on crops and plants within a 9 block radius, randomly growing up to 12 of them. |
| Village Hero | `village_hero` | Gain Hero of the Village I permanently. |
| Seed Pack | `seed_gift` | Gain 1-8 each of random wheat, carrot, potato, beetroot, melon, and pumpkin seeds. |
| Archaeology Brush | `archaeology_brush` | Gain a brush that never loses durability. |
| Jumping Tiger | `jump_tiger` | Gain Jump Boost II permanently. |
| Day-Night Flip | `day_night_flip` | Instantly flip day and night; time will not change naturally for the next 10 minutes. |
| Toolbox | `toolbox` | Gain 1 random crafting table, furnace, anvil, grindstone or stonecutter. |
| Fish God | `dolphin_dash` | Gain permanent Dolphin's Grace II while in water. |
| Portable Furnace | `portable_furnace` | Gain 'Portable Furnace': right-click to open a furnace screen anywhere. Smelting continues after closing the screen. Its smelting speed is affected by Smelting Speed. |
| Smelting Speed | `smelt_speed` | Nearby furnaces, blast furnaces and smokers smelt twice as fast. |
| Meteor | `meteor` | Call down 5-10 amethyst meteors of varying size around you (never more than 500 blocks away). Enemy players get Slowness III while the ground under them crumbles block by block like a real earthquake; you gain a map marking every impact point. The craters spawn randomly equipped skeletons whose attacks can hurt players even while invulnerable. |
| Quality Upgrade | `offer_upgrade` | Your next hex selection is guaranteed to contain at least 1 prismatic hex. Only offered during the first hex selection. |
| Blind | `blind` | Gain 'Blind': right-click to blind all enemies for 8 seconds. Cooldown 3 minutes. |
| Is It Over? | `is_it_over` | Teleport to the End outer islands for 10 minutes. You cannot be teleported during this time and return automatically when it ends. |
| Who Is the Pillager Now | `pillager_outpost` | Teleport to the nearest pillager outpost. |
| The Cherry Blossoms of My Hometown Have Bloomed | `cherry_grove` | Teleport to the nearest cherry grove biome. |
| Red Light, Green Light | `pale_garden_map` | Receive a map pointing to the nearest pale garden biome. |
| Advance Victory | `advance_victory` | Instantly light up one cell, but it goes out after 5 minutes unless you genuinely complete it in time. |
| Steal the Sky | `cell_swap` | Swap ownership of one completed cell between you and an enemy team. |
| Journey to the Core | `journey_core` | Teleport to a cave at Y=-50. |
| Ascetic | `ascetic` | Clear all of your team's completed cells and immediately gain 2 random prismatic hexes. Only appears in the third hex selection. |
| DJ | `dj` | Gain 1 note block and 3 random music discs. |
| Omen Compatible | `bad_omen` | Gain one level of Bad Omen every 30 seconds, up to level III. |
| Brother, You Smell Great | `sniffer_egg` | Gain a sniffer egg; place it on moss to hatch it. |

### Gold (55)

| Name | id | Effect |
|---|---|---|
| Immunity Shield | `immunity_shield` | Every 3 minutes, become immune to the next skill-type hex from an opponent. |
| Forge Master | `inventor` | When acquired, your current tools, weapons and armor are enchanted; tools, weapons and armor you craft later also gain random enchantments. |
| Random Teleport | `random_teleport` | Unlocks /bingo rtp: randomly teleport nearby with a 5s cooldown. |
| Cutting Edge Inventor | `cutting_edge` | All skill-type hex cooldowns are reduced by 50%. |
| I Want More | `want_more_items` | Gain 10 random items (same weighted pool as the Minion Master pet). |
| Greater Utility Scope | `scope_2` | Gain +5 block interaction range and +5 entity interaction range. Stacks with other scopes. |
| Prismatic Ascension | `prismatic_shift` | Gain a random prismatic hex. |
| Silver Ascension | `silver_shift` | Immediately gain 2 random silver hexes. |
| Little Bees | `bees` | Summon 10 angry bees around every enemy team. Bees can sting through invulnerability. |
| Gluttonous Feast | `gluttonous_feast` | Permanently gain Hunger II. Eating any food grants a random level II buff for 15s, and your body grows a little larger each time. |
| Big Brain | `big_brain` | All your inventory items become random items for 5s, then are restored. Note: items newly placed in your main inventory during this time are cleared. |
| Flip the Table? | `flip_table_one` | Reroll one task that no team has completed yet. |
| Soul Bomb | `soul_bomb` | Gain 3 soul sand, 2 wither skeleton skulls and 1 flint and steel. |
| All Roads Lead to Rome | `village_road` | Teleport to the nearest village. |
| Travel Frog | `travel_frog` | Summon a frog; right-click it with half a stack of food for a random structure map (shipwreck / End ruin / woodland mansion / village / ancient city / pillager outpost / desert pyramid / jungle temple; in the Nether: bastion / fortress / ruined portal). |
| Amnesia | `amnesia` | Randomly revert one completed Bingo task of another team. |
| Ice! | `ice_boots` | Gain 1-5 ice, packed ice and blue ice, plus a pair of Frost Walker I iron boots. |
| Snow Golem Society | `snow_pumpkin` | Put cursed carved pumpkins on all enemy players; they cannot be removed by yourself — a teammate must right-click you with shears. A solo player can sneak-right-click with shears. |
| Pokemon Master | `pokemon_master` | Gain 6 random spawn eggs (duplicates allowed). |
| Raven Mouth | `raven_mouth` | Summon a black parrot; it randomly reverts one of your completed tasks or completes one random task. |
| Trapped With Friends | `friends_here` | Each nearby teammate grants +2% speed and Haste I (max +10%). |
| Dechi Song | `dechi_song` | Plays the villager version of the Dechi song; grants random Luck III-V during it, and all pickaxes in your inventory gain Fortune II-III. |
| Utopia | `utopia` | Scrambles all your potion effects and grants 5 new permanent random buffs or debuffs. |
| Rush Rush Rush | `dash_charge` | Gain "Rush Rush Rush": right-click to automatically dash forward for 5 seconds (no forward key needed), steering with your view. Blocks in a 3x3 area along the way are broken and drop (except obsidian and bedrock), and creatures hit take 10 damage. 30 second cooldown after the dash ends. |
| Soul Out | `soul_out` | Immediately enter spectator mode for 30 seconds to scout freely, then return to the position where you entered spectator mode. |
| Eighty-One Trials | `eighty_one_trials` | Teleport to the nearest trial chambers. |
| Chaos Mind | `inventory_shuffle` | Right-click the skill item: randomly shuffle the inventory, offhand and armor slots of all enemy players (items may end up in equipment slots). Bound skill items are unaffected. 8 minute cooldown. |
| Spider Man | `spider_man` | Climb walls like a spider: stick to any wall your body touches, hold forward to climb, release to hang, sneak to cancel. Press V to toggle. |
| Magnet | `magnet` | All dropped items within 16 blocks fly toward you. Press T to toggle. |
| 3-Sided Die | `dice_roll` | Every 5 minutes roll a d6 (a dice animation plays before the result is revealed): above 3 grants Speed II for 4 minutes, below 3 grants Slowness I for 4 minutes, and 3 roots you for 6 seconds. |
| Louis XVI | `louis_xvi` | The first mob you kill is guaranteed to drop 1 of its heads. |
| Delivery Arrived | `airdrop` | Airdrop a chest above you filled with loot from a random vanilla chest loot table. |
| Sea God | `sea_god` | Gain an enchanted trident (Loyalty III, Channeling I, Mending I) that returns when thrown. |
| Totem Wholesale | `totem_bulk` | Gain 3 Totems of Undying. While holding one, you can take damage, so the 'Postmortal' advancement can be completed. |
| Ender Slayer | `ender_slayer` | Killing an Enderman has a 3% chance to drop an enchanted book: Silk Touch I, Mending I or Infinity I. |
| Ultimate Refresh | `ultimate_refresh` | Your next hex selection can be refreshed one extra time, and you immediately gain 2 loot chests. |
| Auto Smelt | `auto_smelt` | Mining ores automatically smelts the drops and grants extra experience. |
| Task Shield | `task_shield` | Protect your team's 3 highest-weight tasks; other hexes can no longer push them back, lock, swap, recast or seal them. |
| Midas Touch | `midas_touch` | Press G to turn your offhand item into the same amount of gold ingots (valuables excluded). Cooldown 1 minute. |
| Bat Form | `bat_form` | Gain 'Bat Form': right-click to become a bat for 60 seconds (flight at 35% reduced speed, cannot attack, bat-sized health and body). The bat follows at your feet; the form ends with 1 minute of Slow Falling. Cooldown 7 minutes. |
| Gaze Into the Abyss | `gaze_abyss` | Teleport to the nearest deep dark biome and find a safe spot inside its caves. |
| Wildcard | `wildcard` | Randomly light up one cell on the Bingo card (shared by all teams). |
| Destiny Dice | `destiny_dice` | Roll a die (revealed after an animation): complete a cell / remove an enemy cell / both complete a cell / reshuffle all cards. |
| Great Migration | `great_migration` | All players are randomly teleported to overworld positions within 1000 blocks of each other. |
| Dragon Tamer | `dragon_rider` | Gain a dragon egg; after placing it, it incubates for 3 minutes with a text-display countdown above it; if it falls, is pushed or teleports, incubation continues at the new position, but destroying it cancels incubation. The hatched baby dragon has a built-in growth bar (shown at the bottom of the screen); its size and flying speed grow with it and it becomes an adult after 15 minutes, right-click with food to speed it up. Baby dragons circle you, break blocks they pass through with drops (but never workstations/containers such as crafting tables, furnaces, anvils or smithing tables, nor obsidian, bedrock or portals), knock back creatures for 4 damage, never attack any player and are fully invulnerable. Once adult, right-click to open the saddle screen, insert a saddle and right-click again to ride: look to steer, W to fly forward, Space to rise, Shift to dismount. While ridden the dragon no longer phases through blocks — blocks it hits are smashed so it keeps flying (ores drop normally, any other block is destroyed without drops and hovering in place does not dig), and circling/natural block breaking pause. |
| Hatsune Miku | `mangrove_map` | Receive a map pointing to the nearest mangrove swamp biome. |
| Blackstone Walker | `blackstone_walker` | Walk on lava like Frost Walker: the lava under your feet turns into temporary blackstone that melts back after a few seconds. |
| Shrink | `shrink` | Gain 'Shrink': right-click to shrink the nearest enemy to 25% size (jump power and interaction range shrink too) for 60 seconds. Cooldown 8 minutes. |
| Backpack Thief | `backpack_thief` | Gain 'Backpack Thief': right-click to randomly steal 3 items from the nearest enemy's inventory (bound skill items excluded). Cooldown 3 minutes. |
| Near Line | `near_line` | When your team is 1 cell away from a bingo line, the whole team gains Haste II and Speed I. |
| Sword Flight | `sword_fly` | Press R to fly by consuming sword durability (order: main hand → offhand → inventory → team backpack, always the lowest-tier sword first): a wooden sword flies about 9 seconds at full durability, gold 13s, stone 17s, iron 26s, diamond 39s, netherite 52s (durability cost +15%). Flight speed ramps up with sword tier, never exceeding elytra speed; the sword being consumed is shown under your feet. |
| Hidden Compartment | `hidden_pocket` | Opening a loot chest has a 3% chance to spawn an extra random loot chest near you. |
| Collector | `collector` | Your attacks instantly execute creatures and players whose current health is below 3% of their maximum. |
| Fisherman | `fisherman` | Gain a fishing rod enchanted with Luck of the Sea X; fishing reels in anything except fish (same random item pool as Minion Master). |
| It's Over! | `ended` | Killing the Ender Dragon immediately ends the current game with the killer's team winning; but in the End your team loses invulnerability — enderman, dragon and fall damage all apply normally (the Overworld and Nether are unaffected). |

### Prismatic (40)

| Name | id | Effect |
|---|---|---|
| Iron Chain | `chain_master` | Each completed team task gives a 1% + 0.1% per-completed-task chance (max 50%) to complete 1 adjacent task. |
| I Really Want It | `want_lots_items` | Gain 30 random items (same weighted pool as the Minion Master pet). |
| Greatest Utility Scope | `scope_3` | Gain +9 block interaction range and +9 entity interaction range. Stacks with other scopes. |
| Gold Rush | `gold_rush` | Attacking a mob grants 1-3 gold nuggets, 5s cooldown per mob. |
| Ascension Ritual | `ascension` | Killing a hostile mob grants a random buff. |
| Still Water Prison | `still_water` | All players can no longer receive any hex rewards. |
| Pandora's Box | `pandora` | Reroll all of your current hexes. |
| Goliath | `goliath` | Gain +30% size, max health, block break speed and attack damage. |
| You Cannot Touch Me | `untouchable` | Right-click the skill item: enemy Bingo teams cannot break blocks for 30s. 5 minute cooldown. |
| Glass Cannon Bag | `glass_cannon_bag` | The last 18 inventory slots are filled with barrier markers (items are moved out or dropped). Item drops are 8x, further boosted by Fortune and Luck (+25% per level). |
| Back to Basics | `back_to_basics` | You cannot use a crafting table, but item drops are doubled (boosted by Looting) and you gain permanent Speed I. |
| Archmage | `archmage` | After crafting, each material has a 10% chance to be refunded. |
| Minion Master | `pet_master` | Gain an invincible pet that gives you a random item from the full item pool every 8s (valuable items are rarer). |
| Crown Me King | `crown_me_king` | A beacon marks an enemy position. Reach it to gain a prismatic hex, a randomly enchanted diamond armor set, an Efficiency IV diamond pickaxe and a Sharpness IV / Fire Aspect I diamond sword. |
| Flip the Table! | `flip_table` | Reroll every task that no team has completed. |
| OK Boomerang | `boomerang_biome` | Gain OK Boomerang: throw it toward an unfinished biome task and it returns. |
| Trapped With Me | `rainbow_duel` | Gain a bound chain. Right-click to enter a bedrock duel with the nearest enemy; PvP is allowed, whoever dies first loses and is jailed for an extra 20 seconds after respawning, the winner leaves at once, and both leave after 5 minutes without PvP. 3 minute cooldown. |
| Fey Magic | `fae_magic` | Gain Fey Magic: right-click to turn the enemy team into critters for 1 minute. 5 minute cooldown. |
| Chinese Can Fly | `chinese_can_fly` | Gain an unbreakable elytra and one bound firework rocket that is always replenished. |
| Spy | `spy` | Use /bingo spy <teammate> <enemy> to swap their positions and inventories. Bound items stay with their owner. Once per game. |
| Common Wealth | `common_wealth` | Confiscate everyone's inventory items into a common pool, then redistribute them randomly. |
| Chaos Shift | `chaos_shift` | Gain two random hexes. |
| CV Engineer | `task_copy` | Randomly copies a task an opponent has completed but your team has not; in Lockout mode, directly claims that slot instead. |
| Dimensional Banish | `nether_banish` | Banish one player from each enemy team to a random place in the Nether for 1 minute. Cooldown: 8 minutes. |
| The World! | `the_world` | Gain the "The World" time-stop skill; right-click to stop time for 8 seconds so only you can move. Cooldown: 3 minutes. |
| Corrupt Backpacks | `corrupt_inventory` | For every team except yours, 5 random items of one random player turn into rotten flesh. |
| Black Market | `black_market` | The next time you open a villager trade screen, that villager reaches max level and you gain Hero of the Village II permanently. |
| Demon Pact | `demon_pact` | Permanently lose 10 max health, but you can take damage; gain a full set of randomly enchanted iron armor and immediately complete the highest-weight Bingo task. |
| Mind Control | `mind_control` | Gain 'Mind Control': right-click to control one enemy for 15 seconds — you stand still and steer the target's movement with WASD. Cooldown 5 minutes. |
| Objective Deny | `objective_deny` | Gain 'Objective Deny': right-click to open the current Bingo card and click a task; no team can complete it for 30 seconds. Cooldown 6 minutes. |
| X-Ray | `xray` | Gain 'Ore Vision': right-click for 1 minute of ore x-ray, with ores glowing in their own colors (redstone red, lapis blue, diamond aqua, emerald green, gold yellow, copper orange...). Cooldown 5 minutes. |
| Scavenger | `scavenger` | Breaking blocks has a 3% chance to drop the block itself as an extra drop. |
| Card Shuffle | `card_shuffle` | Randomly rearrange every team's card positions; completion and lock states follow their tasks. |
| Mirror Hex | `mirror_hex` | Gain 'Mirror Hex': right-click to open a list and copy one hex you do not own from the nearest other team. |
| Blank Cell | `blank_cell` | Gain 'Blank Cell': right-click to open the current Bingo card and click any incomplete task to light it up for your team only, keeping its original icon. One-time item. |
| Pan Is Right | `pan_is_right` | Gain 'Pan Is Right': right-click to reroll every hex of your whole team. One-time item: it disappears after use. |
| Loot Hunter | `loot_hunter` | Killing a creature has a 3% chance to spawn a chest with random vanilla loot at its death spot. |
| Card Recast | `card_recast` | Immediately reroll the contents of your team's completed tasks (replaced with new tasks from the pool), keeping them completed. |
| Seal | `seal` | Gain 'Seal': right-click to open the Bingo card and pick a task; no player can complete it and a barrier icon appears on its cell. One-time item: it disappears after use. |
| All Mine | `all_mine` | Gain 'All Mine': right-click to open the task UI and pick a task; every other team can never complete it (your own team is unaffected). |

## Asset status

- **Dechi Song** (`dechi_song.ogg`) is a third-party recording by **@林晗aaaa**, used **with permission**; see the attribution and authorization screenshot below.
- `sounds/dragon_1.ogg` ~ `dragon_4.ogg` come from the free "Dragon Purring" sound collection on freesoundsite.com, which states they are free, require no attribution and may be used commercially; converted to mono 64 kbps, about 15–18 KB each.
- Hex names and descriptions reference League of Legends augments as a gameplay homage; League of Legends is a trademark of Riot Games, Inc. This project is not affiliated with Riot Games.
- `textures/gui/dice_d4_*.png` (triangular dice textures for the roll animation) are drawn by this project; no third-party assets.
- Textures and the remaining language files are original to this project.

## Building

Requires JDK 25, a NeoForge 26.2.0.93 installation, official LibX and a built ReBongo jar:

```powershell
.\build.ps1 -Version 26.2-1.49.27 `
  -MinecraftDir "D:\minecraft\.minecraft" `
  -NeoForgeVersion "26.2-NeoForge_26.2.0.93" `
  -LibXJar "D:\minecraft\.minecraft\versions\26.2-NeoForge_26.2.0.93\mods\LibX-26.2-8.0.0.jar" `
  -BongoJar ".\..\rebongo\dist\ReBongo-26.2-1.0.0-port.13.jar"
```

Output: `dist/HexBingo-<version>.jar`.

## Version rules

- Bug fixes: increment the third number (`1.33.1`, `1.33.2`, ...)
- New hexes: increment the second number (`1.35.0`)

## License and credits

- This mod: **Big_Jls** design and implementation, **DeepSeek-v4.1-Flash-1017** coding assistance, Apache License 2.0.
- Dependency **Bongo**: **noeppi_noeppi**, Apache License 2.0.
- Dependency **LibX**: **ModdingX (MelanX, noeppi_noeppi)**, Apache License 2.0.

This is an unofficial add-on. It does not use upstream icons and does not imply any official relationship (Apache License 2.0, section 6, does not grant trademark rights).

### Asset attribution

- **Dechi Song** audio (`dechi_song.ogg`): **@林晗aaaa**, https://space.bilibili.com/404083915
  Used with permission; the authorization record is shown below.

![dechi_song.ogg authorization](docs/dechi-song-permission.png)

### Testers

Thanks to summerH8, FoxTwoSour, Crawling_Fish, HikaQ and others for testing this mod (in no particular order).

## Changelog

See [CHANGES.md](CHANGES.md) for the full English changelog.
