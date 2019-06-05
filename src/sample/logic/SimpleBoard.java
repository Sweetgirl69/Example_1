package sample.logic;


import com.sun.javafx.scene.paint.GradientUtils;
import sample.logic_bricks.Brick;
import sample.logic_bricks.RandomGenerator;

import java.awt.*;

public class SimpleBoard implements Board{

    private final  int width;
    private final  int heidth;
    private int[][] currentGameMatrix;
    private  final RandomGenerator brickGenerator;
    private Brick brick;
    private  int currentShape=0;
    private Point currentOffset;
    private   Score score;

    private  LinesClearedValue linesClearedValue;
    private SimpleBoard brickRotator;


    public SimpleBoard(int width, int heidth) {
        this.width = width;
        this.heidth = heidth;
        currentGameMatrix = new  int[width][heidth];
        brickGenerator= new RandomGenerator();
        score = new Score();

        linesClearedValue = new LinesClearedValue();


    }

    public Score getScore() {
        return score;
    }



    public LinesClearedValue getLinesClearedValue() {
        return linesClearedValue;
    }

    public  void setBrick(Brick brick){
       this.brick=brick;
       currentOffset = new Point(4,0);
    }
    public  int[][] getCurrentShape(){
        return  brick.getBrickMatrix().get(currentShape);
    }

    public  boolean createNewBrick(){
        currentShape=0;
        Brick currentBrick = brickGenerator.getBrick();
        setBrick(currentBrick);
        currentOffset=new Point(4,0);
        return MatrixOperations.intersects(currentGameMatrix,getCurrentShape(),(int)currentOffset.getX(),
                (int)currentOffset.getY()) ;
    }

    public int[][] getBoardMatrix() {
        return currentGameMatrix;
    }

    public  ViewData getViewData(){
        return  new ViewData(getCurrentShape(),(int)currentOffset.getX(),(int)currentOffset.getY(),
                brickGenerator.getNextBrick().getBrickMatrix().get(0));

    }

    public boolean  moveBrickDown(){
        Point p= new Point(currentOffset);
        p.translate(0,1);
        currentOffset = p;
        boolean conflict = MatrixOperations.intersects(currentGameMatrix,getCurrentShape(),(int) p.getX(),(int) p.getY());
        if (conflict){

            return  false;
        }else {
            currentOffset = p;
            return  true;
        }
    }
    public boolean moveBrickLeft() {
        Point p= new Point(currentOffset);
        p.translate(-1,0);
        boolean conflict = MatrixOperations.intersects(currentGameMatrix,getCurrentShape(),(int) p.getX(),(int) p.getY());
        if (conflict){

            return  false;
        }else {
            currentOffset = p;
            return  true;
        }

    }
    public boolean moveBrickRight() {
        Point p= new Point(currentOffset);
        p.translate(1,0);
        boolean conflict = MatrixOperations.intersects(currentGameMatrix,getCurrentShape(),(int) p.getX(),(int) p.getY());
        if (conflict){

            return  false;
        }else {
            currentOffset = p;
            return  true;
        }
    }

    @Override
    public boolean rotateLeftBrick() {
        int[][] currentMatrix = MatrixOperations.copy(currentGameMatrix);
        NextShapeInfo nextShape = brickRotator.getNextShape();
        boolean conflict = MatrixOperations.intersects(currentMatrix, nextShape.getShape(), (int) currentOffset.getX(), (int) currentOffset.getY());
        if (conflict) {
            return false;
        } else {
            brickRotator.setCurrentShape(nextShape.getPosition());
            return true;
        }
    }

    public void mergeBrickToBackground() {
        currentGameMatrix = MatrixOperations.merge(currentGameMatrix,getCurrentShape(),
                (int) currentOffset.getX(),(int) currentOffset.getY());

        }


    public boolean rotateBrickLeft() {
    NextShapeInfo nextShapeInfo = getNextShape();

    boolean conflict = MatrixOperations.intersects(currentGameMatrix,nextShapeInfo.getShape(),
            (int)currentOffset.getX(),(int)currentOffset.getY());
    if (conflict){
        return false;
    }else{
        setCurrentShape(nextShapeInfo.getPosition());
        return true;
    }
    }

    private void setCurrentShape(int currentShape) {
        this.currentShape = currentShape;
    }

    public  NextShapeInfo getNextShape(){
        int nextShape = currentShape;
        nextShape=++nextShape%brick.getBrickMatrix().size();
        return new NextShapeInfo(brick.getBrickMatrix().get(nextShape),nextShape);
    }

    public ClearRow clearRows() {
        ClearRow clearRow = MatrixOperations.checkRemoving(currentGameMatrix);
        currentGameMatrix=clearRow.getNewMatrix();
        return  clearRow;
    }



    @Override
    public void newGame() {
        currentGameMatrix=new int[width][heidth];
        score.reset();
        linesClearedValue.reset();

        createNewBrick();
    }
}

