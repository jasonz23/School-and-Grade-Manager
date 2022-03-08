package persistence;

import model.Courses;
import org.json.JSONObject;

import java.io.*;
import java.io.FileNotFoundException;

// writes to JSON file
public class JsonWriter {
    private static final int TAB = 10;
    private String destination;
    private PrintWriter writer;

    //EFFECTS: constructs writer to write to destination file
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    //MODIFIES: this
    //EFFECTS: opens writer. if the destination file can not be open then throw
    // FileNotFoundException
    public void open() throws FileNotFoundException {
        writer = new PrintWriter((new File(destination)));
    }

    //MODIFIES: this
    //EFFECTS: writes JSON representation of courses to file
    public void write(Courses cs) {
        JSONObject json = cs.toJson();
        saveToFile(json.toString(TAB));
    }

    //MODIFIES: this
    //EFFECTS: closes writer
    public void close() {
        writer.close();
    }

    //MODIFIES: this
    //EFFECTS: writes string to file
    public void saveToFile(String json) {
        writer.print(json);
    }
}
