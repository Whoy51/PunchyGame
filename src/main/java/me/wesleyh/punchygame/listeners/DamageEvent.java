package me.wesleyh.punchygame.listeners;

import me.wesleyh.punchygame.PunchyGame;
import me.wesleyh.punchygame.game.GameManager;
import me.wesleyh.punchygame.game.GameState;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.util.Vector;

import java.sql.Time;
import java.util.Timer;


public class DamageEvent implements Listener {

    @EventHandler
    public void onDamageEvent(EntityDamageByEntityEvent e){
        if (e.getEntity() instanceof Player victim && e.getDamager() instanceof Player player && GameManager.isInAGame(victim) && GameManager.isInAGame(player) && GameManager.getGameByPlayer(player).state == GameState.RUNNING) {
            victim.setHealth(0.0);
            victim.setVelocity(new Vector(0, 0 ,0));
            victim.spigot().respawn();
//            victim.teleport(new Location(victim.getWorld(), (int) (Math.random() * 19) -9, 50, (int) (Math.random() * 19) -9));
            GameManager.getGameByPlayer(player).incScore(player);
            victim.setBedSpawnLocation(new Location(victim.getWorld(), (Math.random() * 19) -9, 50, (Math.random() * 19) -9), true);
            player.sendMessage("Your score is : " +GameManager.getGameByPlayer(player).scores.get(player));
        }

    }
}
