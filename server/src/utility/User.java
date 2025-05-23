package utility;

import java.io.Serializable;

public class User implements Serializable {

    private static final long serialVersionUID = 934L;
    private String userName;
    private String password;

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }
}
