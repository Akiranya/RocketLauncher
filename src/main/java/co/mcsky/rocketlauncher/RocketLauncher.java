package co.mcsky.rocketlauncher;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

public class RocketLauncher extends JavaPlugin {

    private HashMap<String, ConvenienceVector> vectors;

    @Override
    public void onDisable() {
        saveConfig();
    }

    @Override
    public void onEnable() {

        VectorManagerCommand executor = new VectorManagerCommand(new VectorManager(this), this);
        this.getCommand("rocketlauncher").setExecutor(executor);

        loadVectors();
    }

    void loadVectors() {
        vectors = new HashMap<>();

        // Loads vectors and meanwhile checks if complete
        for (String key : this.getConfig().getKeys(false)) {
            Location locFrom = (Location) this.getConfig().get(key + ".from");
            Location locTo = (Location) this.getConfig().get(key + ".to");
            int magnitude = this.getConfig().getInt(key + ".magnitude");
            if (locFrom == null || locTo == null || magnitude == 0) {
                Bukkit.getLogger().log(
                        Level.WARNING,
                        "Loading data failed due to incomplete location, skipping '" + key + "'."
                );
            } else {
                vectors.put(key, new ConvenienceVector(locFrom, locTo, magnitude));
            }
        }
    }

    Map<String, ConvenienceVector> getVectorX() {
        return vectors;
    }
}
