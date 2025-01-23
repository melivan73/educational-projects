import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class App {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/skillbox";
        String user = "root";
        String pass = "8Uz1rD492";

        try {
            Connection connection = DriverManager.getConnection(url, user, pass);
            Statement statement = connection.createStatement();

            String query = "SELECT course_name, count(1)/(1 + max(month(subscription_date)) - min(month" +
                    "(subscription_date))) as avg_purch_mon FROM purchaselist WHERE year(subscription_date) = '2018' " +
                    "GROUP BY course_name";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                System.out.println("Course name = " + resultSet.getString("course_name") +
                        ", Average purchases per month = " + resultSet.getFloat("avg_purch_mon"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
