package commands;

import network.Request;
import network.Response;
import network.UDPClient;
import utility.console.Console;

import java.net.SocketTimeoutException;
import java.time.LocalDateTime;

public class Info extends Command{

    private UDPClient client;
    private Console console;

    public Info(Console console, UDPClient client) {
        super("show");
        this.client = client;
        this.console = console;
    }

    @Override
    public boolean execute(String[] arguments) {
        Request request = new Request("info");
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
