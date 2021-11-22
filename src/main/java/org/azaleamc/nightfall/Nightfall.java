package org.azaleamc.nightfall;


import cc.funkemunky.api.event.system.EventManager;
import cc.funkemunky.api.utils.ClassScanner;
import cc.funkemunky.api.utils.Init;
import cc.funkemunky.api.utils.MiscUtils;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.azaleamc.nightfall.checks.CheckManager;
import org.azaleamc.nightfall.data.DataManager;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Created by George on 27/04/2019 at 18:07.
 */

@Getter
public class Nightfall extends JavaPlugin {

    @Getter
    private static Nightfall instance;
    private String[] requiredVersionsOfAtlas = new String[] {"1.2-PRE-b8"};

    private CheckManager checkManager;
    private DataManager dataManager;

    @Override
    public void onEnable() {
        instance = this;

        checkManager = new CheckManager();
        dataManager = new DataManager();

        if (Bukkit.getPluginManager().isPluginEnabled("Atlas") && Arrays.stream(requiredVersionsOfAtlas).anyMatch(version -> Bukkit.getPluginManager().getPlugin("Atlas").getDescription().getVersion().equals(version))) {
            startScanner(getClass(), this);
        }
    }

    private void startScanner(Class<?> mainClass, Plugin plugin) {
        ClassScanner.scanFile(null, mainClass).stream().filter(c -> {
            try {
                Class clazz = Class.forName(c);

                return clazz.isAnnotationPresent(Init.class);
            } catch(Exception e) {
                e.printStackTrace();
            }
            return false;
        }).sorted(Comparator.comparingInt(c -> {
            try {
                Class clazz = Class.forName(c);

                Init annotation = (Init) clazz.getAnnotation(Init.class);

                return annotation.priority().getPriority();
            } catch(Exception e) {
                e.printStackTrace();
            }
            return 3;
        })).forEachOrdered(c -> {
            try {
                Class clazz = Class.forName(c);

                if(clazz.isAnnotationPresent(Init.class)) {
                    Object obj = clazz.getSimpleName().equals(mainClass.getSimpleName()) ? plugin : clazz.newInstance();
                    if (obj instanceof Listener) {
                        MiscUtils.printToConsole("&eFound " + clazz.getSimpleName() + " Bukkit listener. Registering...");
                        Bukkit.getPluginManager().registerEvents((Listener) obj, plugin);
                    } else if(obj instanceof cc.funkemunky.api.event.system.Listener) {
                        MiscUtils.printToConsole("&eFound " + clazz.getSimpleName() + " Atlas listener. Registering...");
                        EventManager.register(plugin, (cc.funkemunky.api.event.system.Listener) obj);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
