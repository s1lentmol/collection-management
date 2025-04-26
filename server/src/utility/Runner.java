package utility;

import managers.CommandManager;
import network.Request;
import network.Response;
import network.UDPServer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;


// класс, обеспечивающий непрерывную работу сервера
public class Runner {
    private final CommandManager commandManager;
    private final UDPServer server;
    private final ExecutorService executorService;
    ReentrantLock lock;



    public Runner(CommandManager commandManager, UDPServer server) {
        this.commandManager = commandManager;
        this.server = server;
        this.executorService = Executors.newFixedThreadPool(3); // Создаем пул из 3 потоков
        this.lock = new ReentrantLock();
    }

    public void run() {
        boolean running = true;
        while (running) {
            lock.lock();
            try {
                Request request = server.receiveData(); // Получаем запрос от клиента
                // Задача для выполнения в отдельном потоке
                Runnable task = () -> {
                    String[] userCommand = new String[]{request.getCommandName(), request.getCommandArguments()};
                    switch (userCommand[0]) {
                        case "add" -> {
                            Response response = commandManager.getCommands().get("add").execute(request.getPerson());
                            server.sendData(response);
                        }
                        case "show" -> {
                            Response response = commandManager.getCommands().get("show").execute();
                            server.sendData(response);
                        }
                        case "clear" -> {
                            Response response = commandManager.getCommands().get("clear").execute(request.getCommandArguments());
                            server.sendData(response);
                        }
                        case "help" -> {
                            Response response = commandManager.getCommands().get("help").execute();
                            server.sendData(response);
                        }
                        case "info" -> {
                            Response response = commandManager.getCommands().get("info").execute();
                            server.sendData(response);
                        }
                        case "remove" -> {
                            Response response = commandManager.getCommands().get("remove").execute(request.getCommandArguments(), request.getUserName());
                            server.sendData(response);
                        }
                        case "update" -> {
                            Response response = commandManager.getCommands().get("update").execute(request.getPerson(),request.getUserName());
                            server.sendData(response);
                        }
                        case "filter_contains_name" -> {
                            Response response = commandManager.getCommands().get("filter_contains_name").execute(request.getCommandArguments());
                            server.sendData(response);
                        }
                        case "advanced_remove" -> {
                            Response response = commandManager.getCommands().get("advanced_remove").execute(request.getMoreArguments(),request.getUserName());
                            server.sendData(response);
                        }
                        case "registry" -> {
                            Response response = commandManager.getCommands().get("registry").execute(request.getUser(),request.getVariant());
                            server.sendData(response);
                        }
                    }
                };
                executorService.execute(task); // Запускаем задачу в пуле потоков
            } catch (Exception e) {
                e.printStackTrace();
                running = false;
            } finally {
                lock.unlock();
            }
        }
        executorService.shutdown(); // Выключаем ExecutorService после остановки сервера
    }

}
    /*
    private final CommandManager commandManager;

    private final UDPServer server;

    public Runner(CommandManager commandManager, UDPServer server) {
        this.commandManager = commandManager;
        this.server = server;
    }
    public void run() {
        boolean running = true;
        // Поток для обработки команд от клиента
        while (running) {
            Request request = server.receiveData();
            String[] userCommand = new String[]{request.getCommandName(), request.getCommandArguments()};
            switch (userCommand[0]) {
                case "add" -> {
                    Response response = commandManager.getCommands().get("add").execute(request.getPerson());
                    server.sendData(response);
                }
                case "show" -> {
                    Response response = commandManager.getCommands().get("show").execute();
                    server.sendData(response);
                }
                case "clear" -> {
                    Response response = commandManager.getCommands().get("clear").execute(request.getCommandArguments());
                    server.sendData(response);
                }
                case "help" -> {
                    Response response = commandManager.getCommands().get("help").execute();
                    server.sendData(response);
                }
                case "info" -> {
                    Response response = commandManager.getCommands().get("info").execute();
                    server.sendData(response);
                }
                case "remove" -> {
                    Response response = commandManager.getCommands().get("remove").execute(request.getCommandArguments(), request.getUserName());
                    server.sendData(response);
                }
                case "update" -> {
                    Response response = commandManager.getCommands().get("update").execute(request.getPerson(),request.getUserName());
                    server.sendData(response);
                }
                case "filter_contains_name" -> {
                    Response response = commandManager.getCommands().get("filter_contains_name").execute(request.getCommandArguments());
                    server.sendData(response);
                }
                case "advanced_remove" -> {
                    Response response = commandManager.getCommands().get("advanced_remove").execute(request.getMoreArguments(),request.getUserName());
                    server.sendData(response);
                }
                case "registry" -> {
                    Response response = commandManager.getCommands().get("registry").execute(request.getUser(),request.getVariant());
                    server.sendData(response);
                }
            }
        }
    }

}
     */


