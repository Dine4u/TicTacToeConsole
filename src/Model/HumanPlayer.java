package Model;

import java.util.Scanner;

public class HumanPlayer extends Player{

    private int undoLimit;
    public HumanPlayer(String name, Symbol symbol) {
        super(name, symbol);
    }

    @Override
    public Pair<Integer, Integer> getCellRowCol(Board board) {//board parameter is for BotPlayer, we dont use in Humanplayer
        Scanner scn=new Scanner(System.in);
        int row= scn.nextInt();
        int col=scn.nextInt();
        Pair<Integer,Integer> pair=new Pair<>(row,col);
        return pair;
    }

    public void setUndoLimt(int undoLimit) {
        this.undoLimit=undoLimit;
    }

    public Integer getUndoLimit(){
        return this.undoLimit;
    }

    public void decrementUndoLimit() {
        this.undoLimit--;
    }
}
