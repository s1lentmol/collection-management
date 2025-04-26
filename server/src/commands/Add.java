package commands;

import data.Person;
import managers.CollectionManager;
import managers.DataBaseManager;
import network.Response;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;


public class Add extends Command{
    private final CollectionManager collectionManager;

    public Add(CollectionManager collectionManager) {
        super("add {element}", "добавить новый элемент в коллекцию");
        this.collectionManager = collectionManager;
    }
    @Override
    public Response execute(Person person){
            try {
                if (!(collectionManager.getCollection().isEmpty())) {
                    Person.incrementNextId();
                    person.setId(Person.getNextId());
                } else {
                    person.setId(Person.getNextId());
                }
                Connection connection = DataBaseManager.getConnection();
                long id = person.getId();
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
                preparedStatement.setLong(1, id);
                preparedStatement.setString(2, name);
                preparedStatement.setDouble(3, x);
                preparedStatement.setFloat(4, y);
                preparedStatement.setDate(5, Date.valueOf(creationDate));
                preparedStatement.setDouble(6, height);
                preparedStatement.setString(7, eyeColor);
                preparedStatement.setString(8, hairColor);
                preparedStatement.setString(9, nationality);
                preparedStatement.setFloat(10, location_x);
                preparedStatement.setInt(11, location_y);
                preparedStatement.setString(12, location_name);
                preparedStatement.setString(13, userName);
                int result = preparedStatement.executeUpdate();
                connection.close();
                if (result > 0) collectionManager.addToCollection(person);
                Response response = new Response();
                response.setMessage("Объект успешно добавлен в коллекцию!");
                return response;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
    }
}

