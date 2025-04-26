package commands;

import managers.DataBaseManager;
import network.Response;
import utility.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Registry extends Command {
    public Registry() {
        super("registry", "");
    }


    public Response execute(User user, String variant) {
        while (true) {
            try {
                Connection connection = DataBaseManager.getConnection();
                if (variant.equals("1")) {
                    Response response = new Response();
                    String checkingSql = "SELECT * FROM users WHERE userName=?";
                    PreparedStatement checkingStatement = connection.prepareStatement(checkingSql);
                    checkingStatement.setString(1, user.getUserName());
                    ResultSet checkingResult = checkingStatement.executeQuery();
                    if(checkingResult.next()) {
                        response.setMessage("Пользователь с таким именем уже существует! Измените имя на другое");
                        return response;
                    }

                    String sql = "INSERT INTO users(userName, password) VALUES (?,?)";
                    PreparedStatement preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setString(1, user.getUserName());
                    preparedStatement.setString(2, user.getPassword());
                    preparedStatement.executeUpdate();
                    response.setMessage("Пользователь успешно авторизован");
                    return response;
                } else if (variant.equals("2")) {
                    String sql = "SELECT * FROM users WHERE userName=? AND password=?";
                    PreparedStatement preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setString(1, user.getUserName());
                    preparedStatement.setString(2, user.getPassword());
                    ResultSet resultSet = preparedStatement.executeQuery();
                    Response response = new Response();
                    if (resultSet.next()) {
                        response.setMessage("Вы успешно вошли!");
                        return response;
                    } else {
                        response.setMessage("Неверное имя пользователя или пароль");
                        return response;
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}


