package managers;

import data.Person;
import java.time.LocalDateTime;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Класс для взаимодействия с коллекцией
 */
public class CollectionManager {
    private Deque<Person> collection = new ArrayDeque<>();
    private LocalDateTime lastInitTime;
    private LocalDateTime lastSaveTime;
    private final DataBaseManager manager;

    private final ReentrantLock lock;

    public CollectionManager(DataBaseManager manager) {
        this.lastInitTime = LocalDateTime.now();;
        this.lastSaveTime = null;
        this.manager = manager;
        this.lock = new ReentrantLock();
        loadCollection();
    }
    public LocalDateTime getLastInitTime() {
        return lastInitTime;
    }

    public LocalDateTime getLastSaveTime() {
        return lastSaveTime;
    }
    public String collectionType() {
        return collection.getClass().getName();
    }

    public Deque<Person> getCollection(){
        return collection;
    }
    public int collectionSize(){
        return collection.size();
    }
    public Person getById(long id){
        for (Person person : collection){
            if (person.getId() == id) return person;
        }
        return null;
    }
    public boolean checkExist(int id){
        for (Person person : collection){
            if (person.getId() == id) return true;
        }
        return false;
    }
    public void addToCollection(Person person){
        lock.lock();
        try {
            collection.add(person);
        } finally {
            lock.unlock();
        }
    }
    public void removeFromCollection(Person person){
        lock.lock();
        try{
            collection.remove(person);
        } finally {
            lock.unlock();
        }
    }
    public void clearCollection(){
        collection.clear();
    }
    public Person getLast() {
        if (collection.isEmpty()) return null;
        return collection.getLast();
    }
    /*
    Сохраняет коллекцию в файл - делать при exit!
     */
    public void saveCollection(){
        lock.lock();
        try {
            manager.writeCollection(collection, DataBaseManager.getConnection());
            lastSaveTime = LocalDateTime.now();
        } finally {
            lock.unlock();
        }
    }
    /*
    Загружает коллекцию из файла.
     */
    private void loadCollection(){
        lock.lock();
        try {
            collection = manager.collectionEntry(DataBaseManager.getConnection());
            lastInitTime = LocalDateTime.now();
        } finally {
            lock.unlock();
        }
    }
    @Override
    public String toString() {
        if (collection.isEmpty()) return "Коллекция пуста!";
        Person last = getLast();
        StringBuilder info = new StringBuilder();
        for (Person person : collection) {
            info.append(person);
            if (person != last) info.append("\n\n");
        }
        return info.toString();
    }

}
