package oop.vizsga;

import java.util.List;

public class Course extends BaseEntity implements Identifiable, Schedulable
{
    private String code;
    private String title;
    private int credits;
    private String type;
    private String day;
    private String start;
    private String end;
    private String room;
    private Instructor instructor;
    private List<Student> students;

    public Course(String code, String title, int credits, String type, String day, String start, String end, String room, Instructor instructor, List<Student> students, String id) 
    {
        super(id);
        this.code = code;
        this.title = title;
        this.credits = credits;
        this.type = type;
        this.day = day;
        this.start = start;
        this.end = end;
        this.room = room;
        this.instructor = instructor;
        this.students = students;
    }
    
    @Override
    public String businessKey() 
    {
        return "COURSE-" + code;
    }

    @Override
    public String getDay() 
    { 
        return day; 
    }
    
    @Override
    public String getStart() 
    { 
        return start; 
    }
    
    @Override
    public String getEnd()
    { 
        return end; 
    }

    public Instructor getInstructor() 
    { 
        return instructor; 
    }
    
    public List<Student> getStudents() 
    { 
        return students; 
    }

    @Override
    public String toString() 
    {
        String displayTitle;
        if (title == null) 
        {
            displayTitle = "Unknown";
        } 
        else 
        {
        displayTitle = title;
        }

        String displayInstructor;
        if (instructor != null) 
        {
            displayInstructor = instructor.getName();
        } 
        else 
        {
            displayInstructor = "Unknown";
        }

        String displayDay;
        if (day == null || day.isEmpty()) 
        {
            displayDay = "Unknown day";
        } 
        else 
        {
            displayDay = day;
        }

        String displayStart;
        if (start == null || start.isEmpty()) 
        {
            displayStart = "??:??";
        } 
        else 
        {
            displayStart = start;
        }

        String displayEnd;
        if (end == null || end.isEmpty())
        {
            displayEnd = "??:??";
        } 
        else 
        {
            displayEnd = end;
        }

        return code + " - " + displayTitle + " (" + credits + "cr) [" + type + "] " + "by " + displayInstructor + " on " + displayDay + " " + displayStart + "-" + displayEnd;
    }
}