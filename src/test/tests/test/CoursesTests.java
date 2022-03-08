package tests.test;

import model.Course;
import model.Courses;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CoursesTests {
    Courses c1;
    Courses c2;
    Course course1;
    Course course2;
    Course course3;
    Course course4;
    Course course5;
    Course b1;

    @BeforeEach
    void setUp() {
        c1 = new Courses();
        c2 = new Courses();
        course1 = new Course("history",5);
        course2 = new Course("math",5);
        course1.addGrade("HomeWork",90,10);
        course1.addGrade("Lab",95,8);
        course1.addGrade("Test",100,9);
        course2.addGrade("lab",66,10);
        b1 = new Course("math",59);
        course3 = new Course("calc",4);
        course3.addGrade("math",0,900);
        course4 = new Course("matd",6);
        course4.addGrade("ss",49,10);
        course5 = new Course("cow",77);
    }

    @Test
    void containsCourseTestSingle() {
        c2.addCourse(course1);
        assertTrue(c2.containsCourse(course1));
        assertFalse(c2.containsCourse(b1));
    }

    @Test
    void containsCourseTestMany() {
        for (int i = 0; i < 10; i++) {
            Course c = new Course("a" + i, i);
            c1.addCourse(c);
            assertTrue(c1.containsCourse(c));
        }
        Course a = new Course("h",59);
        assertFalse(c1.containsCourse(a));
    }

    @Test
    void calculateGpaTestSingle() {
        assertEquals(0,c1.calculateGpa());
        c1.addCourse(course1);
        assertEquals(94,c1.calculateGpa());
        c2.addCourse(course5);
        assertEquals(0,c2.calculateGpa());
        Course c5 = new Course("wow",6);
        c5.addGrade("cow",1,0);
        c2.addCourse(c5);
        assertEquals(0,c2.calculateGpa());
        c5.addGrade("moo",1,0);
    }

    @Test
    void calculateGpaTestMany() {
        for (int i = 1; i < 4; i++) {
            Course c = new Course("a" + i, i);
            c.addGrade("HomeWork",90,15);
            c.addGrade("Lab",95,3);
            c.addGrade("Test",85,5);
            c1.addCourse(c);
            assertTrue(c1.containsCourse(c));
        }
        assertEquals(89,c1.calculateGpa());
    }

    @Test
    void calculateTotalCreditTestSingle() {
        c1.addCourse(course1);
        assertEquals(5,c1.totalCredits());
    }

    @Test
    void calculateTotalCreditTestMany() {
        for (int i = 0; i < 5; i++) {
            c1.addCourse(course1);
        }
        assertEquals(25,c1.totalCredits());
    }

    @Test
    void removeCourseTest() {
        for (int i = 0; i < 5; i++) {
            c1.addCourse(course1);
        }
        c1.removeCourse(1);
        assertEquals(4,c1.getSize());
    }

    @Test
    void getCourseTest() {
        c1.addCourse(course1);
        c1.addCourse(course2);
        assertEquals(course1,c1.getCourse(0));
    }

    @Test
    void returnFailingCoursesTestNone() {
        c1.addCourse(course1);
        c1.addCourse(course2);
        assertEquals(0,c1.returnFailingCourses().getSize());
    }

    @Test
    void returnFailingCoursesTestFew() {
        c1.addCourse(course1);
        c1.addCourse(course2);
        Course c = new Course("calc",4);
        c.addGrade("math",0,900);
        c1.addCourse(c);
        assertEquals(1,c1.returnFailingCourses().getSize());
    }

    @Test
    void returnFailingCoursesTestAll() {
        c1.addCourse(course3);
        c1.addCourse(course4);
        assertEquals(2,c1.returnFailingCourses().getSize());
    }

    @Test
    void returnFailingCoursesWOTestNone() {
        c1.addCourse(course1);
        c1.addCourse(course2);
        assertEquals(0,c1.returnFailingCoursesWO());
    }

    @Test
    void returnFailingCoursesWOTestFew() {
        c1.addCourse(course1);
        c1.addCourse(course2);
        Course c = new Course("calc",4);
        c.addGrade("math",0,900);
        c1.addCourse(c);
        assertEquals(1,c1.returnFailingCoursesWO());
    }

    @Test
    void returnFailingCoursesWOTestAll() {
        c1.addCourse(course3);
        c1.addCourse(course4);
        assertEquals(2,c1.returnFailingCoursesWO());
    }

    @Test
    void getCoursesTest() {
        c1.addCourse(course1);
        c1.addCourse(course3);
        c1.addCourse(course4);
        assertEquals(3,c1.getCourses().size());
    }

    @Test
    void isGradeTest() {
        c1.addCourse(course1);
        c1.addCourse(course2);
        assertTrue(c1.isGrades());
        course1.addGrade("cow",5,5);
        c2.addCourse(course5);
        assertFalse(c2.isGrades());
    }

    @Test
    void removeCourseWithIndexesTest() {
        c1.addCourse(course1);
        c1.addCourse(course2);
        c1.addCourse(course3);
        List<Integer> list;
        List<Integer> list2;
        list2 = new ArrayList<>();
        list = new ArrayList<>();
        list.add(1);
        list.add(2);
        c1.removeCoursesWithIndexes(list);
        assertEquals(1,c1.getSize());
        c2.addCourse(course1);
        c2.addCourse(course2);
        c2.addCourse(course3);
        c2.removeCoursesWithIndexes(list2);
        assertEquals(3,c2.getSize());
    }

}
