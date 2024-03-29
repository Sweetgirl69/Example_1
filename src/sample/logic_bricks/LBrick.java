package sample.logic_bricks;

import java.util.ArrayList;
import java.util.List;

public class LBrick implements Brick{
    private final List<int[][]> brickMatrix= new ArrayList<>();


    public List<int[][]> getBrickMatrix() {
        return brickMatrix;
    }

    public  LBrick(){
        brickMatrix.add(new int[][]{
                {0,0,0,0},
                {0,4,4,4},
                {0,4,0,0},
                {0,0,0,0}
        });
        brickMatrix.add(new int[][]{
                {0,0,0,0},
                {0,4,4,0},
                {0,0,4,0},
                {0,0,4,0}
        });
        brickMatrix.add(new int[][]{
                {0,0,0,0},
                {0,0,4,0},
                {4,4,4,0},
                {0,0,0,0}
        });
        brickMatrix.add(new int[][]{
                {0,4,0,0},
                {0,4,0,0},
                {0,4,4,0},
                {0,0,0,0}
        });

    }
}
