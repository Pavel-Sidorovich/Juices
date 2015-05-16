import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.List;

public class MessageExchange {

    private JSONParser jsonParser = new JSONParser();

    public String getToken(int index) {
        Integer number = index * 8 + 11;
        return "TN" + number + "EN";
    }

    public int getIndex(String token) {
        return (Integer.valueOf(token.substring(2, token.length() - 2)) - 11) / 8;
    }

    public String getServerResponse(List<Mess> posts, int fromIndex) {
        List<Mess> messages = posts.subList(fromIndex, posts.size());
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("messages", messages);
        jsonObject.put("token", getToken(posts.size()));
        return jsonObject.toJSONString();
    }

    public Mess getClientPost(InputStream inputStream) throws ParseException {
        JSONObject json = getJSONObject(inputStreamToString(inputStream));

        return new Mess((String)json.get("message"), (String)json.get("user"), (String)json.get("id"));
    }
    
    public String getClientId(InputStream inputStream) throws ParseException {
	JSONObject jsonObject = getJSONObject(inputStreamToString(inputStream));
	return (String) jsonObject.get("id");
    }

    public JSONObject getJSONObject(String json) throws ParseException {
        return (JSONObject) jsonParser.parse(json.trim());
    }

       public String getErrorMessage(String text) {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("error", text);

        return jsonObject.toJSONString();
    }
       
    public String inputStreamToString(InputStream in) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length = 0;
        try {
            while ((length = in.read(buffer)) != -1) {
                baos.write(buffer, 0, length);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new String(baos.toByteArray());
    }
}
