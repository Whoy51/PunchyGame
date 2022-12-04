package me.wesleyh.punchygame.game;

import me.wesleyh.punchygame.PunchyGame;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class GameManager {
    public static ArrayList<Game> games = new ArrayList<>();

    public static void addNewGame(Game game) {
        games.add(game);
        updateIds();
    }
    public static void endGame(Game game){
        game.players.forEach(player -> player.teleport(PunchyGame.core.getMVWorldManager().getMVWorld("world").getCBWorld().getSpawnLocation()));
        games.remove(game);
        PunchyGame.core.getMVWorldManager().unloadWorld("Game-" + game.name);
        PunchyGame.core.getMVWorldManager().deleteWorld("Game-" + game.name);
        updateIds();
    }
    public static boolean isInAGame(Player player){
        for (Game game: games){
            if (game.isInGame(player)) return true;
        }
        return  false;
    }

    public static void updateIds(){
        for (Game game: games){
            game.id = games.indexOf(game);
        }
    }

    public static Game getGameById(int id){
        for (Game game: games){
            if (game.id == id) return game;
        }
        return null;
    }
    public static Game getGameByName(String name){
        for (Game game: games){
            if (game.name.equalsIgnoreCase(name)) return game;
        }
        return null;
    }
    public static String outputGames(){
        return "Open games: " + games.size() + " /nGames: " + games;
    }

    public static Game getGameByPlayer(Player player){
        for (Game g: games){
            for (Player p: g.players){
                if (p.equals(player)){
                    return g;
                }
            }
        }
        return null;
    }

}
