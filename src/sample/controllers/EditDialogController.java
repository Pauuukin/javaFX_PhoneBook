package sample.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

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

}
