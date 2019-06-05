package sample.logic;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class LinesClearedValue {
    private IntegerProperty linesClearedValue = new SimpleIntegerProperty(0);

    public LinesClearedValue() {
        this.linesClearedValue = linesClearedValue;
    }

    public void  add(int i ){
       linesClearedValue.setValue(linesClearedValue.getValue()+1);
    }

    public void reset() {
        linesClearedValue.setValue(0);
    }

    public int getLinesClearedValue() {
        return linesClearedValue.get();
    }

    public IntegerProperty linesClearedValueProperty() {
        return linesClearedValue;
    }
}
