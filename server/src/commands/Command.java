package commands;

import data.Person;
import network.Response;
import utility.User;

import java.util.Objects;
import java.util.concurrent.locks.ReentrantLock;

public abstract class Command implements Describable  {
    private final String name;
    private final String description;

    protected Command(String name, String description) {
        this.name = name;
        this.description = description;
    }


    public String getName() {
        return name;
    }

    public Response execute(){
        return null;
    }
    public Response execute(Person person){
        return null;
    }
    public Response execute(String arg){
        return null;
    }
    public Response execute(String[] arg, String userName){
        return null;
    }
    public Response execute(User user, String variant){
        return null;
    }
    public Response execute(String arg, String userName){
        return null;
    }
    public Response execute(Person person, String userName){
        return null;
    }
    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Command command = (Command) o;
        return Objects.equals(name, command.name) && Objects.equals(description, command.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description);
    }

    @Override
    public String toString() {
        return "Command{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
