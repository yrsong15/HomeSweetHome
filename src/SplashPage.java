import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class SplashPage {
	
	public Text message(int width, int height, String text){
		Text t = new Text(width/2 - 175, height/2, text);
		t.setFont(Font.font ("Verdana", 20));
		t.setFill(Color.ROYALBLUE);
		return t;
	}
}
