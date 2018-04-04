package test;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.io.File;

public class RunStudent {
    private static final String H2_BACKUP_LOCATION = "~/hibernate/src/main/resources/h2backup.sql";

    public static void main(String[] args) throws Exception {
//        restoreDB();
//        System.out.println(new File(".").getAbsolutePath());
        System.out.println("Hibernate tutorial");

        Session session = CustomSessionFactory.getFactory().openSession();

        session.beginTransaction();

        Student student = new Student();
        student.setAge(25);
        student.setName("Vic");

        session.save(student);
        session.getTransaction().commit();

        session.close();

        CustomSessionFactory.shutdown();
    }

    protected static void restoreDB() throws Exception {
//        Connection conn = DriverManager.getConnection("jdbc:h2:~/test", "sa", null);
//        conn.close();
//        todo: RESTORE STATE
    }
}
