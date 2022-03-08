package ui;

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

//menu to show add and remove grades
public class GradesMenu extends JFrame implements ActionListener {

    private Courses courses;
    private JButton b1;
    private JButton b2;
    private JButton b3;
    private JButton b4;
    private JButton addButton;
    private JButton backAddButton;
    private JButton backRemoveButton;
    private JButton removeButton;
    private JButton backShowButton;
    private JFrame gradesMenu;
    private JFrame addGradeMenu;
    private JFrame showGradeMenu;
    private JFrame removeGradeMenu;
    private JTextField gradeName;
    private JTextField grade;
    private JTextField gradeWeight;
    private JList<JCheckBox> checkBoxJList;
    private DefaultListModel checkBoxList;
    private Integer whichCourse;
    private JComboBox comboBoxAddGrade;
    private JComboBox comboBoxRemoveGrade;
    private List<String> coursesString;
    private CheckBoxListGrade item;
    private List<Integer> indexList;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private static final String JSON_STORE = "./data/courses.json";
    private JMenuBar menuBar;
    private JMenu fileMenu;
    private JMenu infoMenu;
    private JMenuItem saveCourses;
    private JMenuItem loadCourses;
    private String errorMessage;

    //EFFECTS: grade menu
    public GradesMenu(Courses courses) {
        newField(courses);
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(200,200,200,200));
        panel.setLayout(new GridLayout(4,1,10,10));
        createGradesMenuButtons();
        createOtherButtons();
        panel.add(b1);
        panel.add(b2);
        panel.add(b3);
        panel.add(b4);
        gradesMenu.add(panel, BorderLayout.CENTER);
        gradesMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gradesMenu.setSize(750,750);
        gradesMenu.setResizable(false);
        gradesMenu.setTitle("Grades Menu");
        gradesMenu.setJMenuBar(createMenuBar());
        gradesMenu.pack();
        gradesMenu.setVisible(true);
        whichCourse = 0;
        coursesString = new ArrayList<>();
        for (int i = 0; i < courses.getSize(); i++) {
            coursesString.add(courses.getCourse(i).getCourseName());
        }
    }

    //MODIFIES: this
    //EFFECTS: instantiate variables used in grade menu method
    private void newField(Courses courses) {
        checkBoxJList = new JList<>();
        this.courses = courses;
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        gradesMenu = new JFrame();
    }

    //MODIFIES: this
    //EFFECTS: instantiate and set attributes for grade menu buttons
    private void createGradesMenuButtons() {
        b1 = new JButton("Add Grade");
        b2 = new JButton("Remove Grade");
        b3 = new JButton("Show Grade");
        b4 = new JButton("Return To Main Menu");

        b1.setVerticalTextPosition(AbstractButton.CENTER);
        b1.setHorizontalTextPosition(AbstractButton.LEADING);
        b1.setMnemonic(KeyEvent.VK_D);
        b1.setActionCommand("addGrade");

        b2.setVerticalTextPosition(AbstractButton.BOTTOM);
        b2.setHorizontalTextPosition(AbstractButton.CENTER);
        b2.setMnemonic(KeyEvent.VK_M);
        b2.setActionCommand("removeGrade");

        b3.setVerticalTextPosition(AbstractButton.CENTER);
        b3.setHorizontalTextPosition(AbstractButton.LEADING);
        b3.setMnemonic(KeyEvent.VK_E);
        b3.setActionCommand("showGrade");

        b4.setVerticalTextPosition(AbstractButton.CENTER);
        b4.setHorizontalTextPosition(AbstractButton.LEADING);
        b4.setMnemonic(KeyEvent.VK_E);
        b4.setActionCommand("returnToMenu");
        addButtonAction();
    }

    //MODIFIES: this
    //EFFECTS: give action listener to add button
    private void addButtonAction() {
        b1.addActionListener(this);
        b2.addActionListener(this);
        b3.addActionListener(this);
        b4.addActionListener(this);
    }

    //MODIFIES:this
    //EFFECTS: instantiate and set attributes to other buttons used in grades class
    private void createOtherButtons() {
        addButton = new JButton("Add");
        addButton.setVerticalTextPosition(AbstractButton.CENTER);
        addButton.setHorizontalTextPosition(AbstractButton.LEADING);
        addButton.setActionCommand("add");

        backAddButton = new JButton(("Back"));
        backAddButton.setVerticalTextPosition(AbstractButton.CENTER);
        backAddButton.setHorizontalTextPosition(AbstractButton.LEADING);
        backAddButton.setActionCommand("backAdd");

        backRemoveButton = new JButton(("Back"));
        backRemoveButton.setVerticalTextPosition(AbstractButton.CENTER);
        backRemoveButton.setHorizontalTextPosition(AbstractButton.LEADING);
        backRemoveButton.setActionCommand("backRemove");

        removeButton = new JButton("Remove");
        removeButton.setVerticalTextPosition(AbstractButton.CENTER);
        removeButton.setHorizontalTextPosition(AbstractButton.LEADING);
        removeButton.setActionCommand("remove");

        backShowButton = new JButton("Back");
        backShowButton.setVerticalTextPosition(AbstractButton.CENTER);
        backShowButton.setHorizontalTextPosition(AbstractButton.LEADING);
        backShowButton.setActionCommand("backShow");

        buttonActions();
    }

    //MODIFIES: this
    //EFFECTS: give button action listeners
    private void buttonActions() {
        addButton.addActionListener(this);
        backAddButton.addActionListener(this);
        backRemoveButton.addActionListener(this);
        removeButton.addActionListener(this);
        backShowButton.addActionListener(this);
    }

    //MODIFIES: this
    //EFFECTS: actions perform for each action command
    @Override
    public void actionPerformed(ActionEvent e) {
        moveToMenu(e);
        if ("add".equals((e.getActionCommand()))) {
            createAndAddGrade();
        } else if ("remove".equals((e.getActionCommand()))) {
            courses.getCourse(whichCourse).removeGradeWithIndexes(indexList);
            removeGradeMenu.dispose();
            removeGrade();
        } else if (e.getSource() == comboBoxAddGrade) {
            comboBoxAddGrade.getSelectedIndex();
        } else if ("set".equals(e.getActionCommand())) {
            whichCourse = comboBoxRemoveGrade.getSelectedIndex();
            removeGradeMenu.dispose();
            removeGrade();
        }
        backButtonAction(e);
        menuBarOptions(e);
    }

    //MODIFIES: this
    //EFFECTS: actions for back buttons
    private void backButtonAction(ActionEvent e) {
        if ("backAdd".equals(e.getActionCommand())) {
            addGradeMenu.dispose();
            gradesMenu.setVisible(true);
        } else if ("backRemove".equals((e.getActionCommand()))) {
            removeGradeMenu.dispose();
            gradesMenu.setVisible(true);
        } else if ("backShow".equals(e.getActionCommand())) {
            showGradeMenu.dispose();
            gradesMenu.setVisible(true);
        }
    }

    //MODIFIES: this
    //EFFECTS: Actions for menu bar
    private void menuBarOptions(ActionEvent e) {
        if ("save".equals((e.getActionCommand()))) {
            saveCourses();
        } else if ("load".equals(e.getActionCommand())) {
            loadCourses();
        } else if ("info".equals(e.getActionCommand())) {
            infoMenu();
        }
    }

    //MODIFIES: this
    //EFFECTS: Actions for moving between frames button
    private void moveToMenu(ActionEvent e) {
        if ("addGrade".equals(e.getActionCommand())) {
            gradesMenu.setVisible(false);
            addGrade();
        } else if ("removeGrade".equals(e.getActionCommand())) {
            gradesMenu.setVisible(false);
            removeGrade();
        } else if ("returnToMenu".equals(e.getActionCommand())) {
            gradesMenu.dispose();
            new MainMenu(courses);
        } else if ("showGrade".equals(e.getActionCommand())) {
            gradesMenu.dispose();
            showGradesMenu();
        }
    }

    //MODIFIES: this
    //EFFECTS: add grade to selected course if it runs into no errors
    private void createAndAddGrade() {
        try {
            int gradeNum = Integer.parseInt(grade.getText());
            int gradeWei = Integer.parseInt((gradeWeight.getText()));
            courses.getCourse(comboBoxAddGrade.getSelectedIndex()).addGrade(gradeName.getText(),gradeNum,gradeWei);
            updateTextBox("","","");
        } catch (NumberFormatException e) {
            errorMessage = "Enter an integer";
            updateTextBox("",errorMessage,errorMessage);
        }

    }

    //MODIFIES: this
    //EFFECTS: update the text fields in the add grade menu
    public void updateTextBox(String name, String grade, String weight) {
        gradeName.setText(name);
        this.grade.setText(grade);
        gradeWeight.setText(weight);
    }

    //MODIFIES: this
    //EFFECTS:
    public void addGrade() {
        addGradeMenu = new JFrame();
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(125,125,125,125));
        panel.setLayout(new GridLayout(6,2,10,10));
        if (courses.getSize() == 0) {
            comboBoxAddGrade = new JComboBox(new String[]{"No Courses"});
        } else {
            comboBoxAddGrade = new JComboBox(coursesString.toArray());
        }
        createTextFields();
        addToPanel(panel);

        panel.add(addButton);
        panel.add(backAddButton);

        addGradeMenu.add(panel, BorderLayout.CENTER);

        addGradeMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addGradeMenu.setResizable(false);
        addGradeMenu.setTitle("Add Course");
        addGradeMenu.pack();

        addGradeMenu.setVisible(true);
    }

    //MODIFIES: this
    //EFFECTS: create text fields for add grade menu
    private void createTextFields() {
        gradeName = new JTextField();
        gradeName.setPreferredSize(new Dimension(200,40));
        grade = new JTextField();
        grade.setPreferredSize(new Dimension(200,40));
        gradeWeight = new JTextField();
        gradeWeight.setPreferredSize(new Dimension(200,40));
    }

    //MODIFIES: panel
    //EFFECTS: add things to add grade menu panel
    private void addToPanel(JPanel panel) {
        panel.add(new JLabel("Select Course to add grade: "));
        panel.add(comboBoxAddGrade);
        panel.add(new JLabel(""));
        panel.add(new JLabel(""));
        panel.add(new JLabel("Grade Name: "));
        panel.add(gradeName);
        panel.add(new JLabel("Grade: "));
        panel.add(grade);
        panel.add(new JLabel("Grade Weight: "));
        panel.add(gradeWeight);
    }

    //MODIFIES: this
    //EFFECTS:create remove grade menu
    public void removeGrade() {
        removeGradeMenu = new JFrame();
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(125,125,125,125));
        panel.setLayout(new FlowLayout());
        removeGradeMenu.add(panel, BorderLayout.CENTER);
        if (courses.getSize() != 0) {
            removeGradeComboBox(panel);
            for (int i = 0; i < courses.getCourse(whichCourse).getGradesSize(); i++) {
                CheckBoxListGrade cb = new CheckBoxListGrade(""
                        + courses.getCourse(whichCourse).getGrade(i).getGradeName());
                checkBoxList.addElement(cb);
            }
            JScrollPane pane = new JScrollPane((checkBoxJList));
            removeMouseEvent();
            pane.setPreferredSize(new Dimension(100,30 * courses.getCourse(whichCourse).getGradesSize()));
            addToPaneGradeSize(panel, pane);
        } else {
            panel.add(new JLabel("There are no courses"));
        }
        panel.add(removeButton);
        panel.add(backRemoveButton);
        removeGradeMenu.pack();
        setRemoveGradeFrame();
    }

    //MODIFIES:panel,pane
    //EFFECTS: based on grade size add to panel
    private void addToPaneGradeSize(JPanel panel, JScrollPane pane) {
        if (!(courses.getCourse(whichCourse).getGradesSize() == 0)) {
            panel.add(pane);
        } else if (courses.getCourse(whichCourse).getGradesSize() == 0) {
            panel.add(new JLabel("There are no grades to remove for this course"));
            panel.setLayout(new GridLayout(3,1,10,10));
        }
    }

    private void setRemoveGradeFrame() {
        removeGradeMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        removeGradeMenu.setResizable(false);
        removeGradeMenu.setTitle("Remove Course");
        removeGradeMenu.setVisible(true);
    }

    //MODIFIES: this, panel
    //EFFECTS: set combo box remove grade
    private void removeGradeComboBox(JPanel panel) {
        comboBoxRemoveGrade = new JComboBox(coursesString.toArray());
        comboBoxRemoveGrade.setSelectedIndex(whichCourse);
        comboBoxRemoveGrade.addActionListener(this);
        comboBoxRemoveGrade.setActionCommand("set");
        indexList = new ArrayList<>();
        panel.add(comboBoxRemoveGrade);
        checkBoxList = new DefaultListModel();
        checkBoxJList = new JList(checkBoxList);
        checkBoxJList.setCellRenderer(new CheckBoxListGradeRender());
    }

    //MODIFIES: this
    //EFFECTS: mouse event for remove course
    private void removeMouseEvent() {
        checkBoxJList.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent event) {
                JList list = (JList) event.getSource();
                int index = list.locationToIndex(event.getPoint());
                item = (CheckBoxListGrade) list.getModel().getElementAt(index);
                item.setChecked(! item.isChecked());
                list.repaint(list.getCellBounds(index, index));
                indexList.add(index);
            }
        });
    }

    //check box list grade class
    static class CheckBoxListGrade {
        private String checkBoxName;
        private boolean isChecked = false;

        //EFFECTS: set check box name to name
        public CheckBoxListGrade(String name) {
            this.checkBoxName = name;
        }

        //EFFECTS: set is checked to is selected
        public void setChecked(boolean s) {
            this.isChecked = s;
        }

        //getter
        public boolean isChecked() {
            return isChecked;
        }

        @Override
        public String toString() {
            return checkBoxName;
        }
    }

    //class to render check box list for grades
    static class CheckBoxListGradeRender extends JCheckBox implements ListCellRenderer {
        public Component getListCellRendererComponent(JList list, Object value, int index,boolean isSelected,
                                                      boolean hasFocus) {
            setEnabled(list.isEnabled());

            setFont(list.getFont());

            setSelected(((CheckBoxListGrade)value).isChecked());

            setForeground(list.getForeground());

            setBackground(list.getBackground());

            setText(value.toString());
            return this;
        }
    }

    //MODIFIES: this
    //EFFECTS: create show grades menu
    private void showGradesMenu() {
        newShowField();
        if (courses.isGrades()) {
            for (int i = 0; i < courses.getSize(); i++) {
                JPanel panel = new JPanel();
                showCourse(i, panel);
                for (int k = 0; k < courses.getCourse(i).getGradesSize(); k++) {
                    showGrade(i, panel, k);
                }
                if (courses.getCourse(i).getGradesSize() == 0) {
                    noGradesShow(panel);
                }
                showGradeMenu.add(panel,TOP_ALIGNMENT);
            }
        } else {
            showGradeMenu.setPreferredSize(new Dimension(300,300));
            showGradeMenu.add(new JLabel("There are no grades in any of your courses"));
        }
        showGradeMenuStuff();

        showGradeMenu.setVisible(true);
    }

    //MODIFIES:this
    //EFFECTS: show grade menu frame setting things
    private void showGradeMenuStuff() {
        showGradeMenu.add(backShowButton);
        showGradeMenu.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        showGradeMenu.setResizable(false);
        showGradeMenu.setTitle("Remove Course");
        showGradeMenu.pack();
    }

    //MODIFIES: panel
    //EFFECTS: add to panel if there are no grades in the course
    private void noGradesShow(JPanel panel) {
        panel.add(new JLabel("There are no grades for this Course"));
        panel.setPreferredSize(new Dimension(260,50));
    }

    //MODIFIES: this
    //EFFECTS: create new show grade menu frame and set dimension and layout
    private void newShowField() {
        showGradeMenu = new JFrame();
        showGradeMenu.setPreferredSize(new Dimension(courses.getSize() * 300,450));
        showGradeMenu.setLayout(new FlowLayout());
    }

    //MODIFIES: this
    //EFFECTS:
    private void showCourse(int i, JPanel panel) {
        panel.setBorder(BorderFactory.createLineBorder(Color.blue));
        panel.setLayout(new GridLayout(0,1));
        panel.add(new JLabel("Course Name: " + courses.getCourse(i).getCourseName()));
    }

    //MODIFIES: panel
    //EFFECTS: add grade name, grade and grade weight in JLabel into panel
    private void showGrade(int i, JPanel panel, int k) {
        panel.add(new JLabel("Grade Name: " + courses.getCourse(i).getGrade(k).getGradeName()));
        panel.add(new JLabel("Grade: " + courses.getCourse(i).getGrade(k).getGrade()));
        panel.add(new JLabel("Grade Weight: " + courses.getCourse(i).getGrade(k).getWeighting()));
        panel.setPreferredSize(new Dimension(150,courses.getCourse(i).getGradesSize() * 75));
    }

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
            gradesMenu.dispose();
            new GradesMenu(courses);
        } catch (IOException e) {
            System.out.println("Unable to read from file: ");
        }
    }

    public void infoMenu() {

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
        saveCourses.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, ActionEvent.ALT_MASK));
        saveCourses.getAccessibleContext().setAccessibleDescription("Save information into JSON file");
        saveCourses.setActionCommand("save");
        fileMenu.add(saveCourses);
        loadCourses = new JMenuItem("Load", KeyEvent.VK_T);
        loadCourses.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, ActionEvent.ALT_MASK));
        loadCourses.getAccessibleContext().setAccessibleDescription("Load information from JSON file");
        loadCourses.setActionCommand("load");
        fileMenu.add(loadCourses);
        saveCourses.addActionListener(this);
        loadCourses.addActionListener(this);
        infoMenu.addActionListener(this);
        return menuBar;
    }
}
