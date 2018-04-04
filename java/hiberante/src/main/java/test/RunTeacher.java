package test;

import org.hibernate.Session;

import java.util.Set;

public class RunTeacher {
    public static void main(String[] args) {
        Session session = CustomSessionFactory.getFactory().openSession();
        session.beginTransaction();

        Teacher teacher = new Teacher();
        teacher.setAge(22);
        teacher.setName("Teacher");

        Student student1 = new Student();
        student1.setName("1");
        student1.setAge(1);

        Student student2 = new Student();
        student2.setName("2");
        student2.setAge(2);

        Set<Student> students = teacher.getStudents();
        students.add(student1);
        students.add(student2);

        student1.

        session.save(teacher);

        session.getTransaction().commit();

        Teacher teacher1 = session.find(Teacher.class, teacher.getT_id());
        System.out.println(teacher1);

        session.close();
        CustomSessionFactory.shutdown();
    }
}
