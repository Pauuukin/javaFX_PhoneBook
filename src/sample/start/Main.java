package sample.start;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.interfaces.impls.CollectionAddressBook;
import sample.objects.Person;
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
        CollectionAddressBook addressBook = new CollectionAddressBook(); //реализация с помощью коллекции
                                                                       //создаем экземпляр CollectionAddressBook

        Person person = new Person();           //тестовые данные
        person.setFio("test1");
        person.setPhone("1234");
                                                //При дебаге в idea F8, F9
        Person person2 = new Person();
        person2.setFio("test2");
        person2.setPhone("12345");

        addressBook.add(person);
        addressBook.add(person2);

        person.setPhone("22222");
        addressBook.delete(person);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
