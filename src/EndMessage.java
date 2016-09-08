import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class EndMessage {
	private Scene myScene;
	private Button button;
	public boolean clicked = false;
	
	public Scene init(int width, int height){
		
		Group root = new Group();
		
		myScene = new Scene(root, width, height, Color.ROYALBLUE);
		
		Text t = new Text (10, 20, "Now Let's Move to Level Two!!");
		
		t.setFont(Font.font ("Verdana", 20));
		t.setFill(Color.WHITE);
		
		button = new Button("Right Here!");
		button.setOnAction(e-> ButtonClicked(e));
		
		root.getChildren().add(t);
		root.getChildren().add(button);
		return myScene;
	}
	
    public void ButtonClicked(ActionEvent e)
    {
    	System.out.println(clicked);
        clicked = true;
    }
}
