import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.lang.reflect.Type;

import com.google.gson.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonParseException;

public class UserDatabaseManager extends JsonDatabaseManager<User>{
    private final Gson customGson;

    public UserDatabaseManager(String filename) {
        super(filename);

        customGson = new GsonBuilder()
            .registerTypeAdapter(User.class, new UserDeserializer()).create();

        readFromFile();
    }

    @Override
    public void readFromFile() {
        records.clear();
        File file = new File(filename);
        if (!file.exists() || file.length() == 0) {
            return;
        }


        if (customGson == null) {
            return;
        }

        try (FileReader reader = new FileReader(filename)) {
            Type listType = new TypeToken<ArrayList<User>>(){}.getType();
            ArrayList<User> list = customGson.fromJson(reader, listType);
            if (list != null) {
                records = list;
            }
        } catch (IOException e) {
            System.err.println("Error file not found: "+e.getMessage());
            e.printStackTrace();
            throw new RuntimeException();
        }
    }


    private static class UserDeserializer implements JsonDeserializer<User> {
        @Override
        public User deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException {
            JsonObject jsonObject = json.getAsJsonObject();
            String role = jsonObject.get("role").getAsString();

            // Deserialize into the appropriate subclass based on role
            switch (role.toLowerCase()) {
                case "admin":
                    return context.deserialize(json, Admin.class);
                case "student":
                    return context.deserialize(json, Student.class);
                case "instructor":
                    return context.deserialize(json, Instructor.class);
                default:
                    throw new JsonParseException("Unknown user role: " + role);
            }
        }
    }

    @Override
    public void saveToFile(){
        try(java.io.FileWriter writer = new java.io.FileWriter(filename)){
            writer.write(customGson.toJson(records));
        }catch (IOException e){
            System.err.println("Error writing to file:" + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public User getRecordByEmail(String email) {
        for(User record: records){
            if (record.getEmail().equals(email)){
                return record;
            }
        }
        return null;
    }

    public User getRecordByUsername(String name) {
        for(User record: records){
            if (record.getUsername().equals(name)){
                return record;
            }
        }
        return null;
    }

    public ArrayList<Student> getAllStudents(){
        ArrayList<Student> students = new ArrayList<>();
        for(User record : records){
            if (record instanceof Student){
                students.add((Student) record);
            }
        }
        return students;
    }
}
