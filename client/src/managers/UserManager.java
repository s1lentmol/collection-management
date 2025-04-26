package managers;

import exceptions.MustBeNotEmptyException;
import utility.User;
import utility.UserData;
import utility.console.Console;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.NoSuchElementException;

public class UserManager {
    private Console console;

    public UserManager(Console console) {
        this.console = console;
    }


    public String askVariant(){
        console.println("Чтобы начать работать с приложением, необходимо авторизоваться либо войти. Введите 1, если хотите авторизоваться, введите 2, если хотите войти");
        var userScanner = UserData.getUserScanner();
        boolean work = true;
        while(work) {
            console.ps1();
            String variant = userScanner.nextLine().trim();
            if (!(variant.equals("1") || variant.equals("2"))){
                console.println("Неверный ввод. Введите 1 или 2");
                return "ошибка";
            }
            return variant;
        }
        return null;
    }


    public User build(){
        User user = new User(
                askUserName(),
                askPassword()
        );
        return user;
    }


    public String askUserName(){
        String userName;
        while (true){
            try {
                console.println("Введите имя пользователя: ");
                console.ps2();
                userName = UserData.getUserScanner().nextLine().trim();
                if (userName.isEmpty()) throw new MustBeNotEmptyException();
                break;
            } catch (NoSuchElementException e){
                console.printError("Имя пользователя не распознано!");
            } catch (MustBeNotEmptyException e) {
                console.printError("Имя пользователя не может быть пустым!");
            } catch (IllegalStateException e) {
                console.printError("Непредвиденная ошибка!");
                System.exit(0);
            }
        }
        return userName;
    }
    public String askPassword(){
        String password;
        String pepper = "K*-23>>@_45k2!l?M";
        while (true){
            try {
                console.println("Введите пароль: ");
                console.ps2();
                password = UserData.getUserScanner().nextLine().trim();
                if (password.isEmpty()) throw new MustBeNotEmptyException();
                break;
            } catch (NoSuchElementException e){
                console.printError("Пароль не распознан!");
            } catch (MustBeNotEmptyException e) {
                console.printError("Пароль не может быть пустым!");
            } catch (IllegalStateException e) {
                console.printError("Непредвиденная ошибка!");
                System.exit(0);
            }
        }
        return UserManager.hashString((password+pepper));
    }
    public static String hashString(String input) {
        try {
            // Создаем объект MessageDigest для SHA-512
            MessageDigest digest = MessageDigest.getInstance("SHA-512");

            // Получаем байты из входной строки
            byte[] inputBytes = input.getBytes();

            // Вычисляем хэш
            byte[] hashedBytes = digest.digest(inputBytes);

            // Преобразуем байты в строку в шестнадцатеричном формате
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashedBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}
