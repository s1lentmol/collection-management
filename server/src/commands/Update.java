package commands;

import data.Person;
import managers.CollectionManager;
import managers.DataBaseManager;
import network.Response;

import java.sql.*;
import java.time.LocalDate;

public class Update extends Command{
    private final CollectionManager collectionManager;

    public Update(CollectionManager collectionManager) {
        super("update id {element}", "обновить значение элемента коллекции, id которого равен заданному");
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(Person person, String userName){
        try {
            Response response = new Response();
            long id = person.getId();
            Connection connection = DataBaseManager.getConnection();
            String checkingBelong = "SELECT * FROM person WHERE id=? AND username=?";
            PreparedStatement checkingBelongStatement = connection.prepareStatement(checkingBelong);
            checkingBelongStatement.setLong(1,id);
            checkingBelongStatement.setString(2,userName);
            ResultSet resultSet = checkingBelongStatement.executeQuery();
            if(!resultSet.next()){
                response.setMessage("Вы не можете обновить данный объект, так как его добавили не вы или объекта c таким id в коллекции нет!");
                return response;
            }
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
            String sql = "UPDATE person SET name=?, x=?, y=?, creationDate=?,height=?,eyeColor=?,hairColor=?,nationality=?,location_x=?,location_y=?,location_name=? WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(12,id);
            preparedStatement.setString(1,name);
            preparedStatement.setDouble(2,x);
            preparedStatement.setFloat(3,y);
            preparedStatement.setDate(4, Date.valueOf(creationDate));
            preparedStatement.setDouble(5,height);
            preparedStatement.setString(6,eyeColor);
            preparedStatement.setString(7,hairColor);
            preparedStatement.setString(8,nationality);
            preparedStatement.setFloat(9,location_x);
            preparedStatement.setInt(10,location_y);
            preparedStatement.setString(11,location_name);
            int result = preparedStatement.executeUpdate();
            if (result>0){
                Person personToUpdate = collectionManager.getById(person.getId());
                personToUpdate.update(person);
                response.setMessage("Человек успешно обновлен!");
                return response;
            }
            else{
                response.setMessage("Человека с таким ID коллекции нет!");
                return response;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
