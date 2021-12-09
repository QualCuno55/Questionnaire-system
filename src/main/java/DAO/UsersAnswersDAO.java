package DAO;

import java.sql.*;
import java.util.*;

public class UsersAnswersDAO {
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

    public void add(Map<Integer, ArrayList> map, int topicId, int userId) {
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("insert into UsersAnswers (UserId, TopicId, QuestionId, AnswerId) values (?,?,?,?)");
            for (Integer i : map.keySet()) {
                for (Object s : map.get(i)) {
                    preparedStatement.setInt(1, userId);
                    preparedStatement.setInt(2, topicId);
                    preparedStatement.setInt(3, i);
                    preparedStatement.setInt(4, (Integer) s);
                    preparedStatement.executeUpdate();
                }
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public Set<String> getAllUsers() {
        Set<String> set = new HashSet<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet =
                    statement.executeQuery("select  UserName from UsersAnswers,Users where Users.Id=UsersAnswers.UserId ");
            while (resultSet.next()) {
                set.add(resultSet.getString("UserName"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return set;
    }

    public Set<String> getAllTopicsFromUser(String userName) {

        Set<String> set = new HashSet<>();
        int userId = 0;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select Id from Users where UserName=?");
            preparedStatement.setString(1, userName);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                userId = resultSet.getInt("Id");
            }

            PreparedStatement preparedStatement1 =
                    connection.prepareStatement("select TopicName from UsersAnswers, Topic where UsersAnswers.UserId=? " +
                            "and TopicId=Topic.Id");
            preparedStatement1.setInt(1, userId);
            ResultSet resultSet1 = preparedStatement1.executeQuery();
            while (resultSet1.next()) {
                set.add(resultSet1.getString("TopicName"));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return set;
    }

    public Set<Map> getAllQuesAndAnswers(int topicId, int userId) {

        Set<Map> list = new HashSet<>();
        Set<String> set = new HashSet<>();
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("select Question from Questions,UsersAnswers " +
                            "where UserId=? and TopicId=? and UsersAnswers.QuestionId=Questions.Id");
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, topicId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {

                set.add(resultSet.getString("Question"));
            }

            for (String o : set) {

                Set<String> set1 = new HashSet<>();

                PreparedStatement preparedStatement1 =
                        connection.prepareStatement("select Answer from  UsersAnswers, Answers, Questions" +
                                " where  UsersAnswers.AnswerId=Answers.Id and UsersAnswers.UserId=? and  Questions.Question=?" +
                                " and Questions.Id=UsersAnswers.QuestionId");
                preparedStatement1.setInt(1, userId);
                preparedStatement1.setString(2, o);

                ResultSet resultSet1 = preparedStatement1.executeQuery();
                while (resultSet1.next()) {

                    set1.add(resultSet1.getString("Answer"));
                }


                Map<String, Set> map = new TreeMap<>();
                map.put(o, set1);
                list.add(map);
            }


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return list;
    }


    public boolean check(String name, int topicId) {
        try {
            PreparedStatement preparedStatement
                    = connection.prepareStatement("select UserId,TopicId from UsersAnswers,Users " +
                    "where UsersAnswers.UserId=Users.Id and Users.UserName=? and UsersAnswers.TopicId=?");
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, topicId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return true;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }


    public void delete(int topicId, int UserId) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("delete from UsersAnswers where TopicId=? and UserId=?");
            preparedStatement.setInt(1, topicId);
            preparedStatement.setInt(2, UserId);
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

}

