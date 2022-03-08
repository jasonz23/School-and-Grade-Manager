package ui;

import model.Courses;
import model.Event;
import model.EventLog;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import persistence.JsonReader;
import persistence.JsonWriter;


//the main menu window
public class MainMenu extends JFrame implements ActionListener {

    private Courses courses;

    private JButton b1;
    private JButton b2;
    private JButton b3;
    private JFrame frame1;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private static final String JSON_STORE = "./data/courses.json";
    private JMenuBar menuBar;
    private JMenu fileMenu;
    private JMenu infoMenu;
    private JMenuItem saveCourses;
    private JMenuItem loadCourses;
    private JMenuItem exitProgram;

    //MODIFIES: this
    //EFFECTS: create main menu frame and main menu buttons
    public MainMenu(Courses courses) {
        this.courses = courses;
        frame1 = new JFrame();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        b1 = new JButton("Courses");
        b2 = new JButton("Grades");
        b3 = new JButton("Calculations");

        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(200,200,200,200));
        panel.setLayout(new GridLayout(3,1,10,10));

        createButtons();

        panel.add(b1);
        panel.add(b2);
        panel.add(b3);

        b1.addActionListener(this);
        b2.addActionListener(this);
        b3.addActionListener(this);

        setFrame(panel);

    }

    //MODIFIES: this
    //EFFECTS: set all main menu frame
    private void setFrame(JPanel panel) {
        frame1.setJMenuBar(createMenuBar());

        frame1.add(panel, BorderLayout.CENTER);
        frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame1.setSize(750,750);
        frame1.setResizable(false);
        frame1.setTitle("GPA calculator");
        frame1.pack();
        frame1.setVisible(true);
    }

    //MODIFIES: this
    //EFFECTS: set all the features of the main menu buttons
    private void createButtons() {
        b1.setVerticalTextPosition(AbstractButton.CENTER);
        b1.setHorizontalTextPosition(AbstractButton.LEADING);
        b1.setMnemonic(KeyEvent.VK_D);
        b1.setActionCommand("coursesMenu");

        b2.setVerticalTextPosition(AbstractButton.BOTTOM);
        b2.setHorizontalTextPosition(AbstractButton.CENTER);
        b2.setMnemonic(KeyEvent.VK_M);
        b2.setActionCommand("gradesMenu");

        b3.setVerticalTextPosition(AbstractButton.CENTER);
        b3.setHorizontalTextPosition(AbstractButton.LEADING);
        b3.setMnemonic(KeyEvent.VK_E);
        b3.setActionCommand("calcMenu");
    }

    //MODIFIES: this
    //EFFECTS: Create the menu bar
    private JMenuBar createMenuBar() {
        menuBar = new JMenuBar();
        fileMenu = new JMenu("File");
        infoMenu = new JMenu("Info");
        exitProgram = new JMenuItem("Exit");
        fileMenu.setMnemonic(KeyEvent.VK_A);
        infoMenu.setActionCommand("info");
        menuBar.add(fileMenu);
        saveCourses = new JMenuItem("Save", KeyEvent.VK_T);
        saveCourses.getAccessibleContext().setAccessibleDescription("Save information into JSON file");
        saveCourses.setActionCommand("save");
        fileMenu.add(saveCourses);
        loadCourses = new JMenuItem("Load", KeyEvent.VK_T);
        loadCourses.getAccessibleContext().setAccessibleDescription("Load information from JSON file");
        loadCourses.setActionCommand("load");
        fileMenu.add(loadCourses);
        exitProgram.setActionCommand("exit");
        fileMenu.add(exitProgram);
        exitProgram.addActionListener(this);
        saveCourses.addActionListener(this);
        loadCourses.addActionListener(this);
        infoMenu.addActionListener(this);
        return menuBar;
    }

    //MODIFIES: this
    //EFFECTS: Action listener actions
    @Override
    public void actionPerformed(ActionEvent e) {
        if ("coursesMenu".equals(e.getActionCommand())) {
            frame1.setVisible(false);
            new CoursesMenu(courses);
        } else if ("gradesMenu".equals(e.getActionCommand())) {
            frame1.dispose();
            new GradesMenu(courses);
        } else if ("calcMenu".equals(e.getActionCommand())) {
            frame1.dispose();
            uploadPNG();
        } else if ("save".equals((e.getActionCommand()))) {
            saveCourses();
        } else if ("load".equals(e.getActionCommand())) {
            loadCourses();
        } else if ("info".equals(e.getActionCommand())) {
            infoMenu();
        } else if ("exit".equals(e.getActionCommand())) {
            for (Event event: EventLog.getInstance()) {
                System.out.println(event.toString());
            }
            System.exit(0);
        }
    }

    //EFFECT: try catch to upload png
    private void uploadPNG() {
        try {
            new CalcMenu(courses);
        } catch (IOException h) {
            System.out.println("Can not find PNG");
        }
    }

    //MODIFIES: this
    //EFFECTS: saves the courses to file
    public void saveCourses() {
        try {
            jsonWriter.open();
            jsonWriter.write(courses);
            jsonWriter.close();
            System.out.println("Saved" + courses.getName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("unable to save");
        }
    }

    //MODIFIES: this
    //EFFECTS: load courses from file
    public void loadCourses() {
        try {
            courses = jsonReader.read();
            System.out.println("Loaded from" + JSON_STORE);
            System.out.println("Hi! " + courses.getName());
        } catch (IOException e) {
            System.out.println("Unable to read from file: ");
        }
    }

    //MODIFIES: this
    //EFFECTS: shows the information about the application
    public void infoMenu() {
    }

}
