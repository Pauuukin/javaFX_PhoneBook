package sample.objects;

import javafx.beans.property.SimpleStringProperty;

public class Person {     //объект, который будет хранить записи фио\телефон для каждой
                          //записи внутри таблицы
   private SimpleStringProperty fio = new SimpleStringProperty("");        //меняем String -> SimpleStringProperty(класс StringProperty (можно и не инициализировать)
   private SimpleStringProperty phone = new SimpleStringProperty("");      //чтобы данные автоматически обновлялись в таблице


   public Person (String fio, String phone){   // Alt + insert для обычного конструктора
       this.fio = new SimpleStringProperty (fio);
       this.phone = new SimpleStringProperty(phone);
   }
    public Person(){
    }


   public String getFio() {                 // чтобы не писать руками get\set методы:
        return fio.get();                   //Refactor->Encapsulate Field
   }
    public void setFio(String fio) {        //меняем методы под string
        this.fio.set(fio);
    }   //стандартные get/set чуть изменили, чтоб записывать
                                                            //переменные String в SimpleStringProperty
    public String getPhone() {
        return phone.get();
    }

    public void setPhone(String phone) {
        this.phone.set(phone);
    }

    public SimpleStringProperty fioProperty(){          //два новых гетера, чтобы tableview считывала
       return fio;                                      //с их помощью значения(изменения) и отображала пользователю
    }

    public SimpleStringProperty phoneProperty(){
       return phone;
    }

    @Override
    public String toString() {             //alt+insert - toString
        return "Person{" +
                "fio='" + fio + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
