package co.mcsky.rocketlauncher;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class RocketTrigger extends BukkitRunnable {
    private final RocketLauncher plugin;
    private Map<Location, MagLocation> locPairs;

    public RocketTrigger(RocketLauncher plugin) {
        this.plugin = plugin;

        // Gets the config object
        FileConfiguration config = this.plugin.getConfig();

        // Gets keys of location pairs, prepared to loop
        Set<String> locKeys = config.getKeys(false);

        // Initialize map
        locPairs = new HashMap<>();

        // TODO Give some hints when values are not complete

        // Stores location pairs in map
        for (String entry : locKeys) {
            try {
                Location locFrom = ((Location) config.get(entry + ".from"));
                Location locTo = ((Location) config.get(entry + ".to"));
                int magnitude = (int) config.get(entry + ".magnitude");

                locPairs.put(locFrom, new MagLocation(locTo, magnitude));
                Bukkit.getLogger().info(locFrom.toString());
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        for (final Player p : Bukkit.getOnlinePlayers()) {
            /* `FROM` Location*/
            Location locP = p.getLocation().toCenterLocation();
            locP.setPitch(0);
            locP.setYaw(0);

            if (locPairs.keySet().contains(locP)) {
                /* `TO` Location */
                Location locTo = locPairs.get(locP).getLoc();
                int magnitude = locPairs.get(locP).getMagnitude();

                /* Calculates the vector */
                Vector vector = locTo.toVector()
                        .subtract(locP.toVector())
                        .normalize()
                        .multiply(magnitude);

                /* Jumping! */
                p.setVelocity(vector);

                /* Avoid player dying from falling damage */
                new BukkitRunnable() {
                    int count = 0;

                    @Override
                    public void run() {
                        p.setFallDistance(-100000);
                        count++;
                        if (count > 50) this.cancel();
                    }
                }.runTaskTimer(plugin, 0, 2);

                p.sendMessage("Foo! (with magnitude: " + magnitude + ")");
            }
        }
    }
}
