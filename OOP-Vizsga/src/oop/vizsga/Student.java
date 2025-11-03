package oop.vizsga;

import java.util.List;

public class Student extends BaseEntity implements Identifiable 
{
    private String name;
    private int year;
    private String group;
    private List<Integer> grades;
    
    public Student(String id, String name, int year, String group, List<Integer> grades) 
    {
        super(id);
        this.name = name;
        this.year = year;
        this.group = group;
        this.grades = grades;
    }

    public String getName() 
    { 
        return name; 
    }
    
    public List<Integer> getGrades() 
    {
        return grades;
    }
    
    public double averageGrade() 
    {
        if (grades == null || grades.isEmpty()) 
        {
            return 0.0;
        }

        int sum = 0;
        for (int i = 0; i < grades.size(); i++) 
        {
            sum += grades.get(i);
        }

        double average = (double) sum / grades.size();
        return average;
    }

    @Override
    public String businessKey() 
    {
        return "STUD-" + id;
    }

    @Override
    public String toString() 
    {
        return name + " (" + group + ", avg=" + String.format("%.2f", averageGrade()) + ")";
    }
}
