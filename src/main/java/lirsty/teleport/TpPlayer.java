package lirsty.teleport;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;

import static org.bukkit.Bukkit.getServer;

public class TpPlayer {
    private  HandleMem handleMem = new HandleMem();
    private  Map<Player, Timer> TimerMap = new HashMap<>();
    public boolean handle(Command command, CommandSender sender, String[] args) {
        if (command.getName().equals("tpa")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                Player targetPlayer = getServer().getPlayer(args[0]);
                if (player.equals(targetPlayer)) {
                    player.sendMessage("我知道你沒有朋友，但是這裡沒辦法tp自己");
                    return true;
                }
                Map<Player, Player> TpaMap = handleMem.getTpaMap();
                Map<Player, Player> TpaHereMap = handleMem.getTpaMap();
                if (targetPlayer != null) {
                    if (TpaMap.containsKey(player) || TpaHereMap.containsKey(player)) {
                        player.sendMessage("請求失敗:你目前已經有一個傳送請求");
                        return true;
                    }
                    if (TpaMap.containsKey(targetPlayer) || TpaHereMap.containsKey(targetPlayer)) {
                        player.sendMessage("請求失敗:對方目前已經有一個傳送請求");
                        return true;
                    }
                    String name = player.getName();
                    player.sendMessage("發出了傳送請求給" + targetPlayer.getName());
                    targetPlayer.sendMessage(name + "想傳送到你的位置");
                    TpaMap.put(targetPlayer, player);
                    Timer timer = new Timer();
                    TimerMap.put(targetPlayer, timer);
                    TpRequestClearer clearer = new TpRequestClearer();
                    clearer.setPlayer(targetPlayer);
                    clearer.setMap(handleMem);
                    timer.schedule(clearer, 20000);
                } else {
                    player.sendMessage("該玩家不在線上");
                }
            }
            return true;
        }

        if (command.getName().equals("tpahere")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                Player targetPlayer = getServer().getPlayer(args[0]);
                if (player.equals(targetPlayer)) {
                    player.sendMessage("我知道你沒有朋友，但是這裡沒辦法tp自己");
                    return true;
                }
                Map<Player, Player> TpaMap = handleMem.getTpaHereMap();
                Map<Player, Player> TpaHereMap = handleMem.getTpaHereMap();
                if (targetPlayer != null) {
                    if (TpaMap.containsKey(player) || TpaHereMap.containsKey(player)) {
                        player.sendMessage("請求失敗:你目前已經有一個傳送請求");
                        return true;
                    }
                    if (TpaMap.containsKey(targetPlayer) || TpaHereMap.containsKey(targetPlayer)) {
                        player.sendMessage("請求失敗:對方目前已經有一個傳送請求");
                        return true;
                    }
                    String name = player.getName();
                    player.sendMessage("發出了傳送請求給" + targetPlayer.getName());
                    targetPlayer.sendMessage(name + "想傳送你至 他 的位置");
                    TpaHereMap.put(targetPlayer, player);
                    Timer timer = new Timer();
                    TimerMap.put(targetPlayer, timer);
                    TpRequestClearer clearer = new TpRequestClearer();
                    clearer.setPlayer(targetPlayer);
                    clearer.setMap(handleMem);
                    timer.schedule(clearer, 20000);
                } else {
                    player.sendMessage("該玩家不在線上");
                }
            }
            return true;
        }

        if (command.getName().equals("tok")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                Map<Player, Player> TpaMap = handleMem.getTpaMap();
                Map<Player, Player> TpaHereMap = handleMem.getTpaMap();
                // tpa
                Player targetPlayer = TpaMap.get(player);
                if (targetPlayer != null) {
                    player.sendMessage("你接受了傳送請求");
                    targetPlayer.teleportAsync(player.getLocation());
                    TpaMap.remove(player);
                    TimerMap.get(player).cancel();
                    return true;
                } else {
                    // tpahere
                    targetPlayer = TpaHereMap.get(player);
                    if (targetPlayer != null) {
                        player.sendMessage("你接受了傳送請求");
                        player.teleportAsync(targetPlayer.getLocation());
                        TpaHereMap.remove(player);
                        TimerMap.get(player).cancel();
                        return true;
                    }
                }
                player.sendMessage("目前沒有傳送請求");
            }
        }
        return false;
    }

    public class HandleMem{
        private Map<Player, Player> TpaMap = new HashMap<>();
        private  Map<Player, Player> TpaHereMap = new HashMap<>();
        public Map<Player, Player> getTpaMap() { return  this.TpaMap; }
        public Map<Player, Player> getTpaHereMap() { return  this.TpaHereMap; }
    }
}
