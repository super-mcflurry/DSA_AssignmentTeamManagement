/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;
/**
 *
 * @author jsony
 */
import adt.ListInterface;
import adt.DoublyCircularLinkedList;
import java.io.Serializable;
import java.util.Objects;

public class Course implements Serializable {

    
    private String courseCode;
    private String courseName;
    private double courseFee;
    private int creditHours;
    private final double feePerCreditHours = 500.50;
    private double ResitFee = 200.00;
    private ListInterface<Programme> programmesAssociated;
    private Tutor courseLeader;
    private char ctMinGrade;
    private int courseworkWeightage;
    private int examWeightage;
    private enum TeamSize {
        SMALL, MEDIUM, LARGE, CUSTOM, FIXED
    }
    private int minTeamSize;
    private int maxTeamSize;

    

    public Course() {
        this.programmesAssociated = new DoublyCircularLinkedList<>();
    }
    
    public Course(String courseCode, String courseName, int creditHours) {
        this();
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.creditHours = creditHours;
        this.courseFee = creditHours * feePerCreditHours;
    }
    
    public Course(String courseCode, String courseName, int creditHours, int minTeamSize, int maxTeamSize) {
        this();
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.creditHours = creditHours;
        this.courseFee = creditHours * feePerCreditHours;
        this.minTeamSize = minTeamSize;
        this.maxTeamSize = maxTeamSize;
    }

    public Course(String courseCode, String courseName, int creditHours, char ctMinGrade, int courseworkWeightage, int examWeightage, int minTeamSize, int maxTeamSize, ListInterface<Programme> programmesAssociated) {
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.creditHours = creditHours;
        this.courseFee = creditHours * feePerCreditHours;
        this.programmesAssociated = programmesAssociated;
        this.ctMinGrade = ctMinGrade;
        this.courseworkWeightage = courseworkWeightage;
        this.examWeightage = examWeightage;
        this.minTeamSize = minTeamSize;
        this.maxTeamSize = maxTeamSize;
    }
    

    public double getResitFee() {
        return ResitFee;
    }

    public void setResitFee(double ResitFee) {
        this.ResitFee = ResitFee;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public double getCourseFee() {
        return courseFee;
    }

    public void setCourseFee(int creditHours) {
        this.courseFee = creditHours * feePerCreditHours;
    }

    public int getCreditHours() {
        return creditHours;
    }

    public void setCreditHours(int creditHours) {
        this.creditHours = creditHours;
    }

    public ListInterface<Programme> getProgrammesAssociated() {
        return programmesAssociated;
    }

    public void setProgrammesAssociated(ListInterface<Programme> programmesAssociated) {
        this.programmesAssociated = programmesAssociated;
    }

    public void setMinTeamSize(int minTeamSize) {
        this.minTeamSize = minTeamSize;
    }

    public int getMinTeamSize() {
        return minTeamSize;
    }

    public void setMaxTeamSize(int maxTeamSize) {
        this.maxTeamSize = maxTeamSize;
    }

    public int getMaxTeamSize() {
        return maxTeamSize;
    }

    public Tutor getCourseLeader() {
        return courseLeader;
    }

    public void setCourseLeader(Tutor courseLeader) {
        this.courseLeader = courseLeader;
    }

    public char getCtMinGrade() {
        return ctMinGrade;
    }

    public void setCtMinGrade(char ctMinGrade) {
        this.ctMinGrade = ctMinGrade;
    }

    public int getCourseworkWeightage() {
        return courseworkWeightage;
    }

    public void setCourseworkWeightage(int courseworkWeightage) {
        this.courseworkWeightage = courseworkWeightage;
    }

    public int getExamWeightage() {
        return examWeightage;
    }

    public void setExamWeightage(int examWeightage) {
        this.examWeightage = examWeightage;
    }
    
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Course course = (Course) obj;
        return Objects.equals(courseCode, course.courseCode);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        String courseInfoFormat = "%-25s: %-40s | %-40s: %-30s\n";
        stringBuilder.append(String.format(courseInfoFormat, "Course code", courseCode, "Course leader", courseLeader != null ? courseLeader.getName() : "null"));
        stringBuilder.append(String.format(courseInfoFormat, "Course name", courseName, "Minimum grade for credit transfer", ctMinGrade));
        stringBuilder.append(String.format(courseInfoFormat, "Course credit hours", creditHours, "Coursework weightage(%)", courseworkWeightage));
        stringBuilder.append(String.format(courseInfoFormat, "Course fee", "RM" + String.format("%.2f", courseFee), "Exam weightage(%)", examWeightage));
        stringBuilder.append(String.format("%-25s: %d - %d members\n\n", "Assignment team size", minTeamSize, maxTeamSize));

        stringBuilder.append("Programs Associated with This Course:\n");
        if (programmesAssociated.isEmpty()) {
            stringBuilder.append("NULL\n");
        } else {
            for (int i = 0; i < programmesAssociated.getSize(); i++) {
                stringBuilder.append(i + 1).append(". ").append(programmesAssociated.getPosition(i)).append("\n");
            }
        }

        return stringBuilder.toString();
    }


}
