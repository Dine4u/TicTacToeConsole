package Model;

public class Action {
    private Cell cell;
    private Player player;
    public Action(Cell cell, Player player) {
        this.cell = cell;
        this.player = player;
    }

    public Cell getCell() {
        return cell;
    }

    public Player getPlayer() {
        return player;
    }
}
