package commands;

import exceptions.WrongAmountOfElementsException;
import network.Request;
import network.Response;
import network.UDPClient;
import utility.console.Console;

public class FilterContaintName extends Command{
    private final Console console;
    private UDPClient client;

    public FilterContaintName(Console console, UDPClient client) {
        super("filter_contains_name");
        this.console = console;
        this.client = client;
    }

    @Override
    public boolean execute(String[] arguments) {
        try {
            if (arguments[1].isEmpty()) throw new WrongAmountOfElementsException();
            console.println(arguments[1]);
            Request request = new Request("filter_contains_name", arguments[1]);
            Response response = client.sendAndReceiveData(request);
            if (!(response==null)) {
                console.println(response.getMessage());
                return true;
            }
            else {
                return false;
            }
        } catch (WrongAmountOfElementsException e) {
            console.println("Неверное количество аргументов!");
        }
        return false;
    }
}
