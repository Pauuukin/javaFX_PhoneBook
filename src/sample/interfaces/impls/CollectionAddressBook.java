package sample.interfaces.impls;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.interfaces.AddressBook;
import sample.objects.Person;

import java.util.ArrayList;

public class CollectionAddressBook implements AddressBook { //создаем реализацию интерфейса,
                                                            // в этом классе мы должны реализовать
                                                            // методы, описанные в интерфейсе


    private ObservableList<Person> personList = FXCollections.observableArrayList(); //теперь здесь имеем observableArrayListWrapper
                                                                                     //который содержит в себе ArrayList и добавляет новые методы

    @Override
    public void add (Person person){
        personList.add(person);
    }

    @Override
    public void update(Person person){
        // в коллекции не нужно обновлять данные, пригодится для БД
    }

    @Override
    public void delete (Person person){
        personList.remove(person);
    }

    public ObservableList<Person> getPersonList(){   // геттер для коллекции
        return personList;
    }




    public void print(){
        int number=0;
        System.out.println();
        for(Person person : personList){
            number++;
            System.out.println(number+") Fio= "+ person.getFio()+" phone = " +person.getPhone());
        }
    }


    public void fillTestData(){
        personList.add(new Person("Ivan", "123"));
        personList.add(new Person("igor", "234"));
        personList.add(new Person("Petya", "345"));
        personList.add(new Person("Kolya", "456"));
        personList.add(new Person("Vasya", "567"));
        personList.add(new Person("Oleg", "678"));
        personList.add(new Person("Sasha", "123"));


    }
}


