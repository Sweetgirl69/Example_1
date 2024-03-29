package sample.logic;

public class ViewData {
    private  final int[][] brickData;
    private  final int xPosition;
    private  final int yPosition;
    private  final int[][] nextBrickData;


    public ViewData(int[][] brickData, int xPosition, int yPosition, int[][] nextBrickData) {
        this.brickData = brickData;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.nextBrickData = nextBrickData;
    }

    public int[][] getNextBrickData() {
        return nextBrickData;
    }

    public int[][] getBrickData() {
        return brickData;
    }

    public int getxPosition() {
        return xPosition;
    }

    public int getyPosition() {
        return yPosition;
    }
}
