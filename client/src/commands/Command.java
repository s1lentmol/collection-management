package commands;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public abstract class Command implements Executable,Serializable {
    @Serial
    private static final long serialVersionUID = 22L;
    private final String name;

    protected Command(String name) {
        this.name = name;
    }


    public String getName() {
        return name;
    }




    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Command command = (Command) o;
        return Objects.equals(name, command.name);
    }


    @Override
    public String toString() {
        return "Command{" +
                "name='" + name + '\'' +
                '}';
    }
}
