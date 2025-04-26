package network;

import commands.Command;
import data.Person;
import utility.User;

import java.io.Serializable;

// класс, который мы будем передавать на сервер
public class Request implements Serializable {

    private static final long serialVersionUID = 333L;

    private String commandName;
    private String[] moreArguments;
    private Person person;
    private User user;
    private String variant;
    private String userName;

    private String commandArguments;

    public Request(String commandName, Person person, String commandArguments) {
        this.commandName = commandName;
        this.commandArguments = commandArguments;
        this.person = person;
    }
    public Request(String commandName, Person person, String commandArguments,String userName) {
        this.commandName = commandName;
        this.commandArguments = commandArguments;
        this.person = person;
        this.userName = userName;
    }
    public Request(String commandName, String commandArguments) {
        this.commandName = commandName;
        this.commandArguments = commandArguments;
    }
    public Request(String commandName) {
        this.commandName = commandName;
    }
    public Request(String commandName, String[] moreArguments, String userName){
        this.commandName = commandName;
        this.moreArguments = moreArguments;
        this.userName = userName;
    }
    public Request(User user, String variant){
        this.user = user;
        this.variant = variant;
    }
    public Request(String commandName, String commandArguments, String userName){
        this.commandName = commandName;
        this.commandArguments = commandArguments;
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public User getUser() {
        return user;
    }

    public String getVariant() {
        return variant;
    }

    public String[] getMoreArguments() {
        return moreArguments;
    }

    public String getCommandName() {
        return commandName;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public void setCommandName(Command command) {
        this.commandName = commandName;
    }

    public String getCommandArguments() {
        return commandArguments;
    }

    public void setCommandArguments(String commandArguments) {
        this.commandArguments = commandArguments;
    }


}
