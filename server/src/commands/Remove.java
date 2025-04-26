package commands;

import data.Person;
import exceptions.NotFoundException;
import managers.CollectionManager;
import managers.DataBaseManager;
import network.Response;

import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Remove extends Command{

    private final CollectionManager collectionManager;

    public Remove(CollectionManager collectionManager) {
        super("remove id", "удалить элемент из коллекции по его id");
        this.collectionManager = collectionManager;
    }
    @Override
    public Response execute(String arg, String userName){
        try {
            Response response = new Response();
            long id = Long.parseLong(arg);
            Person personToRemove = collectionManager.getById(id);
            Connection connection = DataBaseManager.getConnection();
            String checkingBelong = "SELECT * FROM person WHERE id=? AND username=?";
            PreparedStatement checkingBelongStatement = connection.prepareStatement(checkingBelong);
            checkingBelongStatement.setLong(1,id);
            checkingBelongStatement.setString(2,userName);
            ResultSet resultSet = checkingBelongStatement.executeQuery();
            if(!resultSet.next()){
                response.setMessage("Вы не можеет удалить данный объект, так как его добавили не вы!");
                if (personToRemove == null) {
                    response.setMessage("Человека с таким ID в коллекции нет!");
                    return response;
                }
                return response;
            }
            String sql = "DELETE FROM person WHERE id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1,id);
            int result = preparedStatement.executeUpdate();
            if (result>0) collectionManager.removeFromCollection(personToRemove);
            if (personToRemove == null) {
                response.setMessage("Человека с таким ID в коллекции нет!");
                return response;
            }
            else {
                response.setMessage("Объекты успешно удалены!");
            }
            return response;
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
