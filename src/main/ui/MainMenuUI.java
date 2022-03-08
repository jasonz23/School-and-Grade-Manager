package ui;

import model.Course;
import model.Courses;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

// run main menu
public class MainMenuUI {

    private Courses courses;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private static final String JSON_STORE = "./data/courses.json";

    //EFFECT: run the main menu application
    public MainMenuUI() {
        courses = new Courses();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        mainMenuOptions();
    }


    //EFFECT: print the options in the menu application. Processes user command option and bring user to the respective
    // menu.
    // - Courses menu
    // - Grades menu
    // - calculations menu
    private void mainMenuOptions() {
        mainMenuOptionSelections();
        Scanner in = new Scanner(System.in);
        String nextLine = in.nextLine();
        if (nextLine.equals("0")) {
            coursesMenuOptions();
        } else if (nextLine.equals("1")) {
            haveOneCourseGrade();
        } else if (nextLine.equals("2")) {
            haveOneCourseCalc();
        } else if (nextLine.equals("3")) {
            saveCourses();
        } else if (nextLine.equals("4")) {
            loadCourses();
        } else if (nextLine.equals("5")) {
            return;
        } else {
            System.out.println("please select one of the options shown");
            mainMenuOptions();
        }
    }

    //EFFECTS: run the courses menu. Processes user commend. Based on user command it will bring user to
    // - add courses
    // - remove courses
    // - view courses
    private void coursesMenuOptions() {
        while (true) {
            selectMenuOptions();
            Scanner in = new Scanner(System.in);
            int nextLine = in.nextInt();
            if (nextLine == 0) {
                addCourse();
            } else if (nextLine == 1) {
                dropCourse();
            } else if (nextLine == 2) {
                if (courses.getSize() == 0) {
                    System.out.println("There are no courses. Please add courses");
                } else {
                    returnCourses();
                }
            } else if (nextLine == 3) {
                break;
            } else {
                System.out.println("Select one of the options shown");
            }
        }
        mainMenuOptions();
    }

    //MODIFIES:this
    //EFFECTS: Processes user command to add a course to courses. Then will ask user if they want to add more. If they
    // don't then they will be sent back to course menu
    private void addCourse() {
        while (true) {
            System.out.println("Enter course to enroll (end to quit): ");
            Scanner in = new Scanner(System.in);
            String nextLine = in.nextLine();
            if (nextLine.equals("end") && courses.getSize() == 0) {
                System.out.println("Please have at least 1 course");
            } else if (nextLine.equals("end") && courses.getSize() >= 1) {
                break;
            } else {
                System.out.println("How many credits is this course");
                Scanner pn = new Scanner(System.in);
                int credit = pn.nextInt();
                Course c = new Course(nextLine,credit);
                if (courses.containsCourse(c)) {
                    System.out.println("Course name already taken");
                } else {
                    courses.addCourse(c);
                }
            }
        }
    }

    //MODIFIES: this
    //EFFECTS: Processes user command. Remove the course that the user selects. Then will ask user if they want to
    // more courses. If no then they will be sent back to course menu.
    private void dropCourse() {
        while (true) {
            returnCourses();
            if (courses.getSize() == 0) {
                System.out.println("There are no courses to drop");
                break;
            } else {
                System.out.println("Select Course to drop:");
                Scanner in = new Scanner(System.in);
                int number = in.nextInt();
                courses.removeCourse(number);
            }
            String repeat = correctYesNoInput("Do you want to remove more grade?", "remove");
            if (!repeat.equals("yes")) {
                break;
            }
        }
    }

    //EFFECTS: print the courses in the courses with a number corresponding to each course.
    private void returnCourses() {
        for (int i = 0; i < courses.getSize(); i++) {
            System.out.println("" + i + "-" + " " + courses.getCourse(i).getCourseName());
        }
    }

    //EFFECTS: Processes user command. Based on user command will sent user to
    // - add grade
    // - remove grade
    // - view grades
    private void gradesMenuOptions() {
        while (true) {
            options("grade");
            System.out.println("3 - Go to menu");
            Scanner an = new Scanner(System.in);
            int nextLine = an.nextInt();
            if (nextLine == 0) {
                int number = makeSelection();
                isGradesSizeZero(number);
            } else if (nextLine == 1) {
                int number = makeSelection();
                addGrades(number);
            } else if (nextLine == 2) {
                int number = makeSelection();
                printGrades(courses.getCourse(number));
                removeGrade(number);
            } else if (nextLine == 3) {
                break;
            } else {
                System.out.println("please select one of the options");
            }
        }
        mainMenuOptions();
    }

    //REQUIRES: number has to be greater than 0 and less than or equal to number of course in courses.
    //MODIFIES: this
    //EFFECTS: Process user command and add grade to a course. Then will ask to add more grades. If user answer no then
    // user will return to grades menu
    private void addGrades(int number) {
        while (true) {
            int grade;
            System.out.println("What is the name of this grade");
            Scanner in = new Scanner(System.in);
            String gradeName = in.nextLine();
            while (true) {
                System.out.println("Please enter grade:");
                Scanner en = new Scanner(System.in);
                grade = en.nextInt();
                if (grade <= 100 && grade >= 0) {
                    break;
                }
            }
            System.out.println("Please enter weighting for this grade");
            Scanner an = new Scanner(System.in);
            int weight = an.nextInt();
            courses.getCourse(number).addGrade(gradeName,grade,weight);
            String answer = correctYesNoInput("Do you want to add more grade?", "add");
            if (answer.equals("no")) {
                break;
            }
        }
    }

    //REQUIRES: number has to be greater than 0 and less than or equal to number of course in courses.
    //MODIFIES: this
    //EFFECTS: Process user command. Based on user inputs will remove a grade from a certain course. Then will be asked
    // remove more courses.
    private void removeGrade(int number) {
        while (true) {
            if (courses.getCourse(number).getGradesSize() == 0) {
                System.out.println("There are grades in this course");
                break;
            } else {
                System.out.println("Select grade to remove");
                Scanner in = new Scanner(System.in);
                int gradeNum = in.nextInt();
                courses.getCourse(number).removeGrade(gradeNum);
            }
            String repeat = correctYesNoInput("Do you want to remove more grade?", "remove");
            if (!repeat.equals("yes")) {
                break;
            }
        }
    }

    //EFFECTS: Process user command. allow user to select from
    // - calculate average for user selected course
    // - calculate gpa
    // - sort courses by gpa
    private void calcMenuOptions() {
        while (true) {
            selectCalcOptions();
            Scanner in = new Scanner(System.in);
            String answer = in.nextLine();
            if (answer.equals("0")) {
                int number = makeSelection();
                System.out.println(courses.getCourse(number).getCourseName()
                        + ": " + courses.getCourse(number).calculateAverage() + "%");
            } else if (answer.equals("1")) {
                System.out.println("GPA : " + courses.calculateGpa());
            } else if (answer.equals("2")) {
                System.out.println();
            } else if (answer.equals("3")) {
                System.out.println("I don't know how to sort list like that yet");
            } else if (answer.equals("4")) {
                printFailingCourses();
            } else if (answer.equals("5")) {
                break;
            } else {
                System.out.println("please select one of the options");
            }
        }
        mainMenuOptions();
    }

    //EFFECTS: saves the courses to file
    private void saveCourses() {
        enterName();
        try {
            jsonWriter.open();
            jsonWriter.write(courses);
            jsonWriter.close();
            System.out.println("Saved" + courses.getName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("unable to save");
        }
        mainMenuOptions();
    }

    //EFFECTS: load courses from file
    private void loadCourses() {
        try {
            courses = jsonReader.read();
            System.out.println("Loaded from" + JSON_STORE);
            System.out.println("Hi! " + courses.getName());
        } catch (IOException e) {
            System.out.println("Unable to read from file: ");
        }
        mainMenuOptions();
    }

    //EFFECTS: print menu options for String input options
    private void options(String option) {
        System.out.println("Select Option");
        System.out.println("0 - Show " + option + "s");
        System.out.println("1 - Add " + option);
        System.out.println("2 - Remove " + option);
    }

    //EFFECTS: ensure user command input is one of the options given
    private int makeSelection() {
        int number;
        while (true) {
            returnCourses();
            System.out.println("Select course:");
            Scanner in = new Scanner(System.in);
            number = in.nextInt();
            if (number >= courses.getSize() || number < 0) {
                System.out.println("Please type number corresponding to a course");
            } else {
                break;
            }
        }
        return number;
    }

    //EFFECTS: print grades in a given course
    private void printGrades(Course c) {
        for (int i = 0; i < c.getGradesSize(); i++) {
            System.out.println(i + " - " + "Grade Name: " + c.getGrade(i).getGradeName());
            System.out.println("    Grade: " + c.getGrade(i).getGrade() + "%");
            System.out.println("    Weighting:" + c.getGrade(i).getWeighting());
        }
    }

    //EFFECTS: ensure user command is either yes or no
    private String correctYesNoInput(String input, String ar) {
        String answer;
        while (true) {
            System.out.println(input + "(yes to " + ar + ", no to quit)");
            Scanner pn = new Scanner(System.in);
            answer = pn.nextLine();
            if (answer.equals("yes") || answer.equals("no")) {
                return answer;
            }
        }
    }

    //EFFECTS: print statement if grades list size in a given course is zero. Else print grades
    private void isGradesSizeZero(int number) {
        if (courses.getCourse(number).getGradesSize() == 0) {
            System.out.println("There are no grades in this course");
        } else {
            printGrades(courses.getCourse(number));
        }
    }

    //EFFECTS: if courses size is zero then print statement and make user go back to main menu. Else go to grades menu
    private void haveOneCourseGrade() {
        if (courses.getSize() == 0) {
            System.out.println("Please add at least one course first");
            mainMenuOptions();
        } else {
            gradesMenuOptions();
        }
    }

    //EFFECTS: if courses size is zero then print statement and make user go back to main menu. Else go to calc menu
    public void haveOneCourseCalc() {
        if (courses.getSize() == 0) {
            System.out.println("Please add at least one course first");
            mainMenuOptions();
        } else {
            calcMenuOptions();
        }
    }

    //EFFECTS: print all menu options for courses
    private void selectMenuOptions() {
        System.out.println("Select Option:");
        System.out.println("0 - Add Course");
        System.out.println("1 - Drop Course");
        System.out.println("2 - View Courses");
        System.out.println("3 - Return to Main Menu");
    }

    //EFFECTS: print all menu options for calc menu
    private void  selectCalcOptions() {
        System.out.println("Select Option");
        System.out.println("0 - Show average");
        System.out.println("1 - Show percentage gpa");
        System.out.println("2 - Show percentage gpa");
        System.out.println("3 - Show courses sorted by average");
        System.out.println("4 - Show which course are failing");
    }

    //EFFECTS: print all the course in courses with a failing grade
    private void printFailingCourses() {
        Courses failingCourses = courses.returnFailingCourses();
        for (int i = 0; i < failingCourses.getSize(); i++) {
            System.out.println("0 - " + "Course Name: " + failingCourses.getCourse(i).getCourseName());
            System.out.println("    Grade: " + failingCourses.getCourse(i).calculateAverage() + "%");
        }
    }

    //MODIFIES: this
    //EFFECTS: processes user command. associates a name to a courses.
    private void enterName() {
        System.out.println("What is your name?");
        Scanner in = new Scanner(System.in);
        String nextLine = in.nextLine();
        courses.addName(nextLine);
    }

    //EFFECTS: print options for main menu
    private void mainMenuOptionSelections() {
        System.out.println("Select Menu");
        System.out.println("0 - Courses");
        System.out.println("1 - Grades");
        System.out.println("2 - View Course Grades");
        System.out.println("3 - Save courses to file");
        System.out.println("4 - Load courses from file");
        System.out.println("5 - End program");
    }
}
