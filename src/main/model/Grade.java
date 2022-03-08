package model;

import org.json.JSONObject;
import persistence.JsonReader;
import persistence.Storage;

// represents a grade with it name, grade value and weighting
public class Grade implements Storage {

    private int grade;
    private int weight;
    private String gradeName;

    //EFFECTS: instantiates grade number, grade weighting and grade name
    public Grade(String gradeName, int grade, int weighting) {
        this.grade = grade;
        this.weight = weighting;
        this.gradeName = gradeName;
    }

    //EFFECTS: return grade
    public int getGrade() {
        return grade;
    }

    //EFFECTS: return weight
    public int getWeighting() {
        return weight;
    }

    //EFFECTS: return grade name
    public String getGradeName() {
        return gradeName;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("GradeName", gradeName);
        json.put("GradeNum",grade);
        json.put("GradeWeight",weight);
        return json;
    }

}
