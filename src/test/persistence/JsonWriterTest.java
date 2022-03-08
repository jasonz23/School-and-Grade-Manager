package persistence;

import model.Course;
import model.Courses;
import model.Grade;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonWriterTest {
    @Test
    void testWriterInvalidFile() {
        try {
            Courses cs = new Courses();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyCourses() {
        try {
            Courses cs = new Courses();
            cs.addName("Jason");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyCourses.json");
            writer.open();
            writer.write(cs);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyCourses.json");
            cs = reader.read();
            assertEquals("Jason", cs.getName());
            assertEquals(0, cs.getSize());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterWithCourses() {
        try {
            Courses cs = new Courses();
            cs.addName("Jason");
            Course c1 = new Course("Calc",3);
            Course c2 = new Course("Math",5);
            cs.addCourse(c1);
            cs.addCourse(c2);
            JsonWriter writer = new JsonWriter("./data/testWriterWithCoursesNoGrades.json");
            writer.open();
            writer.write(cs);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterWithCoursesNoGrades.json");
            cs = reader.read();
            assertEquals("Jason",cs.getName());
            assertEquals(2,cs.getSize());
            assertEquals("Calc",cs.getCourse(0).getCourseName());

        } catch (FileNotFoundException e) {
            fail("Exception should not be thrown");
        } catch (IOException e) {
            fail("nope");
        }
    }

    @Test
    void testWriterWithCoursesAndGrades() {
        try {
            Courses cs = new Courses();
            cs.addName("Jason");
            Course c1 = new Course("Calc",3);
            Course c2 = new Course("Math",5);
            c1.addGrade("Homework",94,45);
            c1.addGrade("Labs",55,9);
            c2.addGrade("Cow",45,2);
            c2.addGrade("Meow",22,67);
            c2.addGrade("poop",44,65);
            cs.addCourse(c1);
            cs.addCourse(c2);
            JsonWriter writer = new JsonWriter("./data/testWriterWithCoursesAndGrades.json");
            writer.open();
            writer.write(cs);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterWithCoursesAndGrades.json");
            cs = reader.read();
            assertEquals("Jason",cs.getName());
            assertEquals(2,cs.getSize());
            assertEquals(2,cs.getCourse(0).getGradesSize());
            assertEquals(3,cs.getCourse(1).getGradesSize());

        } catch (FileNotFoundException e) {
            fail("Exception should not be thrown");
        } catch (IOException e) {
            fail("nope");
        }
    }
}
