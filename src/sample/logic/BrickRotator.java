package sample.logic;

import sample.logic_bricks.Brick;

public class BrickRotator {
    private Brick brick;
    private int currentShape = 0;

    public NextShapeInfo getNextShape() {
        int nextShape = currentShape;
        nextShape = (++nextShape) % brick.getBrickMatrix().size();
        return new NextShapeInfo(brick.getBrickMatrix().get(nextShape), nextShape);
    }

    public int[][] getCurrentShape() {
        return brick.getBrickMatrix().get(currentShape);
    }

    public void setCurrentShape(int currentShape) {
        this.currentShape = currentShape;
    }

    public void setBrick(Brick brick) {
        this.brick = brick;
        currentShape = 0;
    }

}
