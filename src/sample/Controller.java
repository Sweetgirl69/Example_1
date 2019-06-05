package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.*;
import java.util.Scanner;

import javafx.scene.control.TableColumn;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Controller {
    private ObservableList<User> usersData = FXCollections.observableArrayList();
    private TableView<User> tableUsers;
    private TableColumn<User, String> loginColumn;
    private TableColumn<User, Integer> scoreColumn;
    private TableColumn<User, Integer> linesClearedColumn;
    private GuiController guiController;
    private Pane pane;
    private Stage stage;
    private Scene scene;

    public Controller(GuiController guiController) {
        this.guiController = guiController;
        init();
    }

    private void init() {
        pane = new Pane();
        scene = new Scene(pane, 600, 324);
        stage = new Stage();
        stage.setScene(scene);
        stage.setResizable(false);
        tableUsers = new TableView<>();
        tableUsers.setPrefSize(600, 324);
        loginColumn = new TableColumn<>("login");
        scoreColumn = new TableColumn<>("score");
        linesClearedColumn = new TableColumn<>("lines");
        loginColumn.setPrefWidth(150);
        scoreColumn.setPrefWidth(150);
        linesClearedColumn.setPrefWidth(200);
        loginColumn.setCellValueFactory(new PropertyValueFactory<User, String>("login"));
        scoreColumn.setCellValueFactory(new PropertyValueFactory<User, Integer>("score"));
        linesClearedColumn.setCellValueFactory(new PropertyValueFactory<User, Integer>("linesCleared"));
        tableUsers.getColumns().addAll(loginColumn, scoreColumn, linesClearedColumn);
        tableUsers.setItems(usersData);
        pane.getChildren().add(tableUsers);

    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void showScoreTable() {
        stage.show();
    }

    public void closeScoreTable() {
        stage.close();
    }

    public void addUser(String login, String score, String clearLines){
        usersData.add(new User(login,Integer.parseInt(score),Integer.parseInt(clearLines)));

    }

    /*
    private ObservableList<User> usersData = FXCollections.observableArrayList();
    @FXML
    private TableView<User> tableUsers;

    @FXML
    private TableColumn<User, String> loginColumn;
    @FXML
    private TableColumn<User, Integer> scoreColumn;

    @FXML
    private TableColumn<User, Integer> linesClearedColumn;

    public Controller(GuiController guiController) {
    //    initialize();
    }


    // инициализируем форму данными

    private void initialize()  {
        loginColumn.setCellValueFactory(new PropertyValueFactory<User, String>("login"));
        scoreColumn.setCellValueFactory(new PropertyValueFactory<User, Integer>("score"));
        linesClearedColumn.setCellValueFactory(new PropertyValueFactory<User, Integer>("linesCleared"));
    }
    public void addUser(String login, String score, String clearLines){

        usersData.add(new User(login,Integer.parseInt(score),Integer.parseInt(clearLines)));
        //tableUsers.getItems().add(usersData.get(0));

    }

    public ObservableList<User> getUsersData() {
        return usersData;
    }

    public void setUsersData(ObservableList<User> usersData) {
        this.usersData = usersData;
    }

    public TableView<User> getTableUsers() {
        return tableUsers;
    }

    public void setTableUsers(TableView<User> tableUsers) {
        this.tableUsers = tableUsers;
    }

    public TableColumn<User, String> getLoginColumn() {
        return loginColumn;
    }

    public void setLoginColumn(TableColumn<User, String> loginColumn) {
        this.loginColumn = loginColumn;
    }

    public TableColumn<User, Integer> getScoreColumn() {
        return scoreColumn;
    }

    public void setScoreColumn(TableColumn<User, Integer> scoreColumn) {
        this.scoreColumn = scoreColumn;
    }

    public TableColumn<User, Integer> getLinesClearedColumn() {
        return linesClearedColumn;
    }

    public void setLinesClearedColumn(TableColumn<User, Integer> linesClearedColumn) {
        this.linesClearedColumn = linesClearedColumn;
    }*/
}
