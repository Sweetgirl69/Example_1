package sample;




import sample.logic.ClearRow;
import sample.logic.DownData;
import sample.logic.SimpleBoard;
import sample.logic.ViewData;
import sample.logic_events.EventSource;
import sample.logic_events.InputEventListener;
import sample.logic_events.MoveEvent;


public class GameController  implements InputEventListener {

    private SimpleBoard board = new SimpleBoard(25,10);
    private  final  GuiController viewController;


    public GameController(GuiController c){

        this.viewController= c ;

        board.createNewBrick();
        viewController.setEventListener(this);
         viewController.initGameView(board.getBoardMatrix(),board.getViewData());
         viewController.bindScore(board.getScore().scoreProperty());

         viewController.bindLinesClearedValue(board.getLinesClearedValue().linesClearedValueProperty());
    }

    @Override
    public DownData onDownEvent(MoveEvent event) {
        boolean canMove =   board.moveBrickDown();
        ClearRow clearRow=null;
        if (!canMove){
            board.mergeBrickToBackground();
            clearRow = board.clearRows();
            if (clearRow.getLinesRemoved()>0){
                board.getScore().add(clearRow.getScoreBonus());
                board.getLinesClearedValue().add(1);
            }
            if (board.createNewBrick()){
            viewController.GameOver();
            }

        }else {
            if (event.getEventSource()== EventSource.USER){
                board.getScore().add(1);

            }
        }
        viewController.refreshGameBackground(board.getBoardMatrix());
          return  new DownData(clearRow, board.getViewData());
    }

    @Override
    public ViewData onLeftEvent() {
        board.moveBrickLeft();
        return board.getViewData();
    }

    @Override
    public ViewData onRightEvent() {
        board.moveBrickRight();
        return board.getViewData();
    }

    @Override
    public ViewData onRotateEvent() {
        board.rotateBrickLeft();
        return board.getViewData();
    }

    @Override
    public void createNewGame() {
        board.newGame();
        viewController.refreshGameBackground(board.getBoardMatrix());
    }
}
