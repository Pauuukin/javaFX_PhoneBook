package sample.interfaces;

import sample.objects.Person;

public interface AddressBook {
    //добавить объект типа Person
    void add(Person person);

    //изменить значения
    void update (Person person);

    //удалить объект типа Person
    void delete (Person person);

}
