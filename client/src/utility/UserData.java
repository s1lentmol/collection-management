package utility;

import java.util.Scanner;

/**
 * Класс для взаимодействия с пользовательскими данными
 */
public class UserData {
    private static Scanner userScanner;

    private static boolean fileMode = false;
    public static Scanner getUserScanner() {
        return userScanner;
    }
    public static void setUserScanner(Scanner userScanner) {
        UserData.userScanner = userScanner;
    }
    public static boolean fileMode() {
        return fileMode;
    }

    public static void setUserMode() {
        UserData.fileMode = false;
    }

    public static void setFileMode() {
        UserData.fileMode = true;
    }
}
