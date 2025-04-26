import commands.*;
import managers.CommandManager;
import managers.UserManager;
import network.UDPClient;
import utility.Runner;
import utility.User;
import utility.UserData;
import utility.console.Console;
import utility.console.StandartConsole;
import java.net.UnknownHostException;
import java.util.Scanner;


public class Client {

    public static void main(String[] args) throws UnknownHostException {
        UserData.setUserScanner(new Scanner(System.in));
        Console console = new StandartConsole();
        UDPClient client = new UDPClient();

        //регистрация
        UserManager userManager = new UserManager(console);
        boolean askVariant = true;
        String variant;
        User user = null;
        while (askVariant) {
            variant = userManager.askVariant();
            if ((variant.equals("ошибка"))) askVariant = true;
            else if(variant.equals("1") || variant.equals("2")) {
                user = userManager.build();
                Registry registry = new Registry(console, client, user, variant);
                boolean registryFlag;
                registryFlag = registry.execute(new String[]{""});
                if (!registryFlag){
                    askVariant = true;
                }
                else{
                    askVariant = false;
                }
            }
        }
        CommandManager commandManager = new CommandManager();
        commandManager.register("add", new Add(console, client, user.getUserName()));
        commandManager.register("show", new Show(console, client));
        commandManager.register("clear", new Clear(console, client, user.getUserName()));
        commandManager.register("help", new Help(console, client));
        commandManager.register("exit", new Exit(console));
        commandManager.register("info", new Info(console, client));
        commandManager.register("execute_script", new ExecuteScript(console, client));
        commandManager.register("history", new History(client, console, commandManager));
        commandManager.register("remove", new Remove(console, client, user.getUserName()));
        commandManager.register("update", new Update(console, client,user.getUserName()));
        commandManager.register("filter_contains_name", new FilterContaintName(console, client));
        commandManager.register("advanced_remove", new AdvancedRemove(console, client, user.getUserName()));
        Runner runner = new Runner(console, commandManager, client);
        runner.interactiveMode();
    }
}
