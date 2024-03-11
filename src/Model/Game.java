package Model;

import Exceptions.BotExceededException;
import Model.Strategies.WinCheck.TC_OrderOneWinningStrategy;
import Model.Strategies.WinCheck.WinningStrategies;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Game {
    private Board board;
    private List<Player> players;
    private int currentPlayerIndex;
    private List<Action> actions;
    private GameStatus gameStatus;
    private WinningStrategies winningStatus;

    private Game(List<Player> players,int boardSize,int currentPlayerIndex,List<Action> actions, GameStatus gameStatus,WinningStrategies winningStatus,int undoLimitPerPlayer){
        this.players=players;
        this.board=createBoard(boardSize);
        this.currentPlayerIndex=currentPlayerIndex;
        this.actions=actions;
        this.gameStatus=gameStatus;
        this.winningStatus=winningStatus;
        set(undoLimitPerPlayer);
    }

    public void set(int undoLimitPerPlayer){
        for(Player player:this.players){
            if(player instanceof HumanPlayer){
                HumanPlayer humanPlayer=(HumanPlayer) player;//typecasting to access Humanplayer specific methods and attributes - undoLimit is human specific attribute
                humanPlayer.setUndoLimt(undoLimitPerPlayer);
            }
        }
    }

    public Board getBoard() {
        return board;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

    public List<Action> getActions() {
        return actions;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public Board createBoard(int boardSize){
        return new Board(boardSize);
    }

    public void printBoard(){
        this.board.print();
    }

    public void makeMove() {
        Player player= players.get(this.currentPlayerIndex);
        System.out.println(player.getName()+"'s move");
        Pair<Integer,Integer> cellRowColToBeSet=player.getCellRowCol(this.board);
        int row=cellRowColToBeSet.getRow();
        int col=cellRowColToBeSet.getCol();
        Cell currentCell = this.board.getCell(row, col);
        while(currentCell.getCellstatus()==CellStatus.OCCUPIED){
            System.out.println("Selected Cell is occupied. please select different cell");
            cellRowColToBeSet=player.getCellRowCol(this.board);
            row=cellRowColToBeSet.getRow();
            col=cellRowColToBeSet.getCol();
            currentCell = this.board.getCell(row, col);
        }
        this.board.setCell(row, col,currentCell,player);
        Action action=new Action(currentCell,player);
        this.actions.add(action);
//        this.doUndo();//its bug to add win check and draw check in makemove() since if we undo after makemove(), then gamestatus changes to won in makemove() itself and undo dont reflect/work. -> To resolve shifted undo() from client to makeMove()
        if(player instanceof HumanPlayer){
            HumanPlayer humanPlayer = (HumanPlayer) player;
            if(humanPlayer.getUndoLimit() > 0){
                System.out.println("Do you want to undo? (y/n)");
                Scanner scanner = new Scanner(System.in);
                String input = scanner.next();
                if (input.charAt(0) == 'y' || input.charAt(0) == 'Y'){
                    this.doUndo();
                    System.out.println("We have successfully undoed " + player.getName() + "'s last move");
                    humanPlayer.decrementUndoLimit();
                    if(humanPlayer.getUndoLimit() == 1){
                        System.out.println("One more Undo left for "+humanPlayer.getName());
                    }
                    return;
                }
            }
        }

        this.checkWin(currentCell);
        this.checkDraw();
        this.currentPlayerIndex=(this.currentPlayerIndex+1)%this.players.size();
    }

    public void checkDraw() {
        if(this.actions.size()==((this.players.size()+1)*(this.players.size()+1))){
            this.gameStatus=GameStatus.DRAWN;
            System.out.println("Game Drawn");
        }
    }

    public void checkWin(Cell cell) {
        if(winningStatus.checkWin(this.board,cell)){
            this.gameStatus=GameStatus.WON;
            System.out.println("Game Won by "+cell.getPlayer().getName());
            printBoard();
        }
    }

    public void doUndo() {
        Action action = actions.removeLast();//similar to actions.remove(actions.size() - 1);
        Cell cell = action.getCell();
        cell.removePlayer();
//        winningStatus.handleUndo(action);//not needed since we doing checkwin() after undo operatio, so addition didn't happen in checkwin HashMaps
    }

    public void replay() {
        this.board.reset();
        for(Action action:actions){
            Cell currentCell=action.getCell();
            Player currentPlayer=action.getPlayer();
            this.board.setCell(currentCell.getRow(),currentCell.getCol(),currentCell,currentPlayer);
            System.out.println("player " + currentPlayer.getName() + " makes a move");
            this.printBoard();
        }
    }

    public static GameBuilder getBuilder(){
        return new GameBuilder();
    }

    public static class GameBuilder{
        private List<Player> players;

        private int undoLimitPerPlayer;

        public GameBuilder setPlayers(List<Player> players) {
            this.players = players;
            return this;
        }

        public GameBuilder setUndoLimit(int undoLimit){
            this.undoLimitPerPlayer = undoLimit;
            return this;
        }

        public Game build(){
            int botCount=0;
            for(Player player:this.players){
                if(player instanceof BotPlayer){
                    botCount++;
                    if(botCount>1){
                        throw new BotExceededException("Bot limit Exceeded than 1");
                    }
                }
            }

            int boardSize = this.players.size() + 1;
            int currentPlayerIndex = 0;
            List<Action> actions = new ArrayList<>();
            GameStatus gameStatus = GameStatus.INPROGRESS;

            return new Game(this.players,boardSize,currentPlayerIndex,actions,gameStatus,new TC_OrderOneWinningStrategy(boardSize),this.undoLimitPerPlayer);
        }
    }
}
