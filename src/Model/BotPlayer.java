package Model;

import Model.Factories.BotStrategyFactory;
import Model.Strategies.BotPlaying.BotStrategy;

public class BotPlayer extends Player{
    private BotLevel botLevel;
    private BotStrategy botStrategy;

    public BotPlayer(String name, Symbol symbol,BotLevel botLevel) {
        super(name, symbol);
        this.botLevel=botLevel;
        this.botStrategy= BotStrategyFactory.getBotPlayStrategy(this.botLevel);
    }

    @Override
    public Pair<Integer, Integer> getCellRowCol(Board board) {
        return this.botStrategy.getCellRowCol(board);
    }
}
