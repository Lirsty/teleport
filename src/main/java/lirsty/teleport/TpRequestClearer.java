package lirsty.teleport;


import org.bukkit.entity.Player;

import java.util.Map;
import java.util.TimerTask;

public class TpRequestClearer extends TimerTask {

    private Player player;
    private TpPlayer.HandleMem handleMem;

    public void run() {
        Map<Player, Player> TpaMap = handleMem.getTpaMap();
        Map<Player, Player> TpaHereMap = handleMem.getTpaMap();
        if  (TpaMap.containsKey(player)) {
            Player targetPlayer = TpaMap.get(player);
            player.sendMessage("來自"+targetPlayer.getName()+"的傳送請求已過期");
            targetPlayer.sendMessage("你的傳送請求已過期");
            TpaMap.remove(player);
            TpaHereMap.remove(player);
            return;
        }
        if  (TpaHereMap.containsKey(player)) {
            Player targetPlayer = TpaHereMap.get(player);
            player.sendMessage("來自"+targetPlayer.getName()+"的傳送請求已過期");
            targetPlayer.sendMessage("你的傳送請求已過期");
            TpaMap.remove(player);
            TpaHereMap.remove(player);
            return;
        }
    }

    public void setMap(TpPlayer.HandleMem handleMem) {
        this.handleMem = handleMem;
    }

    public void setPlayer(Player p) {
        player = p;
    }
}