import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class StartMessage {
	
	private Scene myScene;
	private Button button;
	public boolean clicked = false;
	
//	public Scene init(FlowPane pane, int width, int height){
	public Scene init(int width, int height){
		Group root = new Group();
		
//		myScene = new Scene(pane, width, height, Color.ROYALBLUE);
		myScene = new Scene(root, width, height, Color.ROYALBLUE);
		
//		Text t = new Text (10, 20, "Click button to proceed\n\n\n\n\n");
		Text t = new Text(10, 20, "Click anywhere to proceed to game");
		t.setFont(Font.font ("Verdana", 20));
		t.setFill(Color.WHITE);
		t.setX(width/2 - 175);
		t.setY(height/2);
		
		button = new Button("Right Here!");
		button.setOnAction(e-> ButtonClicked(e));
		
		root.getChildren().add(t);
		root.getChildren().add(button);
	
		
//		myScene.setOnMouseClicked(e -> handleMouseInput(e.getX(), e.getY()));
		return myScene;
	}	
    public void ButtonClicked(ActionEvent e)
    {
        if (e.getSource()==button)
            clicked = true;
        	
    }
//    private void handleMouseInput (double x, double y) {
//        Platform.exit();
//    }
	
}
