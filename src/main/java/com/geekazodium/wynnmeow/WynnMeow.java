package com.geekazodium.wynnmeow;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;

@Mod(modid = WynnMeow.MODID, name = WynnMeow.NAME, version = WynnMeow.VERSION)
public class WynnMeow {
    public static final String MODID = "wynn-meow";
    public static final String NAME = "Wynn Meow";
    public static final String VERSION = "1.0";
    private static WynnMeow instance;
    public static final int TOGGLE = 1;
    public static final int KEYBIND = 2;
    public static final int OFF = 3;
    public static int mode = OFF;
    public static String lastShouter;
    public static boolean toggled = false;
    public Logger LOGGER;
    public static WynnMeow getInstance(){
        return instance;
    }
    public final KeyBinding meowKey= new KeyBinding(
            "reply with meow",
            Keyboard.KEY_M,
            "wynnmeow"
    );
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event){
        LOGGER = event.getModLog();
        instance = this;
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        ClientRegistry.registerKeyBinding(meowKey);
        MinecraftForge.EVENT_BUS.register(new KeybindHandler());
        MinecraftForge.EVENT_BUS.register(new ChatEventHandler());
        MinecraftForge.EVENT_BUS.register(new CommandEventHandler());
    }
}
