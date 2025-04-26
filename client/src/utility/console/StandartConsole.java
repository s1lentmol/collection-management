package utility.console;


/**
 * Стандартная консоль
 */
public class StandartConsole implements Console{
    private static final String PS1 = "$ ";
    private static final String PS2 = "> ";


    @Override
    public void println(Object obj) {
        System.out.println(obj);
    }

    @Override
    public void printError(Object obj) {
        System.out.println("ошибка: " + obj);
    }
    @Override
    public void printTable(Object elementLeft, Object elementRight) {
        System.out.printf(" %-45s%-1s%n", elementLeft, elementRight);
    }
    @Override
    public void ps1() {
        System.out.print(PS1);
    }

    @Override
    public void ps2() {
        System.out.print(PS2);

    }
    @Override
    public String getPS1() {
        return PS1;
    }

    @Override
    public String getPS2() {
        return PS2;
    }
}
