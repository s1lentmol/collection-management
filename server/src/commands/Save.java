package commands;

import managers.CollectionManager;
import network.Response;

public class Save extends Command{
    private final CollectionManager collectionManager;
    public Save(CollectionManager collectionManager) {
        super("save", "сохранить коллекцию в файл");
        this.collectionManager = collectionManager;
    }
    @Override
    public Response execute(){
        collectionManager.saveCollection();
        System.out.println("Коллекция успешно сохранена в базу данных");
        return null;
    }
}
