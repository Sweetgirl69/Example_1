package sample;


import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

import javafx.scene.media.AudioClip;



public class GameOverPanel extends BorderPane {
        private AudioClip mApplause;
    public  GameOverPanel(){
        mApplause = new AudioClip(this.getClass().getResource("resources/gameOver.mp3").toExternalForm());
        Label gameOverLabel = new Label("Game over");
        gameOverLabel.getStyleClass().add("gameOverStyle");

        setCenter(gameOverLabel);

    }
    public void gameOverPlay(){
        Main.music.stop();
        mApplause.play();

    }
}
