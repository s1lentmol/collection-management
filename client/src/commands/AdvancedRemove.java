package commands;

import exceptions.WrongAmountOfElementsException;
import network.Request;
import network.Response;
import network.UDPClient;
import utility.console.Console;

public class AdvancedRemove extends Command{
    private final Console console;
    private UDPClient client;
    private String userName;

    public AdvancedRemove(Console console, UDPClient client, String userName) {
        super("remove id");
        this.console = console;
        this.client = client;
        this.userName = userName;
    }

    @Override
    public boolean execute(String[] arguments) {
        try {
            if (arguments[1].isEmpty() || arguments[2].isEmpty()) throw new WrongAmountOfElementsException();
            String[] newArguments = {arguments[1], arguments[2]};
            Request request = new Request("advanced_remove", newArguments,userName);
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
