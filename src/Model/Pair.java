package Model;

public class Pair<U,V> {
    private U row;
    private V col;

    public Pair(U row, V col) {
        this.row = row;
        this.col = col;
    }

    public U getRow() {
        return row;
    }

    public void setRow(U row) {
        this.row = row;
    }

    public V getCol() {
        return col;
    }

    public void setCol(V col) {
        this.col = col;
    }
}
