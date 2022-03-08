package ui;

import model.Courses;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;

//calculation menu class
public class CalcMenu extends JFrame implements ActionListener {

    private Courses courses;
    private JFrame calcMenu;
    private JButton back;
    private JPanel smallMenu;
    private GridBagConstraints gbc;

    //EFFECTS: create calculation menu
    public CalcMenu(Courses courses) throws IOException {
        this.courses = courses;
        back = new JButton("Back");
        back.addActionListener(this);
        back.setActionCommand("back");
        calcMenuFrame();
    }

    //MODIFIES: this
    //EFFECTS: create calculation menu frame, buttons and images
    private void calcMenuFrame() throws IOException {
        newField();
        calcMenu.add(new JLabel("Average For each course:"), gbc);
        for (int i = 0; i < courses.getSize(); i++) {
            JPanel panel = new JPanel();
            panel.add(new JLabel(courses.getCourse(i).getCourseName() + ": "
                    + courses.getCourse(i).calculateAverage()));
            gbc.gridy = i + 1;
            calcMenu.add(panel,gbc);
        }
        gbc.gridy += 1;
        calcMenu.add(new JLabel("Percentage GPA: " + courses.calculateGpa()),gbc);
        if (courses.returnFailingCoursesWO() == 0) {
            equalZero(gbc);
        } else {
            gbc.gridy += 1;
            calcMenu.add(new JLabel("Failing Courses:"),gbc);
            failingCourses(gbc);
        }
        gbc.gridy += 1;
        calcMenu.add(smallMenu,gbc);
        gbc.gridy += 1;
        calcMenu.add(back,gbc);
        calcMenu.pack();
    }

    //MODIFIES: this
    //EFFECTS: add the failing courses as JLabel into smallMenu
    private void failingCourses(GridBagConstraints gbc) throws IOException {
        for (int i = 0; i < courses.returnFailingCoursesWO(); i++) {
            JPanel panel = new JPanel();
            panel.add(new JLabel("Course Name: " + courses.returnFailingCourses().getCourse(i).getCourseName()));
            gbc.gridy += 1;
            calcMenu.add(panel,gbc);
        }
        BufferedImage thumbsUp = ImageIO.read(new File("./data/sad.png"));
        Image finalImage = thumbsUp.getScaledInstance(150,150,150);
        JLabel picLabel = new JLabel(new ImageIcon(finalImage));
        gbc.gridy += 1;
        calcMenu.add(picLabel,gbc);
    }

    //MODIFIES: this
    //EFFECTS: give image if there are zero failing courses
    private void equalZero(GridBagConstraints gbc) throws IOException {
        gbc.gridx = 0;
        gbc.gridy += 1;
        calcMenu.add(new JLabel("You have no failing courses"), gbc);
        BufferedImage thumbsUp = ImageIO.read(new File("./data/thumbsup.png"));
        Image finalImage = thumbsUp.getScaledInstance(150,150,150);
        JLabel picLabel = new JLabel(new ImageIcon(finalImage));
        smallMenu.add(picLabel);
    }

    //MODIFIES: this
    //EFFECTS: instantiates new variables for calculation menu
    private void newField() {
        calcMenu = new JFrame();
        smallMenu = new JPanel();
        calcMenu.setPreferredSize(new Dimension(400,600));
        calcMenu.setLayout(new GridLayout(8,1,10,10));
        calcMenu.setLayout(new GridBagLayout());
        smallMenu.setPreferredSize(new Dimension(150,150));
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        gbc.gridx = 0;
        gbc.gridy = 0;
        calcMenu.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        calcMenu.setVisible(true);
        calcMenu.setTitle("Calculations");
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        if ("back".equals(e.getActionCommand())) {
            calcMenu.dispose();
            new MainMenu(courses);
        }

    }

}
