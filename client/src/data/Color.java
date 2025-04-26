package data;

import java.io.Serializable;

public enum Color implements Serializable {

    BLACK,
    BLUE,
    ORANGE,
    WHITE,
    BROWN,
    RED,
    YELLOW;
    /*
    Строка со всеми элементами enum'а через строку.
 */
    private static final long serialVersionUID = 859L;
    public static String names() {
        StringBuilder nameList = new StringBuilder();
        for (var weaponType : values()) {
            nameList.append(weaponType.name()).append(", ");
        }
        return nameList.substring(0, nameList.length()-2);
    }
}
