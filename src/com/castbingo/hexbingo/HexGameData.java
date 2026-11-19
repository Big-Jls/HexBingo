/*
 * Copyright (c) 2026 Big_Jls and DeepSeek-v4.1-Flash-1017.
 * Licensed under the Apache License, Version 2.0.
 * See LICENSE and NOTICE.
 */
package com.castbingo.hexbingo;

import com.castbingo.hexbingo.compat.Nbt;

import com.mojang.serialization.Codec;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.saveddata.SavedData;

public class HexGameData extends SavedData {
   public static final Codec<HexGameData> CODEC = CompoundTag.CODEC.xmap(HexGameData::new, var0 -> var0.save(new CompoundTag()));
   private boolean mayhem;
   private long gameStart;
   private boolean allPrismatic;
   private final Map<UUID, List<String>> hexes = new HashMap<>();
   private final Map<UUID, Map<String, String>> granted = new HashMap<>();
   private final Map<UUID, List<Integer>> pending = new HashMap<>();
   private final Map<UUID, HexGameData.Offer> offers = new HashMap<>();
   private final Map<String, int[]> teams = new HashMap<>();
   private final Set<UUID> extraChoice = new HashSet<>();
   private final Map<UUID, Integer> refreshCharges = new HashMap<>();
   private final Map<UUID, CompoundTag> furnaces = new HashMap<>();
   private final Map<UUID, CompoundTag> beacons = new HashMap<>();
   private final Map<UUID, Integer> qualityUp = new HashMap<>();
   private final Set<UUID> hexLocked = new HashSet<>();
   private final Set<Integer> sealed = new HashSet<>();
   private final Set<Integer> allMineLocked = new HashSet<>();
   private final Set<UUID> allMineUsed = new HashSet<>();
   private final Set<UUID> spyUsed = new HashSet<>();
   private boolean hexesLocked;

   public static HexGameData get(MinecraftServer var0) {
      // overworld() 即 1.20.1 的 MinecraftServer#overworld()
      return var0.overworld().getDataStorage().computeIfAbsent(HexGameData::new, HexGameData::new, "hexbingo");
   }

   public HexGameData() {
   }

   public HexGameData(CompoundTag var1) {
      this.load(var1);
   }

   public boolean mayhem() {
      return this.mayhem;
   }

   public void setMayhem(boolean var1) {
      this.mayhem = var1;
      this.setDirty();
   }

   public long gameStart() {
      return this.gameStart;
   }

   public void setGameStart(long var1) {
      this.gameStart = var1;
      this.setDirty();
   }

   public boolean allPrismatic() {
      return this.allPrismatic;
   }

   public void setAllPrismatic(boolean var1) {
      this.allPrismatic = var1;
      this.setDirty();
   }

   public List<String> hexes(UUID var1) {
      return this.hexes.computeIfAbsent(var1, var0 -> new ArrayList<>());
   }

   public Map<String, String> grantedPrefixes(UUID var1) {
      Map<String, String> var2 = this.granted.get(var1);
      return var2 == null ? Map.of() : Map.copyOf(var2);
   }

   public String grantedPrefix(UUID var1, String var2) {
      Map<String, String> var3 = this.granted.get(var1);
      return var3 == null ? null : var3.get(var2);
   }

   public void markGranted(UUID var1, String var2, String var3) {
      if (var3 != null) {
         Map<String, String> var4 = this.granted.computeIfAbsent(var1, var0 -> new HashMap<>());
         String var5 = var4.put(var2, var3);
         if (var5 == null || !var5.equals(var3)) {
            this.setDirty();
         }
      }
   }

   public void clearGranted(UUID var1) {
      if (this.granted.remove(var1) != null) {
         this.setDirty();
      }
   }

   public boolean addHex(UUID var1, String var2) {
      if (this.hexesLocked) {
         return false;
      }

      List var3 = this.hexes(var1);
      if (var3.contains(var2)) {
         return false;
      }

      var3.add(var2);
      this.setDirty();
      return true;
   }

   public List<Integer> pending(UUID var1) {
      return this.pending.computeIfAbsent(var1, var0 -> new ArrayList<>());
   }

   public HexGameData.Offer offer(UUID var1) {
      return this.offers.get(var1);
   }

   public void setOffer(UUID var1, int var2, List<String> var3) {
      if (this.hexesLocked) {
         return;
      }

      this.offers.put(var1, new HexGameData.Offer(var2, List.copyOf(var3)));
      this.setDirty();
   }

   public void removeOffer(UUID var1) {
      this.offers.remove(var1);
      this.setDirty();
   }

   public int[] flags(String var1) {
      return this.teams.computeIfAbsent(var1, var0 -> new int[]{0, 0, 0, 0});
   }

   public void markTeamsDirty() {
      this.setDirty();
   }

   public boolean hasExtraChoice(UUID var1) {
      return this.extraChoice.contains(var1);
   }

   public void setExtraChoice(UUID var1) {
      if (this.extraChoice.add(var1)) {
         this.setDirty();
      }
   }

   public void clearExtraChoice(UUID var1) {
      if (this.extraChoice.remove(var1)) {
         this.setDirty();
      }
   }

   public int refreshCharges(UUID var1) {
      return this.refreshCharges.getOrDefault(var1, 0);
   }

   public void addRefreshCharge(UUID var1) {
      this.refreshCharges.merge(var1, 1, Integer::sum);
      this.setDirty();
   }

   public void consumeRefreshCharge(UUID var1) {
      int var2 = this.refreshCharges(var1);
      if (var2 > 0) {
         if (var2 == 1) {
            this.refreshCharges.remove(var1);
         } else {
            this.refreshCharges.put(var1, var2 - 1);
         }

         this.setDirty();
      }
   }

   public void clearRefreshCharges(UUID var1) {
      if (this.refreshCharges.remove(var1) != null) {
         this.setDirty();
      }
   }

   public CompoundTag portableFurnace(UUID var1) {
      return this.furnaces.computeIfAbsent(var1, var0 -> new CompoundTag());
   }

   public void setPortableFurnace(UUID var1, CompoundTag var2) {
      this.furnaces.put(var1, var2.copy());
      this.setDirty();
   }

   public CompoundTag beacon(UUID var1) {
      CompoundTag var2 = this.beacons.get(var1);
      return var2 == null ? null : var2.copy();
   }

   public void setBeacon(UUID var1, CompoundTag var2) {
      this.beacons.put(var1, var2.copy());
      this.setDirty();
   }

   public void clearBeacon(UUID var1) {
      if (this.beacons.remove(var1) != null) {
         this.setDirty();
      }
   }

   public int qualityUp(UUID var1) {
      return this.qualityUp.getOrDefault(var1, 0);
   }

   public void addQualityUp(UUID var1, int var2) {
      this.qualityUp.merge(var1, var2, Integer::sum);
      this.setDirty();
   }

   public void consumeQualityUp(UUID var1) {
      int var2 = this.qualityUp(var1);
      if (var2 > 0) {
         if (var2 == 1) {
            this.qualityUp.remove(var1);
         } else {
            this.qualityUp.put(var1, var2 - 1);
         }

         this.setDirty();
      }
   }

   public void clearQualityUp(UUID var1) {
      if (this.qualityUp.remove(var1) != null) {
         this.setDirty();
      }
   }

   public boolean isHexLocked(UUID var1) {
      return this.hexesLocked || this.hexLocked.contains(var1);
   }

   public boolean hexesLocked() {
      return this.hexesLocked;
   }

   public void lockAllHexes() {
      if (!this.hexesLocked) {
         this.hexesLocked = true;
         this.setDirty();
      }
   }

   public void lockHexes(UUID var1) {
      if (this.hexLocked.add(var1)) {
         this.setDirty();
      }
   }

   public Set<Integer> sealedSlots() {
      return this.sealed;
   }

   public void sealSlot(int var1) {
      if (this.sealed.add(var1)) {
         this.setDirty();
      }
   }

   public void setSealed(Set<Integer> var1) {
      if (!this.sealed.equals(var1)) {
         this.sealed.clear();
         this.sealed.addAll(var1);
         this.setDirty();
      }
   }

   public void clearSealed() {
      if (!this.sealed.isEmpty()) {
         this.sealed.clear();
         this.setDirty();
      }
   }

   public Set<Integer> allMineLocks() {
      return this.allMineLocked;
   }

   public void addAllMineLock(int var1) {
      if (this.allMineLocked.add(var1)) {
         this.setDirty();
      }
   }

   public void setAllMineLocks(Set<Integer> var1) {
      if (!this.allMineLocked.equals(var1)) {
         this.allMineLocked.clear();
         this.allMineLocked.addAll(var1);
         this.setDirty();
      }
   }

   public void clearAllMineLocks() {
      if (!this.allMineLocked.isEmpty()) {
         this.allMineLocked.clear();
         this.setDirty();
      }
   }

   public Set<UUID> allMineUsed() {
      return this.allMineUsed;
   }

   public void addAllMineUsed(UUID var1) {
      if (this.allMineUsed.add(var1)) {
         this.setDirty();
      }
   }

   public void clearAllMineUsed(UUID var1) {
      if (this.allMineUsed.remove(var1)) {
         this.setDirty();
      }
   }

   public void clearAllMineUsed() {
      if (!this.allMineUsed.isEmpty()) {
         this.allMineUsed.clear();
         this.setDirty();
      }
   }

   public Set<UUID> spyUsed() {
      return this.spyUsed;
   }

   public void addSpyUsed(UUID var1) {
      if (this.spyUsed.add(var1)) {
         this.setDirty();
      }
   }

   public void clearSpyUsed(UUID var1) {
      if (this.spyUsed.remove(var1)) {
         this.setDirty();
      }
   }

   public void clearSpyUsed() {
      if (!this.spyUsed.isEmpty()) {
         this.spyUsed.clear();
         this.setDirty();
      }
   }

   public void clearPending(UUID var1) {
      if (this.pending.remove(var1) != null) {
         this.setDirty();
      }
   }

   public void clearOffer(UUID var1) {
      if (this.offers.remove(var1) != null) {
         this.setDirty();
      }
   }

   public void reset(boolean var1) {
      this.mayhem = var1;
      this.gameStart = 0L;
      this.allPrismatic = false;
      this.hexes.clear();
      this.granted.clear();
      this.pending.clear();
      this.offers.clear();
      this.teams.clear();
      this.extraChoice.clear();
      this.refreshCharges.clear();
      this.furnaces.clear();
      this.beacons.clear();
      this.qualityUp.clear();
      this.hexLocked.clear();
      this.sealed.clear();
      this.allMineLocked.clear();
      this.allMineUsed.clear();
      this.spyUsed.clear();
      this.hexesLocked = false;
      this.setDirty();
   }

   public void endGame() {
      this.reset(false);
   }

   public CompoundTag save(CompoundTag var1) {
      var1.putInt("format", 2);
      var1.putBoolean("mayhem", this.mayhem);
      var1.putLong("gameStart", this.gameStart);
      var1.putBoolean("allPrismatic", this.allPrismatic);
      CompoundTag var2 = new CompoundTag();
      this.hexes.forEach((var1x, var2x) -> {
         if (!var2x.isEmpty()) {
            ListTag var3x = new ListTag();
            var2x.forEach(var3y -> var3x.add(StringTag.valueOf(var3y)));
            var2.put(var1x.toString(), var3x);
         }
      });
      var1.put("hexes", var2);
      CompoundTag var7 = new CompoundTag();
      this.granted.forEach((var1x, var2x) -> {
         if (!var2x.isEmpty()) {
            ListTag var3x = new ListTag();
            var2x.forEach((var3y, var4y) -> {
               CompoundTag var5y = new CompoundTag();
               var5y.putString("id", var3y);
               var5y.putString("prefix", var4y);
               var3x.add(var5y);
            });
            var7.put(var1x.toString(), var3x);
         }
      });
      var1.put("granted", var7);
      CompoundTag var3 = new CompoundTag();
      this.pending.forEach((var1x, var2x) -> {
         if (!var2x.isEmpty()) {
            ListTag var3x = new ListTag();
            var2x.forEach(var3y -> var3x.add(IntTag.valueOf(var3y)));
            var3.put(var1x.toString(), var3x);
         }
      });
      var1.put("pending", var3);
      CompoundTag var4 = new CompoundTag();
      this.offers.forEach((var1x, var2x) -> {
         if (!var2x.ids().isEmpty()) {
            CompoundTag var3x = new CompoundTag();
            var3x.putInt("reward", var2x.rewardIndex());
            ListTag var4x = new ListTag();
            var2x.ids().forEach(var4y -> var4x.add(StringTag.valueOf(var4y)));
            var3x.put("ids", var4x);
            var4.put(var1x.toString(), var3x);
         }
      });
      var1.put("offers", var4);
      CompoundTag var5 = new CompoundTag();
      this.teams.forEach(var5::putIntArray);
      var1.put("teams", var5);
      ListTag var15 = new ListTag();
      this.extraChoice.forEach(var1x -> var15.add(StringTag.valueOf(var1x.toString())));
      var1.put("extraChoice", var15);
      CompoundTag refreshTag = new CompoundTag();
      this.refreshCharges.forEach((var1x, var2x) -> refreshTag.putInt(var1x.toString(), var2x));
      var1.put("refreshes", refreshTag);
      CompoundTag furnaceTag = new CompoundTag();
      this.furnaces.forEach((var1x, var2x) -> furnaceTag.put(var1x.toString(), var2x));
      var1.put("furnaces", furnaceTag);
      CompoundTag beaconTag = new CompoundTag();
      this.beacons.forEach((var1x, var2x) -> beaconTag.put(var1x.toString(), var2x));
      var1.put("beacons", beaconTag);
      CompoundTag var6 = new CompoundTag();
      this.qualityUp.forEach((var1x, var2x) -> var6.putInt(var1x.toString(), var2x));
      var1.put("qualityUp", var6);
      ListTag var16 = new ListTag();
      this.hexLocked.forEach(var1x -> var16.add(StringTag.valueOf(var1x.toString())));
      var1.put("hexLocked", var16);
      int[] sealedSlots = new int[this.sealed.size()];
      int sealedIndex = 0;

      for (int sealedSlot : this.sealed) {
         sealedSlots[sealedIndex++] = sealedSlot;
      }

      var1.putIntArray("sealed", sealedSlots);
      int[] allMineSlots = new int[this.allMineLocked.size()];
      int allMineIndex = 0;

      for (int allMineSlot : this.allMineLocked) {
         allMineSlots[allMineIndex++] = allMineSlot;
      }

      var1.putIntArray("allMineLocks", allMineSlots);
      ListTag allMineUsedTag = new ListTag();
      this.allMineUsed.forEach(var1x -> allMineUsedTag.add(StringTag.valueOf(var1x.toString())));
      var1.put("allMineUsed", allMineUsedTag);
      ListTag spyUsedTag = new ListTag();
      this.spyUsed.forEach(var1x -> spyUsedTag.add(StringTag.valueOf(var1x.toString())));
      var1.put("spyUsed", spyUsedTag);
      var1.putBoolean("hexesLocked", this.hexesLocked);
      return var1;
   }

   private void load(CompoundTag var1) {
      this.hexes.clear();
      this.granted.clear();
      this.pending.clear();
      this.offers.clear();
      this.teams.clear();
      this.extraChoice.clear();
      this.refreshCharges.clear();
      this.furnaces.clear();
      this.beacons.clear();
      this.qualityUp.clear();
      this.hexLocked.clear();
      this.sealed.clear();
      this.allMineLocked.clear();
      this.allMineUsed.clear();
      this.spyUsed.clear();
      this.hexesLocked = false;
      this.mayhem = var1.getBoolean("mayhem");
      this.gameStart = var1.getLong("gameStart");
      this.allPrismatic = var1.getBoolean("allPrismatic");
      int format = Nbt.getIntOr(var1, "format", 1);
      CompoundTag var2 = Nbt.getCompoundOrEmpty(var1, "hexes");

      for (String var4 : Nbt.keys(var2)) {
         UUID var5 = parseUuid(var4);
         if (var5 != null) {
            ArrayList<String> var6 = new ArrayList<>();
            Tag var10 = var2.get(var4);
            if (format >= 2 && var10 instanceof ListTag var11) {
               for (int var12 = 0; var12 < var11.size(); var12++) {
                  var6.add(var11.getString(var12));
               }
            } else {
               String var13 = Nbt.getStringOr(var2, var4, "");
               if (!var13.isEmpty()) {
                  var6.addAll(List.of(var13.split(",")));
               }
            }

            if (!var6.isEmpty()) {
               this.hexes.put(var5, var6);
            }
         }
      }

      CompoundTag grantedTag = Nbt.getCompoundOrEmpty(var1, "granted");

      for (String uuidKey : Nbt.keys(grantedTag)) {
         UUID uuid = parseUuid(uuidKey);
         if (uuid != null) {
            HashMap<String, String> sources = new HashMap<>();
            Tag var20 = grantedTag.get(uuidKey);
            if (format >= 2 && var20 instanceof ListTag var21) {
               for (int var22 = 0; var22 < var21.size(); var22++) {
                  CompoundTag var23 = var21.getCompound(var22);
                  sources.put(Nbt.getStringOr(var23, "id", ""), Nbt.getStringOr(var23, "prefix", ""));
               }
            } else {
               String payload = Nbt.getStringOr(grantedTag, uuidKey, "");
               if (!payload.isEmpty()) {
                  for (String entry : payload.split(",")) {
                     int split = entry.indexOf(124);
                     if (split > 0) {
                        sources.put(entry.substring(0, split), entry.substring(split + 1));
                     }
                  }
               }
            }

            if (!sources.isEmpty()) {
               this.granted.put(uuid, sources);
            }
         }
      }

      // 兼容旧存档：之前的 transformed 标记统一按“质变”前缀显示
      CompoundTag legacyTag = Nbt.getCompoundOrEmpty(var1, "transformed");

      for (String uuidKey : Nbt.keys(legacyTag)) {
         UUID uuid = parseUuid(uuidKey);
         if (uuid != null) {
            String payload = Nbt.getStringOr(legacyTag, uuidKey, "");
            if (!payload.isEmpty()) {
               for (String hexId : payload.split(",")) {
                  this.granted.computeIfAbsent(uuid, var0 -> new HashMap<>()).put(hexId, "transformed");
               }
            }
         }
      }

      CompoundTag var16 = Nbt.getCompoundOrEmpty(var1, "pending");

      for (String var19 : Nbt.keys(var16)) {
         UUID var22 = parseUuid(var19);
         if (var22 != null) {
            ArrayList var8 = new ArrayList();
            Tag var24 = var16.get(var19);
            if (format >= 2 && var24 instanceof ListTag var25) {
               for (int var26 = 0; var26 < var25.size(); var26++) {
                  var8.add(var25.getInt(var26));
               }
            } else {
               String var7 = Nbt.getStringOr(var16, var19, "");
               if (!var7.isEmpty()) {
                  for (String var12 : var7.split(",")) {
                     try {
                        var8.add(Integer.parseInt(var12));
                     } catch (NumberFormatException var15) {
                     }
                  }
               }
            }

            if (!var8.isEmpty()) {
               this.pending.put(var22, var8);
            }
         }
      }

      CompoundTag var18 = Nbt.getCompoundOrEmpty(var1, "offers");

      for (String var23 : Nbt.keys(var18)) {
         UUID var26 = parseUuid(var23);
         if (var26 != null) {
            Tag var27 = var18.get(var23);
            if (format >= 2 && var27 instanceof CompoundTag var28) {
               int var29 = Nbt.getIntOr(var28, "reward", 0);
               ArrayList<String> var30 = new ArrayList<>();
               Tag var31 = var28.get("ids");
               if (var31 instanceof ListTag var32) {
                  for (int var33 = 0; var33 < var32.size(); var33++) {
                     var30.add(var32.getString(var33));
                  }
               }

               if (!var30.isEmpty()) {
                  this.offers.put(var26, new HexGameData.Offer(var29, var30));
               }
            } else {
               String var30 = Nbt.getStringOr(var18, var23, "");
               int var35 = var30.indexOf(124);
               if (var35 >= 0) {
                  try {
                     int var39 = Integer.parseInt(var30.substring(0, var35));
                     String var43 = var30.substring(var35 + 1);
                     if (!var43.isEmpty()) {
                        this.offers.put(var26, new HexGameData.Offer(var39, new ArrayList<>(List.of(var43.split(",")))));
                     }
                  } catch (NumberFormatException var14) {
                  }
               }
            }
         }
      }

      CompoundTag var21 = Nbt.getCompoundOrEmpty(var1, "teams");

      for (String var27 : Nbt.keys(var21)) {
         int[] var31 = var21.getIntArray(var27);
         if (var31.length < 4) {
            var31 = Arrays.copyOf(var31, 4);
         }

         this.teams.put(var27, var31);
      }

      Tag var25 = var1.get("extraChoice");
      if (format >= 2 && var25 instanceof ListTag var26) {
         for (int var27 = 0; var27 < var26.size(); var27++) {
            UUID var28 = parseUuid(var26.getString(var27));
            if (var28 != null) {
               this.extraChoice.add(var28);
            }
         }
      } else {
         String var29 = Nbt.getStringOr(var1, "extraChoice", "");

         for (String var40 : var29.split(",")) {
            UUID var44 = parseUuid(var40);
            if (var44 != null) {
               this.extraChoice.add(var44);
            }
         }
      }

      CompoundTag refreshTag = Nbt.getCompoundOrEmpty(var1, "refreshes");

      for (String var50 : Nbt.keys(refreshTag)) {
         UUID var51 = parseUuid(var50);
         if (var51 != null) {
            int var52 = Nbt.getIntOr(refreshTag, var50, 0);
            if (var52 > 0) {
               this.refreshCharges.put(var51, var52);
            }
         }
      }

      CompoundTag furnaceTag = Nbt.getCompoundOrEmpty(var1, "furnaces");

      for (String var53 : Nbt.keys(furnaceTag)) {
         UUID var54 = parseUuid(var53);
         if (var54 != null) {
            this.furnaces.put(var54, Nbt.getCompoundOrEmpty(furnaceTag, var53));
         }
      }

      CompoundTag beaconTag = Nbt.getCompoundOrEmpty(var1, "beacons");

      for (String var55 : Nbt.keys(beaconTag)) {
         UUID var56 = parseUuid(var55);
         if (var56 != null) {
            this.beacons.put(var56, Nbt.getCompoundOrEmpty(beaconTag, var55));
         }
      }

      CompoundTag var29 = Nbt.getCompoundOrEmpty(var1, "qualityUp");

      for (String var37 : Nbt.keys(var29)) {
         UUID var41 = parseUuid(var37);
         if (var41 != null) {
            int var45 = Nbt.getIntOr(var29, var37, 0);
            if (var45 > 0) {
               this.qualityUp.put(var41, var45);
            }
         }
      }

      Tag var34 = var1.get("hexLocked");
      if (format >= 2 && var34 instanceof ListTag var35) {
         for (int var36 = 0; var36 < var35.size(); var36++) {
            UUID var37 = parseUuid(var35.getString(var36));
            if (var37 != null) {
               this.hexLocked.add(var37);
            }
         }
      } else {
         String var38 = Nbt.getStringOr(var1, "hexLocked", "");

         for (String var47 : var38.split(",")) {
            UUID var13 = parseUuid(var47);
            if (var13 != null) {
               this.hexLocked.add(var13);
            }
         }
      }

      for (int sealedSlot : var1.getIntArray("sealed")) {
         if (sealedSlot >= 0) {
            this.sealed.add(sealedSlot);
         }
      }

      for (int allMineSlot : var1.getIntArray("allMineLocks")) {
         if (allMineSlot >= 0) {
            this.allMineLocked.add(allMineSlot);
         }
      }

      Tag allMineUsedTag = var1.get("allMineUsed");
      if (allMineUsedTag instanceof ListTag allMineList) {
         for (int i = 0; i < allMineList.size(); i++) {
            UUID usedBy = parseUuid(allMineList.getString(i));
            if (usedBy != null) {
               this.allMineUsed.add(usedBy);
            }
         }
      }

      Tag spyUsedTag = var1.get("spyUsed");
      if (spyUsedTag instanceof ListTag spyUsedList) {
         for (int i = 0; i < spyUsedList.size(); i++) {
            UUID usedBy = parseUuid(spyUsedList.getString(i));
            if (usedBy != null) {
               this.spyUsed.add(usedBy);
            }
         }
      }

      this.hexesLocked = var1.getBoolean("hexesLocked");
   }

   private static UUID parseUuid(String var0) {
      try {
         return UUID.fromString(var0);
      } catch (IllegalArgumentException var2) {
         return null;
      }
   }

   public record Offer(int rewardIndex, List<String> ids) {
   }
}
