package commands;
import data.Person;
import data.input.PersonInput;
import exceptions.IncorrectInputInScriptException;
import exceptions.InvalidFormException;
import network.UDPClient;
import network.Request;
import network.Response;
import utility.User;
import utility.console.Console;

import java.net.SocketTimeoutException;

public class Add extends Command {
    private final Console console;
    private UDPClient client;
    private String userName;
    public Add(Console console, UDPClient client, String userName) {
        super("add {element}");
        this.console = console;
        this.client = client;
        this.userName = userName;
    }

    public boolean execute(String[] arguments) {
        try {
            PersonInput personInput = new PersonInput(console,userName);
            Person newPerson = personInput.build();
            if (!newPerson.validate()) throw new InvalidFormException();
            else {
                Request request = new Request("add", newPerson, "");
                Response response = client.sendAndReceiveData(request);
                if (!(response==null)) {
                    console.println(response.getMessage());
                    return true;
                }
                else {
                    return false;
                }
            }
        } catch (IncorrectInputInScriptException e) {
            console.println("Введены некорректные данные!");
        } catch (InvalidFormException e) {
            console.println("Поля человека не валидны!");
        }
        return false;
    }
}

