package Model.Strategies.WinCheck;

import Model.Action;
import Model.Board;
import Model.Cell;
import Model.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TC_OrderOneWinningStrategy implements WinningStrategies{
    private List<HashMap<Character,Integer>> rowsList;
    private List<HashMap<Character,Integer>> colsList;
    private HashMap<Character,Integer> diagonalCheck;
    private HashMap<Character,Integer> reverseDiagonalCheck;
    private int boardSize;

    public TC_OrderOneWinningStrategy(int boardSize){
        this.rowsList=new ArrayList<>();
        this.colsList=new ArrayList<>();
        for(int i=0;i<boardSize;i++){
            this.rowsList.add(new HashMap<>());
            this.colsList.add(new HashMap<>());
        }
        this.diagonalCheck=new HashMap<>();
        this.reverseDiagonalCheck=new HashMap<>();
        this.boardSize=boardSize;
    }
    @Override
    public boolean checkWin(Board board, Cell currentCell) {
        int rowToBeChecked=currentCell.getRow();
        int colToBeChecked= currentCell.getCol();
        Player player=currentCell.getPlayer();
        char symbol=player.getSymbol().getSymbolChar();

        //Row Check
        HashMap<Character, Integer> rowForCurrentCell = this.rowsList.get(rowToBeChecked);//returns the specific cell row
        rowForCurrentCell.put(symbol,rowForCurrentCell.getOrDefault(symbol,0)+1 );
        if(rowForCurrentCell.get(symbol)==board.getBoardSize()){
            return true;
        }

        //Column check
        HashMap<Character, Integer> colForCurrentCell = this.colsList.get(colToBeChecked);//returns the specific cell col
        colForCurrentCell.put(symbol,colForCurrentCell.getOrDefault(symbol,0)+1 );
        if(colForCurrentCell.get(symbol)==board.getBoardSize()){
            return true;
        }

        //Diagonal Check
        if(isDiagonalCell(rowToBeChecked, colToBeChecked)){
            this.diagonalCheck.put(symbol,this.diagonalCheck.getOrDefault(symbol,0)+1 );
            if(this.diagonalCheck.get(symbol)==board.getBoardSize()){
                return true;
            }
        }

        //ReverseDiagonal Check
        if(isReverseDiagonalCell(rowToBeChecked, colToBeChecked,board.getBoardSize())){
            this.reverseDiagonalCheck.put(symbol,this.reverseDiagonalCheck.getOrDefault(symbol,0)+1 );

            if(this.reverseDiagonalCheck.get(symbol)==board.getBoardSize()){
                return true;
            }
        }
        return false;
    }

    private boolean isDiagonalCell(int rowToBeChecked,int colToBeChecked) {
        if(rowToBeChecked==colToBeChecked){
            return true;
        }
        return false;
    }

    private boolean isReverseDiagonalCell(int rowToBeChecked, int colToBeChecked,int boardSize) {
        if((rowToBeChecked+colToBeChecked)==(boardSize-1)){
            return true;
        }
        return false;
    }

    //not needed since we doing checkwin() after undo operatio, so addition didn't happen in checkwin HashMaps
//    @Override
//    public void handleUndo(Action action) {
//        Cell currentCell = action.getCell();
//        int rowToBeUndoed = currentCell.getRow();
//        int colToBeUndoed = currentCell.getCol();
//
//        Player player = action.getPlayer();
//        char symbol = player.getSymbol().getSymbolChar();
//
//        HashMap<Character, Integer> rowForCurrentCell = this.rowsList.get(rowToBeUndoed);
//        if(rowForCurrentCell.containsKey(symbol)){
//            rowForCurrentCell.put(symbol, rowForCurrentCell.get(symbol) - 1);
//        }
//
//
//        HashMap<Character, Integer> colForCurrentCell = this.colsList.get(colToBeUndoed);
//        if(colForCurrentCell.containsKey(symbol)){
//            colForCurrentCell.put(symbol, colForCurrentCell.get(symbol) - 1);
//        }
//
//        if(isDiagonalCell(rowToBeUndoed, colToBeUndoed)){
//            if(this.diagonalCheck.containsKey(symbol)){
//                this.diagonalCheck.put(symbol,this.diagonalCheck.get(symbol) - 1 );
//            }
//        }
//
//        if(isReverseDiagonalCell(rowToBeUndoed, colToBeUndoed, this.boardSize)){
//            if(this.reverseDiagonalCheck.containsKey(symbol)) {
//                this.reverseDiagonalCheck.put(symbol, reverseDiagonalCheck.get(symbol) - 1);
//            }
//        }
//    }
}
