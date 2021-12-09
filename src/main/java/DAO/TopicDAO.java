package DAO;

import Models.Topic;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class TopicDAO {
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

    public int getId(String topicName) {

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select Id from Topic where TopicName=?");
            preparedStatement.setString(1, topicName);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("Id");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return 0;

    }

    public List<Topic> selectAll() {
        List<Topic> topics = new ArrayList<>();
        try {

            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("select * from Topic");
            while (resultSet.next()) {
                Topic topic = new Topic();
                topic.setName(resultSet.getString("TopicName"));
                topic.setDescription(resultSet.getString("Description"));
                topic.setId(resultSet.getInt("Id"));
                topics.add(topic);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return topics;
    }

    public Topic select(int id) {
        Topic topic = new Topic();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from Topic where Id=?");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {

                topic.setName(resultSet.getString("TopicName"));
                topic.setDescription(resultSet.getString("Description"));
                topic.setId(resultSet.getInt("Id"));

            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return topic;
    }

    public Integer count(int id) {
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("select count(Question) from Questions,Topic where Topic.Id=? and Topic.Id=Topic_Id");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public Integer add(String name, String desc) {
        try {

            PreparedStatement preparedStatement =
                    connection.prepareStatement("insert into Topic (Description, TopicName) values (?,?)",
                            Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, desc);
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        return null;
    }


    public void delete(int id) {
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("DELETE FROM Topic WHERE Id=?");
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
