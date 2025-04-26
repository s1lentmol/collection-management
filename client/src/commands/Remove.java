package commands;

import exceptions.WrongAmountOfElementsException;
import network.Request;
import network.Response;
import network.UDPClient;
import utility.console.Console;

import java.net.SocketTimeoutException;

public class Remove extends Command{
    private final Console console;
    private UDPClient client;
    private String userName;

    public Remove(Console console, UDPClient client, String userName) {
        super("remove id");
        this.console = console;
        this.client = client;
        this.userName = userName;
    }

    @Override
    public boolean execute(String[] arguments) {
        try {
            if (arguments[1].isEmpty()) throw new WrongAmountOfElementsException();
            Request request = new Request("remove", arguments[1],userName);
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
