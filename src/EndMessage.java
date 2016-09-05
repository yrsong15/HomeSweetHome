import javafx.scene.Scene;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class EndMessage {
	private Scene myScene;
	
	public Scene init(FlowPane pane, int width, int height){
		myScene = new Scene(pane, width, height, Color.WHITE);
		
		Text t = new Text (10, 20, "Congratulations, Mimi is back home!");
		t.setFont(Font.font ("Verdana", 20));
		t.setFill(Color.ROYALBLUE);
		
		pane.getChildren().add(t);
	
		return myScene;
	}
}
