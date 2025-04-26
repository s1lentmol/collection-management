package commands;

import utility.console.Console;

public class Exit extends Command{

    private final Console console;

    public Exit(Console console) {
        super("exit");
        this.console = console;
    }
    /**
     * Выполняет команду
     * @return Успешность выполнения команды.
     */
    @Override
    public boolean execute(String[] arguments) {
        console.println("Завершение выполнения...");
        return true;
    }
}
