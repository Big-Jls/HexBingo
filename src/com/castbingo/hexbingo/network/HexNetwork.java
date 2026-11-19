/*
 * Copyright (c) 2026 Big_Jls and DeepSeek-v4.1-Flash-1017.
 * Licensed under the Apache License, Version 2.0.
 * See LICENSE and NOTICE.
 */
package com.castbingo.hexbingo.network;

import com.castbingo.hexbingo.HexEffects;
import com.castbingo.hexbingo.HexManager;
import com.castbingo.hexbingo.client.HexClient;
import com.castbingo.hexbingo.client.HexDiceAnim;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

/** Forge 1.20.1 网络层：SimpleChannel 注册、字段编解码与消息处理。 */
public final class HexNetwork {
   private static final String PROTOCOL_VERSION = "1";
   public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
      new ResourceLocation("hexbingo", "main"), () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals
   );

   private HexNetwork() {
   }

   public static void init() {
      int id = 0;
      CHANNEL.registerMessage(
         id++,
         HexOfferPayload.class,
         (msg, buf) -> {
            buf.writeVarInt(msg.rewardIndex());
            writeStrings(buf, msg.ids());
            buf.writeBoolean(msg.refreshAvailable());
         },
         buf -> new HexOfferPayload(buf.readVarInt(), readStrings(buf), buf.readBoolean()),
         (msg, ctx) -> {
            ctx.get().enqueueWork(() -> HexClient.open(msg.rewardIndex(), msg.ids(), msg.refreshAvailable()));
            ctx.get().setPacketHandled(true);
         }
      );
      CHANNEL.registerMessage(
         id++,
         HexSelectPayload.class,
         (msg, buf) -> buf.writeUtf(msg.id(), 128),
         buf -> new HexSelectPayload(buf.readUtf(128)),
         (msg, ctx) -> {
            ctx.get().enqueueWork(() -> {
               ServerPlayer sender = ctx.get().getSender();
               if (sender != null) {
                  HexManager.select(sender, msg.id());
               }
            });
            ctx.get().setPacketHandled(true);
         }
      );
      CHANNEL.registerMessage(
         id++,
         HexListPayload.class,
         (msg, buf) -> writeStrings(buf, msg.ids()),
         buf -> new HexListPayload(readStrings(buf)),
         (msg, ctx) -> {
            ctx.get().enqueueWork(() -> HexClient.openList(msg.ids()));
            ctx.get().setPacketHandled(true);
         }
      );
      CHANNEL.registerMessage(
         id++,
         HexPickPayload.class,
         (msg, buf) -> buf.writeUtf(msg.id(), 128),
         buf -> new HexPickPayload(buf.readUtf(128)),
         (msg, ctx) -> {
            ctx.get().enqueueWork(() -> {
               ServerPlayer sender = ctx.get().getSender();
               if (sender != null) {
                  HexManager.freePick(sender, msg.id());
               }
            });
            ctx.get().setPacketHandled(true);
         }
      );
      CHANNEL.registerMessage(
         id++,
         HexRosterPayload.class,
         (msg, buf) -> {
            buf.writeVarInt(msg.teams().size());
            for (HexRosterPayload.TeamEntry team : msg.teams()) {
               buf.writeComponent(team.name());
               buf.writeInt(team.color());
               buf.writeVarInt(team.players().size());
               for (HexRosterPayload.PlayerEntry player : team.players()) {
                  buf.writeComponent(player.name());
                  writeStrings(buf, player.hexes());
                  buf.writeVarInt(player.prefixes().size());
                  player.prefixes().forEach((hexId, prefix) -> {
                     buf.writeUtf(hexId);
                     buf.writeUtf(prefix);
                  });
                  buf.writeVarInt(player.counts().size());
                  player.counts().forEach((hexId, count) -> {
                     buf.writeUtf(hexId);
                     buf.writeVarInt(count);
                  });
               }
            }
         },
         buf -> {
            int teamCount = buf.readVarInt();
            List<HexRosterPayload.TeamEntry> teams = new ArrayList<>(teamCount);
            for (int i = 0; i < teamCount; i++) {
               Component teamName = buf.readComponent();
               int color = buf.readInt();
               int playerCount = buf.readVarInt();
               List<HexRosterPayload.PlayerEntry> players = new ArrayList<>(playerCount);
               for (int j = 0; j < playerCount; j++) {
                  Component playerName = buf.readComponent();
                  List<String> hexes = readStrings(buf);
                  int prefixCount = buf.readVarInt();
                  Map<String, String> prefixes = new HashMap<>(prefixCount);
                  for (int k = 0; k < prefixCount; k++) {
                     prefixes.put(buf.readUtf(), buf.readUtf());
                  }
                  int countSize = buf.readVarInt();
                  Map<String, Integer> counts = new HashMap<>(countSize);
                  for (int k = 0; k < countSize; k++) {
                     counts.put(buf.readUtf(), buf.readVarInt());
                  }
                  players.add(new HexRosterPayload.PlayerEntry(playerName, hexes, Map.copyOf(prefixes), Map.copyOf(counts)));
               }
               teams.add(new HexRosterPayload.TeamEntry(teamName, color, List.copyOf(players)));
            }
            return new HexRosterPayload(List.copyOf(teams));
         },
         (msg, ctx) -> {
            ctx.get().enqueueWork(() -> HexClient.openRoster(msg));
            ctx.get().setPacketHandled(true);
         }
      );
      CHANNEL.registerMessage(
         id++,
         HexRosterRequestPayload.class,
         (msg, buf) -> {
         },
         buf -> new HexRosterRequestPayload(),
         (msg, ctx) -> {
            ctx.get().enqueueWork(() -> {
               ServerPlayer sender = ctx.get().getSender();
               if (sender != null) {
                  HexManager.openRoster(sender);
               }
            });
            ctx.get().setPacketHandled(true);
         }
      );
      CHANNEL.registerMessage(
         id++,
         HexSpiderTogglePayload.class,
         (msg, buf) -> {
         },
         buf -> new HexSpiderTogglePayload(),
         (msg, ctx) -> {
            ctx.get().enqueueWork(() -> {
               ServerPlayer sender = ctx.get().getSender();
               if (sender != null) {
                  HexEffects.toggleSpiderClimb(sender);
               }
            });
            ctx.get().setPacketHandled(true);
         }
      );
      CHANNEL.registerMessage(
         id++,
         HexMagnetTogglePayload.class,
         (msg, buf) -> {
         },
         buf -> new HexMagnetTogglePayload(),
         (msg, ctx) -> {
            ctx.get().enqueueWork(() -> {
               ServerPlayer sender = ctx.get().getSender();
               if (sender != null) {
                  HexEffects.toggleMagnet(sender);
               }
            });
            ctx.get().setPacketHandled(true);
         }
      );
      CHANNEL.registerMessage(
         id++,
         HexSwordFlyTogglePayload.class,
         (msg, buf) -> {
         },
         buf -> new HexSwordFlyTogglePayload(),
         (msg, ctx) -> {
            ctx.get().enqueueWork(() -> {
               ServerPlayer sender = ctx.get().getSender();
               if (sender != null) {
                  HexEffects.toggleSwordFly(sender);
               }
            });
            ctx.get().setPacketHandled(true);
         }
      );
      CHANNEL.registerMessage(
         id++,
         HexDiceRollPayload.class,
         (msg, buf) -> buf.writeVarInt(msg.durationTicks()),
         buf -> new HexDiceRollPayload(buf.readVarInt()),
         (msg, ctx) -> {
            ctx.get().enqueueWork(() -> HexDiceAnim.start(msg.durationTicks()));
            ctx.get().setPacketHandled(true);
         }
      );
      CHANNEL.registerMessage(
         id++,
         HexSealPayload.class,
         (msg, buf) -> {
            buf.writeVarInt(msg.indices().length);
            for (int index : msg.indices()) {
               buf.writeVarInt(index);
            }
         },
         buf -> {
            int length = buf.readVarInt();
            int[] indices = new int[length];
            for (int i = 0; i < length; i++) {
               indices[i] = buf.readVarInt();
            }
            return new HexSealPayload(indices);
         },
         (msg, ctx) -> {
            ctx.get().enqueueWork(() -> HexClient.applySeals(msg.indices()));
            ctx.get().setPacketHandled(true);
         }
      );
      CHANNEL.registerMessage(
         id++,
         HexAllMineLockPayload.class,
         (msg, buf) -> {
            buf.writeVarInt(msg.indices().length);
            for (int index : msg.indices()) {
               buf.writeVarInt(index);
            }
         },
         buf -> {
            int length = buf.readVarInt();
            int[] indices = new int[length];
            for (int i = 0; i < length; i++) {
               indices[i] = buf.readVarInt();
            }
            return new HexAllMineLockPayload(indices);
         },
         (msg, ctx) -> {
            ctx.get().enqueueWork(() -> HexClient.applyAllMineLocks(msg.indices()));
            ctx.get().setPacketHandled(true);
         }
      );
      CHANNEL.registerMessage(
         id++,
         HexDragonGrowthPayload.class,
         (msg, buf) -> {
            buf.writeFloat(msg.progress());
            buf.writeBoolean(msg.adult());
         },
         buf -> new HexDragonGrowthPayload(buf.readFloat(), buf.readBoolean()),
         (msg, ctx) -> {
            ctx.get().enqueueWork(() -> HexClient.acceptDragonGrowth(msg.progress(), msg.adult()));
            ctx.get().setPacketHandled(true);
         }
      );
      CHANNEL.registerMessage(
         id++,
         HexDragonInputPayload.class,
         (msg, buf) -> {
            buf.writeBoolean(msg.forward());
            buf.writeBoolean(msg.up());
         },
         buf -> new HexDragonInputPayload(buf.readBoolean(), buf.readBoolean()),
         (msg, ctx) -> {
            ctx.get().enqueueWork(() -> {
               ServerPlayer sender = ctx.get().getSender();
               if (sender != null) {
                  HexEffects.setDragonInput(sender, msg.forward(), msg.up());
               }
            });
            ctx.get().setPacketHandled(true);
         }
      );
      CHANNEL.registerMessage(
         id++,
         HexRefreshPayload.class,
         (msg, buf) -> {
         },
         buf -> new HexRefreshPayload(),
         (msg, ctx) -> {
            ctx.get().enqueueWork(() -> {
               ServerPlayer sender = ctx.get().getSender();
               if (sender != null) {
                  HexManager.refresh(sender);
               }
            });
            ctx.get().setPacketHandled(true);
         }
      );
      CHANNEL.registerMessage(
         id++,
         HexMidasPayload.class,
         (msg, buf) -> {
         },
         buf -> new HexMidasPayload(),
         (msg, ctx) -> {
            ctx.get().enqueueWork(() -> {
               ServerPlayer sender = ctx.get().getSender();
               if (sender != null) {
                  HexEffects.castMidas(sender);
               }
            });
            ctx.get().setPacketHandled(true);
         }
      );
      CHANNEL.registerMessage(
         id++,
         HexMirrorPayload.class,
         (msg, buf) -> writeStrings(buf, msg.ids()),
         buf -> new HexMirrorPayload(readStrings(buf)),
         (msg, ctx) -> {
            ctx.get().enqueueWork(() -> HexClient.openMirror(msg.ids()));
            ctx.get().setPacketHandled(true);
         }
      );
      CHANNEL.registerMessage(
         id++,
         HexMirrorPickPayload.class,
         (msg, buf) -> buf.writeUtf(msg.id(), 128),
         buf -> new HexMirrorPickPayload(buf.readUtf(128)),
         (msg, ctx) -> {
            ctx.get().enqueueWork(() -> {
               ServerPlayer sender = ctx.get().getSender();
               if (sender != null) {
                  HexManager.mirrorPick(sender, msg.id());
               }
            });
            ctx.get().setPacketHandled(true);
         }
      );
      CHANNEL.registerMessage(
         id++,
         HexTaskListPayload.class,
         (msg, buf) -> {
            buf.writeUtf(msg.mode());
            buf.writeVarInt(msg.tasks().size());
            for (HexTaskListPayload.Entry entry : msg.tasks()) {
               buf.writeVarInt(entry.index());
               buf.writeComponent(entry.name());
            }
         },
         buf -> {
            String mode = buf.readUtf();
            int size = buf.readVarInt();
            List<HexTaskListPayload.Entry> tasks = new ArrayList<>(size);
            for (int i = 0; i < size; i++) {
               tasks.add(new HexTaskListPayload.Entry(buf.readVarInt(), buf.readComponent()));
            }
            return new HexTaskListPayload(mode, List.copyOf(tasks));
         },
         (msg, ctx) -> {
            ctx.get().enqueueWork(() -> HexClient.openTasks(msg));
            ctx.get().setPacketHandled(true);
         }
      );
      CHANNEL.registerMessage(
         id++,
         HexTaskPickPayload.class,
         (msg, buf) -> buf.writeVarInt(msg.index()),
         buf -> new HexTaskPickPayload(buf.readVarInt()),
         (msg, ctx) -> {
            ctx.get().enqueueWork(() -> {
               ServerPlayer sender = ctx.get().getSender();
               if (sender != null) {
                  HexManager.denyTaskPick(sender, msg.index());
               }
            });
            ctx.get().setPacketHandled(true);
         }
      );
   }

   public static void sendToServer(Object msg) {
      CHANNEL.sendToServer(msg);
   }

   public static void sendToPlayer(ServerPlayer player, Object msg) {
      CHANNEL.send(PacketDistributor.PLAYER.with(() -> player), msg);
   }

   public static void sendToAll(Object msg) {
      CHANNEL.send(PacketDistributor.ALL.noArg(), msg);
   }

   private static void writeStrings(FriendlyByteBuf buf, List<String> values) {
      buf.writeVarInt(values.size());
      for (String value : values) {
         buf.writeUtf(value);
      }
   }

   private static List<String> readStrings(FriendlyByteBuf buf) {
      int size = buf.readVarInt();
      List<String> values = new ArrayList<>(size);
      for (int i = 0; i < size; i++) {
         values.add(buf.readUtf());
      }
      return List.copyOf(values);
   }
}
