package DAO;


import Models.Question;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuestionDAO {
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


    public Integer count(Integer topicId) {
        try {
            int count = 0;
            PreparedStatement preparedStatement = connection.prepareStatement("select Id from Questions where Topic_Id=?");
            preparedStatement.setInt(1, topicId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                count++;
            }
            return count;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;
    }

    public Integer add(String question, int topicId, String type) {
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("insert into Questions (Question, Topic_Id, Question_type) values (?,?,?)", Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, question);
            preparedStatement.setInt(2, topicId);
            preparedStatement.setString(3, type);
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


    public List<Question> select(int topicId) {
        List<Question> questions = new ArrayList();
        try {

            PreparedStatement preparedStatement =
                    connection.prepareStatement("Select Questions.Id,Question,Question_type from Questions,Topic" +
                            " where Topic.Id=? and Topic_Id=Topic.Id ");
            preparedStatement.setInt(1, topicId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Question question = new Question();
                question.setId(resultSet.getString("Id"));
                question.setQuestion(resultSet.getString("Question"));
                question.setQuestionType(resultSet.getString("Question_type"));
                questions.add(question);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return questions;
    }


}
