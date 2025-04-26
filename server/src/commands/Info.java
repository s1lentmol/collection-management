package commands;

import managers.CollectionManager;
import network.Response;

import java.time.LocalDateTime;

public class Info extends Command{
    private final CollectionManager collectionManager;

    public Info(CollectionManager collectionManager) {
        super("show","вывести в стандартный поток вывода все элементы коллекции в строковом представлении");
        this.collectionManager = collectionManager;
    }
    @Override
    public Response execute(){

        Response response = new Response();
        LocalDateTime lastInitTime = collectionManager.getLastInitTime();
        String lastInitTimeString = (lastInitTime == null) ? "в данной сессии инициализации еще не происходило" :
                lastInitTime.toLocalDate().toString() + " " + lastInitTime.toLocalTime().toString();
        LocalDateTime lastSaveTime = collectionManager.getLastSaveTime();
        String lastSaveTimeString = (lastSaveTime == null) ? "в данной сессии сохранения еще не происходило" :
                lastSaveTime.toLocalDate().toString() + " " + lastSaveTime.toLocalTime().toString();
        response.setMessage("Сведения о коллекции:\n" +
                " Тип: " + collectionManager.collectionType()+"\n" +" Количество элементов: " + collectionManager.collectionSize() +
                "\n" + " Дата последнего сохранения: " + lastSaveTimeString + "\n" + " Дата последней инициализации: " + lastInitTimeString);

        return response;
    }
}
