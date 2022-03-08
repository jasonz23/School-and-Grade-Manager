package ui;

import model.Course;
import model.Courses;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// courses menu class
public class CoursesMenu extends JFrame implements ActionListener {

    private Courses courses;
    private JButton addCourseButton;
    private JButton removeCourseButton;
    private JButton b3;
    private JButton b4;
    private JButton addButton;
    private JButton backAddButton;
    private JButton backRemoveButton;
    private JButton removeButton;
    private JButton backShowButton;
    private JFrame coursesMenu;
    private JFrame addCourseMenu;
    private JFrame showCourseMenu;
    private JFrame removeCourseMenu;
    private JTextField courseName;
    private JTextField courseCredits;
    private JList<JCheckBox> checkBoxJList;
    private DefaultListModel checkBoxList;
    private CheckListCourse item;
    private List<Integer> indexList;
    private static final String JSON_STORE = "./data/courses.json";
    private JMenuBar menuBar;
    private JMenu fileMenu;
    private JMenu infoMenu;
    private JMenuItem saveCourses;
    private JMenuItem loadCourses;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private JPanel removePanel;
    private String errorName;
    private String errorCredit;

    //EFFECTS: create course menu frame. add buttons into frame
    public CoursesMenu(Courses courses) {
        this.courses = courses;
        newField();
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(200,200,200,200));
        panel.setLayout(new GridLayout(4,1,10,10));
        createCourseMenuButtons();
        createAddRemoveButtons();
        createBackButtons();
        panel.add(addCourseButton);
        panel.add(removeCourseButton);
        panel.add(b3);
        panel.add(b4);
        coursesMenu.setJMenuBar(createMenuBar());
        coursesMenu.add(panel, BorderLayout.CENTER);
        coursesMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        coursesMenu.setSize(750,750);
        coursesMenu.setResizable(false);
        coursesMenu.setTitle("Courses Menu");
        coursesMenu.pack();
        coursesMenu.setVisible(true);
    }

    //EFFECTS: instantiate new course menu frame, and JSON
    private void newField() {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        coursesMenu = new JFrame();
    }

    //MODIFIES: this
    //EFFECTS: create buttons on the course menu
    private void createCourseMenuButtons() {
        addCourseButton = new JButton("Add Course");
        removeCourseButton = new JButton("Remove Course");
        b3 = new JButton("Show Courses");
        b4 = new JButton("Return To Main Menu");
        addCourseButton.addActionListener(this);
        removeCourseButton.addActionListener(this);
        b3.addActionListener(this);
        b4.addActionListener(this);
        addCourseButton.setVerticalTextPosition(AbstractButton.CENTER);
        addCourseButton.setHorizontalTextPosition(AbstractButton.LEADING);
        addCourseButton.setActionCommand("addCourse");
        removeCourseButton.setVerticalTextPosition(AbstractButton.BOTTOM);
        removeCourseButton.setHorizontalTextPosition(AbstractButton.CENTER);
        removeCourseButton.setActionCommand("removeCourse");
        b3.setVerticalTextPosition(AbstractButton.CENTER);
        b3.setHorizontalTextPosition(AbstractButton.LEADING);
        b3.setActionCommand("showCourse");
        b4.setVerticalTextPosition(AbstractButton.CENTER);
        b4.setHorizontalTextPosition(AbstractButton.LEADING);
        b4.setActionCommand("returnToMenu");
    }

    //MODIFIES: this
    //EFFECTS: create add course and remove course button
    private void createAddRemoveButtons() {
        addButton = new JButton("Add");
        addButton.setVerticalTextPosition(AbstractButton.CENTER);
        addButton.setHorizontalTextPosition(AbstractButton.LEADING);
        addButton.setMnemonic(KeyEvent.VK_D);
        addButton.setActionCommand("add");
        addButton.addActionListener(this);


        removeButton = new JButton("Remove");
        removeButton.setVerticalTextPosition(AbstractButton.CENTER);
        removeButton.setHorizontalTextPosition(AbstractButton.LEADING);
        removeButton.setMnemonic(KeyEvent.VK_D);
        removeButton.setMaximumSize(new Dimension(100,20));
        removeButton.setActionCommand("remove");
        removeButton.addActionListener(this);
    }

    //MODIFIES: this
    //EFFECTS: create back button. To go back to previous frame
    private void createBackButtons() {
        backAddButton = new JButton(("Back"));
        backAddButton.setVerticalTextPosition(AbstractButton.CENTER);
        backAddButton.setHorizontalTextPosition(AbstractButton.LEADING);
        backAddButton.setMnemonic(KeyEvent.VK_D);
        backAddButton.setActionCommand("backAdd");
        backAddButton.addActionListener(this);

        backRemoveButton = new JButton(("Back"));
        backRemoveButton.setVerticalTextPosition(AbstractButton.CENTER);
        backRemoveButton.setHorizontalTextPosition(AbstractButton.LEADING);
        backRemoveButton.setMaximumSize(new Dimension(100,20));
        backRemoveButton.setMnemonic(KeyEvent.VK_D);
        backRemoveButton.setActionCommand("backRemove");
        backRemoveButton.addActionListener(this);

        backShowButton = new JButton("Back");
        backShowButton.setVerticalTextPosition(AbstractButton.CENTER);
        backShowButton.setHorizontalTextPosition(AbstractButton.LEADING);
        backShowButton.setMnemonic(KeyEvent.VK_D);
        backShowButton.setActionCommand("backShow");
        backShowButton.addActionListener(this);
    }

    //EFFECTS: Actions for each action command
    @Override
    public void actionPerformed(ActionEvent e) {
        moveMenu(e);
        if ("add".equals((e.getActionCommand()))) {
            addCourseToCourses();
        } else if ("backAdd".equals(e.getActionCommand())) {
            backAddButton(addCourseMenu);
        } else if ("backRemove".equals((e.getActionCommand()))) {
            removeCourseMenu.dispose();
            coursesMenu.setVisible(true);
        } else if ("remove".equals((e.getActionCommand()))) {
            removeCourseFromCourses();
        } else if ("showCourse".equals(e.getActionCommand())) {
            coursesMenu.dispose();
            showCourseMenu();
        } else if ("backShow".equals(e.getActionCommand())) {
            showCourseMenu.dispose();
            coursesMenu.setVisible(true);
        } else if ("save".equals((e.getActionCommand()))) {
            saveCourses();
        } else if ("load".equals(e.getActionCommand())) {
            loadCourses();
        } else if ("info".equals(e.getActionCommand())) {
            infoMenu();
        }
    }

    //MODIFIES: this
    //EFFECTS: action commands for course menu buttons
    private void moveMenu(ActionEvent e) {
        if ("addCourse".equals(e.getActionCommand())) {
            goToAddCourseMenu();
        } else if ("removeCourse".equals(e.getActionCommand())) {
            goToRemoveCourseMenu();
        } else if ("returnToMenu".equals(e.getActionCommand())) {
            returnToMainMenu();
        }
    }

    //MODIFIES: this
    //EFFECTS: close add course menu frame and make course menu frame visible
    private void backAddButton(JFrame addCourseMenu) {
        addCourseMenu.dispose();
        coursesMenu.setVisible(true);
    }

    //MODIFIES: this
    //EFFECTS: information about the app
    private void infoMenu() {
    }

    //MODIFIES: this
    //EFFECTS: remove course from courses
    private void removeCourseFromCourses() {
        courses.removeCoursesWithIndexes(indexList);
        removeCourseMenu.dispose();
        removeCourse();
    }

    //MODIFIES: this
    //EFFECTS: if course credits is greater and course name is unique then add course to courses
    // otherwise: produce an error message
    private void addCourseToCourses() {
        try {
            int courseCredit = Integer.parseInt(courseCredits.getText());
            if (courseCredit < 0) {
                throw new NumberFormatException();
            }
            Course c = new Course(courseName.getText(),courseCredit);
            if (courses.containsCourse(c)) {
                errorName = "Courses not added because Name matches with another";
            } else {
                courses.addCourse(c);
                errorName = "course added";
            }
            updateTextBox(errorName,"");
        } catch (NumberFormatException e) {
            errorCredit = "Please enter a Integer (> 0)";
            updateTextBox("",errorCredit);
        }
    }

    //MODIFIES: this
    //EFFECTS: After value is added to text field, put name and credit string into text field
    public void updateTextBox(String name, String credit) {
        courseName.setText(name);
        courseCredits.setText(credit);
    }

    //MODIFIES: this
    //EFFECTS:close course menu and open new main menu
    private void returnToMainMenu() {
        coursesMenu.dispose();
        new MainMenu(courses);
    }

    //MODIFIES: this
    //EFFECTS: make course menu invisible and open remove course menu
    private void goToRemoveCourseMenu() {
        coursesMenu.setVisible(false);
        removeCourse();
    }

    //MODIFIES: this
    //EFFECTS: make course menu invisible and open add course menu
    private void goToAddCourseMenu() {
        coursesMenu.setVisible(false);
        addCourses();
    }

    //MODIFIES: this
    //EFFECTS: create add course menu frame and add the buttons into frame
    public void addCourses() {
        addCourseMenu = new JFrame();
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(125,125,125,125));
        panel.setLayout(new GridLayout(3,2,10,10));

        courseName = new JTextField();
        courseName.setPreferredSize(new Dimension(200,40));

        courseCredits = new JTextField();
        courseCredits.setPreferredSize(new Dimension(200,40));
        panel.add(new JLabel("Course Name: "));
        panel.add(courseName);
        panel.add(new JLabel("Course Credit: "));
        panel.add(courseCredits);

        panel.add(addButton);
        panel.add(backAddButton);

        addCourseMenu.add(panel, BorderLayout.CENTER);

        addCourseMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addCourseMenu.setResizable(false);
        addCourseMenu.setTitle("Add Course");
        addCourseMenu.pack();

        addCourseMenu.setVisible(true);
    }

    //MODIFIES: this
    //EFFECTS: create remove course menu frame and add the buttons into frame
    public void removeCourse() {
        newRemoveField();
        removeCourseMenu.add(removePanel, BorderLayout.CENTER);
        checkBoxJList.setCellRenderer(new CheckBoxRender());
        for (int i = 0; i < courses.getSize(); i++) {
            CheckListCourse cb = new CheckListCourse("" + courses.getCourse(i).getCourseName());
            checkBoxList.addElement(cb);
        }
        JScrollPane pane = new JScrollPane((checkBoxJList));
        mouseEvent();
        pane.setPreferredSize(new Dimension(100,30 * courses.getSize()));
        if (!(courses.getSize() == 0)) {
            removePanel.add(pane);
        } else if (courses.getSize() == 0) {
            removePanel.add(new JLabel("There are no courses to remove"));
            removePanel.setLayout(new GridLayout(3,1,10,10));
        }
        removePanel.add(removeButton);
        removePanel.add(backRemoveButton);
        removeCourseMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        removeCourseMenu.setResizable(false);
        removeCourseMenu.setTitle("Remove Course");
        removeCourseMenu.pack();
        removeCourseMenu.setVisible(true);
    }

    //MODIFIES: this
    //EFFECTS: instantiate all the field used in remove menu
    private void newRemoveField() {
        removeCourseMenu = new JFrame();
        removePanel = new JPanel();
        checkBoxJList = new JList<>();
        indexList = new ArrayList<>();
        checkBoxList = new DefaultListModel();
        checkBoxJList = new JList(checkBoxList);
        removePanel.setLayout(new FlowLayout());
        removePanel.setBorder(BorderFactory.createEmptyBorder(125,125,125,125));
    }

    //MODIFIES: this
    //EFFECTS: Mouse event for remove course menu
    private void mouseEvent() {
        checkBoxJList.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent event) {
                JList list = (JList) event.getSource();
                int index = list.locationToIndex(event.getPoint());
                item = (CheckListCourse) list.getModel().getElementAt(index);
                item.setChecked(! item.isChecked());
                list.repaint(list.getCellBounds(index, index));
                indexList.add(index);
            }
        });
    }

    //class for items in checkbox J list.
    static class CheckListCourse {
        private String checkBoxName;
        private boolean isChecked = false;

        //getter
        public boolean isChecked() {
            return isChecked;
        }

        //MODIFIES: this
        //EFFECTS: set checkBoxName to name
        public CheckListCourse(String name) {
            this.checkBoxName = name;
        }

        //MODIFIES: this
        //EFFECTS: set isChecked to isSelected
        public void setChecked(boolean isCheck) {
            this.isChecked = isCheck;
        }

        //getter
        @Override
        public String toString() {
            return checkBoxName;
        }
    }

    //render the checkbox
    static class CheckBoxRender extends JCheckBox implements ListCellRenderer {
        //EFFECT:
        public Component getListCellRendererComponent(JList list, Object value, int index,boolean isSelected,
                                                      boolean hasFocus) {
            setSelected(((CheckListCourse)value).isChecked());
            setFont(list.getFont());
            setEnabled(list.isEnabled());
            setForeground(list.getForeground());
            setBackground(list.getBackground());
            setText(value.toString());
            return this;
        }
    }

    //MODIFIES: this
    //EFFECTS: set show course menu frame
    private void showCourseMenu() {
        newFrameShow();
        if (!(courses.getSize() == 0)) {
            for (int i = 0; i < courses.getSize(); i++) {
                JPanel panel = new JPanel();
                panel.setLayout(new GridLayout(2,1,10,10));
                panel.setBorder(BorderFactory.createLineBorder(Color.blue));
                panel.setPreferredSize(new Dimension(175,75));
                panel.add(new JLabel("Course Name: " + courses.getCourse(i).getCourseName()));
                panel.add(new JLabel("Course Credit: " + courses.getCourse(i).getCredit()));
                showCourseMenu.add(panel, BorderLayout.CENTER);
            }
        } else if (courses.getSize() == 0) {
            showCourseMenu.setPreferredSize(new Dimension(300,300));
            showCourseMenu.add(new JLabel("There are no Courses"));
        }
        showCourseMenu.add(backShowButton);
        showCourseMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        showCourseMenu.setResizable(false);
        showCourseMenu.setTitle("Show Courses");
        showCourseMenu.pack();

        showCourseMenu.setVisible(true);
    }

    //MODIFIES: this
    //EFFECTS: instantiate and set show course menu frame
    private void newFrameShow() {
        showCourseMenu = new JFrame();
        showCourseMenu.setLayout(new FlowLayout());
        showCourseMenu.setPreferredSize(new Dimension(courses.getSize() * 250, 180));
        showCourseMenu.setBackground(Color.white);
    }

    //MODIFIES: this
    //EFFECTS: create menu bar
    private JMenuBar createMenuBar() {
        menuBar = new JMenuBar();
        fileMenu = new JMenu("File");
        infoMenu = new JMenu("Info");
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
        saveCourses.addActionListener(this);
        loadCourses.addActionListener(this);
        infoMenu.addActionListener(this);
        return menuBar;
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
            coursesMenu.dispose();
            new CoursesMenu(courses);
        } catch (IOException e) {
            System.out.println("Unable to read from file: ");
        }
    }
}
