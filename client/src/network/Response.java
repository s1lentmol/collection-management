package network;

import java.io.Serializable;

// класс, который мы получаем от сервера
public class Response implements Serializable {
    private static final long serialVersionUID = 555L;

    private String message;

    public Response(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
