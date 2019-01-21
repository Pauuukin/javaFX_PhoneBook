package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.objects.Person;
import sample.utils.DialogManager;

import java.net.URL;
import java.security.PublicKey;
import java.util.ResourceBundle;

public class EditDialogController implements Initializable {

    @FXML
    private Button btnOk;                   //привязка компонентов из .fxml
                                            //в контроллере
    @FXML
    private Button btnCancel;

    @FXML
    private TextField txtFIO;

    @FXML
    private TextField txtPhone;

    private ResourceBundle resourceBundle;

    private Person person;

    public void actionClose(ActionEvent actionEvent) {
        Node sourse =  (Node) actionEvent.getSource();          /*определяем какой именно источник (Node),
                                                                получаем его Stage, делаем приведение и закрываем его,
                                                                причем нам не важно, кто его вызвал*/
        Stage stage = (Stage) sourse.getScene().getWindow();
        stage.hide();                                           /*меняем close на hide т.к. теперь это окно вызывается лишь однажды*/
    }

    public Person getPerson() {                                 //добавляем гетер типа Person для добавления новых записей в таблицу
        return person;
    }

    public void setPerson(Person person){
        if (person == null){                                    //добавили обработку для путого пользователя
            return;
        }
        this.person = person;
        txtFIO.setText(person.getFio());
        txtPhone.setText(person.getPhone());
    }
    public void actionSave(ActionEvent actionEvent){            //метод реактирования записей
        if (!checkValues()){
            return;
        }

        person.setPhone(txtPhone.getText());                    //работает с актуальным объектом, выбранным в таблице
        person.setFio(txtFIO.getText());
        actionClose(actionEvent);
    }

    private boolean checkValues(){
        if (txtFIO.getText().trim().length()==0 || txtPhone.getText().trim().length() == 0){
            DialogManager.showErrorDialog(resourceBundle.getString("error"), resourceBundle.getString("fill_field"));
            return false;
        }
        return true;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.resourceBundle = resources;
    }
}
