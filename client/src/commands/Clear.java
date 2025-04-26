package commands;

import network.Request;
import network.Response;
import network.UDPClient;
import utility.console.Console;

import java.net.SocketTimeoutException;

public class Clear extends Command{
    private UDPClient client;
    private Console console;
    private String userName;

    public Clear(Console console, UDPClient client, String userName) {
        super("clear2");
        this.client = client;
        this.console = console;
        this.userName = userName;
    }

    @Override
    public boolean execute(String[] arguments) {
        Request request = new Request("clear",userName);
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
