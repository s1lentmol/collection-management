package commands;

import managers.CommandManager;
import network.Request;
import network.Response;
import network.UDPClient;
import utility.console.Console;

import java.net.SocketTimeoutException;

public class Help extends Command{
    private UDPClient client;
    private Console console;

    public Help(Console console, UDPClient client) {
        super("show");
        this.client = client;
        this.console = console;
    }

    @Override
    public boolean execute(String[] arguments) {
        Request request = new Request("help");
        Response response = client.sendAndReceiveData(request);
        if (!(response==null)) {
            console.println(response.getMessage());
            return true;
        }
        else {
            return false;
        }
    }
}
