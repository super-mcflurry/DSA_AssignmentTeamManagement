/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import adt.DoublyCircularLinkedList;
import adt.ListInterface;
import entity.*;

/**
 * @author jsony
 */
public class Initializer {

    public boolean initializeProgramme(ListInterface<Programme> programmeList) {
        if (programmeList == null) {
            return false; // Return false if the list is null
        }

        boolean success = true;
        success &= programmeList.add(new Programme("RSD", "BACHELOR OF INFORMATION TECHNOLOGY (HONOURS) IN SOFTWARE SYSTEM DEVELOPMENT ", "MAY 2024", 3, "FOCS"));
        success &= programmeList.add(new Programme("RSF", "BACHELOR OF COMPUTER SCIENCE (HONOURS) IN SOFTWARE ENGINEERING ", "MAY 2024", 4, "FOCS"));
        success &= programmeList.add(new Programme("RIT", "BACHELOR OF INFORMATION TECHNOLOGY (HONOURS) IN INTERNET TECHNOLOGY", "AUGUST 2024", 3, "FOCS"));
        success &= programmeList.add(new Programme("RIS", "BACHELOR OF INFORMATION TECHNOLOGY (HONOURS) IN INFORMATION SECURITY", "AUGUST 2024", 3, "FOCS"));
        success &= programmeList.add(new Programme("RST", "BACHELOR OF COMPUTER SCIENCE (HONOURS) IN ITERACTIVE SOFTWARE TECHNOLOGY", "NOVEMBER 2024", 3, "FOCS"));
        success &= programmeList.add(new Programme("RDS", "BACHELOR OF COMPUTER SCIENCE (HONOURS) IN DATA SCIENCE", "NOVEMBER 2024", 3, "FOCS"));
        success &= programmeList.add(new Programme("RFI", "BACHELOR OF FINANCE AND INVESTMENT (HONOURS)", "MAY 2024", 3, "FAFB"));
        success &= programmeList.add(new Programme("RFN", "BACHELOR OF FINANCE (HONOURS)", "MAY 2024", 3, "FAFB"));
        success &= programmeList.add(new Programme("RAC", "BACHELOR OF ACCOUNTING (HONOURS)", "AUGUST 2024", 4, "FAFB"));
        success &= programmeList.add(new Programme("RAF", "BACHELOR OF BUSINESS (HONOURS) ACCOUNTING AND FINANCE", "AUGUST 2024", 4, "FAFB"));
        success &= programmeList.add(new Programme("RBA", "BACHELOR OF BUSINESS ADMINISTRATION (HONOURS)", "NOVEMBER 2024", 3, "FAFB"));
        success &= programmeList.add(new Programme("RCA", "BACHELOR OF CORPORATE ADMINISTRATIONS (HONOURS)", "NOVEMBER 2024", 3, "FAFB"));

        return success;
    }

    public boolean initializeCourse(ListInterface<Course> courseList, ListInterface<Programme> programmeList, ListInterface<Tutor> tutorList) {
        ListInterface<Programme> programmeAssociated1 = new DoublyCircularLinkedList<>();
        for (int i = 0; i < 3; i++) {
            programmeAssociated1.add(programmeList.getPosition(i));
        }

        ListInterface<Programme> programmeAssociated2 = new DoublyCircularLinkedList<>();
        for (int i = 3; i < 6; i++) {
            programmeAssociated2.add(programmeList.getPosition(i));
        }

        ListInterface<Programme> programmeAssociated3 = new DoublyCircularLinkedList<>();
        for (int i = 6; i < 9; i++) {
            programmeAssociated3.add(programmeList.getPosition(i));
        }

        ListInterface<Programme> programmeAssociated4 = new DoublyCircularLinkedList<>();
        for (int i = 9; i < programmeList.getSize(); i++) {
            programmeAssociated4.add(programmeList.getPosition(i));
        }

        if (courseList == null) {
            return false; // Return false if the list is null
        }

        boolean success = true;
        success &= courseList.add(new Course("FPIT1053", "DATA MANAGEMENT", 2, 'B', 60, 40, 2, 3, programmeAssociated1));
        success &= courseList.add(new Course("BJEL1023", "ACADEMIC ENGLISH", 2, 'C', 70, 30, 4, 5, programmeAssociated2));
        success &= courseList.add(new Course("BACS2063", "DATA STRUCTURES AND ALGORITHMS", 4, 'B', 50, 50, 5, 5, programmeAssociated1));
        success &= courseList.add(new Course("FPMA1014", "STATISTICS", 3, 'B', 60, 40, 1, 1, programmeAssociated2));
        success &= courseList.add(new Course("BAIT1173", "IT FUNDAMENTALS", 3, 'C', 80, 20, 6, 8, programmeAssociated1));
        success &= courseList.add(new Course("BACS3074", "ARTIFICIAL INTELLIGENCE", 4, 'C', 70, 30, 3, 4, programmeAssociated2));
        success &= courseList.add(new Course("BACS1564", "PRINCIPLES OF ACCOUNTING", 3, 'C', 70, 30, 3, 4, programmeAssociated4));
        success &= courseList.add(new Course("BAIT1597", "ECONOMICS", 3, 'B', 80, 20, 6, 8, programmeAssociated3));
        success &= courseList.add(new Course("FPMA1205", "PRINCIPLES OF FINANCE", 3, 'B', 60, 40, 1, 1, programmeAssociated4));
        success &= courseList.add(new Course("FPIT1554", "BUSINESS COMMUNICATION", 2, 'C', 50, 50, 4, 5, programmeAssociated3));
        success &= courseList.add(new Course("FPCS2045", "COMMERCIAL LAW", 4, 'B', 60, 40, 4, 5, programmeAssociated3));
        success &= courseList.add(new Course("BAMS1456", "BUSINESS RESEARCH", 3, 'C', 70, 30, 4, 5, programmeAssociated4));

        return success;
    }

    public DoublyCircularLinkedList<Student> initializeStudent(ListInterface<Programme> programmeList, ListInterface<Course> courseList) {
        DoublyCircularLinkedList<Student> studentList = new DoublyCircularLinkedList<>();
        Programme programme1 = programmeList.getPosition(0);
        Programme programme2 = programmeList.getPosition(1);
        Programme programme3 = programmeList.getPosition(8);

        studentList.add(new Student("22WMR00001", "Jay Chou", "030303020001", "M", "01234567890", programme1, "Jay@gmail.com"));
        studentList.add(new Student("22WMR00002", "Eason Chan", "040404020001", "M", "01123456789", programme1, "Eason@gmail.com"));
        studentList.add(new Student("22WMR00003", "Jackson Wang", "050505020001", "M", "01234567891", programme1, "Jackson@gmail.com"));
        studentList.add(new Student("22WMR00004", "JJ Lim", "060606020003", "M", "01123456780", programme1, "Lim@gmail.com"));
        studentList.add(new Student("23WMR00005", "Leehom Wang", "070707020003", "M", "01234567592", programme1, "WangLH@gmail.com"));
        studentList.add(new Student("23WMR00006", "Kris Wu Yi fan", "030301234003", "M", "01233454890", programme1, "Kris@gmail.com"));
        studentList.add(new Student("23WMR00007", "Jacky Cheung Hok Yau", "040404540001", "M", "01123452349", programme1, "Cheung@gmail.com"));
        studentList.add(new Student("23WMR00008", "Hebe Tien Fu Chen", "050534578002", "F", "01234567891", programme1, "Hebe@gmail.com"));
        studentList.add(new Student("23WMR00009", "David Tao Zhe", "060456420003", "M", "01121114780", programme1, "David@gmail.com"));
        studentList.add(new Student("23WMR00010", "Joker Xue", "070713234003", "M", "01239999892", programme1, "Joker@gmail.com"));
        studentList.add(new Student("23WMR00011", "Zhou Shen", "030123450007", "M", "01238886890", programme1, "Zhou@gmail.com"));
        studentList.add(new Student("23WMR00012", "Eric Chou", "040423333407", "M", "01123343679", programme1, "Eric@gmail.com"));
        studentList.add(new Student("23WMR00013", "Mao bu yi", "050505567877", "M", "01238887891", programme1, "Mao@gmail.com"));
        studentList.add(new Student("24WMR00014", "Yan Ren zhong", "060607777457", "M", "01134089620", programme1, "Yan@gmail.com"));
        studentList.add(new Student("24WMR00015", "Hu Xia", "070707088887", "M", "01555887892", programme1, "HuXia@gmail.com"));
        studentList.add(new Student("24WMR00016", "Tank Lu Jian Zhong", "030301211117", "M", "01299994890", programme1, "Tank@gmail.com"));
        studentList.add(new Student("23WMR00017", "Khalil Fong", "040404540451", "M", "01123446749", programme1, "KFong@gmail.com"));
        studentList.add(new Student("23WMR00018", "Yu Jia Yun", "050534578267", "M", "01234342391", programme1, "YuJY@gmail.com"));
        studentList.add(new Student("23WMR00019", "Raymmond Lam", "060456420853", "M", "01188864780", programme1, "Raymmond@gmail.com"));
        studentList.add(new Student("23WMR00020", "Shan Yi Chun", "070713234972", "F", "01239990042", programme1, "ShaYCn@gmail.com"));
        studentList.add(new Student("22WMR00021", "Gem Tang Sze Wing", "030303021238", "F", "01238833890", programme2, "Gem@gmail.com"));
        studentList.add(new Student("22WMR00022", "Han Hong", "040404023336", "F", "01123482389", programme2, "HanH@gmail.com"));
        studentList.add(new Student("22WMR00023", "Jolin Tsai", "051211020002", "F", "01298737891", programme2, "Jolin@gmail.com"));
        studentList.add(new Student("22WMR00024", "Na Ying", "060923022236", "F", "01123238580", programme2, "NaYing@gmail.com"));
        studentList.add(new Student("23WMR00025", "Yeung Qian Hua", "070216028934", "F", "01238395792", programme2, "YCHua@gmail.com"));
        studentList.add(new Student("23WMR00026", "Li Jian", "030306234347", "M", "01233993390", programme2, "LiJian@gmail.com"));
        studentList.add(new Student("23WMR00027", "Lay Zhang Yi Xing", "040804540127", "M", "01123222349", programme2, "Lay@gmail.com"));
        studentList.add(new Student("23WMR00028", "Andy Lau Tak Wah", "080534588802", "F", "01234522281", programme2, "Andy@gmail.com"));
        studentList.add(new Student("23WMR00029", "Henry Lau", "080456480099", "M", "01128886780", programme2, "Henry@gmail.com"));
        studentList.add(new Student("23WMR00030", "Zhang Bi Chen", "070823236674", "F", "01239226892", programme2, "ZhangBC@gmail.com"));
        studentList.add(new Student("22WBR00031", "Lebron James", "030302222238", "M", "01238845590", programme3, "Lebron@gmail.com"));
        studentList.add(new Student("22WBR00032", "Michael Jordan", "040404066336", "M", "01123358389", programme3, "Jordan@gmail.com"));
        studentList.add(new Student("22WBR00033", "Stephen Curry", "051211787802", "M", "012987897771", programme3, "Curry@gmail.com"));
        studentList.add(new Student("22WBR00034", "Kevin Durant", "060923866536", "M", "01123238580", programme3, "Durant@gmail.com"));
        studentList.add(new Student("23WBR00035", "Nikola Jokic", "070216883334", "M", "0123839992", programme3, "Jokic@gmail.com"));
        studentList.add(new Student("23WBR00036", "Giannis Antetokunmpo", "030304568847", "M", "01234483390", programme3, "Giannis@gmail.com"));
        studentList.add(new Student("23WBR00037", "James Harden", "040804577527", "M", "01123924349", programme3, "Harden@gmail.com"));
        studentList.add(new Student("23WBR00038", "Russel Westbrook", "080534589912", "M", "01234522281", programme3, "Westbrook@gmail.com"));
        studentList.add(new Student("23WBR00039", "Paul George", "031216433399", "M", "01128899680", programme3, "George@gmail.com"));
        studentList.add(new Student("23WBR00040", "Kawhi Leonard", "070855237774", "M", "01239222492", programme3, "Leonard@gmail.com"));
        studentList.add(new Student("22WBR00041", "Kyrie Irving", "030304441238", "M", "01238237890", programme3, "Irving@gmail.com"));
        studentList.add(new Student("22WBR00042", "Luka Doncic", "040405673336", "M", "01123480044", programme3, "Doncic@gmail.com"));
        studentList.add(new Student("22WBR00043", "Anthony Davis", "051210920002", "M", "01298567891", programme3, "Davis@gmail.com"));
        studentList.add(new Student("22WBR00044", "Devin Booker", "060924588236", "M", "01123283580", programme3, "Booker@gmail.com"));
        studentList.add(new Student("23WBR00045", "Jayson Tatum", "070216033584", "M", "01238394892", programme3, "Tatum@gmail.com"));
        studentList.add(new Student("23WBR00046", "Jimmy Bulter", "030306224747", "M", "01233958190", programme3, "Bulter@gmail.com"));
        studentList.add(new Student("23WBR00047", "Anthony Edwards", "040805577327", "M", "01126837349", programme3, "Edwards@gmail.com"));
        studentList.add(new Student("23WBR00048", "Draymond Green", "0805343673702", "M", "01234448081", programme3, "Greenn@gmail.com"));
        studentList.add(new Student("23WBR00049", "Tyrese Haliburton", "080457270099", "M", "01144812280", programme3, "Haliburton@gmail.com"));
        studentList.add(new Student("23WBR00050", "Zion Williamson", "070873214674", "M", "01239992792", programme3, "Zion@gmail.com"));

        Course course = courseList.getPosition(1);
        
        Student.CourseStatus courseStatusToAdd = studentList.getPosition(0).new CourseStatus(course, "Repeat");
        for(int i= 0;i<10;i++){
            studentList.getPosition(i).getStudentCourseList().add(courseStatusToAdd);
            int ch = studentList.getPosition(i).getTotalCreditHour();
            ch += course.getCreditHours();
            studentList.getPosition(i).setTotalCreditHour(ch);
        }
        Student.CourseStatus courseStatusToAdd2 = studentList.getPosition(0).new CourseStatus(course, "Resit");
        for(int i= 11;i<20;i++){
            studentList.getPosition(i).getStudentCourseList().add(courseStatusToAdd2);
        }
        
        for (int i = 0; i < studentList.getSize(); i++) {
            enrollStudentForCourse(studentList.getPosition(i), courseList);
        }

        return studentList;
    }

    public ListInterface<Course> findCoursesByProgramme(Programme studentProgramme, ListInterface<Course> courseList) {
        ListInterface<Course> registeredCourses = new DoublyCircularLinkedList<>();

        for (int i = 0; i < courseList.getSize(); i++) {
            Course course = courseList.getPosition(i);

            for (int j = 0; j < course.getProgrammesAssociated().getSize(); j++) {
                Programme programme = course.getProgrammesAssociated().getPosition(j);
                if (programme.equals(studentProgramme)) {
                    registeredCourses.add(course);
                    break;
                }
            }
        }

        return registeredCourses;
    }

    public void enrollStudentForCourse(Student student, ListInterface<Course> courseList) {
        ListInterface<Course> courses = findCoursesByProgramme(student.getStudentProgramme(), courseList);
        for (int i = 0; i < courses.getSize(); i++) {
            Course course = courses.getPosition(i);
            int totalCreditHour = student.getTotalCreditHour();
            totalCreditHour += course.getCreditHours();
            student.getStudentCourseList().add(student.new CourseStatus(course, "Main"));
            student.setTotalCreditHour(totalCreditHour);
        }
        
    }

    public DoublyCircularLinkedList<Tutor> initializeTutor() {
        DoublyCircularLinkedList<Tutor> tutorList = new DoublyCircularLinkedList<>();
        tutorList.add(new Tutor("CHAI XU KUN", "901045124835", "Male", "Full Time", "0147852369", "FOCS"));
        tutorList.add(new Tutor("NIU MO WONG", "840515101579", "Male", "Part Time", "0123698745", "FOCS"));
        tutorList.add(new Tutor("QIN SHI HUANG", "951214011253", "Male", "Part Time", "0125489635", "FOCS"));
        tutorList.add(new Tutor("PAN JIN LIAN", "941002145648", "Female", "Full Time", "0123654789", "FAFB"));
        tutorList.add(new Tutor("CHANG TIAO RAP", "910228133528", "Female", "Full Time", "0145236987", "FAFB"));
        tutorList.add(new Tutor("HENG ONG HUAT", "840512120545", "Male", "Full Time", "0135236987", "FAFB"));
        tutorList.add(new Tutor("MONEY COME", "820715548562", "Female", "Full Time", "0155236987", "FOCS"));
        tutorList.add(new Tutor("MICKY MOUSE", "970625485126", "Female", "Full Time", "0175236987", "FOCS"));
        tutorList.add(new Tutor("HELLO KITTY", "830228154203", "Male", "Part Time", "0185236987", "FAFB"));
        tutorList.add(new Tutor("OLD SIX", "820317201457", "Male", "Part Time", "0195236987", "FAFB"));

        return tutorList;
    }

    public DoublyCircularLinkedList<CourseAssignmentTeams> initializeTeams(ListInterface<Course> courseList, ListInterface<TutorialGroup> tutorialGroupList) {
        DoublyCircularLinkedList<CourseAssignmentTeams> teamsList = new DoublyCircularLinkedList<>();
        Course course1 = courseList.getPosition(0);
        TutorialGroup tg1 = tutorialGroupList.getPosition(0);
        CourseAssignmentTeams ca1 = new CourseAssignmentTeams(course1, tg1);
        teamsList.add(ca1);
        return teamsList;
    }

    public DoublyCircularLinkedList<CourseAssignmentTeams> initializeAssignmentTeams(ListInterface<CourseAssignmentTeams> courseAssignmentTeamsList, ListInterface<TutorialGroup> tutorialGroupList) {
        DoublyCircularLinkedList<AssignmentTeam> assignmentTeamsList = new DoublyCircularLinkedList<>();
        DoublyCircularLinkedList<CourseAssignmentTeams> teamsList = new DoublyCircularLinkedList<>();
        DoublyCircularLinkedList<Student> studentList = new DoublyCircularLinkedList<>();
        DoublyCircularLinkedList<Student> teamMember1 = new DoublyCircularLinkedList<>();
        DoublyCircularLinkedList<Student> teamMember2 = new DoublyCircularLinkedList<>();
        DoublyCircularLinkedList<Student> teamMember3 = new DoublyCircularLinkedList<>();
        DoublyCircularLinkedList<Student> teamMember4 = new DoublyCircularLinkedList<>();
        DoublyCircularLinkedList<Student> teamMember5 = new DoublyCircularLinkedList<>();
        DoublyCircularLinkedList<Student> teamMember6 = new DoublyCircularLinkedList<>();
        DoublyCircularLinkedList<Student> teamMember7 = new DoublyCircularLinkedList<>();

        CourseAssignmentTeams ca1 = courseAssignmentTeamsList.getPosition(0);
        TutorialGroup tg1 = tutorialGroupList.getPosition(0);
        for (int i = 0; i < tg1.getStudents().getSize(); i++) {
            studentList.add(tg1.getStudents().getPosition(i));
        }
        teamMember1.add(studentList.getPosition(0));
        teamMember1.add(studentList.getPosition(1));
        teamMember2.add(studentList.getPosition(2));
        teamMember2.add(studentList.getPosition(3));
        teamMember2.add(studentList.getPosition(4));
        teamMember3.add(studentList.getPosition(5));
        teamMember3.add(studentList.getPosition(6));
        teamMember3.add(studentList.getPosition(7));
        teamMember4.add(studentList.getPosition(8));
        teamMember4.add(studentList.getPosition(9));
        teamMember4.add(studentList.getPosition(10));
        teamMember5.add(studentList.getPosition(11));
        teamMember5.add(studentList.getPosition(12));
        teamMember5.add(studentList.getPosition(13));
        teamMember6.add(studentList.getPosition(14));
        teamMember6.add(studentList.getPosition(15));
        teamMember6.add(studentList.getPosition(16));
        teamMember7.add(studentList.getPosition(17));
        teamMember7.add(studentList.getPosition(18));
        teamMember7.add(studentList.getPosition(19));
        AssignmentTeam at1 = new AssignmentTeam("RSD-2024-1-1", "Canteen Delights", 2, 2, teamMember1, "69");
        AssignmentTeam at2 = new AssignmentTeam("RSD-2024-1-2", "Supreme Market", 3, 3, teamMember2, "50");
        AssignmentTeam at3 = new AssignmentTeam("RSD-2024-1-3", "Library Management", 3, 3, teamMember3, "55");
        AssignmentTeam at4 = new AssignmentTeam("RSD-2024-1-4", "T&A Automobile", 3, 3, teamMember4, "98");
        AssignmentTeam at5 = new AssignmentTeam("RSD-2024-1-5", "The Simpsons Shop", 3, 3, teamMember5, "33");
        AssignmentTeam at6 = new AssignmentTeam("RSD-2024-1-6", "Black Digger Ace", 3, 3, teamMember6, "77");
        AssignmentTeam at7 = new AssignmentTeam("RSD-2024-1-7", "Meow Meow Drink", 3, 3, teamMember7, "84");
        assignmentTeamsList.add(at1);
        assignmentTeamsList.add(at2);
        assignmentTeamsList.add(at3);
        assignmentTeamsList.add(at4);
        assignmentTeamsList.add(at5);
        assignmentTeamsList.add(at6);
        assignmentTeamsList.add(at7);
        ca1.setAssignmentTeams(assignmentTeamsList);
        teamsList.add(ca1);
        return teamsList;
    }

    public boolean initializeTutorialGroup(ListInterface<TutorialGroup> tutorialGroupList, ListInterface<Programme> programmeList, ListInterface<Student> studentList) {
        if (tutorialGroupList == null) {
            return false; // Return false if the list is null
        }

        boolean success = true;
        if (success) {
            for (Programme p : programmeList) {
                for (Student s : studentList) {
                    if (s.getStudentProgramme().equals(p)) {
                        s.setIsInTutorialGroup(true);
                        if (p.getTutorialGroups().getPosition(1) == null) {
                            TutorialGroup tg = new TutorialGroup(p.getProgrammeCode(), p.getProgrammeIntake().substring(p.getProgrammeIntake().length()-4),p.getTutorialGroups().getSize());
                            p.getTutorialGroups().add(tg);
                            tutorialGroupList.add(tg);
                        }
                        p.getTutorialGroups().getPosition(1).getStudents().add(s);
                    }
                }

            }

        }
            return success;
    }
}
