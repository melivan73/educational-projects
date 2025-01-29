package DB_Tables_Classes;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "LinkedPurchaseList")
public class LinkedPurchaseList {
    @EmbeddedId
    LinkedPurchaseListKey id;

    public LinkedPurchaseListKey getId() {
        return id;
    }

    public void setId(LinkedPurchaseListKey id) {
        this.id = id;
    }

    public static class LinkedPurchaseListKey implements Serializable {
        @Column(name = "student_id")
        private int studentId;

        @Column(name = "course_id")
        private int courseId;

        public int getStudentId() {
            return studentId;
        }

        public void setStudentId(int studentId) {
            this.studentId = studentId;
        }

        public int getCourseId() {
            return courseId;
        }

        public void setCourseId(int courseId) {
            this.courseId = courseId;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            LinkedPurchaseList.LinkedPurchaseListKey that = (LinkedPurchaseList.LinkedPurchaseListKey) o;
            return studentId == that.studentId && courseId == that.courseId;
        }

        @Override
        public int hashCode() {
            return Objects.hash(studentId, courseId);
        }
    }
}