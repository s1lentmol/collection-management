package commands;

import data.Person;
import exceptions.NotFoundException;
import managers.CollectionManager;
import managers.DataBaseManager;
import network.Response;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdvancedRemove extends Command {
    private final CollectionManager collectionManager;

    public AdvancedRemove(CollectionManager collectionManager) {
        super("remove id", "удалить элемент из коллекции по его id");
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(String[] args, String userName) {
        try {
            args[0] = args[0].trim();
            args[1] = args[1].trim();
            long id1 = Long.parseLong(args[0]);
            long id2 = Long.parseLong(args[1]);
            Response response = new Response();
            for (long i = id2; i >= id1; i--) {
                Remove remove = new Remove(collectionManager);
                String removeArgument = String.valueOf(i);
                Response result = remove.execute(removeArgument,userName);
                response.setMessage(result.getMessage());
            }
            return response;
        } catch (NumberFormatException e) {
            throw new RuntimeException(e);
        }
    }
}
