package ch.ffhs.ftoop.interceptor.dame.beans;

public class MessageBox {
    private String title = "";
    private String header = "";
    private String message = "";
    private MsgType type;

    public MessageBox(MsgType type) {
		this.type = type;
	}

    public MessageBox(String title, String message, MsgType type) {
		this.title = title;
		this.type = type;
		this.message = message;
	}
	
	public void setHeader(String header) {
		this.header = header;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getMessage() {
		return message;
	}
	
	public String getHeader() {
		return header;
	}
	
	public MsgType getType() {
		return type;
	}
	
	public enum MsgType{
		OK,YESNO,VICTORY,LOSE
	}
}
