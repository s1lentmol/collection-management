package commands;

import managers.CollectionManager;
import network.Response;

public class Show extends Command{
    private final CollectionManager collectionManager;

    public Show(CollectionManager collectionManager) {
        super("show","вывести в стандартный поток вывода все элементы коллекции в строковом представлении");
        this.collectionManager = collectionManager;
    }
    @Override
    public Response execute(){
        Response response = new Response();
        response.setMessage(collectionManager.toString());
        return response;
    }
}
