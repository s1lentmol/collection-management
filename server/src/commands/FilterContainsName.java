package commands;

import data.Person;
import managers.CollectionManager;
import network.Response;

import java.util.List;
import java.util.stream.Collectors;

public class FilterContainsName extends Command{
    private final CollectionManager collectionManager;

    public FilterContainsName(CollectionManager collectionManager) {
        super("filter_contains_name name", "вывести элементы, значение поля name которых содержит заданную подстроку");
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(String arg){
        var people = filterByName(arg);
        Response response = new Response();
        if (people.isEmpty()) {
            response.setMessage("Людей, чьи имена содержат '" + arg + "' не обнаружено.");
            return response;
        } else {
            StringBuilder humans = new StringBuilder();
            for (Person person : people){
                humans.append(person.toString());
            }
            response.setMessage("Людей, чьи имена содержат '" + arg + "' обнаружено " + people.size() + " шт.\n" + humans);
            //people.forEach(console::println);
            return response;
        }
    }

    private List<Person> filterByName(String nameSubstring) {
        return collectionManager.getCollection().stream()
                .filter(person -> (person.getName() != null && person.getName().contains(nameSubstring)))
                .collect(Collectors.toList());
    }
}
