package ca.j0e.damagedealtfix;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Scoreboard;

import java.util.Objects;

public class DamageDealtFix extends JavaPlugin {
    private static DamageDealtFix instance = null;
    private FileConfiguration config = getConfig();
    private boolean debug = false;

    @Override
    public void onEnable() {
        config.addDefault("debug", false);
        config.options().copyDefaults(true);
        this.saveConfig();

        debug = config.getBoolean("debug");

        Scoreboard scoreboard = Objects.requireNonNull(getServer().getScoreboardManager()).getMainScoreboard();
        if (scoreboard.getObjective("ddf-dmg") == null) {
            this.registerObjectives(scoreboard, false);
        }

        this.getCommand("ddfreset").setExecutor(new ResetDamageBoardCommand());
        getServer().getPluginManager().registerEvents(new EntityDamageListener(), this);

        getLogger().info("DamageDealtFix v" + this.getDescription().getVersion() + " enabled.");
        instance = this;
    }

    @Override
    public void onDisable() {
        getLogger().info("DamageDealtFix v" + this.getDescription().getVersion() + " disabled.");
    }

    public void registerObjectives(Scoreboard scoreboard, boolean show) {
        scoreboard.registerNewObjective("ddf-dmg", "dummy", ChatColor.translateAlternateColorCodes('&', "&e&lDamage Dealt"));

        if (show) {
            scoreboard.getObjective("ddf-dmg").setDisplaySlot(DisplaySlot.SIDEBAR);
        }
    }

    public static DamageDealtFix getInstance() {
        return instance;
    }
    public boolean getDebug() {
        return debug;
    }
}
