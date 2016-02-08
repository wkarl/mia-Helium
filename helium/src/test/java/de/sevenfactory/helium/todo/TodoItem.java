package de.sevenfactory.helium.todo;

import com.google.gson.annotations.SerializedName;

public class TodoItem {
    private static final String CHECKED = "checked";

    @SerializedName("_id")
    private String mId;
    @SerializedName("name")
    private String mName;
    @SerializedName("status")
    private String mStatus;

    public String getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public boolean getStatus() {
        return mStatus.equals(CHECKED);
    }
}
