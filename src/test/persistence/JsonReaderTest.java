package persistence;

import model.Courses;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonReaderTest {
    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Courses cs = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyCourses() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyCourses.json");
        try {
            Courses cs = reader.read();
            assertEquals("Jason", cs.getName());
            assertEquals(0, cs.getSize());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderWithCoursesNoGrades() {
        JsonReader reader = new JsonReader("./data/testReaderWithCoursesNoGrades.json");
        try {
            Courses cs = reader.read();
            assertEquals("Jason", cs.getName());
            assertEquals(2, cs.getSize());
            assertEquals(0,cs.getCourse(0).getGradesSize());
            assertEquals(0,cs.getCourse(1).getGradesSize());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderWithCoursesAndGrades() {
        JsonReader reader = new JsonReader("./data/testReaderWithCoursesAndGrades.json");
        try {
            Courses cs = reader.read();
            assertEquals("Jason", cs.getName());
            assertEquals(2, cs.getSize());
            assertEquals(2,cs.getCourse(0).getGradesSize());
            assertEquals(3,cs.getCourse(1).getGradesSize());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
