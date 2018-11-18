package sample.interfaces.impls;

import sample.interfaces.AddressBook;
import sample.objects.Person;

import java.util.ArrayList;

public class CollectionAddressBook implements AddressBook { //создаем реализацию интерфейса,
                                                            // в этом классе мы должны реализовать
                                                            // методы, описанные в интерфейсе
    private ArrayList<Person> personList = new ArrayList<Person>();

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

    public ArrayList<Person> getPersonList(){   // геттер для коллекции
        return personList;
    }
}
