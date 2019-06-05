package sample.logic;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.stream.Collectors;

public class MatrixOperations {
    public static boolean intersects(int[][] matrix, int[][] brick, int x, int y){

        for (int i = 0; i< brick.length;i++){
            for (int j = 0; j< brick[i].length;j++){
                int targetX = x+i;
               int  targetY=y+j;
                if (brick[j][i] !=0 && (outOfBounds(matrix,targetX,targetY) || matrix[targetY][targetX]!=0)){

                    return true;
                }

            }
        }

        return false;
    }

    private  static  boolean  outOfBounds(int [][] matrix,int targetX, int targetY){
        if (targetX>=0 && targetY<matrix.length&& targetX<matrix[targetY].length){
            return false;
        }
        return true;
    }
    public static  int[][] merge(int[][] filledFields, int[][] brick, int x, int y){
       int [][] copy = copy(filledFields);

       for (int i = 0; i<brick.length;i++){
           for (int j =0; j<brick[i].length;j++){
               int targetX = x+i;
                int targetY=y+j;
               if (brick[j][i] !=0){
                   copy[targetY-1][targetX] = brick[j][i];
               }
           }
       }

       return copy;
    }

    public  static  int[][] copy(int[][] original){
        int[][] myInt = new int[original.length][];
        for (int i=0; i< original.length;i++){
            int[] aMatrix = original[i];
            int aLength = aMatrix.length;
            myInt[i]= new  int[aLength];
            System.arraycopy(aMatrix,0,myInt[i],0,aLength);
        }
        return  myInt;
    }

    public static ClearRow checkRemoving(int[][] matrix) {

        int[][] tmp = new  int[matrix.length][matrix[0].length];
        List<Integer> clearRows = new ArrayList<>();
        Deque<int[]> newRows = new ArrayDeque<>();
        for (int i=0; i <matrix.length;i++){
            int [] tmpRow = new  int [matrix[i].length];
            boolean rowToClear = true;
            for ( int j =0 ; j< matrix[0].length;j++){
                if (matrix[i][j] == 0){
                    rowToClear=false;
                }
                tmpRow[j]=matrix[i][j];
            }
            if (rowToClear){
                clearRows.add(i);

            }else {
                newRows.add(tmpRow);

            }
        }

        for (int i = matrix.length - 1; i >=0; i-- ){
            int [] row = newRows.pollLast();
            if (row!=null){
                tmp[i]=row;
            }else {
                break;
            }
        }

        int scoreBonus = 50*clearRows.size()*clearRows.size();


          return  new ClearRow(clearRows.size(),tmp,scoreBonus);
    }
    public static List<int[][]> deepCopyList(List<int[][]> list){
        return list.stream().map(MatrixOperations::copy).collect(Collectors.toList());
    }

}
