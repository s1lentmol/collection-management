package commands;

import network.UDPClient;
import utility.console.Console;

public class ExecuteScript extends Command {
    private UDPClient client;
    private Console console;
    public ExecuteScript(Console console, UDPClient client) {
        super("execute_script file_name");
        this.console = console;
        this.client = client;
    }

    /**
     * Выполняет команду
     * @return Успешность выполнения команды.
     */
    @Override
    public boolean execute(String[] arguments) {
        console.println("Выполнение скрипта '" + arguments[1] + "'...");
        return true;
    }
}
