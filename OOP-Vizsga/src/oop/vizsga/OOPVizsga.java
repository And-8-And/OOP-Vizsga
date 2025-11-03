package oop.vizsga;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonSyntaxException;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.io.PrintWriter;
    
public class OOPVizsga 
{
    public static void main(String[] args) 
    {
        String filePath = "C:/Users/And8And/Desktop/data.json";
        List<Course> courses = new ArrayList<>();
        List<Student> allStudents = new ArrayList<>();
        List<Course> invalidCourses = new ArrayList<>();
        
        try (FileReader reader = new FileReader(filePath))
        {
            Gson gson = new Gson();
            JsonObject root = gson.fromJson(reader, JsonObject.class);
            
            JsonArray coursesArray = root.getAsJsonArray("courses");
            if (coursesArray != null) {
                for (int i = 0; i < coursesArray.size(); i++) {
                    JsonObject c = coursesArray.get(i).getAsJsonObject();

                    String code = getSafeString(c, "code");
                    String title = getSafeString(c, "title");
                    int credits = getSafeInt(c, "credits");
                    String type = getSafeString(c, "type");

                    // Schedule
                    JsonObject sched = c.getAsJsonObject("schedule");
                    String day = getSafeString(sched, "day");
                    String start = getSafeString(sched, "start");
                    String end = getSafeString(sched, "end");
                    String room = getSafeString(sched, "room");

                    // Instructor
                    JsonObject instrObj = c.getAsJsonObject("instructor");
                    String instrId = getSafeString(instrObj, "id");
                    String instrName = getSafeString(instrObj, "name");
                    Instructor instructor = new Instructor(instrId, instrName);

                    // Students
                    List<Student> students = new ArrayList<>();
                    JsonElement studentsElem = c.get("students");
                    if (studentsElem != null && studentsElem.isJsonArray()) 
                    {
                        JsonArray sArr = studentsElem.getAsJsonArray();
                        for (int j = 0; j < sArr.size(); j++) 
                        {
                            JsonObject sObj = sArr.get(j).getAsJsonObject();
                            String sId = getSafeString(sObj, "id");
                            String sName = getSafeString(sObj, "name");
                            int sYear = getSafeInt(sObj, "year");
                            String sGroup = getSafeString(sObj, "group");

                            // Grades
                            List<Integer> grades = new ArrayList<>();
                            JsonArray gArr = sObj.getAsJsonArray("grades");
                            if (gArr != null) 
                            {
                                for (int k = 0; k < gArr.size(); k++) 
                                {
                                    grades.add(gArr.get(k).getAsInt());
                                }
                            }

                            Student student = new Student(sId, sName, sYear, sGroup, grades);
                            students.add(student);
                            
                            if (!allStudents.contains(student)) 
                            {
                                allStudents.add(student);
                            }
                        }
                    }

                    Course course = new Course(code, title, credits, type, day, start, end, room, instructor, students, code);

                    if (credits <= 0 || day.equals("Unknown") || start.equals("??:??") || end.equals("??:??")) 
                    {
                        invalidCourses.add(course);
                    } else 
                    {
                        courses.add(course);
                    }
                }
            }

        } catch (FileNotFoundException e) 
        {
            System.err.println("File not found: " + e.getMessage());
        } 
        catch (IOException e) 
        {
            System.err.println("IO Error: " + e.getMessage());
        } 
        catch (JsonSyntaxException e) 
        {
            System.err.println("JSON malformed: " + e.getMessage());
        }  
        
        try (PrintWriter writer = new PrintWriter("C:/Users/And8And/Desktop/report.txt"))
        {
            writer.println("=== ALL COURSES ===");
            for (int i = 0; i < courses.size(); i++) 
            {
                Course c = courses.get(i);
                writer.println(c);

                for (int j = 0; j < c.getStudents().size(); j++) 
                {
                    Student s = c.getStudents().get(j);
                    double weighted = 0.0;

                    List<Integer> g = s.getGrades();
                    if (g != null && !g.isEmpty()) 
                    {
                        int sum = 0;
                        
                        for (int k = 0; k < g.size(); k++) 
                        {
                            sum += g.get(k);
                        }
                        weighted = (double) sum / g.size();
                    }

                    writer.println("  Student: " + s.getName() + " | Weighted grade: " + String.format("%.2f", weighted));
                }
            }
            
            writer.println("\n=== TOP 2 STUDENTS ===");
            allStudents.sort(new Comparator<Student>() 
            {
                public int compare(Student o1, Student o2) 
                {
                    return Double.compare(o2.averageGrade(), o1.averageGrade());
                }
            });

            for (int i = 0; i < Math.min(2, allStudents.size()); i++) 
            {
                Student s = allStudents.get(i);
                writer.println((i + 1) + ". " + s.getName() + " | Avg across courses: " + String.format("%.2f", s.averageGrade()));
            }
            
            if (!invalidCourses.isEmpty()) 
            {
                writer.println("\n=== INVALID COURSES ===");
                for (int i = 0; i < invalidCourses.size(); i++) 
                {
                    Course c = invalidCourses.get(i);
                    writer.println(c);
                }
            }
        }
        catch (FileNotFoundException e) 
        {
            System.err.println("Cannot create report.txt: " + e.getMessage());
        }
    }
    
    private static String getSafeString(JsonObject obj, String key) 
    {
        if (obj == null || !obj.has(key) || obj.get(key).isJsonNull()) 
        {
            return "Unknown";
        }
        try 
        {
            return obj.get(key).getAsString();
        } 
        catch (Exception e) 
        {
            return "Unknown";
        }
    }

    private static int getSafeInt(JsonObject obj, String key) {
        if (obj == null || !obj.has(key) || obj.get(key).isJsonNull()) 
        {
            return 0;
        }
        try 
        {
            return obj.get(key).getAsInt();
        } 
        catch (Exception e) 
        {
            return 0;
        }
    }
}
