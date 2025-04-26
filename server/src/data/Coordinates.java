package data;

import utility.Validatable;

import java.io.Serializable;
import java.util.Objects;

public class Coordinates implements Validatable, Serializable {
    private static final long serialVersionUID = 858L;
    private final Double x; //Максимальное значение поля: 550, Поле не может быть null
    private final float y; //Значение поля должно быть больше -7
    public Coordinates(Double x, float y){
        this.x = x;
        this.y = y;
    }

    public Double getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    @Override
    public boolean validate() {
        if (x>550) return false;
        return !(y <= -7);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinates that = (Coordinates) o;
        return Float.compare(y, that.y) == 0 && Objects.equals(x, that.x);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
