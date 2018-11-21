package sample.start;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.interfaces.impls.CollectionAddressBook;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../fxml/main.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
        primaryStage.setMinHeight(490);
        primaryStage.setMinWidth(420);

        testData(); //вызываем тестовый метод
    }

    private void testData() {
        CollectionAddressBook addressBook = new CollectionAddressBook();    //реализация с помощью коллекции
                                                                            //создаем экземпляр CollectionAddressBook
        addressBook.fillTestData();                                         //заполняем коллекцию данными
        addressBook.print();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
