package DAO;

import Models.User;

import java.sql.*;

public class UsersDAO {
    private static Connection connection;

    static {
        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            connection = DriverManager.getConnection("jdbc:h2:~/Questionnaire;DATABASE_TO_UPPER=false");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public boolean check(String log, String pass) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select Login,Password from Users");
            while (resultSet.next()) {
                if (resultSet.getString("Login").equals(log) &
                        resultSet.getString("Password").equals(pass)) {
                    return true;
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }


    public int getId(String name) {
        User user = new User();
        try {

            PreparedStatement preparedStatement = connection.prepareStatement("select Id from Users where UserName=?");
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user.setId(resultSet.getInt("Id"));


            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return user.getId();
    }


    public User select(String log, String pass) {
        User user = new User();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from Users where Login=? and Password=?");
            preparedStatement.setString(1, log);
            preparedStatement.setString(2, pass);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                user.setId(resultSet.getInt("Id"));
                user.setName(resultSet.getString("UserName"));
                user.setRole(resultSet.getString("Role"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return user;
    }


    public void add(User user) {
        try {

            PreparedStatement preparedStatement =
                    connection.prepareStatement("insert into Users (Login, Password, UserName, Role) values (?,?,?,?)");
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPass());
            preparedStatement.setString(3, user.getName());
            preparedStatement.setString(4, user.getRole());
            preparedStatement.executeUpdate();


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


    }


}
