package co.mcsky.rocketlauncher;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import static co.mcsky.rocketlauncher.VectorManager.ACTION.FROM;
import static co.mcsky.rocketlauncher.VectorManager.ACTION.TO;

class VectorManagerCommand implements CommandExecutor {

    private final VectorManager vectorManager;
    private final RocketLauncher plugin;
    private BukkitTask rocketTrigger;

    VectorManagerCommand(VectorManager vectorManager, RocketLauncher plugin) {
        this.vectorManager = vectorManager;
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) return true;

        Player player = ((Player) sender).getPlayer();

        if (args.length < 1 || args.length > 3) return false;

        if (args[0].equalsIgnoreCase("add")) {
            if (args.length != 3) return false;
            if (args[2].equalsIgnoreCase("from")) {
                vectorManager.setVector(args[1], player, FROM);
            } else if (args[2].equalsIgnoreCase("to")) {
                vectorManager.setVector(args[1], player, TO);
            }
            return true;

        } else if (args[0].equalsIgnoreCase("remove")) {
            if (args.length != 2) return false;
            vectorManager.removeVector(args[1], player);
            return true;

        } else if (args[0].equalsIgnoreCase("list")) {
            vectorManager.listVector(player);
            return true;

        } else if (args[0].equalsIgnoreCase("toggle")) {
            if (rocketTrigger == null || rocketTrigger.isCancelled()) {
                if (args.length != 2) return false;

                int period;
                try {
                    period = Integer.parseInt(args[1]);
                } catch (NumberFormatException e) {
                    player.sendMessage(e.getCause().getMessage());
                    return true;
                }

                rocketTrigger = new RocketTrigger(plugin, player).runTaskTimer(plugin, 0, period);
                player.sendMessage("弹射任务已开始, 扫描间隔: " + period + " ticks");
                return true;

            } else {
                rocketTrigger.cancel();
                player.sendMessage("弹射任务已终止.");
                return true;

            }

        } else if (args[0].equalsIgnoreCase("tp")) {
            return vectorManager.teleportTo(args[1], player);

        }

        return false;
    }

}
