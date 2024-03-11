package Model.Strategies.WinCheck;

import Model.Action;
import Model.Board;
import Model.Cell;

//Strategy Design Pattern
public interface WinningStrategies {
    boolean checkWin(Board board, Cell cell);

//    void handleUndo(Action action);//not needed since we doing checkwin() after undo operatio, so addition didn't happen in checkwin HashMaps
}
