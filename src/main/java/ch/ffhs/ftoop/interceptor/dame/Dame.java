package ch.ffhs.ftoop.interceptor.dame;

public class Dame {
	GUI gui;
	Backend backend;
	
	
	public Dame(String[] args) {
		gui = new GUI(backend);
		backend = new Backend(gui);
	}
	
	
	public static void main(String[] args) {
		new Dame(args);
	}
	
	
	

}
