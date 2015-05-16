public class Mess{
        String message;
        String user;
        String id;

public Mess(String message, String user, String id) {
	this.message = message;
	this.user = user;
	this.id = id;
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