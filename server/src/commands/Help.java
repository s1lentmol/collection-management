package commands;

import managers.CollectionManager;
import managers.CommandManager;
import network.Response;

public class Help extends Command{
    private final CommandManager commandManager;


    public Help(CommandManager commandManager) {
        super("show","вывести в стандартный поток вывода все элементы коллекции в строковом представлении");
        this.commandManager = commandManager;
    }
    @Override
    public Response execute(){
        Response response = new Response();
        //response.setMessage(collectionManager.toString());
        commandManager.getCommands().values().forEach(command -> {
            response.setMessage(" add {element}                                добавить новый элемент в коллекцию\n" +
                                " execute_script file_name                     исполнить скрипт из указанного файла\n" +
                                " show                                         вывести в стандартный поток вывода все элементы коллекции в строковом представлении\n" +
                                " clear                                        очистить коллекцию\n" +
                                " update id {element}                          обновить значение элемента коллекции, id которого равен заданному\n" +
                                " history                                      вывести последние 5 команд (без их аргументов)\n" +
                                " remove id                                    удалить элемент из коллекции по его id\n" +
                                " help                                         вывести справку по доступным командам\n" +
                                " exit                                         завершить программу (без сохранения в файл)\n" +
                                " filter_contains_name name                    вывести элементы, значение поля name которых содержит заданную подстроку\n" +
                                " info                                         вывести информацию о коллекции\n"+
                                " advanced_remove id1 id2                      удалить элементы коллекци с id1 по id2");
        });
        return response;
    }
}
