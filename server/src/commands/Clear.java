package commands;

import data.Person;
import managers.CollectionManager;
import managers.DataBaseManager;
import network.Response;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Clear extends Command {
    private final CollectionManager collectionManager;

    public Clear(CollectionManager collectionManager) {
        super("clear", "очистить коллекцию");
        this.collectionManager = collectionManager;
    }

    public Response execute(String userName) {
        try {
            Connection connection = DataBaseManager.getConnection();

            String clearCollectionSql = "SELECT * FROM person WHERE username=?";
            PreparedStatement clearCollectionStatement = connection.prepareStatement(clearCollectionSql);
            clearCollectionStatement.setString(1, userName);
            ResultSet resultSet = clearCollectionStatement.executeQuery();
            while (resultSet.next()) {
                int curId = resultSet.getInt("id");
                Person personToRemove = collectionManager.getById(curId);
                collectionManager.removeFromCollection(personToRemove);
            }
            String sql = "DELETE FROM person WHERE username = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, userName);
            preparedStatement.execute();

            Response response = new Response();
            response.setMessage("Удалены все объекты коллекции, принадлежащие вам!");
            return response;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}