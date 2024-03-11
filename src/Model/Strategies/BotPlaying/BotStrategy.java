package Model.Strategies.BotPlaying;

import Model.Board;
import Model.Pair;

public interface BotStrategy {
    Pair<Integer,Integer> getCellRowCol(Board board);
}
