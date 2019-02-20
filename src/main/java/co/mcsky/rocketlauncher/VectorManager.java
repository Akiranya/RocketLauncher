package co.mcsky.rocketlauncher;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

class VectorManager {

    enum ACTION {
        FROM,
        TO
    }

    private final RocketLauncher plugin;
    private final FileConfiguration config;

    VectorManager(RocketLauncher plugin) {
        this.plugin = plugin;
        this.config = plugin.getConfig();
    }

    void setVector(String name, Player player, ACTION action) {
        switch (action) {
            case FROM:
                if (config.getKeys(false).contains(name + ".from")) {
                    player.sendMessage("弹射点 " + name + " 早已存在!");
                    return;
                }

                Location locFrom = player.getLocation().toCenterLocation();
                locFrom.setYaw(0);
                locFrom.setPitch(0);

                config.set(name + ".from", locFrom);
                plugin.saveConfig();
                player.sendMessage("起跳点 " + name + " 已设置.");
                break;
            case TO:
                if (config.getKeys(false).contains(name + ".to")) {
                    player.sendMessage("弹射点 " + name + " 早已存在!");
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

                player.sendMessage("终点 " + name + " 已设置, 起跳速度: " + magnitude + ".");
                plugin.saveConfig();
                break;
        }
    }

    void removeVector(String name, Player player) {
        if (config.getKeys(false).contains(name)) {
//            config.set(name + ".from", null);
//            config.set(name + ".to", null);
//            config.set(name + ".magnitude", null);
            config.set(name, null);
            plugin.saveConfig();
            player.sendMessage("弹射点 " + name + " 已移除.");
        } else {
            player.sendMessage("弹射点 " + name + " 不存在.");
        }
    }

    boolean teleportTo(String name, Player player) {
        if (config.getKeys(false).contains(name)) {
            Location locFrom = (Location) config.get(name + ".from");

            // .clone() is necessary here, because I don't want the location itself to be added by 2
            player.teleport(locFrom.clone().add(0, 2, 0));
            player.sendMessage("传送到弹射点 " + name + ".");
            return true;
        } else {
            player.sendMessage("弹射点 " + name + " 不存在.");
            return false;
        }
    }

    void listVector(Player player) {
        if (config.getKeys(false).isEmpty()) {
            player.sendMessage("还没有设置好的弹射点.");
            return;
        }
        for (String s : config.getKeys(false)) {
            player.sendMessage(s);
        }
    }
}
