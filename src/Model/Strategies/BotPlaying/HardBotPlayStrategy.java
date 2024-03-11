package Model.Strategies.BotPlaying;

import Exceptions.BotPlayExecption;
import Model.Board;
import Model.Cell;
import Model.CellStatus;
import Model.Pair;

public class HardBotPlayStrategy implements BotStrategy{
    @Override
    public Pair<Integer, Integer> getCellRowCol(Board board) {
        //Used same EasyBot logic, try ChatGPT api to generate row and col for Hard Strategy
        Cell cell=null;
        for(int i=0;i< board.getBoardSize();i++){
            for(int j=0;j< board.getBoardSize();j++){
                cell=board.getCell(i,j);
                if(cell.getCellstatus()== CellStatus.UNOCCUPIED){
                    return new Pair<>(i,j);
                }
            }
        }
        throw new BotPlayExecption("No place for bot to make a move");
    }
}
