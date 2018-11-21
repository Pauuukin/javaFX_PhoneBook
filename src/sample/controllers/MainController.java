package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Scene;
import sample.interfaces.impls.CollectionAddressBook;
import sample.objects.Person;
import javafx.collections.ListChangeListener;

import java.io.IOException;

public class MainController {

    private CollectionAddressBook addressBookImpl = new CollectionAddressBook();  /*создаем экземпляр коллекции*/


    private  void updateCountLabel(){                                    /*обновляет лейбл "количество записей"*/
        labelCount.setText("Количество записей: " +addressBookImpl.getPersonList().size());
    }

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
    private TableColumn <Person, String> columnFIO;
    @FXML
    private TableColumn <Person, String> columnPhone;
    @FXML
    private Label labelCount;

    /*
    Смысл в том, что в PropertyValueFactory мы указываем название поля, и PropertyValueFactory
    автоматически считывает определенный гетер из Person(в дженерик указываем Класс и тип поля) и записывает его в данный column
    setCellValueFactory - позволяет указать значение для данного column.
    Сам метод initialize для контроллера срабатывает один раз в контроллере сразу после загрузки FXML
    ______________________________________________________________________________
                   |__|
                    \/
     */

    @FXML
    private void initialize(){
        columnFIO.setCellValueFactory(new PropertyValueFactory<Person, String>("fio"));
        columnPhone.setCellValueFactory(new PropertyValueFactory<Person,String>("phone"));

        addressBookImpl.getPersonList().addListener(new ListChangeListener<Person>(){   /*добавляем слушателя для события изменения листа*/
            @Override
                    public void onChanged(Change<? extends Person > c) {                /*делаем метод onChange который срабатывает
                                                                                         при зменении коллекции и обновляет лейбл; у параметра "с" есть свои методы*/
                updateCountLabel();
            }
        });



        addressBookImpl.fillTestData();                                 /*заполняем тестовыми данными*/
        tableAddressBook.setItems(addressBookImpl.getPersonList());     /*вызываем метод setItems (он может принимать только ObservableList)
                                                                          для fx:id таблицы и передаем в него addressBookImpl
                                                                          c методом getPersonList, который является просто геттером*/

    }



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
