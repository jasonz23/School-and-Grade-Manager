package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Storage;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

//represents a list of courses
public class Courses implements Storage {

    private List<Course> courses;
    private String name;

    //EFFECTS: instantiates courses variable
    public Courses() {
        courses = new ArrayList<>();
        name = "JASON";
    }

    //EFFECTS: if given course is in courses then return true. Otherwise, return false
    public boolean containsCourse(Course c) {
        List<String> stringCourses = new ArrayList<>();
        for (Course course: courses) {
            stringCourses.add(course.getCourseName());
        }
        if (stringCourses.contains(c.getCourseName())) {
            return true;
        } else {
            return false;
        }
    }

    public void addName(String name) {
        this.name = name;
        EventLog.getInstance().logEvent(new Event("Loaded Courses"));
    }

    //MODIFIES: this
    //EFFECTS: add given course to courses
    public void addCourse(Course c) {
        courses.add(c);
        EventLog.getInstance().logEvent(new Event("Added Course"));
    }

    //EFFECTS: return size of courses
    public int getSize() {
        return courses.size();
    }

    //EFFECTS: given a number return the course in courses that corresponds to that number placement in courses
    public Course getCourse(int i) {
        return courses.get(i);
    }

    //MODIFIES: this
    //EFFECTS: remove the course in courses that are in the index
    public void removeCourse(int index) {
        courses.remove(index);
    }

    //EFFECTS: return true if there are grades in any of the courses
    public boolean isGrades() {
        for (Course c: courses) {
            if (c.getGradesSize() != 0) {
                return true;
            }
        }
        return false;
    }

    public List<Course> getCourses() {
        return courses;
    }

    //EFFECTS: calculate gpa of all courses
    public int calculateGpa() {
        int totalGrade = 0;
        for (Course c: courses) {
            totalGrade += c.calculateAverageWO() * c.getCredit();
        }
        if (totalCredits() != 0) {
            EventLog.getInstance().logEvent(new Event("Returned calculated gpa"));
            return totalGrade / totalCredits();
        } else {
            EventLog.getInstance().logEvent(new Event("Returned calculated gpa"));
            return 0;
        }

    }

    //EFFECT: return student name
    public String getName() {
        return name;
    }

    //EFFECTS: calculate total credits in courses
    public int totalCredits() {
        int totalCredits = 0;
        for (Course c: courses) {
            totalCredits += c.getCredit();
        }
        return totalCredits;
    }

    //MODIFIES: this
    //EFFECTS: remove courses with a list of index
    public void removeCoursesWithIndexes(List<Integer> indexes) {
        Courses removeCourses = new Courses();
        for (int i: indexes) {
            removeCourses.addCourseWO(courses.get(i));
        }
        for (int i = 0; i < removeCourses.getSize(); i++) {
            courses.remove(removeCourses.getCourse(i));
            EventLog.getInstance().logEvent(new Event("Removed Courses"));
        }
    }

    //EFFECTS: return courses with grades below 50
    public Courses returnFailingCourses() {
        Courses failCourses = new Courses();
        for (Course c: courses) {
            if (c.calculateAverageWO() < 50) {
                failCourses.addCourseWO(c);
            }
        }
        EventLog.getInstance().logEvent(new Event("Returned failing grades"));
        return failCourses;
    }

    //EFFECT: return amount (int) of failing courses
    public int returnFailingCoursesWO() {
        Courses failCourses = new Courses();
        for (Course c: courses) {
            if (c.calculateAverageWO() < 50) {
                failCourses.addCourseWO(c);
            }
        }
        return failCourses.getSize();
    }

    //EFFECT: add course to a courses without the Event Log
    public void addCourseWO(Course c) {
        courses.add(c);
    }


    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("StudentName",name);
        json.put("courses",coursesToJson());
        EventLog.getInstance().logEvent(new Event("Saved Courses"));
        return json;
    }

    //EFFECTS: returns course in courses as a JSON array
    private JSONArray coursesToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Course c: courses) {
            jsonArray.put(c.toJson());
        }
        return jsonArray;
    }


}
