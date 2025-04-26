package data.input;

import data.*;
import exceptions.*;

import utility.User;
import utility.UserData;
import utility.console.Console;

import java.util.NoSuchElementException;

public class PersonInput extends Input<Person>{
    private final Console console;
    private String userName;
    //private final User user;

    public PersonInput(Console console, String userName) {
        this.console = console;
        this.userName = userName;
        //this.user = user;
    }
    @Override
    public Person build() throws IncorrectInputInScriptException, InvalidFormException {
        Person person = new Person(
                askName(),
                askCoordinates(),
                askHeight(),
                askEyeColor(),
                askHairColor(),
                askNationality(),
                askLocation(),
                userName
        );
        if (!person.validate()) throw new InvalidFormException();
        return person;
    }

    private String askName() {
        String name;
        var fileMode = UserData.fileMode();
        while (true){
            try {
                console.println("Введите имя: ");
                console.ps2();
                name = UserData.getUserScanner().nextLine().trim();
                if (fileMode) console.println(name);
                if (name.isEmpty()) throw new MustBeNotEmptyException();
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
        return name;
    }
    private Coordinates askCoordinates() throws IncorrectInputInScriptException, InvalidFormException {
        return new CoordinatesInput(console).build();
    }
    private double askHeight() {
        double height;
        var fileMode = UserData.fileMode();
        while (true) {
            try {
                console.println("Введите рост человека:");
                console.ps2();
                String strHeight = UserData.getUserScanner().nextLine().trim();
                height = Double.parseDouble(strHeight);
                if (fileMode) console.println(height);
                if (height < 0) throw new NotInDeclaredLimitsException();
                break;
            } catch (NoSuchElementException exception) {
                console.printError("Рост человека не распознан!");
            } catch (NotInDeclaredLimitsException exception) {
                console.printError("Рост должна быть больше нуля!");
            } catch (NumberFormatException exception) {
                console.printError("Рост человека должен быть представлен числом!");
            } catch (NullPointerException | IllegalStateException exception) {
                console.printError("Непредвиденная ошибка!");
                System.exit(0);
            }
        }
        return height;
    }
    private Color askEyeColor() throws IncorrectInputInScriptException, InvalidFormException {
        return new ColorEyeInput(console).build();
    }
    private Color askHairColor() throws IncorrectInputInScriptException, InvalidFormException {
        return new ColorHairInput(console).build();
    }
    private Country askNationality() throws IncorrectInputInScriptException, InvalidFormException {
        return new CountryInput(console).build();
    }
    private Location askLocation() throws IncorrectInputInScriptException, InvalidFormException {
        return new LocationInput(console).build();
    }

}
