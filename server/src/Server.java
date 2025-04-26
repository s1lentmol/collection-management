import commands.*;
import managers.CollectionManager;
import managers.CommandManager;
import managers.DataBaseManager;
import network.UDPServer;
import utility.Runner;
import java.io.IOException;

public class Server {
    public static void main(String[] args) {
        try {
            DataBaseManager dataBaseManager = new DataBaseManager();
            CommandManager commandManager = new CommandManager();
            CollectionManager collectionManager = new CollectionManager(dataBaseManager);
            UDPServer server = new UDPServer();
            commandManager.register("add", new Add(collectionManager));
            commandManager.register("show", new Show(collectionManager));
            commandManager.register("info", new Info(collectionManager));
            commandManager.register("clear", new Clear(collectionManager));
            commandManager.register("help", new Help(commandManager));
            commandManager.register("save", new Save(collectionManager));
            commandManager.register("remove", new Remove(collectionManager));
            commandManager.register("update", new Update(collectionManager));
            commandManager.register("filter_contains_name", new FilterContainsName(collectionManager));
            commandManager.register("advanced_remove", new AdvancedRemove(collectionManager));
            commandManager.register("registry", new Registry());
            Runner runner = new Runner(commandManager, server);
            runner.run();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
