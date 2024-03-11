import Controller.GameController;
import Model.Game;
import Model.GameStatus;
import Model.Player;
import Model.PlayerType;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        GameController gameController=new GameController();

        Scanner scn=new Scanner(System.in);
        System.out.println("How many human players? ");
        int numOfHumanPlayers=scn.nextInt();

        List<Player> players=new ArrayList<>();
        HashSet<Character> symbols=new HashSet<>();
        for(int i=1;i<=numOfHumanPlayers;i++){
            System.out.println("Enter the Player" +i+ " name: ");
            String name=scn.next();
            System.out.println("Enter a new symbol: ");
            String symbolStr=scn.next();
            Character symbol=symbolStr.charAt(0);
            while(symbols.contains(symbol)){
                System.out.println("Please enter different symbol: ");
                symbolStr=scn.next();
                symbol=symbolStr.charAt(0);
            }
            symbols.add(symbol);
            players.add(gameController.createPlayer(PlayerType.Human,name,symbol,null));
        }

        System.out.println("How many bots? ");
        int numOfBots=scn.nextInt();
        String botSymbols="abcdefghijklmnopqrstuvwxyz";
        int botSymbolIndex=0;
        for(int i=1;i<=numOfBots;i++){
            Character symbol=botSymbols.charAt(botSymbolIndex);
            while(symbols.contains(symbol)){
                botSymbolIndex+=1;
                symbol=botSymbols.charAt(botSymbolIndex);
            }
            symbols.add(symbol);
            System.out.println("Please select Bot level(E/M/H): ");
            String botLevelStr=scn.next();
            players.add(gameController.createPlayer(PlayerType.Bot,"Bot"+i ,symbol,botLevelStr));
        }

        System.out.println("Enter Number of undos a player can do");
        int numOfUndos = scn.nextInt();

        Game game = gameController.createGame(players,numOfUndos);
        while (game.getGameStatus()== GameStatus.INPROGRESS){
            gameController.print(game);
            //use controller class to communicate with model classes
            gameController.makeMove(game);
//            game.checkWin();//doing this checks in Game class itself
//            game.checkDraw();//doing this checks in Game class itself
//            gameController.undo(game);//its bug to add win check and draw check in makemove() since if we undo after makemove(), then gamestatus changes to won in makemove() itself and undo dont reflect/work. -> To resolve shifted undo() from client to makeMove()
        }

        System.out.println("Do you want re-watch the game?(Y/N)");
        String reWatch= scn.next();

        if(reWatch.charAt(0) == 'y' || reWatch.charAt(0) == 'Y'){
            gameController.replay(game);
        }

    }
}
