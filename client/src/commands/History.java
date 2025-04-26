package commands;

import exceptions.HistoryIsEmptyException;
import managers.CommandManager;
import network.UDPClient;
import utility.console.Console;

public class History extends Command {
    private UDPClient client;
    private Console console;
    private final CommandManager commandManager;

    public History(UDPClient client, Console console, CommandManager commandManager) {
        super("history");
        this.client = client;
        this.console = console;
        this.commandManager = commandManager;
    }

    @Override
    public boolean execute(String[] arguments) {
        try {
            if (!commandManager.printCommandHistory().isEmpty()) console.println(commandManager.printCommandHistory());
            else throw new HistoryIsEmptyException();
        } catch (HistoryIsEmptyException e){
            console.println("История комманд пуста!");
        }
        return true;
    }
}