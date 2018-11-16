package sample.objects;

public class Person {     //будет хранить записи фио\телефон для каждой
                          //записи внутри таблицы
   private String fio;
   private String phone;

    public String getFio() {          // чтобы не писать руками get\set методы:
        return fio;                   //Refactor->Encapsulate Field
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
