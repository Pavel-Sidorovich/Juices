package bsu.fpmi.chat.model;

public class Mess{
    private String message;
    private String user;
    private String id;

public  Mess(String message,String user, String id){
    this.message = message;
    this.user = user;
    this.id = id;
}

public String getId() {
		return id;
	}

public void setId(String id) {
		this.id = id;
	}

public String getMessage() {
		return message;
	}

public void setMessage(String message) {
		this.message = message;
	}

public String getUser() {
		return user;
	}

public void setUser(String user) {
		this.user = user;
	}

public String toString() {
	return "{\"id\":\"" + this.id + "\",\"user\":\"" + this.user + "\",\"message\":\"" + this.message + "\"}";
}

public boolean equals(Mess mess) {
	if(!mess.id.equals (this.id))
		return false;
	if(!mess.user.equals (this.user))
		return false;
	if(!mess.message.equals (this.message))
		return false;
	return true;
}

};