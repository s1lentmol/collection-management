package data;

import utility.Validatable;

import java.io.Serializable;
import java.util.Objects;

public class Location implements Validatable, Serializable {
    private final Float x; //Поле не может быть null
    private final Integer y; //Поле не может быть null
    private final String name; //Строка не может быть пустой, Поле может быть null
    private static final long serialVersionUID = 8511L;

    public Location(Float x, Integer y, String name) {
        this.x = x;
        this.y = y;
        this.name = name;
    }

    public Float getX() {
        return x;
    }

    public Integer getY() {
        return y;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean validate() {
        if (x == null) return false;
        if (y == null) return false;
        return !Objects.equals(name, "");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return Objects.equals(x, location.x) && Objects.equals(y, location.y) && Objects.equals(name, location.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, name);
    }

    @Override
    public String toString() {
        return "Название города: " + name + ". Координаты: (" + x + ", " + y + ")";
    }
}
