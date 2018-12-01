package sample.start;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.controllers.MainController;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        FXMLLoader fxmlLoader = new FXMLLoader();                                       //используем отдельный загрузчик, чтобы мы могли взять из него mainController
        fxmlLoader.setLocation(getClass().getResource("../fxml/main.fxml"));      //и для него указать setMainStage(primaryStage), чтобы внутри MainController иметь доступ к главному окну
        Parent fxmlMain = fxmlLoader.load();
        MainController mainController = fxmlLoader.getController();
        mainController.setMainStage(primaryStage);

        primaryStage.setTitle("Телефонный справочник");
        primaryStage.setMinHeight(490);
        primaryStage.setMinWidth(400);
        primaryStage.setScene(new Scene(fxmlMain, 300, 275));
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
