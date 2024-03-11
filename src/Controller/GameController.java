package Controller;

import Model.*;

import java.util.List;

public class GameController {
    public Game createGame(List<Player> players,int undoLimit){
        return Game.getBuilder().setPlayers(players).setUndoLimit(undoLimit).build();
    }

    public Player createPlayer(PlayerType playerType,String name, Character symbol,String botLevelStr) {
        if(playerType==PlayerType.Human){
            return new HumanPlayer(name,new Symbol(symbol));
        }
        //Botlevel is part of Business logic, so can be included in Service class
        BotLevel botLevel=BotLevel.EASY;;
        if(botLevelStr.charAt(0)=='M'){
            botLevel=BotLevel.MEDIUM;
        } else if (botLevelStr.charAt(0)=='H') {
            botLevel=BotLevel.HARD;
        }

        return new BotPlayer(name,new Symbol(symbol),botLevel);
    }

    public void print(Game game) {
        game.printBoard();
    }

    public Player getGamePlayers(Game game) {
        return game.getPlayers().get(game.getCurrentPlayerIndex());
    }

    public void makeMove(Game game) {
        game.makeMove();
    }

    public GameStatus getGameStatus(Game game) {
        return game.getGameStatus();
    }

//    public void undo(Game game) {
//        game.undo();
//    }

    public void replay(Game game) {
        game.replay();
    }
}
