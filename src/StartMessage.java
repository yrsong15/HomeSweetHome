import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class StartMessage {
	
	private Scene myScene;
	
	public Scene init(FlowPane pane, int width, int height){
//		Group root = new Group();
		myScene = new Scene(pane, width, height, Color.ROYALBLUE);
		
		Text t = new Text (10, 20, "Click to proceed");
		t.setFont(Font.font ("Verdana", 20));
		t.setFill(Color.WHITE);
		t.setX((double)width/2 + 100);
		t.setY(height/2);
		
		
		pane.getChildren().add(t);
		
//		myScene.setOnMouseClicked(e -> handleMouseInput(e.getX(), e.getY()));
		return myScene;
	}	
//    private void handleMouseInput (double x, double y) {
//        Platform.exit();
//    }
}
