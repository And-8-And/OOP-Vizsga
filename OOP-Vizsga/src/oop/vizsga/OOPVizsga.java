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
