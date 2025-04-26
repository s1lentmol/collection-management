package commands;
import network.Request;
import network.Response;
import network.UDPClient;
import utility.User;
import utility.console.Console;

public class Registry extends Command{
    private final Console console;
    private UDPClient client;
    private User user;
    private String variant;
    public Registry(Console console, UDPClient client, User user, String variant) {
        super("registry");
        this.console = console;
        this.client = client;
        this.user = user;
        this.variant = variant;
    }

    // если execute вернул false - то успешная регистрация

    @Override
    public boolean execute(String[] arguments) {
        if (variant.equals("1")) {
            Request request = new Request("registry", user, "1");
            Response response = client.sendAndReceiveData(request);
            if (!(response == null)) {
                if (response.getMessage().equals("Пользователь с таким именем уже существует! Измените имя на другое")) {
                    console.println(response.getMessage());
                    return false;
                } else {
                    console.println(response.getMessage());
                    return true;
                }
            }
        } else if (variant.equals("2")) {
            Request request = new Request("registry", user, "2");
            Response response = client.sendAndReceiveData(request);
            if (!(response == null)){
                if (response.getMessage().equals("Вы успешно вошли!")) {
                    console.println(response.getMessage());
                    return true;
                }
            console.println(response.getMessage());
            if (response.getMessage().equals("Неверное имя пользователя или пароль")) {
                return false;
            }
        }
    }
        else {
            console.println("Неверный ввод. Введите 1 или 2");
            return false;
        }
    return false;
}
}

