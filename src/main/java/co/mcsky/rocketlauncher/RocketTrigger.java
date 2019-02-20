package co.mcsky.rocketlauncher;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.Map;
import java.util.logging.Level;

class RocketTrigger extends BukkitRunnable {
    private final RocketLauncher plugin;
    private final Map<String, ConvenienceVector> vectors;
    private final Player player;

    RocketTrigger(RocketLauncher plugin, Player player) {
        this.plugin = plugin;
        this.player = player;

        // Must do loadVectors() first, so data can be assigned to vectors properly
        this.plugin.loadVectors();
        vectors = plugin.getVectorX();
        Bukkit.getLogger().info(vectors.size() + " 个弹射点已加载成功.");
    }

    @Override
    public void run() {
        if (vectors.size() == 0) {
            Bukkit.getLogger().log(Level.SEVERE, "没有找到坐标, 弹射任务取消.");
            player.sendMessage(ChatColor.RED + "没有找到坐标, 弹射任务取消.");
            this.cancel();
            return;
        }

        for (final Player p : Bukkit.getOnlinePlayers()) {
            /* `FROM` Location*/
            Location locP = p.getLocation().toCenterLocation();
            locP.setPitch(0);
            locP.setYaw(0);

            for (ConvenienceVector v : vectors.values()) {
                if (v.isSame(locP)) {
                    Location locTo = v.getLocTo();
                    int magnitude = v.getMagnitude();

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
                            if (count > 10 && p.isOnGround()) {
                                this.cancel();
                                return;
                            }
                            count++;
                            if (count > 50) this.cancel();
                        }
                    }.runTaskTimer(plugin, 0, 2);

                    p.sendMessage("飞翔的感觉真好! (起飞速度: " + magnitude + ")");
                }
            }
        }
    }
}
