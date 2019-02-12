package co.mcsky.rocketlauncher;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class VectorManager {

    private FileConfiguration config;

    public VectorManager(FileConfiguration config) {
        this.config = config;
    }

    void setVector(String name, Player player, ACTION action) {
        switch (action) {
            case FROM:
                if (config.getKeys(false).contains(name + ".from")) {
                    player.sendMessage("Jump point `" + name + "` already exists!");
                    return;
                }

                Location locFrom = player.getLocation().toCenterLocation();
                locFrom.setYaw(0);
                locFrom.setPitch(0);

                config.set(name + ".from", locFrom);
                player.sendMessage("`From` jump point `" + name + "` set.");
                break;
            case TO:
                if (config.getKeys(false).contains(name + ".to")) {
                    player.sendMessage("Jump point `" + name + "` already exists!");
                    return;
                }

                Location locTo = player.getLocation().toCenterLocation();
                locTo.setYaw(0);
                locTo.setPitch(0);

                // Sets location
                config.set(name + ".to", locTo);

                // Define the magnitude by getting currently selected slot index
                int magnitude = player.getInventory().getHeldItemSlot();
                config.set(name + ".magnitude", magnitude);

                player.sendMessage("`To` jump point `" + name + "` set with magnitude " + magnitude + ".");
                break;
        }
    }

    void removeVector(String name, Player player) {
        if (config.getKeys(false).contains(name)) {
            config.set(name, null);
            player.sendMessage("Jump point `" + name + "` removed.");
        } else {
            player.sendMessage("Jump point `" + name + "` does not exist!");
        }
    }

    void listVector(Player player) {
        if (config.getKeys(false).isEmpty()) {
            player.sendMessage("There is no jump points set yet.");
            return;
        }
        for (String s : config.getKeys(false)) {
            player.sendMessage(s);
        }
    }
}
