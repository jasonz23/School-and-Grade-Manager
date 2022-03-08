package tests.test;

import model.Course;
import model.Grade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CourseTest {
    Course c1;
    Course c2;

    @BeforeEach
    void setUp() {
    c1 = new Course("History",5);
    c2 = new Course("Math",10);
    }

    @Test
    void getCourseName() {
        assertEquals("History",c1.getCourseName());
    }

    @Test
    void getGradeSizeSingle() {
        c1.addGrade("cow",2,3);
        assertEquals(1,c1.getGradesSize());
    }

    @Test
    void getGradeSizeTestMany() {
        for (int i = 0; i < 3; i++) {
            c1.addGrade("a" + i, i ,i);
        }
        assertEquals(3,c1.getGradesSize());
    }

    @Test
    void getTotalWeightTestOnce() {
        c1.addGrade("a", 10 ,10);
        assertEquals(10,c1.getTotalWeight());
    }

    @Test
    void getTotalWeightTestMany() {
        for (int i = 0; i < 3; i++) {
            c1.addGrade("a" + i, i ,i);
        }
        assertEquals(3,c1.getTotalWeight());
    }

    @Test
    void calculateAverageTestOnce() {
        c1.addGrade("a", 10 ,10);
        assertEquals(10,c1.calculateAverage());
        assertEquals(0,c2.calculateAverage());
    }

    @Test
    void calculateAverageTestMany() {
        for (int i = 0; i < 3; i++) {
            c1.addGrade("a" , 80+i ,i);
        }
        assertEquals(81,c1.calculateAverage());
    }

    @Test
    void getGradeTest() {
        c1.addGrade("a",99,10);
        c1.addGrade("b",80,2);
        c1.getGrade(1);
        assertEquals(2,c1.getGradesSize());
        c1.removeGrade(1);
        assertEquals(1,c1.getGradesSize());
    }

    @Test
    void removeGradeWithIndexes() {
        c1.addGrade("a",1,1);
        c1.addGrade("b",2,2);
        c1.addGrade("c",3,3);
        List<Integer> list;
        list = new ArrayList<>();
        List<Integer> list2;
        list2 = new ArrayList<>();
        list2.add(1);
        list2.add(2);
        c1.removeGradeWithIndexes(list);
        assertEquals(3,c1.getGradesSize());
        c1.removeGradeWithIndexes(list2);
        assertEquals(1,c1.getGradesSize());
    }

}
