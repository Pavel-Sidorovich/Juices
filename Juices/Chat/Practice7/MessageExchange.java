import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.List;

public class MessageExchange {

    public class Post{
        String message;
        String user;
        String id;
    };
    private JSONParser jsonParser = new JSONParser();

    public String getToken(int index) {
        Integer number = index * 8 + 11;
        return "TN" + number + "EN";
    }

    public int getIndex(String token) {
        return (Integer.valueOf(token.substring(2, token.length() - 2)) - 11) / 8;
    }

    public String getServerResponse(List<String> messages) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("messages", messages);
        jsonObject.put("token", getToken(messages.size()));
        return jsonObject.toJSONString();
    }

    public String getClientSendMessageRequest(String message) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("message", message);
        return jsonObject.toJSONString();
    }

    public Post getClientPost(InputStream inputStream) throws ParseException {
        Post post = new Post();
        JSONObject jsonObject = getJSONObject(inputStreamToString(inputStream));
        post.message = (String) jsonObject.get("message");
        post.id = (String) jsonObject.get("id");
        if(post.id == null){
            String id = "";
            int n = (int)(Math.random() * 5 + 1);
            String dict = "qwertyuioplkjhgfdsazxcvbnm";
            for(int i = 0; i < n; i ++)
                id = id.concat(String.valueOf(dict.charAt((int) (Math.random() * 10000) %dict.length())));
            post.id = id;
        }  
        post.user = (String) jsonObject.get("user");
        if(post.user == null){
            String user = "";
            int n = (int)(Math.random() * 5 + 1);
            String dict = "qwertyuioplkjhgfdsazxcvbnm";
            for(int i = 0; i < n; i ++)
                user = user.concat(String.valueOf(dict.charAt((int) (Math.random() * 10000) %dict.length())));
            post.user = user;
        }  
        return post;
    }
    
    public String getClientId(InputStream inputStream) throws ParseException {
	JSONObject jsonObject = getJSONObject(inputStreamToString(inputStream));
	return (String) jsonObject.get("id");
    }

    public JSONObject getJSONObject(String json) throws ParseException {
        return (JSONObject) jsonParser.parse(json.trim());
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
