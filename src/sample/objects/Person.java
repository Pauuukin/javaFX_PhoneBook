package sample.objects;

public class Person {     //объект, который будет хранить записи фио\телефон для каждой
                          //записи внутри таблицы
   private String fio;
   private String phone;

   public Person (String fio, String phone){   // Alt + insert
       this.fio = fio;
       this.phone = phone;
   }



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

    @Override
    public String toString() {             //alt+insert - toString
        return "Person{" +
                "fio='" + fio + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
