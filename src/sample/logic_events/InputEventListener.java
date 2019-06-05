package sample.logic_events;

import sample.logic.DownData;
import sample.logic.ViewData;

public interface InputEventListener {
    DownData onDownEvent(MoveEvent event);

    ViewData onLeftEvent();

    ViewData onRightEvent();

    ViewData onRotateEvent();

     void createNewGame();
}
