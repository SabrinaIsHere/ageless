package org.sabrina_the_bitch.ageless.Networking;

import com.mojang.serialization.Codec;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.math.BlockPos;

public record VampirismUpdatePayload(boolean value) implements CustomPayload {
    public static final CustomPayload.Id<VampirismUpdatePayload> ID = new CustomPayload.Id<>(NetworkingHelper.UPDATE_VARMPIRISM_ID);
    public static final PacketCodec<RegistryByteBuf, VampirismUpdatePayload> CODEC = PacketCodec.tuple(PacketCodecs.BOOL, VampirismUpdatePayload::value, VampirismUpdatePayload::new);

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
