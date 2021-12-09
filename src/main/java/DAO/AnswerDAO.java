package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class AnswerDAO {

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

    public void add(String answer, int questionId) {

        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("insert into Answers (Answer, Question_Id) values (?,?)");
            preparedStatement.setString(1, answer);
            preparedStatement.setInt(2, questionId);
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public Map<Integer, ArrayList> getAllId(String[] answers) {
        Map<Integer, ArrayList> idQuestionAndAnswers = new TreeMap<>();
        ArrayList answersId = new ArrayList<>();
        for (String s : answers) {
            try {
                int QuestionId = 0;
                PreparedStatement preparedStatement = connection.prepareStatement("select Id,Question_Id from Answers where Answer=?");
                preparedStatement.setString(1, s);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    answersId.add(resultSet.getInt("Id"));
                    QuestionId = resultSet.getInt("Question_Id");
                }
                idQuestionAndAnswers.put(QuestionId, answersId);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return idQuestionAndAnswers;

    }

    public List<ArrayList> selectAnswersForQuestion(ArrayList<Integer> questionsId) {
        List<ArrayList> answers = new ArrayList<>();

        try {
            for (Integer i : questionsId) {
                PreparedStatement preparedStatement = connection.prepareStatement("Select Answer from Answers where Question_Id=?");
                preparedStatement.setInt(1, i);
                ResultSet resultSet = preparedStatement.executeQuery();
                ArrayList<String> array = new ArrayList();
                while (resultSet.next()) {
                    array.add(resultSet.getString("Answer"));
                }
                answers.add(array);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        return answers;
    }


}
