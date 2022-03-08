package persistence;

import org.json.JSONObject;

//returns JSON
public interface Storage {

    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
