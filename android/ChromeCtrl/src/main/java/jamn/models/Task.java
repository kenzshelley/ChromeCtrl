package jamn.models;

import android.util.Log;

public class Task {
    private String name;
    private String key;

    public void setName(String taskName) {
        name = taskName;
    }

    public String getName() {
        return name;
    }

    public void setKey(String taskKey) {
        Log.d("task", taskKey);
        key = taskKey;
    }

    public String getKey() {
        Log.d("task", key);
        return key;
    }

    public Task() {
    }
}
