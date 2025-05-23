package utility.console;

/**
 * Интерфейс для вывода результата и ошибок
 */
public interface Console {
    void println(Object obj);
    void printError(Object obj);
    void printTable(Object obj1, Object obj2);
    void ps1();
    void ps2();
    String getPS1();
    String getPS2();
}
