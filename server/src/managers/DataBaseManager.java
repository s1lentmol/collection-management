package managers;


import data.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.sql.Date;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.*;

// класс для взаимодействия с базой данных Person
public class DataBaseManager {
    public static Connection getConnection(){
        try {
            Properties info = new Properties();

            info.load(new FileInputStream("db.cfg"));

            String dataBaseUrl = "jdbc:postgresql://localhost:5432/lab7";

            Connection connection = DriverManager.getConnection(dataBaseUrl, info);
            return connection;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    // Метод для сохранения коллекции в базу данных
    public void writeCollection(Deque<Person> collection, Connection connection){
        try {
        for (Person person : collection) {
            long id = person.getId();
            String checking = "SELECT * FROM person WHERE id=?";
            PreparedStatement check = connection.prepareStatement(checking);
            check.setLong(1,id);
            ResultSet resultSet = check.executeQuery();
            if (resultSet.next()){
            }
            else{
                String name = person.getName();
                Double x = person.getCoordinates().getX();
                float y = person.getCoordinates().getY();
                LocalDate creationDate = person.getCreationDate();
                double height = person.getHeight();
                String eyeColor = String.valueOf(person.getEyeColor());
                String hairColor = String.valueOf(person.getHairColor());
                String nationality = String.valueOf(person.getNationality());
                Float location_x = person.getLocation().getX();
                Integer location_y = person.getLocation().getY();
                String location_name = person.getLocation().getName();
                String userName = person.getUserName();
                String sql = "INSERT INTO person(id,name,x,y,creationDate,height,eyeColor, hairColor, nationality, location_x, location_y, location_name,username) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setLong(1,id);
                preparedStatement.setString(2,name);
                preparedStatement.setDouble(3,x);
                preparedStatement.setFloat(4,y);
                preparedStatement.setDate(5, Date.valueOf(creationDate));
                preparedStatement.setDouble(6,height);
                preparedStatement.setString(7,eyeColor);
                preparedStatement.setString(8,hairColor);
                preparedStatement.setString(9,nationality);
                preparedStatement.setFloat(10,location_x);
                preparedStatement.setInt(11,location_y);
                preparedStatement.setString(12,location_name);
                preparedStatement.setString(13,userName);
                preparedStatement.execute();
            }
            }
        } catch (SQLException e) {
            System.out.println("SQL ошибка!");
        }
    }




    // сохранение коллекции из бд
    public Deque<Person> collectionEntry(Connection connection){
        try {
            Deque<Person> people = new ArrayDeque<>();
            ArrayList<Long> ids = new ArrayList<>();
            boolean flag = false;
            String sql = "SELECT * FROM person";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                try {
                    long id = resultSet.getInt("id");
                    ids.add(id);
                    String name = resultSet.getString("name");
                    Double x = resultSet.getDouble("x");
                    float y = resultSet.getFloat("y");
                    Coordinates c1 = new Coordinates(x, y);
                    LocalDate creationDate = resultSet.getDate("creationDate").toLocalDate();
                    double height = resultSet.getDouble("height");
                    Color eyeColor = Color.valueOf(resultSet.getString("eyeColor"));
                    Color hairColor = Color.valueOf(resultSet.getString("hairColor"));
                    Country nationality = Country.valueOf(resultSet.getString("nationality"));
                    Float location_x = resultSet.getFloat("location_x");
                    Integer location_y = resultSet.getInt("location_y");
                    String location_name = resultSet.getString("location_name");
                    String userName = resultSet.getString("username");
                    Location l1 = new Location(location_x, location_y, location_name);
                    Person person = new Person(name, c1, height, eyeColor, hairColor, nationality, l1,userName);
                    person.setId(id);
                    person.setCreationDate(creationDate);
                    people.add(person);
                } catch (IllegalArgumentException | DateTimeException e) {
                    people.clear();
                    flag = true;
                    break;
                }
            }
            long maxId;
            if (ids.isEmpty()){
                maxId = 1;
            } else{
                maxId = Collections.max(ids);
            }
            Person.setNextId(maxId);
            if (hasDuplicates(ids)) throw new IllegalArgumentException();
            if (!flag) {
                System.out.println("Сервер запущен, коллекция успешно загружена");
            }
            return people;

        } catch (SQLException e) {
            System.out.println("SQL ошибка!");
        }
        return new ArrayDeque<>();
    }

    public static <T> boolean hasDuplicates(ArrayList<T> list) {
        HashSet<T> set = new HashSet<>(list);
        return set.size() < list.size();
    }

}
