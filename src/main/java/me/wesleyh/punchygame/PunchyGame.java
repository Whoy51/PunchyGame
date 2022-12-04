package me.wesleyh.punchygame;

import com.onarandombox.MultiverseCore.MultiverseCore;
import com.onarandombox.MultiverseCore.api.Core;
import com.onarandombox.MultiverseCore.api.MVWorldManager;
import me.wesleyh.punchygame.command.GameCommand;
import me.wesleyh.punchygame.listeners.DamageEvent;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class PunchyGame extends JavaPlugin {
    public static Core core;
    public static PunchyGame instance;
    @Override
    public void onEnable() {
        // Plugin startup logic
        getServer().getPluginManager().registerEvents(new DamageEvent(), this);
        getCommand("game").setExecutor(new GameCommand());
        core = (MultiverseCore) Bukkit.getServer().getPluginManager().getPlugin("Multiverse-Core");
        assert core != null;
        MVWorldManager worldManager = core.getMVWorldManager();
        if (!worldManager.isMVWorld("Arena")) {
            getServer().getPluginManager().disablePlugin(this);
        }
        instance = this;
    }
    public static PunchyGame getInstance(){
        return instance;
    }
    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
