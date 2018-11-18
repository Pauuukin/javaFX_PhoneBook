package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Scene;
import java.io.IOException;

public class MainController {


    @FXML
    private Button btnAdd;     //аннотация @FXML позволяет использовать в контроллере
                                  //связанную с .fxml файлом переменную, которая работает с конкретным компонентом
    @FXML
    private Button btnEdit;

    @FXML
    private Button btnDelite;

    @FXML
    private Button btnSearch;

    @FXML
    private TextField txtSearch;

    @FXML
    private TableView tableAddressBook;

    @FXML
    private Label labelCount;





    public void showDialog(ActionEvent actionEvent) {
        try{

            btnAdd.setText("clicked!");   // при нажатии на кнопку срабатывает метод showDialog, и в нем
                                          // мы можем работать с элементами по их fx:id
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("../fxml/edit.fxml"));
            stage.setTitle("Добавление записи");
            stage.setMinWidth(300);
            stage.setMinHeight(150);
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.initModality(Modality.WINDOW_MODAL);            // модальность
            stage.initOwner(((Node)actionEvent.getSource()).getScene().getWindow()); // определяем родителское окно
            stage.show();                                                            //у actionEvent вызываем метод .getSource -> приводим к элементу Node, т.к. у него
                                                                                     //есть методы .getScene().getWindow() -> получаем родительское окно

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
