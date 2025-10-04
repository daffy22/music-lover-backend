package com.app.musiclover.util;

import org.json.JSONObject;

public class CriteriaBuilder {
    public static String buildJsonCriteria(String moodName, String era, String instrument, Integer duration) {
        JSONObject json = new JSONObject();
        if (moodName != null && !moodName.isBlank()) {
            json.put("mood", moodName.toLowerCase());
        }
        if (era != null && !era.isBlank()) {
            json.put("era", era.toLowerCase());
        }
        if (instrument != null && !instrument.isBlank()) {
            json.put("instrument", instrument.toLowerCase());
        }
        if (duration != null) {
            json.put("duration", duration);
        }
        return json.toString();
    }
}
