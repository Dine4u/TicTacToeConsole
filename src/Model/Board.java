package Model;


import java.util.ArrayList;
import java.util.List;

public class Board {

    private int boardSize;
    private List<List<Cell>> board= new ArrayList<>();

    public Board(int boardSize){
        this.boardSize=boardSize;
        for(int i=0;i<boardSize;i++){
            List<Cell> row=new ArrayList<>();
            for(int j=0;j<boardSize;j++){
                row.add(new Cell(i,j));
            }
            this.board.add(row);
        }
    }

    public void setCell(int row,int col,Cell currentCell,Player player) {
        currentCell.setRow(row);
        currentCell.setCol(col);
        currentCell.setPlayer(player);
        currentCell.setCellstatus(CellStatus.OCCUPIED);
    }

    public Cell getCell(int row, int col) {
        return this.board.get(row).get(col);
    }

    public int getBoardSize() {
        return boardSize;
    }

    public void print() {
        for(int i=0;i<board.size();i++){
            for (int j=0;j<board.size();j++){
                Cell cell = board.get(i).get(j);
                if(cell.getCellstatus()==CellStatus.UNOCCUPIED){
                    System.out.print(" - ");
                } else {
                    char symbol=cell.getPlayer().getSymbol().getSymbolChar();
                    System.out.print(" "+symbol+" ");
                }
            }
            System.out.println();
        }
    }

    public void reset() {
        for(List<Cell> row:this.board){
            for (Cell cell:row){
                cell.removePlayer();
            }
        }
    }
}
