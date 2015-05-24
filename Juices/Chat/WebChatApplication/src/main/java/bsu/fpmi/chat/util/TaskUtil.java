package bsu.fpmi.chat.util;

import bsu.fpmi.chat.model.Mess;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public final class TaskUtil {
    public static final String TOKEN = "token";
    public static final String MESSAGES = "messages";
    private static final String TN = "TN";
    private static final String EN = "EN";
    private static final String ID = "id";
    private static final String MESSAGE = "message";
    private static final String USER = "user";

    private TaskUtil() {
    }

    public static String getToken(int index) {
        Integer number = index * 8 + 11;
        return TN + number + EN;
    }

    public static int getIndex(String token) {
        return (Integer.valueOf(token.substring(2, token.length() - 2)) - 11) / 8;
    }

    public static JSONObject stringToJson(String data) throws ParseException {
        JSONParser parser = new JSONParser();
        return (JSONObject) parser.parse(data.trim());
    }

    public static Mess jsonToTask(JSONObject json) {
        Object id = json.get(ID);
        Object message = json.get(MESSAGE);
        Object user = json.get(USER);

        if (id != null && message != null && user != null) {
            return new Mess((String) message, (String) user, (String) id);
        }
        return null;
    }
}