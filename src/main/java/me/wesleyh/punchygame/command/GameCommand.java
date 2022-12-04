package me.wesleyh.punchygame.command;

import me.wesleyh.punchygame.game.Game;
import me.wesleyh.punchygame.game.GameManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GameCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player player){
            if (args.length > 0){
                if (args[0].equalsIgnoreCase("create") && args.length > 1){
                    if (GameManager.games.contains(GameManager.getGameByName(args[1]))) {
                        Game game = new Game(player, args[1]);
                        player.sendMessage("You created a game!");
                    } else {
                        player.sendMessage("This game already exists");
                    }
                    Game game = new Game(player, args[1]);
                }
                else if (args[0].equalsIgnoreCase("join") && args.length > 1){
                    if (GameManager.games.contains(GameManager.getGameByName(args[1]))) {
                        GameManager.getGameByName(args[1]).joinGame(player);
                        player.sendMessage("You joined " + GameManager.getGameByName(args[1]).name);
                    } else {
                        player.sendMessage("This game does not exist");
                    }
                }
                else if (args[0].equalsIgnoreCase("end") && args.length > 1){
                    if (GameManager.games.contains(GameManager.getGameByName(args[1]))) {
                        GameManager.endGame(GameManager.getGameByName(args[1]));
                        player.sendMessage("Game over!");
                    } else {
                        player.sendMessage("This game does not exist");
                    }
                }
                else if (args[0].equalsIgnoreCase("list")){
                    player.sendMessage(GameManager.outputGames());
                } else{
                    player.sendMessage("Invalid arguments");
                    return false;
                }
                return true;
            }else {
                player.sendMessage("Invalid arguments");
            }
        }
        return false;
    }
}
