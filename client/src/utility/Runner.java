package utility;

import exceptions.ScriptRecursionException;
import managers.CommandManager;
import network.UDPClient;
import utility.console.Console;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Класс, который обеспечивает непрерывную работу клиента
 */

public class Runner {

    public enum ExitCode {
        OK,
        ERROR,
        EXIT,
    }

    private final Console console;
    private final CommandManager commandManager;
    private final List<String> scriptStack = new ArrayList<>();
    private final UDPClient client;

    public Runner(Console console, CommandManager commandManager, UDPClient client) {
        this.console = console;
        this.commandManager = commandManager;
        this.client = client;
    }




    public void interactiveMode() {
        var userScanner = UserData.getUserScanner();
        console.println("Введите help для справки");
        try {
            ExitCode commandStatus;
            String[] userCommand;
                do {
                    if (client.isFlagOfTimeOut()) System.out.println("");
                    console.ps1();
                    userCommand = (userScanner.nextLine().trim() + " ").split(" ", 3);
                    userCommand[1] = userCommand[1].trim();
                    if (userCommand[0].equals("advanced_remove")) userCommand[2] = userCommand[2].trim();
                    List<String> history = commandManager.getCommandHistory();
                    if (history.size() < 6) {
                        commandManager.addToHistory(userCommand[0]);
                    } else {
                        history.remove(0);
                        commandManager.addToHistory(userCommand[0]);
                    }
                    commandStatus = launchCommand(userCommand);
                } while (commandStatus != ExitCode.EXIT);

        } catch (NoSuchElementException exception) {
            console.printError("Пользовательский ввод не обнаружен!");
        } catch (IllegalStateException exception) {
            console.printError("Непредвиденная ошибка!");
        }
    }
    /**
     * Режим для запуска скрипта.
     * @param argument имя файла в котором лежит скрипт
     * @return Код завершения.
     */
    public ExitCode scriptMode(String argument) {
        String[] userCommand;
        ExitCode commandStatus;
        scriptStack.add(argument);
        try (Scanner scriptScanner = new Scanner(new File(argument))) {
            if (!scriptScanner.hasNext()) throw new NoSuchElementException();
            Scanner tmpScanner = UserData.getUserScanner();
            UserData.setUserScanner(scriptScanner);
            UserData.setFileMode();
            do {
                userCommand = (scriptScanner.nextLine().trim() + " ").split(" ", 2);
                userCommand[1] = userCommand[1].trim();
                //userCommand[0] - сама команда
                //userCommand[1] - аргументы команды
                while (scriptScanner.hasNextLine() && userCommand[0].isEmpty()) {
                    userCommand = (scriptScanner.nextLine().trim() + " ").split(" ", 2);
                    userCommand[1] = userCommand[1].trim();
                }
                console.println(console.getPS1()+String.join(" ", userCommand));
                if (userCommand[0].equals("execute_script")) {
                    for (String script : scriptStack) {
                        if (userCommand[1].equals(script)) throw new ScriptRecursionException();
                    }
                }
                commandStatus = launchCommand(userCommand);
            } while (commandStatus == ExitCode.OK && scriptScanner.hasNextLine());
            UserData.setUserScanner(tmpScanner);
            UserData.setUserMode();
            if (commandStatus == ExitCode.ERROR && !(userCommand[0].equals("execute_script") && !userCommand[1].isEmpty())) {
                console.println("Проверьте скрипт на корректность введенных данных!");
            }
            return commandStatus;
        } catch (FileNotFoundException exception) {
            console.printError("Файл со скриптом не найден!");
        } catch (NoSuchElementException exception) {
            console.printError("Файл со скриптом пуст!");
        } catch (ScriptRecursionException exception) {
            console.printError("Скрипты не могут вызываться рекурсивно!");
        } catch (IllegalStateException exception) {
            console.printError("Непредвиденная ошибка!");
            System.exit(0);
        } finally {
            scriptStack.remove(scriptStack.size() - 1);
        }
        return ExitCode.ERROR;
    }


    /**
     * @param userCommand Команда для запуска
     * @return Код завершения.
     */

    private ExitCode launchCommand(String[] userCommand) {
        if (userCommand[0].isEmpty()) return ExitCode.OK;
        var command = commandManager.getCommands().get(userCommand[0]);
        if (command == null) {
            console.printError("Команда '" + userCommand[0] + "' не найдена. Наберите 'help' для справки");
            return ExitCode.ERROR;
        }

        switch (userCommand[0]) {
            case "exit" -> {
                if (!commandManager.getCommands().get("exit").execute(userCommand)) return ExitCode.ERROR;
                else return ExitCode.EXIT;
            }

            case "execute_script" -> {
                if (!commandManager.getCommands().get("execute_script").execute(userCommand)) return ExitCode.ERROR;
                else return scriptMode(userCommand[1]);
            }
            default -> { if (!command.execute(userCommand)) return ExitCode.OK; }
        };
        return ExitCode.OK;
    }
}

