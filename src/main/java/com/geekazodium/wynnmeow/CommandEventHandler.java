package com.geekazodium.wynnmeow;

import net.minecraft.client.Minecraft;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static com.geekazodium.wynnmeow.WynnMeow.*;

public class CommandEventHandler {
    @SubscribeEvent
    public void onClientCommand(ClientChatEvent event){
        String message = event.getMessage();
        if(message.startsWith("/wynnmeow")){
            event.setCanceled(true);
            String[] command = message.split(" ");
            if(command.length <= 1){
                help();
                return;
            }
            switch (command[1]) {
                case "help":{
                    help();
                    return;
                }
                case "mode":{
                    setMode(command[2]);
                    return;
                }
                default:{
                    invalid(command);
                }
            }
        }
    }

    private void setMode(String s) {
        switch (s){
            case "toggle":
            case "1": {
                mode = TOGGLE;
                toggled = false;
                Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(
                        new TextComponentString("mod is now set to toggle, currently off")
                                .setStyle(new Style().setColor(TextFormatting.YELLOW).setBold(true))
                );
                return;
            }
            case "keybind":
            case "2": {
                mode = KEYBIND;
                Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(
                        new TextComponentString("mod is now set to keybind")
                                .setStyle(new Style().setColor(TextFormatting.YELLOW).setBold(true))
                );
                return;
            }
            case "off":
            case "0": {
                Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(
                        new TextComponentString("mod is now set to off")
                                .setStyle(new Style().setColor(TextFormatting.YELLOW).setBold(true))
                );
                mode = OFF;
                return;
            }
        }
    }

    private void invalid(String[] args) {
        Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(
                new TextComponentString("invalid argument"+args[1])
                        .setStyle(new Style().setColor(TextFormatting.RED))
        );
    }

    private void help(){
        Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(
                new TextComponentString("Wynn Meow version"+ VERSION +" by Geekazodium (aka.) GeekaTheCatgirl")
                        .setStyle(new Style().setColor(TextFormatting.YELLOW).setBold(true))
        );
        Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(
                new TextComponentString("/wynnmeow help - shows this prompt")
                        .setStyle(new Style().setColor(TextFormatting.YELLOW))
        );
        Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(
                new TextComponentString("/wynnmeow mode <toggle|keybind|off>")
                        .setStyle(new Style().setColor(TextFormatting.YELLOW))
        );
        Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(
                new TextComponentString("    toggle -> automatically responds to shouts with meow, use keybind to toggle on and off.")
                        .setStyle(new Style().setColor(TextFormatting.YELLOW))
        );
        Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(
                new TextComponentString("    keybind -> responds to shouts with meow when key is pressed.")
                        .setStyle(new Style().setColor(TextFormatting.YELLOW))
        );
        Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(
                new TextComponentString("    off -> mod functionality is turned off")
                        .setStyle(new Style().setColor(TextFormatting.YELLOW))
        );
    }
}
