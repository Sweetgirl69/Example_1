package sample.logic;

public class ClearRow {
    private  final int linesRemoved;
    private final int[][] newMatrix;
    private final int scoreBonus;


    public ClearRow(int linesRemoved, int[][] newMatrix, int scoreBonus) {

        this.linesRemoved = linesRemoved;
        this.newMatrix = newMatrix;
        this.scoreBonus=scoreBonus;


    }


    public int getScoreBonus() {
        return scoreBonus;
    }

    public int getLinesRemoved() {
        return linesRemoved;
    }

    public int[][] getNewMatrix() {
        return newMatrix;
    }
}
