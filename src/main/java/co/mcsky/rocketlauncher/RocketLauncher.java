package co.mcsky.rocketlauncher;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class RocketLauncher extends JavaPlugin {

    private FileConfiguration config = getConfig();

    @Override
    public void onDisable() {
        config.options().copyDefaults(true);
        saveConfig();
    }

    @Override
    public void onEnable() {
        // Get Vectors from config
        /*Map<String, Object> vectors = this
                .getConfig()
                .getConfigurationSection("jump-points")
                .getValues(false);*/

        this.getCommand("rocketlauncher")
                .setExecutor(new VectorManagerCommand(new VectorManager(config), this));

        this.getCommand("test")
                .setExecutor(new CommandExecutor() {
                    @Override
                    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
                        Player p = (Player) sender;
                        String s = p.getLocation().getDirection().toLocation(p.getWorld()).toString();
                        p.sendMessage(s);
                        return false;
                    }
                });
    }
}
