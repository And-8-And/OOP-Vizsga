package oop.vizsga;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonSyntaxException;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
    
public class OOPVizsga 
{
    public static void main(String[] args) 
    {
        String filePath = "C:/Users/And8And/Desktop/data.json";
        
        try (FileReader reader = new FileReader(filePath))
        {
            Gson gson = new Gson();
            JsonObject root = gson.fromJson(reader, JsonObject.class);
            
            String term = getSafeString(root, "term");
            System.out.println("Term: " + term);
            
            JsonObject dept = root.getAsJsonObject("department");
            String depCode = getSafeString(dept, "code");
            String depName = getSafeString(dept, "name");
            System.out.println("Department: " + depCode + " - " + depName);
            
            JsonArray courses = root.getAsJsonArray("courses");
            if (courses != null) 
            {
                System.out.println("\n--- Courses ---");
                for (int i = 0; i < courses.size(); i++) 
                {
                    JsonObject c = courses.get(i).getAsJsonObject();

                    String code = getSafeString(c, "code");
                    String title = getSafeString(c, "title");
                    int credits = getSafeInt(c, "credits");

                    System.out.println(code + " | " + title + " | " + credits + " credits");
                    
                    JsonObject instr = c.getAsJsonObject("instructor");
                    if (instr != null) 
                    {
                        System.out.println("  Instructor: " + getSafeString(instr, "name"));
                    }
                    
                    JsonElement studentsElem = c.get("students");
                    if (studentsElem != null && studentsElem.isJsonArray()) 
                    {
                        JsonArray sArr = studentsElem.getAsJsonArray();
                        for (int j = 0; j < sArr.size(); j++) 
                        {
                            JsonObject sObj = sArr.getAsJsonObject();
                            String sName = getSafeString(sObj, "name");
                            int sYear = getSafeInt(sObj, "year");
                            System.out.println("    Student: " + sName + " (" + sYear + ")");
                        }
                    }
                    else
                    {
                        System.out.println("No valid student list for this course");
                    }
                }
            }
        } 
        catch (FileNotFoundException e) 
        {
            System.err.println("File not found: " + e.getMessage());
        } 
        catch (IOException e) 
        {
            System.err.println("IO Error: " + e.getMessage());
        } 
        catch (JsonSyntaxException e) 
        {
            System.err.println("JSON is malformed: " + e.getMessage());
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
