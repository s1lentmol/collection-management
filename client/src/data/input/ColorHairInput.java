package data.input;

import data.Color;
import exceptions.IncorrectInputInScriptException;
import exceptions.InvalidFormException;
import utility.UserData;
import utility.console.Console;

import java.util.NoSuchElementException;

public class ColorHairInput extends Input<Color> {
    private final Console console;

    public ColorHairInput(Console console) {
        this.console = console;
    }

    @Override
    public Color build() throws IncorrectInputInScriptException, InvalidFormException {
        String strHairColor;
        Color hairColor;
        var fileMode = UserData.fileMode();
        while (true) {
            try {
                console.println("Список цветов - " + Color.names());
                console.println("Введите цвет волос из данного списка(регистр не важен)");
                console.ps2();
                strHairColor = UserData.getUserScanner().nextLine().trim();
                if (strHairColor.isEmpty() || strHairColor.equals("null")) throw new NoSuchElementException();
                hairColor = Color.valueOf(strHairColor.toUpperCase());
                if (fileMode) console.println(hairColor);
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
        return hairColor;
    }
}
