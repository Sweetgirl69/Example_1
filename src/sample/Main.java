package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.net.URL;


public  class Main extends Application {

    public static MediaPlayer music;
    public static void main(String[] args) {
        launch(args);




    }

    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("sample.fxml"));
        Parent root = fxmlLoader.load();
        GuiController c = fxmlLoader.getController();
        primaryStage.setTitle("TetrisFX");
        primaryStage.getIcons().add(new Image("sample/resources/tetris.png"));
        Scene scene = new Scene(root, 400, 510);
        URL resource = getClass().getResource("resources/Tetris.mp3");
        music =new MediaPlayer(new Media(resource.toString()));
        music.setVolume(0.1);
        music.setOnEndOfMedia(new Runnable() {
            public void run() {
                music.seek(Duration.ZERO);

            }
        });
        music.play();
        primaryStage.setScene(scene);
        primaryStage.show();
        new GameController(c);

    }



}





