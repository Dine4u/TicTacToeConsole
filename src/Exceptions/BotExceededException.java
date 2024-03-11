package Exceptions;

public class BotExceededException extends RuntimeException{
    private String message;
    public BotExceededException(String message){
        this.message=message;
    }
}
