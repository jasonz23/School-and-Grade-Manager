package persistence;

import model.Course;
import model.Courses;
import model.Grade;

import org.json.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// reads JSON file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    //EFFECT: reads courses from file and returns it
    // if an error occurs while courses data is being read, throw IOException
    public Courses read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseCourses(jsonObject);
    }

    //EFFECT: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    //EFFECTS: parses courses from JSON object and returns it
    private Courses parseCourses(JSONObject jsonObject) {
        String name = jsonObject.getString("StudentName");
        Courses cs = new Courses();
        cs.addName(name);
        addCourses(cs,jsonObject);
        return cs;
    }

    //MODIFIES: cs
    //EFFECTS: parses through course from JSON object and add them to courses
    private void addCourses(Courses cs, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("courses");
        for (Object json : jsonArray) {
            JSONObject nextThingy = (JSONObject) json;
            addCourse(cs, nextThingy);
        }
    }

    //MODIFIES: cs
    //EFFECTS: parses though course from JSON object and adds it to courses
    private void addCourse(Courses cs, JSONObject jsonObject) {
        String name = jsonObject.getString("CourseName");
        int credit = jsonObject.getInt("CourseCredit");
        Course c = new Course(name, credit);
        addGrades(c,jsonObject);
        cs.addCourse(c);
    }

    //MODIFIES: cs
    //EFFECTS: parses though grades from JSON object and adds it to course
    private void addGrades(Course c, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("grades");
        for (Object json : jsonArray) {
            JSONObject nextGrade = (JSONObject) json;
            addGrade(c,nextGrade);
        }
    }

    //MODIFIES: cs
    //EFFECTS: parses though grade from JSON object and adds it to course
    private void addGrade(Course c, JSONObject jsonObject) {
        String name = jsonObject.getString("GradeName");
        int gradeNum = jsonObject.getInt("GradeNum");
        int gradeWeight = jsonObject.getInt("GradeWeight");
        c.addGrade(name,gradeNum,gradeWeight);
    }
}
