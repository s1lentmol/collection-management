package data;

import java.io.Serializable;

public enum Country implements Serializable {
    USA,
    SPAIN,
    INDIA,
    VATICAN,
    SOUTH_KOREA;
    /*
        Строка со всеми элементами enum'а через строку.
     */
    private static final long serialVersionUID = 8510L;
    public static String names() {
        StringBuilder nameList = new StringBuilder();
        for (var weaponType : values()) {
            nameList.append(weaponType.name()).append(", ");
        }
        return nameList.substring(0, nameList.length()-2);
    }
}
