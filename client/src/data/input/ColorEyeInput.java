package data.input;

import data.Color;
import exceptions.IncorrectInputInScriptException;
import exceptions.InvalidFormException;
import utility.UserData;
import utility.console.Console;

import java.util.NoSuchElementException;

public class ColorEyeInput extends Input<Color> {
    private final Console console;

    public ColorEyeInput(Console console) {
        this.console = console;
    }

    @Override
    public Color build() throws IncorrectInputInScriptException, InvalidFormException {
        String strEyeColor;
        Color eyeColor;
        var fileMode = UserData.fileMode();
        while (true) {
            try {
                console.println("Список цветов - " + Color.names());
                console.println("Введите цвет глаз из данного списка(регистр не важен)");
                console.ps2();
                strEyeColor = UserData.getUserScanner().nextLine().trim();
                if (strEyeColor.isEmpty() || strEyeColor.equals("null")) throw new NoSuchElementException();
                eyeColor = Color.valueOf(strEyeColor.toUpperCase());
                if (fileMode) console.println(eyeColor);
                break;
            } catch (NoSuchElementException exception) {
                console.printError("Цвет не распознан!");
            } catch (IllegalArgumentException exception) {
                console.printError("Такого цвета нет в списке!");
            } catch (IllegalStateException exception) {
                console.printError("Непредвиденная ошибка!");
                System.exit(0);
            }
        }
        return eyeColor;
    }
}
