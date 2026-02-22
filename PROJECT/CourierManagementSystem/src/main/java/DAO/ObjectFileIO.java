package DAO;

import java.io.*;
import java.util.ArrayList;

public class ObjectFileIO <T extends Serializable> {
    private final String filePath;

    public ObjectFileIO(String filePath) {
        this.filePath = filePath;
    }
    
    // DeSerializer
    public ArrayList<T> load() {
     
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            // We read the single object (the ArrayList) that was written
            return (ArrayList<T>) ois.readObject();
        
        } catch (FileNotFoundException e) {
            System.out.println("ERROR: File not found at "+filePath);
            return new ArrayList<>();
        
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("ERROR: Failed to load data from " + filePath);
            return new ArrayList<>(); 
        }
    }

    // Serializer
    public void save(ArrayList<T> data) {
        
        File file = new File(filePath);
        file.getParentFile().mkdirs();  // Creating file-parent-directory if not exist
        
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(data);
            System.out.println("Data saved successfully to: " + filePath);
        } catch (IOException e) {
            System.err.println("ERROR: Failed to save data to " + filePath);
        }
    }
    
}
