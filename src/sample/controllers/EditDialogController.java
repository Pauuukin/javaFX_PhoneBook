package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.objects.Person;

import java.security.PublicKey;

public class EditDialogController {

    @FXML
    private Button btnOk;                   //привязка компонентов из .fxml
                                            //в контроллере
    @FXML
    private Button btnCancel;

    @FXML
    private TextField txtFIO;

    @FXML
    private TextField txtPhone;



    private Person person;

    public void actionClose(ActionEvent actionEvent) {
        Node sourse =  (Node) actionEvent.getSource();          /*определяем какой именно источник (Node),
                                                                получаем его Stage, делаем приведение и закрываем его,
                                                                причем нам не важно, кто его вызвал*/
        Stage stage = (Stage) sourse.getScene().getWindow();
        stage.close();
    }

    public void setPerson(Person person){
        this.person = person;

        txtFIO.setText(person.getFio());
        txtPhone.setText(person.getPhone());
    }

}
