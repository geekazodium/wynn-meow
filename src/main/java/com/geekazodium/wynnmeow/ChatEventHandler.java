package com.geekazodium.wynnmeow;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.chat.IChatListener;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import static com.geekazodium.wynnmeow.WynnMeow.*;

public class ChatEventHandler {
    GuiIngame lastChatGui;
    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) throws IllegalAccessException {
        NetHandlerPlayClient connection = Minecraft.getMinecraft().getConnection();
        if (connection == null) return;
        GuiIngame chatGUI = Minecraft.getMinecraft().ingameGUI;
        if (lastChatGui != chatGUI) {
            Field listeners = null;
            Field[] fields = GuiIngame.class.getDeclaredFields();
            for (Field field : fields) {
                Class<?> type = field.getType();
                if (type.equals(Map.class)) {
                    listeners = field;
                }
            }
            listeners.setAccessible(true);
            Object o = listeners.get(chatGUI);
            assert o instanceof Map;
            Map<ChatType, List<IChatListener>> chatListeners = (Map<ChatType, List<IChatListener>>) o;
            chatListeners.get(ChatType.CHAT).add(new MessageEventListener(this));
            chatListeners.get(ChatType.SYSTEM).add(new MessageEventListener(this));
            chatListeners.get(ChatType.GAME_INFO).add(new MessageEventListener(this));
            lastChatGui = chatGUI;
        }
    }
    private static class MessageEventListener implements IChatListener{
        private final ChatEventHandler instance;
        public MessageEventListener(ChatEventHandler instance){
            this.instance = instance;
        }
        @Override
        public void say(ChatType chatTypeIn, ITextComponent message) {
            instance.onNewMessage(message.getUnformattedText());
        }
    }
    public void onNewMessage(String message){
        String regex = " \\[WC[0-9]*\\] shouts:";
        String[] split = message.split(regex);
        if (split.length > 1) {
            if (split[0].matches("[a-zA-Z_0-9]*")) {
                lastShouter = split[0];
                if(mode == TOGGLE){
                    if (toggled){
                        NetworkHelper.sendMeowPacket(lastShouter,Minecraft.getMinecraft().getConnection());
                    }
                }
            }
        }
    }
}
