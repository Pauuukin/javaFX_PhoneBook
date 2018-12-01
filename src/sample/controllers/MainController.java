package sample.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.stage.Window;
import sample.interfaces.impls.CollectionAddressBook;
import sample.objects.Person;
import javafx.collections.ListChangeListener;

import java.io.IOException;

public class MainController {

    private CollectionAddressBook addressBookImpl = new CollectionAddressBook();    /*создаем экземпляр коллекции*/
    private Stage mainStage;                                                        //ссылка на главное окно

    public void setMainStage(Stage mainStage){                                      //в классе Main вызывается этот метод и теперь
        this.mainStage = mainStage;                                                 //в контроллере мы имеем ссылку на главное окно
    }



    private  void updateCountLabel(){                                    /*обновляет лейбл "количество записей"*/
        labelCount.setText("Количество записей: " +addressBookImpl.getPersonList().size());
    }

    @FXML
    private Button btnAdd;      //аннотация @FXML позволяет использовать в контроллере
    @FXML                       //связанную с .fxml файлом переменную, которая работает с конкретным компонентом
    private Button btnEdit;
    @FXML
    private Button btnDelete;
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

    private Parent fxmlEdit;                                    /*выносим переменные на уровень класса, чтобы
                                                                 они были доступны в методах*/
    private FXMLLoader fxmlLoader = new FXMLLoader();
    private EditDialogController editDialogController;          //экземпляр класса контроллера модального окна
    private Stage editDialogStage;



    /*
    Смысл в том, что в PropertyValueFactory мы указываем название поля, и PropertyValueFactory
    автоматически считывает определенный гетер из Person(в дженерик указываем Класс и тип поля) и записывает его в данный column
    setCellValueFactory - позволяет указать значение для данного column.
    Сам метод initialize срабатывает один раз в контроллере сразу после загрузки FXML
    ______________________________________________________________________________
                   |__|
                    \/
     */

    @FXML
    private void initialize(){
        columnFIO.setCellValueFactory(new PropertyValueFactory<Person, String>("fio"));
        columnPhone.setCellValueFactory(new PropertyValueFactory<Person,String>("phone"));
        fillData();
        initListeners();
        initLoader();
    }

    private void fillData() {
        addressBookImpl.fillTestData();                                 /*заполняем тестовыми данными*/
        tableAddressBook.setItems(addressBookImpl.getPersonList());     /*вызываем метод setItems (он может принимать только ObservableList)
                                                                          для fx:id таблицы и передаем в него addressBookImpl
                                                                          c методом getPersonList, который является просто геттером*/
    }

    private void initListeners(){                                                                                       //в данном методе инициализируем все listener

        addressBookImpl.getPersonList().addListener(new ListChangeListener<Person>(){                                   /*добавляем слушателя для события изменения листа*/
            @Override
            public void onChanged(Change<? extends Person > c) {                                                        //делаем метод onChange который срабатывает
                updateCountLabel();                                                                                     //при зменении коллекции и обновляет лейбл; у параметра "с" есть свои методы
            }
        });
        tableAddressBook.setOnMouseClicked(new EventHandler<MouseEvent>() {                                             //пишем setOnMouseClicked(new ctrl+space и выбираем нужное
            @Override                                                                                                   //сам метод вызывается при двойном нажатии на строку таблицы
            public void handle(MouseEvent event) {
                if (event.getClickCount()==2){                                                                          //смотрим, сколько раз была нажата клавиша мыши
                    editDialogController.setPerson((Person)tableAddressBook.getSelectionModel().getSelectedItem());     //получаем выбранную строку и кастуем в тип Person
                    showDialog();
                }
            }
        });
    }

    private void initLoader(){
        try {
            fxmlLoader.setLocation(getClass().getResource("../fxml/edit.fxml"));    //теперь загружаем fxml один раз чтобы не было нагрузки на файловую систему
            fxmlEdit = fxmlLoader.load();
            editDialogController = fxmlLoader.getController();                            // и тут же получаем у него контроллер
        } catch (IOException e){
            e.printStackTrace();
        }
    }


    public void actionButtonPressed(ActionEvent actionEvent) {

        Object source = actionEvent.getSource();  //получаем источник и записываем его в Object
        if(!(source instanceof Button)){          //проверяем, является ли текущий объект кнопкой, если нажата не кнопка - выходим из метода
            return;
        }
        Button clickedButton =(Button) source;                                                    //source приводит к типу button и записываем к clickedButton
        Person selectedPerson = (Person)tableAddressBook.getSelectionModel().getSelectedItem();   /*у tableView получаем SelectionModel
                                                                                                (выбранная запись в таблице), у него выбираем нужную запись*/

        Window parentWindow = ((Node) actionEvent.getSource()).getScene().getWindow();          // определяем родителское окно: у actionEvent вызываем метод .getSource -> приводим к элементу Node, т.к. у него
                                                                                                //есть методы .getScene().getWindow() -> получаем родительское окно
        editDialogController.setPerson(selectedPerson);

        switch (clickedButton.getId()){
            case "btnAdd":
                editDialogController.setPerson(new Person());               //создаем новый объект типа персон
                showDialog();                                               //вызываем метод
                addressBookImpl.add(editDialogController.getPerson());      //добовляем в коллекцию новый объект, введенный в showDialog
                break;

            case  "btnEdit":
                editDialogController.setPerson((Person)tableAddressBook.getSelectionModel().getSelectedItem()); //в выюранный объект записываем новые значения
                showDialog();
                break;

            case  "btnDelete":
                addressBookImpl.delete((Person)tableAddressBook.getSelectionModel().getSelectedItem());
                break;

        }
           /*
           btnAdd.setText("clicked!");    при нажатии на кнопку срабатывает метод showDialog, и в нем
                                           мы можем работать с элементами по их fx:id
            */
    }

    private void showDialog() {
        if(editDialogStage==null){                              //ленивая инициализация окна
            editDialogStage = new Stage();
            editDialogStage.setTitle("Редактирование записи");
            editDialogStage.setMinHeight(150);
            editDialogStage.setMinWidth(300);
            editDialogStage.setResizable(false);
            editDialogStage.setScene(new Scene(fxmlEdit));          //модальность
            editDialogStage.initModality(Modality.WINDOW_MODAL);
            editDialogStage.initOwner(mainStage);                   //теперь тут mainStage
        }
      editDialogStage.showAndWait();        //меняем show на showAndWait т.к. нужно дождаться ответа пользователя

        //  editDialogStage.show();
    }
}
