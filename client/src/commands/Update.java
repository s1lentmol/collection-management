package commands;

import data.input.PersonInput;
import exceptions.CollectionIsEmptyException;
import exceptions.IncorrectInputInScriptException;
import exceptions.InvalidFormException;
import exceptions.WrongAmountOfElementsException;
import network.Request;
import network.Response;
import network.UDPClient;
import utility.User;
import utility.console.Console;

import java.net.SocketTimeoutException;

public class Update extends Command {
    private final Console console;
    private UDPClient client;
    private String userName;
    public Update(Console console, UDPClient client, String userName) {
        super("update id {element}");
        this.console = console;
        this.client = client;
        this.userName = userName;
    }

    @Override
    public boolean execute(String[] arguments) {
        try {
            if (arguments[1].isEmpty()) throw new WrongAmountOfElementsException();
            console.println("* Введите данные обновленного человека:");
            var newPerson = (new PersonInput(console,userName)).build();
            newPerson.setId(Long.parseLong(arguments[1]));
            Request request = new Request("update",newPerson, arguments[1],userName);
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
        } catch (IncorrectInputInScriptException e) {
            e.printStackTrace();
        } catch (InvalidFormException e) {
            console.printError("Поля человкека не валидны! Человек не обновлен!");
        }
        return false;
    }
}
