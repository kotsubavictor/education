package test;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "TEACHER")
public class Teacher {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer t_id;

    @Basic
    @Column
    private String name;

    @Basic
    @Column
    private Integer age;

    @OneToMany(mappedBy = "teacher")
    private Set<Student> students;

    public Teacher() {
        students = new HashSet<>();
    }

    public Integer getT_id() {
        return t_id;
    }

    public void setT_id(Integer t_id) {
        this.t_id = t_id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Teacher student = (Teacher) o;
        return Objects.equals(name, student.name) &&
                Objects.equals(age, student.age);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age);
    }

    public Set<Student> getStudents() {
        return students;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "t_id=" + t_id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", students=" + students +
                '}';
    }
}
