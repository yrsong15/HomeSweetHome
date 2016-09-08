import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Intro {
	
	public static final String TITLE = "Home Sweet Home";
	private boolean clicked;
	
	public Scene init(int width, int height){
		Group root = new Group();
		Scene scene = new Scene(root, width, height);
		
		Text t = new Text(10, 20, "Click anywhere to proceed to game");
		t.setFont(Font.font ("Verdana", 20));
		t.setFill(Color.ROYALBLUE);
		t.setX(width/2 - 175);
		t.setY(height/2);
		
		root.getChildren().add(t);
		
		scene.setOnMouseClicked(e -> handleMouseInput(e));
		return scene;
		
	}
	
	public void step(){
		if(clicked){
			System.out.println("aaa");
//			TheGame game = new TheGame();
//			game.init(400, 400);
		}
	}
	
    public void handleMouseInput(MouseEvent e)
    {
    	clicked = true;
    }
    
    
    //probably unnecessary
    public boolean isClicked(){
    	return clicked;
    }
    
	public String getTitle(){
		return TITLE;
	}
	

}
