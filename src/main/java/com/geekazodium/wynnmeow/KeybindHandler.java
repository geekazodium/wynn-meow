package com.geekazodium.wynnmeow;

import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;


import static com.geekazodium.wynnmeow.WynnMeow.*;

public class KeybindHandler {
    boolean kd = false;
    boolean lastKd = false;

    @SubscribeEvent
    public void onKeyDown(InputEvent event){
        WynnMeow instance = getInstance();
        kd = instance.meowKey.isKeyDown();
    }
    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event){
        NetHandlerPlayClient connection = Minecraft.getMinecraft().getConnection();
        if (connection == null) return;
        if (lastKd != kd) {
            lastKd = kd;
            if(mode == OFF){
                return;
            }
            if (kd) {
                if (mode == KEYBIND){
                    NetworkHelper.sendMeowPacket(lastShouter, connection);
                }
                if (mode == TOGGLE){
                    toggled = !toggled;
                }
            }
        }
    }
}
