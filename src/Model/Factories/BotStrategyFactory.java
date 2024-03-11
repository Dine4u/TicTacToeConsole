package Model.Factories;

import Model.BotLevel;
import Model.Strategies.BotPlaying.BotStrategy;
import Model.Strategies.BotPlaying.EasyBotPlayStrategy;
import Model.Strategies.BotPlaying.HardBotPlayStrategy;
import Model.Strategies.BotPlaying.MediumBotPlayStrategy;

public class BotStrategyFactory {
    public static BotStrategy getBotPlayStrategy(BotLevel botLevel){
        if(botLevel==BotLevel.EASY){
            return new EasyBotPlayStrategy();
        } else if(botLevel==BotLevel.MEDIUM){
            return new MediumBotPlayStrategy();
        } else if(botLevel==BotLevel.HARD){
            return new HardBotPlayStrategy();
        } else {
            return new EasyBotPlayStrategy();
        }
    }
}
