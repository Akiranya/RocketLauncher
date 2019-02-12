package co.mcsky.rocketlauncher;

import org.bukkit.Location;

/**
 * 坐标，以及对应的模长
 */
public class MagLocation {
    private Location location;
    private int magnitude;

    public MagLocation(Location location, int magnitude) {
        this.location = location;
        this.magnitude = magnitude;
    }

    Location getLoc() {
        return location;
    }

    int getMagnitude() {
        return magnitude;
    }
}
