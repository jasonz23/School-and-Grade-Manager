package ui;

import model.Courses;

public class Main {
//    public static void main(String[] args) {
//        new MainMenuUI();
//    }
    public static void main(String[] args) {
        Courses courses = new Courses();
        new MainMenu(courses);
    }
}
