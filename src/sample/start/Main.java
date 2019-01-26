package sample.start;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.controllers.MainController;

import java.util.Locale;
import java.util.ResourceBundle;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        FXMLLoader fxmlLoader = new FXMLLoader();                                       //используем отдельный загрузчик, чтобы мы могли взять из него mainController
        fxmlLoader.setLocation(getClass().getResource("../fxml/main.fxml"));      //и для него указать setMainStage(primaryStage), чтобы внутри MainController иметь доступ к главному окну
        fxmlLoader.setResources(ResourceBundle.getBundle("sample.bundles.Locale",new Locale("ru"))); //указываем Бандл и указываем текущую локализацию

        Parent fxmlMain = fxmlLoader.load();
        MainController mainController = fxmlLoader.getController();
        mainController.setMainStage(primaryStage);

        primaryStage.setTitle(fxmlLoader.getResources().getString("phone_book"));   //теперь все через ресурсы по ключу для локализации
        primaryStage.setMinHeight(490);
        primaryStage.setMinWidth(400);
        primaryStage.setScene(new Scene(fxmlMain, 450 , 275));
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
