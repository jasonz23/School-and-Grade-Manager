# Course Grade Management Application

## Course Grade Calculator and Manager 

**What will the application do?**

This application will store courses and have grades in each course. Each grade will have their own weighting and name. 
Each course will also have the amount of credits its worth. The application can add and remove courses as well as grades.
It can calculate gpa for individual courses or of all inputted courses. It can show the courses you are struggling with 
so that you can focus on them more. 

**Who will use it?**

- Students who want to know their gpa for their courses and know which courses they are doing poorly on.

**Why is this project of interest to you?**
- Professor usually only post grades onto canvas without the proper weighting. This means the grade on canvas is not the
actual grade in the course. I usually put all my courses into an Excel sheet to calculate my grade. Having an 
application to keep track of all my grades for all of my courses will be very convenient.
- Also having my courses sorted by grades will help me see which courses I need to focus on. 

**User Stories**

*As a user, I want to be able to:*
- Add and remove course to my course list (where each course has a course name and  course credits)
- Be able to view courses in course list
- add grades to specific course and remove grades in specific course (each grade has a name, grade, and weighting)
- view grades in a course 
- calculate average in the course
- calculate gpa of all the courses
- return courses with failing average
- save course list with courses and each courses grades list to a file
- load course list with courses and each courses grades list to from a file

**Phase 4: Task 3**
In my UML diagram there is a lot of coupling. MainMenu, CourseMenu, GradesMenu and CalcMenu all have a courses in the
field variable. MainMenu, CourseMenu and GradesMenu also all have a JsonReader and JsonWriter. If I wanted to fix this, 
I would make all these GUI classes extend an abstract class called "submenu". This submenu has one courses, one 
JsonReader and one JsonWriter. This would decrease coupling. Main class is also redundant because I could have put a 
main method into the MainMenu class. Also in my submenus (CourseMenu, GradeMenu) there are around 15+ field variables 
because there are submenus in the submenu (addCourseMenu, RemoveCourseMenu, showCourseMenu). I think I should make a 
sub-sub menu abstract class. This way the submenus are shorter and easier to read. Right now my code is extremely messy
and hard to read. 