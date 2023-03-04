package com.geekazodium.wynnmeow;

import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.play.client.CPacketChatMessage;

public class NetworkHelper {
    public static void sendMeowPacket(String target, NetHandlerPlayClient connection) {
        if(target == null) return;
        if(connection == null) return;
        connection.sendPacket(new CPacketChatMessage("/msg " + target + " meow"));
    }
}
