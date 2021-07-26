package ca.j0e.damagedealtfix;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.scoreboard.Scoreboard;

import java.util.Objects;

public class ResetDamageBoardCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String message = ChatColor.translateAlternateColorCodes('&', "&8[DamageDealtFix] &aDamageDealtFix scoreboard has been successfully reset");
        Scoreboard scoreboard = Objects.requireNonNull(Bukkit.getScoreboardManager()).getMainScoreboard();
        scoreboard.getObjective("ddf-dmg").unregister();

        if (args.length == 0) {
            DamageDealtFix.getInstance().registerObjectives(scoreboard, true);
            message += " (shown).";
        }
        else if (args[0].equals("hide")) {
            DamageDealtFix.getInstance().registerObjectives(scoreboard, false);
            message += " (hidden).";
        }
        else {
            return false;
        }

        Bukkit.broadcastMessage(message);
        return true;
    }
}
