package sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;


import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.effect.Reflection;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import sample.logic.DownData;
import sample.logic.ViewData;

import sample.logic_events.EventSource;
import sample.logic_events.EventType;
import sample.logic_events.InputEventListener;
import sample.logic_events.MoveEvent;

import java.io.IOException;
import java.net.URL;
import java.security.Key;
import java.sql.Time;
import java.util.ResourceBundle;


public class GuiController implements Initializable {

    private static final int BRICK_SIZE = 20;
    private Timeline timeline;
    private InputEventListener eventListener;
    private Rectangle[][] displayMatrix;
    private Rectangle[][] rectangles;
    private boolean isPaused;
    private ImageView pauseImage;
    private BorderPane pauseCenter;
    private BooleanProperty paused = new SimpleBooleanProperty();
    private BooleanProperty isGameOver = new SimpleBooleanProperty();
    private BooleanProperty isSettings = new SimpleBooleanProperty();


    @FXML
    private ToggleButton newGameButton;

    @FXML
    protected Pane pane;
    @FXML
    private ToggleButton settingsButton;
    @FXML
    private ToggleButton pauseButton;
    @FXML
    private ToggleButton progressButton;
    @FXML
    private GridPane gamePanel;

    @FXML
    private GridPane brickPanel;
    @FXML
    private Text scoreValue;
    @FXML
    private AnchorPane anchorPane;


    @FXML
    private Text linesClearedValue;

    @FXML
    private GridPane nextBrick;

    @FXML
    private Group groupNotification;

    @FXML
    private GameOverPanel gameOverPanel;

    private Stage stage1;
    private Scene scene1;
    private Pane root1;

    private Text text;
    private TextField textField;
    private Button button;
    private String names;

    private FXMLLoader loader;
    private Parent parent;
    private Stage stage;

    private Controller controller;
    public GuiController() throws Exception {
        pane = new Pane();
        scene1 = new Scene(pane, 250, 150);
        stage1 = new Stage();
        stage1.setScene(scene1);
        setOprions();
        addToPane();
        controller = new Controller(this);
    }

    private void setOprions() {
        stage1.setResizable(false);
        text = new Text("Введите имя");
        textField = new TextField();
        text.setLayoutX(scene1.getWidth() / 2 - 23);
        text.setLayoutY(20);
        textField.setPrefSize(100, 20);
        textField.setLayoutX(scene1.getWidth() / 2 - textField.getPrefWidth() / 2 + 10);
        textField.setLayoutY(60);
        textField.setText(null);
        button = new Button("Ok");
        button.setPrefSize(100, 20);
        button.setLayoutX(scene1.getWidth() / 2 - button.getPrefWidth() / 2 + 10);
        button.setLayoutY(100);
        button.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (textField.getText() != null) {
                    names = textField.getText();
                    stage1.close();
                    controller.addUser(textField.getText(),scoreValue.getText(),linesClearedValue.getText());
                }
            }
        });
    }

    private void addToPane() {
        pane.getChildren().addAll(text, textField, button);
    }

    public void initGameView(int[][] boardMatrix, ViewData viewData) {
        displayMatrix = new Rectangle[boardMatrix.length][boardMatrix[0].length];
        for (int i = 2; i < boardMatrix.length; i++) {
            for (int j = 0; j < boardMatrix[i].length; j++) {
                Rectangle rectangle = new Rectangle(BRICK_SIZE, BRICK_SIZE);
                rectangle.setFill(Color.TRANSPARENT);
                displayMatrix[i][j] = rectangle;
                gamePanel.add(rectangle, j, i - 2);

            }
        }

        rectangles = new Rectangle[viewData.getBrickData().length][viewData.getBrickData()[0].length];

        for (int i = 0; i < viewData.getBrickData().length; i++) {

            for (int j = 0; j < viewData.getBrickData()[i].length; j++) {
                Rectangle rectangle = new Rectangle(BRICK_SIZE, BRICK_SIZE);
                rectangle.setFill(getFillColor(viewData.getBrickData()[i][j]));
                rectangles[i][j] = rectangle;
                brickPanel.add(rectangle, j, i);
            }
        }
        brickPanel.setLayoutX(gamePanel.getLayoutX() + viewData.getxPosition() * brickPanel.getVgap()
                + viewData.getxPosition() * BRICK_SIZE);
        brickPanel.setLayoutY(-42 + gamePanel.getLayoutY() + viewData.getyPosition() * brickPanel.getHgap()
                + viewData.getyPosition() * BRICK_SIZE);
        generatePreviewPanel(viewData.getNextBrickData());

        timeline = new Timeline(new KeyFrame(Duration.millis(400), ae -> moveDown(new MoveEvent(EventType.DOWN, EventSource.THREAD))));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void generatePreviewPanel(int[][] nextBrickData) {
        nextBrick.getChildren().clear();
        for (int i = 0; i < nextBrickData.length; i++) {
            for (int j = 0; j < nextBrickData[i].length; j++) {
                Rectangle rectangle = new Rectangle(BRICK_SIZE, BRICK_SIZE);
                setRectangleData(nextBrickData[i][j], rectangle);
                if (nextBrickData[i][j] != 0) {
                    nextBrick.add(rectangle, j, i);
                }

            }
        }
    }


    private void moveDown(MoveEvent event) {
        DownData downData = eventListener.onDownEvent(event);
        if (downData.getClearRow() != null && downData.getClearRow().getLinesRemoved() > 0) {
            NotificationPanel notificationPanel = new NotificationPanel("+" + downData.getClearRow().getScoreBonus());

            groupNotification.getChildren().add(notificationPanel);
            notificationPanel.showScore(groupNotification.getChildren());

        }
        refreshBrick(downData.getViewData());
    }

    private void refreshBrick(ViewData brick) {
        brickPanel.setLayoutX(gamePanel.getLayoutX() + brick.getxPosition() * brickPanel.getVgap()
                + brick.getxPosition() * BRICK_SIZE);
        brickPanel.setLayoutY(-42 + gamePanel.getLayoutY() + brick.getyPosition() * brickPanel.getHgap()
                + brick.getyPosition() * BRICK_SIZE);


        for (int i = 0; i < brick.getBrickData().length; i++) {
            for (int j = 0; j < brick.getBrickData()[i].length; j++) {
                setRectangleData(brick.getBrickData()[i][j], rectangles[i][j]);
            }
        }
        generatePreviewPanel(brick.getNextBrickData());
    }

    public void refreshGameBackground(int[][] board) {
        for (int i = 2; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                setRectangleData(board[i][j], displayMatrix[i][j]);
            }
        }

    }

    private void setRectangleData(int color, Rectangle rectangle) {
        rectangle.setFill(getFillColor(color));
        rectangle.setArcHeight(9);
        rectangle.setArcWidth(9);
    }

    public void setEventListener(InputEventListener eventListener) {
        this.eventListener = eventListener;
    }

    public void bindScore(IntegerProperty integerProperty) {
        scoreValue.textProperty().bind(integerProperty.asString());

    }

    public void bindLinesClearedValue(IntegerProperty integerProperty) {
        linesClearedValue.textProperty().bind(integerProperty.asString());

    }

    private Paint getFillColor(int i) {

        Paint returnPaint;
        switch (i) {

            case 0:
                returnPaint = Color.TRANSPARENT;
                break;
            case 1:
                returnPaint = Color.AQUA;
                break;
            case 2:
                returnPaint = Color.BLUEVIOLET;
                break;
            case 3:
                returnPaint = Color.GREEN;
                break;
            case 4:
                returnPaint = Color.YELLOW;
                break;
            case 5:
                returnPaint = Color.YELLOWGREEN;
                break;
            case 6:
                returnPaint = Color.CORAL;
                break;
            case 7:
                returnPaint = Color.AQUAMARINE;
                break;
            default:
                returnPaint = Color.DARKGREY;
                break;
        }


        return returnPaint;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        gamePanel.setFocusTraversable(true);
        gamePanel.requestFocus();
        gamePanel.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {


                if ((event.getCode() == KeyCode.UP || event.getCode() == KeyCode.D) && !isPaused && isGameOver.getValue() == Boolean.FALSE) {
                    refreshBrick(eventListener.onRotateEvent());
                    event.consume();
                }
                if ((event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.D) && !isPaused && isGameOver.getValue() == Boolean.FALSE) {
                    refreshBrick(eventListener.onRightEvent());
                    event.consume();
                }
                if ((event.getCode() == KeyCode.LEFT || event.getCode() == KeyCode.A) && !isPaused && isGameOver.getValue() == Boolean.FALSE) {
                    refreshBrick(eventListener.onLeftEvent());
                    event.consume();
                }
                if ((event.getCode() == KeyCode.DOWN || event.getCode() == KeyCode.S) && !isPaused && isGameOver.getValue() == Boolean.FALSE) {
                    moveDown(new MoveEvent(EventType.DOWN, EventSource.USER));
                    event.consume();
                }

                if (event.getCode() == KeyCode.ENTER) {

                    newGame(null);


                }
                if (event.getCode() == KeyCode.SPACE) {
                    pauseButton.selectedProperty().setValue(!pauseButton.selectedProperty().getValue());

                }
                if (event.getCode() == KeyCode.H) {

                    progressButton.selectedProperty().setValue(!progressButton.selectedProperty().getValue());

                }
            }


            private void showing() {
                stage1.show();
            }

            private void newGame(ActionEvent actionEvent) {
                timeline.stop();
                gameOverPanel.setVisible(false);
                eventListener.createNewGame();
                gamePanel.requestFocus();
                timeline.play();
                paused.setValue(Boolean.FALSE);
                isGameOver.setValue(Boolean.FALSE);
            }
        });
        progressButton.selectedProperty().

                addListener(new ChangeListener<Boolean>() {
                    @Override
                    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                        if (!controller.getStage().isShowing()) {
                            controller.showScoreTable();
                        }
                    }
                });


        gameOverPanel.setVisible(false);
        pauseButton.selectedProperty().

                bindBidirectional(paused);
        pauseButton.selectedProperty().

                addListener(new ChangeListener<Boolean>() {
                    @Override
                    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                        if (newValue) {
                            timeline.pause();
                            pauseButton.setText("Resume");
                            pauseImage = new ImageView("sample/resources/gamePause.png");
                            pane.getChildren().add(pauseImage);
                            Main.music.pause();
                            pauseImage.setLayoutX(gamePanel.getLayoutX());
                            pauseImage.setLayoutY(gamePanel.getLayoutY());
                        } else {
                            timeline.play();
                            pauseButton.setText("Pause");
                            gamePanel.requestFocus();
                            pane.getChildren().remove(pauseImage);
                            pauseImage = null;
                            Main.music.play();
                            isPaused = false;
                        }
                    }
                });
        Reflection reflection = new Reflection();
        reflection.setFraction(0.8);
        reflection.setTopOpacity(0.9);
        reflection.setTopOffset(-12);
        scoreValue.setEffect(reflection);

        linesClearedValue.setEffect(reflection);


    }

    public void startGame(Stage stage) {
        newGameButton.setOnAction(e -> {
            restart(stage);
        });
        stage.show();
    }

    void restart(Stage stage) {
        //stop animations reset model ect
        startGame(stage);
    }

    public void GameOver() {
        stage1.show();
        timeline.stop();
        gameOverPanel.setVisible(true);
        gameOverPanel.gameOverPlay();
        isGameOver.setValue(Boolean.TRUE);
        System.out.println("Game over");

    }

    public void newGame(ActionEvent actionEvent) {
        timeline.stop();
        gameOverPanel.setVisible(false);
        eventListener.createNewGame();
        gamePanel.requestFocus();
        timeline.play();
        paused.setValue(Boolean.FALSE);
        isGameOver.setValue(Boolean.FALSE);
    }


    public void pauseGame(ActionEvent actionEvent) {
        gamePanel.requestFocus();
    }

    public TextField getTextField() {
        return textField;
    }

    public void setTextField(TextField textField) {
        this.textField = textField;
    }
}
