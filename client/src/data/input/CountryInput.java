package data.input;

import data.Country;
import exceptions.IncorrectInputInScriptException;
import exceptions.InvalidFormException;
import utility.UserData;
import utility.console.Console;

import java.util.NoSuchElementException;

public class CountryInput extends Input<Country>{
    private final Console console;

    public CountryInput(Console console) {
        this.console = console;
    }

    @Override
    public Country build() throws IncorrectInputInScriptException, InvalidFormException {
        String strCountry;
        Country country;
        var fileMode = UserData.fileMode();
        while (true) {
            try {
                console.println("Список национальностей - " + Country.names());
                console.println("Введите национальность из данного списка(регистр не важен)");
                console.ps2();
                strCountry = UserData.getUserScanner().nextLine().trim();
                if (strCountry.isEmpty() || strCountry.equals("null")) throw new NoSuchElementException();
                country = Country.valueOf(strCountry.toUpperCase());
                if (fileMode) console.println(country);
                break;
            } catch (NoSuchElementException exception) {
                console.printError("Национальность не распознана!");
            } catch (IllegalArgumentException exception) {
                console.printError("Национальности нет в списке!");
            } catch (IllegalStateException exception) {
                console.printError("Непредвиденная ошибка!");
                System.exit(0);
            }
        }
        return country;
    }
}

