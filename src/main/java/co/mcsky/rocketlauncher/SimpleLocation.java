package co.mcsky.rocketlauncher;

import org.bukkit.Location;
import org.bukkit.World;

/**
 * 这是一个没有 Pitch 和 Yaw 的 Location 类
 *
 * 主要用于 Pitch 和 Yaw 不重要的时候，方便进行比较
 */
public class SimpleLocation {
    private World world;
    private int x;
    private int y;
    private int z;
    private int multiplier;

    public SimpleLocation(Location location) {
        this.world = location.getWorld();
        this.x = (int) location.getX();
        this.y = (int) location.getY();
        this.z = (int) location.getZ();
    }

    public SimpleLocation(Location location, int multiplier) {
        this.world = location.getWorld();
        this.x = (int) location.getX();
        this.y = (int) location.getY();
        this.z = (int) location.getZ();
        this.multiplier = multiplier;
    }

    Location toLoc() {
        return new Location(world, x, y, z);
    }

    int getMultiplier() {
        return multiplier;
    }

    @Override
    public String toString() {
        return "(" + world + ", " + x + ", " + y + ", " + z + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof SimpleLocation)) {
            return false;
        }

        SimpleLocation otherLoc = (SimpleLocation) obj;

        if (this.world != otherLoc.world) {
            return false;
        }
        if (this.x != otherLoc.x) {
            return false;
        }
        if (this.y != otherLoc.y) {
            return false;
        }
        if (this.z != otherLoc.z) {
            return false;
        }
        return true;
    }
}
