package tests.test;


import model.Grade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GradeTest {
    Grade g1;

    @BeforeEach
    void setUp() {
        g1 = new Grade("a",15,10);
    }

    @Test
    void getGradeNameTest() {
        assertEquals("a",g1.getGradeName());
    }

    @Test
    void getGradeWeightTest() {
        assertEquals(10,g1.getWeighting());
    }

    @Test
    void getGradeNumTest() {
        assertEquals(15,g1.getGrade());
    }
}
