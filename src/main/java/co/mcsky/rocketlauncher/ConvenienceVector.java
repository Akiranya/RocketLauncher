package co.mcsky.rocketlauncher;

import org.bukkit.Location;

class ConvenienceVector {
    private final Location locFrom;
    private final Location locTo;
    private final int magnitude;

    public ConvenienceVector(Location locFrom, Location locTo, int magnitude) {
        this.locFrom = locFrom;
        this.locTo = locTo;
        this.magnitude = magnitude;
    }

    boolean isSame(Location location) {
        return location.equals(locFrom);
    }

    public Location getLocFrom() {
        return locFrom;
    }

    Location getLocTo() {
        return locTo;
    }

    int getMagnitude() {
        return magnitude;
    }
}
