package data;

import utility.Validatable;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class Person implements Comparable<Person>, Validatable, Serializable {
    private static final long serialVersionUID = 77L;
    private static long nextId = 1;
    private static final LocalDate rn = LocalDate.now();
    private long id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.time.LocalDate creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private double height; //Значение поля должно быть больше 0
    private Color eyeColor; //Поле не может быть null
    private Color hairColor; //Поле может быть null
    private Country nationality; //Поле не может быть null
    private Location location; //Поле может быть null

    private String userName;
    public Person(String name, Coordinates coordinates, double height, Color eyeColor, Color hairColor, Country nationality, Location location, String userName) {
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = rn;
        this.height = height;
        this.eyeColor = eyeColor;
        this.hairColor = hairColor;
        this.nationality = nationality;
        this.location = location;
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public static void setNextId(long nextId){
        Person.nextId = nextId;
    }

    public static long getNextId() {
        return nextId;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public long getId() {
        return id;
    }
    public void setId(long id){
        this.id = id;
    }
    public static void incrementNextId(){
        nextId++;
    }
    public String getName() {
        return name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public double getHeight() {
        return height;
    }

    public Color getEyeColor() {
        return eyeColor;
    }

    public Color getHairColor() {
        return hairColor;
    }

    public Country getNationality() {
        return nationality;
    }

    public Location getLocation() {
        return location;
    }

    public void update(Person person){
        this.name = person.name;
        this.coordinates = person.coordinates;
        this.creationDate = person.creationDate;
        this.height = person.height;
        this.eyeColor = person.eyeColor;
        this.hairColor = person.hairColor;
        this.nationality = person.nationality;
        this.location = person.location;
    }
    @Override
    public boolean validate() {
        if (name == null || name.isEmpty()) return false;
        if (coordinates == null) return false;
        if (creationDate == null) return false;
        if (height <= 0) return false;
        if (eyeColor == null) return false;
        if (hairColor == null) return false;
        if (nationality == null) return false;
        return location != null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return id == person.id && Double.compare(height, person.height) == 0 && Objects.equals(name, person.name) && Objects.equals(coordinates, person.coordinates) && Objects.equals(creationDate, person.creationDate) && eyeColor == person.eyeColor && hairColor == person.hairColor && nationality == person.nationality && Objects.equals(location, person.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, coordinates, creationDate, height, eyeColor, hairColor, nationality, location);
    }

    @Override
    public String toString() {
        String info = "";
        info += "\nЧеловек №" + id;
        info += " (добавлен " + creationDate.toString() + ")";
        info += "\n Имя: " + name;
        info += "\n Местоположение: " + coordinates;
        info += "\n Рост: " + height;
        info += "\n Цвет глаз: " + eyeColor;
        info += "\n Цвет волос: " + hairColor;
        info += "\n Страна: " + nationality;
        info += "\n Локация:\n    " + location;
        return info;

    }

    @Override
    public int compareTo(Person otherPerson) {
        return Double.compare(height, otherPerson.height);
    }
}
