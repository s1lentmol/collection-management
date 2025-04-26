package data.input;

import data.Location;
import exceptions.IncorrectInputInScriptException;
import exceptions.InvalidFormException;
import exceptions.MustBeNotEmptyException;
import utility.UserData;
import utility.console.Console;

import java.util.NoSuchElementException;

public class LocationInput extends Input<Location>{
    private final Console console;
    public LocationInput(Console console) {
        this.console = console;
    }

    @Override
    public Location build() throws IncorrectInputInScriptException, InvalidFormException {
        Location location = new Location(askLocataionX(),askLocationY(),askLocationName());
        if(!location.validate()) throw new InvalidFormException();
        return location;
    }


    public Float askLocataionX() {
        float x;
        while (true) {
            var fileMode = UserData.fileMode();
            try {
                console.println("Введите координату X города, в котором живет данный человек:");
                console.ps2();
                String strX = UserData.getUserScanner().nextLine().trim();
                x = Float.parseFloat(strX);
                if (fileMode) console.println(x);
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
    public Integer askLocationY() {
        int y;
        var fileMode = UserData.fileMode();
        while (true) {
            try {
                console.println("Введите координату Y города, в котором живет данный человек(значение должно быть целым!):");
                console.ps2();
                String strY = UserData.getUserScanner().nextLine().trim();
                if (strY.contains(",") || strY.contains(".")) throw new NoSuchElementException();
                y = Integer.parseInt(strY);
                if (fileMode) console.println(y);
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
    private String askLocationName() {
        String locationName;
        var fileMode = UserData.fileMode();
        while (true){
            try {
                console.println("Введите название города: ");
                console.ps2();
                locationName = UserData.getUserScanner().nextLine().trim();
                if (fileMode) console.println(locationName);
                if (locationName.isEmpty()) throw new MustBeNotEmptyException();
                break;
            } catch (NoSuchElementException e){
                console.printError("Название не распознано!");
            } catch (MustBeNotEmptyException e) {
                console.printError("Название не может быть пустым!");
            } catch (IllegalStateException e) {
                console.printError("Непредвиденная ошибка!");
                System.exit(0);
            }
        }
        return locationName;
    }
}
