package commands;

import network.Response;
import network.UDPClient;
import network.Request;
import utility.console.Console;

import java.net.SocketTimeoutException;

public class Show extends Command{
    private UDPClient client;
    private Console console;

    public Show(Console console, UDPClient client) {
        super("show");
        this.client = client;
        this.console = console;
    }

    @Override
    public boolean execute(String[] arguments) {
        Request request = new Request("show");
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
