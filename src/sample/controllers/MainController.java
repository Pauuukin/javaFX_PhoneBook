package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Scene;
import java.io.IOException;

public class MainController {


    @FXML
    private Button addButton;     //аннотация @FXML позволяет использовать в контроллере
                                  //связанную с .fxml файлом переменную, которая работает с конкретным компонентом

    public void showDialog(ActionEvent actionEvent) {
        try{

            addButton.setText("clicked!");

            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("../fxml/edit.fxml"));
            stage.setTitle("Добавление записи");
            stage.setMinWidth(300);
            stage.setMinHeight(150);
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(((Node)actionEvent.getSource()).getScene().getWindow()); // определяем родителское окно
            stage.show();                                                            //приводим к элементу Node, т.к. у него
                                                                                     //есть методы .getScene().getWindow()

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
