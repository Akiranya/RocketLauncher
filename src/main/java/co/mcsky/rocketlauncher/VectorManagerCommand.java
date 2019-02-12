package co.mcsky.rocketlauncher;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

public class VectorManagerCommand implements CommandExecutor {

    private final VectorManager vectorManager;
    private RocketLauncher plugin;
    private BukkitTask rocketTrigger;

    public VectorManagerCommand(VectorManager vectorManager, RocketLauncher plugin) {
        this.vectorManager = vectorManager;
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            return true;
        }

        Player player = ((Player) sender).getPlayer();

        if (args.length < 1 || args.length > 3) {
            return false;
        }

        if (args[0].equalsIgnoreCase("add")) {
            if (args.length != 3) return false;
            if (args[2].equalsIgnoreCase("from")) {
                vectorManager.setVector(args[1], player, ACTION.FROM);
            } else if (args[2].equalsIgnoreCase("to")) {
                vectorManager.setVector(args[1], player, ACTION.TO);
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
                rocketTrigger = new RocketTrigger(plugin).runTaskTimer(plugin, 0, 10);
                player.sendMessage("Rocket trigger task has started.");
                return true;
            } else {
                rocketTrigger.cancel();
                player.sendMessage("Rocket trigger task has been cancelled.");
                return true;
            }
        }
        // TODO Add support to teleport to specified jump point
        return false;
    }
}
