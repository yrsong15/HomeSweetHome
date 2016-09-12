import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class SplashPage {
	
	public Text message(int width, int height, String text){
		Text t = new Text(width/6, height/2, text);
		t.setFont(Font.font ("Verdana", 20));
		t.setFill(Color.ROYALBLUE);
		return t;
	}
	
	public Text message(int width, int height, String text, Color c){
		Text t = new Text(width/6, height/2, text);
		t.setFont(Font.font ("Verdana", 20));
		t.setFill(c);
		return t;
	}
	
	public Text formattedMessage(int width, int height, String text){
		Text t = new Text(width, height, text);
		t.setFont(Font.font ("Verdana", 20));
		t.setFill(Color.ROYALBLUE);
		return t;
	}
	
	public Text formattedMessage(int width, int height, int size, String text){
		Text t = new Text(width, height, text);
		t.setFont(Font.font ("Verdana", size));
		t.setFill(Color.ROYALBLUE);
		return t;
	}
	
	public Text formattedMessage(int width, int height, String text, Color c){
		Text t = new Text(width, height, text);
		t.setFont(Font.font ("Verdana", 20));
		t.setFill(c);
		return t;
	}
}
