package data.input;

import data.Coordinates;
import exceptions.IncorrectInputInScriptException;
import exceptions.InvalidFormException;
import utility.UserData;
import utility.console.Console;

import java.util.NoSuchElementException;

public class CoordinatesInput extends Input<Coordinates>{
    private final Console console;
    public CoordinatesInput(Console console) {
        this.console = console;
    }

    @Override
    public Coordinates build() throws IncorrectInputInScriptException, InvalidFormException {
        Coordinates coordinates = new Coordinates(askX(),askY());
        if(!coordinates.validate()) throw new InvalidFormException();
        return coordinates;
    }


    public Double askX() {
        double x;
        var fileMode = UserData.fileMode();
        while (true) {
            try {
                console.println("Введите координату X местоположения данного человека (данное значение не должно превышать 550!):");
                console.ps2();
                String strX = UserData.getUserScanner().nextLine().trim();
                x = Double.parseDouble(strX);
                if (fileMode) console.println(x);
                if (x>550) throw new NoSuchElementException();
                break;
            } catch (NoSuchElementException exception) {
                console.printError("Координата X не распознана!");
            } catch (NumberFormatException exception) {
                console.printError("Координата X должна быть представлена числом!");
            } catch (NullPointerException | IllegalStateException exception) {
                console.printError("Непредвиденная ошибка!");
                System.exit(0);
            }
        }
        return x;
    }
    public float askY() {
        float y;
        var fileMode = UserData.fileMode();
        while (true) {
            try {
                console.println("Введите координату Y местоположения данного человека(данное значение должно быть больше -7!):");
                console.ps2();
                String strY = UserData.getUserScanner().nextLine().trim();
                y = Float.parseFloat(strY);
                if (fileMode) console.println(y);
                if (y<=-7 || y>Float.MAX_VALUE) throw new NoSuchElementException();
                break;
            } catch (NoSuchElementException exception) {
                console.printError("Координата Y не распознана!");
            } catch (NumberFormatException exception) {
                console.printError("Координата Y должна быть представлена числом!");
            } catch (NullPointerException | IllegalStateException exception) {
                console.printError("Непредвиденная ошибка!");
                System.exit(0);
            }
        }
        return y;
    }
}
