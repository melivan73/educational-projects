import DB_Tables_Classes.LinkedPurchaseList;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.MutationQuery;
import org.hibernate.query.Query;

import java.util.List;

public class App {
    private static final SessionFactory sessionFactory = new Configuration()
            .configure("hibernate.cfg.xml")
            .buildSessionFactory();

    public static void main(String[] args) {
        Session session = sessionFactory.openSession();
        try {
            Transaction transaction = session.beginTransaction();
            String hql = "DELETE FROM LinkedPurchaseList";
            MutationQuery truncateQuery = session.createMutationQuery(hql);
            int result = truncateQuery.executeUpdate();

            hql = "INSERT INTO LinkedPurchaseList (id.courseId, id.studentId) " +
                    "SELECT crs.id, st.id " +
                    "FROM PurchaseList pl " +
                    "INNER JOIN Course crs ON pl.courseName = crs.name " +
                    "INNER JOIN Student st ON pl.studentName = st.name";
            MutationQuery insertQuery = session.createMutationQuery(hql);
            result = insertQuery.executeUpdate();

            hql = "FROM LinkedPurchaseList ORDER BY id.courseId";
            Query<LinkedPurchaseList> selectQuery = session.createQuery(hql, LinkedPurchaseList.class);
            List<LinkedPurchaseList> linkedPurchaseLists = selectQuery.getResultList();

            for (LinkedPurchaseList list : linkedPurchaseLists) {
                int courseId = list.getId().getCourseId();
                int studentId = list.getId().getStudentId();
                System.out.println(courseId + " - " + studentId);
            }
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        session.close();
        sessionFactory.close();
    }
}
