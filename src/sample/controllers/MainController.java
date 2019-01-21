package sample.controllers;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.ObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.stage.Window;
import org.controlsfx.control.textfield.CustomTextField;
import org.controlsfx.control.textfield.TextFields;
import sample.interfaces.impls.CollectionAddressBook;
import sample.objects.Lang;
import sample.objects.Person;
import javafx.collections.ListChangeListener;
import sample.utils.DialogManager;
import sample.utils.LocaleManager;

import javax.naming.ldap.ExtendedRequest;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class MainController  implements Initializable {                              //добавляем реализацию интерфейса Initializable для локализации в контроллере

    private CollectionAddressBook addressBookImpl = new CollectionAddressBook();    /*создаем экземпляр коллекции*/
    private Stage mainStage;                                                        //ссылка на главное окно

    public void setMainStage(Stage mainStage) {                                      //в классе Main вызывается этот метод и теперь
        this.mainStage = mainStage;                                                 //в контроллере мы имеем ссылку на главное окно
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
    private CustomTextField txtSearch;
    @FXML
    private TableView tableAddressBook;
    @FXML
    private TableColumn<Person, String> columnFIO;
    @FXML
    private TableColumn<Person, String> columnPhone;
    @FXML
    private Label labelCount;
    @FXML
    private ComboBox comboLocales;

    private Parent fxmlEdit;                                    /*выносим переменные на уровень класса, чтобы
                                                                 они были доступны в методах*/
    private FXMLLoader fxmlLoader = new FXMLLoader();
    private EditDialogController editDialogController;          //экземпляр класса контроллера модального окна
    private Stage editDialogStage;

    private ResourceBundle resourceBundle;                      //переменная для Локализации

    private ObservableList<Person> backupList;

    private static final String RU_CODE = "ru";
    private static final String EN_CODE = "en";

    /*
    Смысл в том, что в PropertyValueFactory мы указываем название поля, и PropertyValueFactory
    автоматически считывает определенный гетер из Person(в дженерик указываем Класс и тип поля) и записывает его в данный column
    setCellValueFactory - позволяет указать значение для данного column.
    Сам метод initialize срабатывает один раз в контроллере сразу после загрузки FXML
    ______________________________________________________________________________
                   |__|
                    \/
     */

    @Override
    public void initialize(URL location, ResourceBundle resources) {             //теперь этот метод реализует еще и локализацию
        initListeners();
        this.resourceBundle = resources;
        columnFIO.setCellValueFactory(new PropertyValueFactory<Person, String>("fio"));
        columnPhone.setCellValueFactory(new PropertyValueFactory<Person, String>("phone"));
        fillData();
        setupClearButtonField(txtSearch);
        initLoader();
    }


    private void setupClearButtonField(CustomTextField customTextField) {
        try {
            Method m = TextFields.class.getDeclaredMethod("setupClearButtonField", TextField.class, ObjectProperty.class);
            m.setAccessible(true);
            m.invoke(null, customTextField, customTextField.rightProperty());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void fillData() {
        fillTable();
   //     fillLangComboBox();
    }
/*    private void fillLangComboBox() {
        Lang langRU = new Lang(0, RU_CODE, resourceBundle.getString("ru"), LocaleManager.RU_LOCALE);
        Lang langEN = new Lang(0, EN_CODE, resourceBundle.getString("en"), LocaleManager.EN_LOCALE);

        comboLocales.getItems().add(langRU);
        comboLocales.getItems().add(langEN);

        if (LocaleManager.getCurrentLang() == null) {
            comboLocales.getSelectionModel().select(0);
        } else {
            comboLocales.getSelectionModel().select(LocaleManager.getCurrentLang().getIndex());
        }
    }*/

    private void fillTable() {
        addressBookImpl.fillTestData();                                 /*заполняем тестовыми данными*/
        backupList = FXCollections.observableArrayList();
        backupList.addAll(addressBookImpl.getPersonList());             /*добавляем все записи в бекап лист */
        tableAddressBook.setItems(addressBookImpl.getPersonList());     /*вызываем метод setItems (он может принимать только ObservableList)
                                                                          для fx:id таблицы и передаем в него addressBookImpl
                                                                          c методом getPersonList, который является просто геттером*/
    }

    private void initListeners() {                                                                                       //в данном методе инициализируем все listener

        addressBookImpl.getPersonList().addListener(new ListChangeListener<Person>() {                                   /*добавляем слушателя для события изменения листа*/
            @Override
            public void onChanged(Change<? extends Person> c) {                                                        //делаем метод onChange который срабатывает
                updateCountLabel();                                                                                     //при зменении коллекции и обновляет лейбл; у параметра "с" есть свои методы
            }
        });
        tableAddressBook.setOnMouseClicked(new EventHandler<MouseEvent>() {                                             //пишем setOnMouseClicked(new ctrl+space и выбираем нужное
            @Override
            //сам метод вызывается при двойном нажатии на строку таблицы
            public void handle(MouseEvent event) {
                if (event.getClickCount() == 2) {                                                                          //смотрим, сколько раз была нажата клавиша мыши
                    editDialogController.setPerson((Person) tableAddressBook.getSelectionModel().getSelectedItem());     //получаем выбранную строку и кастуем в тип Person
                    showDialog();
                }
            }
        });
//
//        //слушаем изменение языка
//        comboLocales.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                Lang selecedLang = (Lang) comboLocales.getSelectionModel().getSelectedItem();
//                LocaleManager.setCurrentLang(selectedLang);
//
//                //уведомляем, что язык изменен
//                setChanged();
//                notifyObservers(selecedLang);
//
//            }
//        });
    }

    private void initLoader() {
        try {
            fxmlLoader.setLocation(getClass().getResource("../fxml/edit.fxml"));    //теперь загружаем fxml один раз чтобы не было нагрузки на файловую систему
            fxmlLoader.setResources(ResourceBundle.getBundle("sample.bundles.Locale", new Locale("ru")));
            fxmlEdit = fxmlLoader.load();
            editDialogController = fxmlLoader.getController();                            // и тут же получаем у него контроллер
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateCountLabel() {                                    /*обновляет лейбл "количество записей"*/
        labelCount.setText(resourceBundle.getString("count") + ": " + addressBookImpl.getPersonList().size());
        //   System.out.println(addressBookImpl.getPersonList().size());
    }


    public void actionButtonPressed(ActionEvent actionEvent) {

        Object source = actionEvent.getSource();  //получаем источник и записываем его в Object
        if (!(source instanceof Button)) {          //проверяем, является ли текущий объект кнопкой, если нажата не кнопка - выходим из метода
            return;
        }
        Button clickedButton = (Button) source;                                                    //source приводит к типу button и записываем к clickedButton
        Person selectedPerson = (Person) tableAddressBook.getSelectionModel().getSelectedItem();   /*у tableView получаем SelectionModel
                                                                                                (выбранная запись в таблице), у него выбираем нужную запись*/

        Window parentWindow = ((Node) actionEvent.getSource()).getScene().getWindow();          // определяем родителское окно: у actionEvent вызываем метод .getSource -> приводим к элементу Node, т.к. у него
        //есть методы .getScene().getWindow() -> получаем родительское окно
        editDialogController.setPerson(selectedPerson);

        switch (clickedButton.getId()) {
            case "btnAdd":
                editDialogController.setPerson(new Person());               //создаем новый объект типа персон
                showDialog();                                               //вызываем метод
                addressBookImpl.add(editDialogController.getPerson());      //добовляем в коллекцию новый объект, введенный в showDialog
                break;

            case "btnEdit":
                if (!personIsSelected(selectedPerson)) {
                    return;
                }

                editDialogController.setPerson(selectedPerson); //в выюранный объект записываем новые значения
                showDialog();
                break;

            case "btnDelete":
                if (!personIsSelected(selectedPerson)) {
                    return;
                }

                addressBookImpl.delete(selectedPerson);
                break;

        }
           /*
           btnAdd.setText("clicked!");    при нажатии на кнопку срабатывает метод showDialog, и в нем
                                           мы можем работать с элементами по их fx:id
            */
    }

    private boolean personIsSelected(Person selectedPerson) {
        if (selectedPerson == null) {
            DialogManager.showInfoDialog(resourceBundle.getString("error"), resourceBundle.getString("select_person"));
            return false;
        }
        return true;
    }

    private void showDialog() {
        if (editDialogStage == null) {                              //ленивая инициализация окна
            editDialogStage = new Stage();
            editDialogStage.setTitle(resourceBundle.getString("editing"));
            editDialogStage.setMinHeight(150);
            editDialogStage.setMinWidth(300);
            editDialogStage.setResizable(false);
            editDialogStage.setScene(new Scene(fxmlEdit));          //модальность
            editDialogStage.initModality(Modality.WINDOW_MODAL);
            editDialogStage.initOwner(mainStage);                   //теперь тут mainStage
        }
        editDialogStage.showAndWait();        //меняем show на showAndWait т.к. нужно дождаться ответа пользователя
    }

    public void actionSearch(ActionEvent actionEvent) {
        addressBookImpl.getPersonList().clear();                //при нажатии на "поиск" коллекция очищается

        for (Person person : backupList) {
            if (person.getFio().toLowerCase().contains(txtSearch.getText().toLowerCase()) ||   //если содержит (contains), то добавляем и при поиске приводим к нижнему регистру
                    person.getPhone().toLowerCase().contains(txtSearch.getText().toLowerCase())) {   // то же и для телефона
                addressBookImpl.getPersonList().add(person);
            }
        }
    }

}