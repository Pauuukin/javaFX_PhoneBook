package sample.interfaces;

import sample.objects.Person;

public interface AddressBook {
    //добавить запись
    void add(Person person);

    //изменить значения
    void update (Person person);

    //удалить запись
    void delete (Person person);

}
