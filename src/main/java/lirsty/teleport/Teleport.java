package lirsty.teleport;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;


public final class Teleport extends JavaPlugin {

    private TpPlayer HandleTpCommand;

    @Override
    public void onEnable() {
        this.HandleTpCommand = new TpPlayer();
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (HandleTpCommand.handle(command,sender,args)) { return true; }

        return true;
    }
    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
