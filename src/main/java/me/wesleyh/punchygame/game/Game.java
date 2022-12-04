package me.wesleyh.punchygame.game;

import me.wesleyh.punchygame.PunchyGame;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameRule;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;

public class Game {

    ArrayList<Player> players = new ArrayList<>();
    public GameState state = GameState.WAITING;
    int numPlayersNeeded = 2;
    public String name;
    int id;
    public HashMap<Player, Integer> scores = new HashMap<>();

    public Game(Player player, String name){
        this.name = name;
        players.add(player);
        GameManager.addNewGame(this);
        createGameWorld();
        scores.put(player, 0);
        player.setBedSpawnLocation(new Location(player.getWorld(), (Math.random() * 19) -9, 50, (Math.random() * 19) -9), true);
        checkGameState();
    }
    public Game(ArrayList<Player> players, String name){
        this.name = name;
        this.players = players;
        GameManager.addNewGame(this);
        createGameWorld();
        for (Player p: players){
            scores.put(p, 0);
            p.setBedSpawnLocation(new Location(p.getWorld(), (Math.random() * 19) -9, 50, (Math.random() * 19) -9), true);
        }
        checkGameState();
    }
    public void checkGameState() {
        if (state == GameState.WAITING){
            if (players.size() >= numPlayersNeeded){
                Bukkit.broadcastMessage("Game starting in 5 seconds...");
                Bukkit.getScheduler().runTaskLater(PunchyGame.getInstance(), () -> {
                    Bukkit.broadcastMessage("Game started!");
                    state = GameState.RUNNING;
                }, 100L);
            }
        }
    }
    public void createGameWorld() {
        if (!PunchyGame.core.getMVWorldManager().isMVWorld("Game-" + name)){
            PunchyGame.core.getMVWorldManager().cloneWorld("Arena", "Game-" + name);
            PunchyGame.core.getMVWorldManager().getMVWorld("Game-" + name).getCBWorld().setGameRule(GameRule.SHOW_DEATH_MESSAGES, false);
            for (Player p: players){
                p.teleport(new Location(PunchyGame.core.getMVWorldManager().getMVWorld("Game-" + name).getCBWorld(), 0, 50, 0));
            }
        }

    }
    public void joinGame(Player player){
        players.add(player);
        player.teleport(new Location(PunchyGame.core.getMVWorldManager().getMVWorld("Game-" + name).getCBWorld(), 0, 50, 0));
        scores.put(player, 0);
        player.setBedSpawnLocation(new Location(player.getWorld(), (Math.random() * 19) -9, 50, (Math.random() * 19) -9), true);
        checkGameState();
    }

    public boolean isInGame(Player player){
        return players.contains(player);
    }

    public void incScore(Player p) {
        scores.put(p, scores.get(p) + 1);
        if (scores.get(p) > 19){
            Bukkit.broadcastMessage(ChatColor.GREEN + p.getName() + " has won in Arena " + name + "!");
            GameManager.endGame(this);

        }
    }

}
