package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Storage;

import java.util.ArrayList;
import java.util.List;

// represent a course with a list of grade and number of credits
public class Course implements Storage {

    private String courseName;
    private List<Grade> grades;
    private int credit;

    //EFFECTS: instantiates field variables
    public Course(String courseName, int credit) {
        this.courseName = courseName;
        this.grades = new ArrayList<>();
        this.credit = credit;

    }

    //EFFECTS: return course name
    public String getCourseName() {
        return courseName;
    }

    //EFFECTS: return amount of grades in grade
    public int getGradesSize() {
        return grades.size();
    }

    //EFFECTS: given a number find the grade in the list of grades corresponding to that number
    public Grade getGrade(int index) {
        return grades.get(index);
    }

    //EFFECTS: return average grade of course
    public int calculateAverage() {
        List<Integer> list = new ArrayList<>();
        int total = 0;
        for (Grade g: grades) {
            list.add(g.getGrade() * g.getWeighting());
        }
        for (int i: list) {
            total += i;
        }
        if (getTotalWeight() != 0) {
            EventLog.getInstance().logEvent(new Event("Return Course Average"));
            return total / getTotalWeight();
        }
        EventLog.getInstance().logEvent(new Event("Return Course Average"));
        return 0;
    }

    public int calculateAverageWO() {
        List<Integer> list = new ArrayList<>();
        int total = 0;
        for (Grade g: grades) {
            list.add(g.getGrade() * g.getWeighting());
        }
        for (int i: list) {
            total += i;
        }
        if (getTotalWeight() != 0) {
            return total / getTotalWeight();
        }
        return 0;
    }

    //EFFECTS: remove grades from certain index
    public void removeGradeWithIndexes(List<Integer> indexes) {
        List<Grade> removeGrades = new ArrayList<>();
        for (int i: indexes) {
            removeGrades.add(grades.get(i));
        }
        for (int i = 0; i < removeGrades.size(); i++) {
            grades.remove(removeGrades.get(i));
            EventLog.getInstance().logEvent(new Event("Removed Grade"));
        }

    }

    //EFFECTS: return total weighting of course
    public int getTotalWeight() {
        int totalWeight = 0;
        for (Grade g: grades) {
            totalWeight += g.getWeighting();
        }
        return totalWeight;
    }

    //MODIFIES: this
    //EFFECTS: add grade into grades with given input values
    public void addGrade(String gradeName, int grade, int weight) {
        Grade g = new Grade(gradeName,grade,weight);
        grades.add(g);
        EventLog.getInstance().logEvent(new Event("Added Grade"));
    }

    //MODIFIES: this
    //EFFECTS: given a number remove the grade in grades list that corresponds to the numbers placement
    public void removeGrade(int index) {
        grades.remove(index);
    }

    //EFFECTS: return credit amount
    public int getCredit() {
        return credit;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("CourseName",courseName);
        json.put("CourseCredit",credit);
        json.put("grades",gradesToJson());
        return json;
    }

    //EFFECTS: return grades in course as JSON array
    private JSONArray gradesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Grade g: grades) {
            jsonArray.put(g.toJson());
        }
        return jsonArray;
    }
}
