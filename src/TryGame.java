import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;


public class TryGame {
	public static final String TITLE = "Home Sweet Home";
	public static final int KEY_INPUT_SPEED = 50;    //fix later
	public static final double GROWTH_RATE = 1.1;
	private static final int BERRY_SPEED = 60;
	private static final int BERRY_SIZE = 20;
	
	private Scene myScene;
	private ImageView mimi;
	private ArrayList<ImageView> strawberries = new ArrayList<ImageView>();
	private int[] directions = {1,1,1};
	private ImageView strawberry1;
	private ImageView strawberry2;
	private ImageView strawberry3;
	public boolean endOfGame = false;
	private Group root;
	
	public String getTitle(){
		return TITLE;
	}
	
	public ImageView setPosition(ImageView temp, int width, int height, double d, double e){
		temp.setFitWidth(width);
		temp.setFitHeight(height);
		temp.setX(d);
		temp.setY(e);
		return temp;
	}
	
	public int[] generateRandom(int elems, int min, int max){
		int[] temp = new int[elems];
		for(int i=0;i<elems;i++){
			temp[i] = ThreadLocalRandom.current().nextInt(min, max + 1);
		}
		return temp;
	}
	
	public int keepInBounds(Scene scene, ImageView temp, int direction){
		Scene tempScene = scene;
		if(temp.getX() > tempScene.getWidth() - BERRY_SIZE || temp.getX() < 0){
			direction *= -1;
		}
		if(temp.getY() > tempScene.getHeight() - BERRY_SIZE || temp.getY() < 0){
			direction *= -1;
		}
		return direction;
	}
	
	public void eatBerries(ImageView character, ImageView object){
		if(character.getBoundsInParent().intersects(object.getBoundsInParent())){
			Image temp = null;
			object.setImage(temp);
			System.gc();
			return;
		}
	}
	
	public void moveBerries(ImageView object, int direction, int xy, double elapsedTime){
		if(xy%2 == 0){
			object.setX(object.getX() + direction * BERRY_SPEED * elapsedTime);
		}
		else{
			object.setY(object.getY() + direction * BERRY_SPEED * elapsedTime);
		}
		
	}
	
//	public Scene init(FlowPane pane, int width, int height){
	public Scene init(int width, int height){
		root = new Group();
		myScene = new Scene(root, width, height, Color.BEIGE);
		Image dog = new Image(getClass().getClassLoader().getResourceAsStream("dog.gif"));
		Image berry1 = new Image(getClass().getClassLoader().getResourceAsStream("strawberry.png"));
		Image berry2 = new Image(getClass().getClassLoader().getResourceAsStream("strawberry.png"));
		Image berry3 = new Image(getClass().getClassLoader().getResourceAsStream("strawberry.png"));
		mimi = new ImageView(dog);
		
		strawberry1 = new ImageView(berry1); strawberries.add(strawberry1);
		strawberry2 = new ImageView(berry2); strawberries.add(strawberry2);
		strawberry3 = new ImageView(berry3); strawberries.add(strawberry3);

		int[] positions = generateRandom(8, 0, 380);
		
		setPosition(mimi, 50, 50, positions[0], positions[1]);
		int positionCount = 0;
		while(positionCount < strawberries.size()){
			ImageView berryRightNow = strawberries.get(positionCount);
			setPosition(berryRightNow, BERRY_SIZE, BERRY_SIZE, 
					positions[positionCount*2 + 2], positions[positionCount*2 + 3]);
			root.getChildren().add(berryRightNow);
			positionCount++;
		}
		root.getChildren().add(mimi);
		
		myScene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
		myScene.setOnMouseClicked(e -> handleMouseInput(e.getX(), e.getY()));
		
		return myScene;
	}
	
	public void step(double elapsedTime){
	
		int positionCount = 0;
		while(positionCount < strawberries.size()){
			ImageView berryRightNow = strawberries.get(positionCount);
			moveBerries(berryRightNow, directions[positionCount], positionCount, elapsedTime);
			directions[positionCount] = keepInBounds(myScene, berryRightNow, 
					directions[positionCount]);
			eatBerries(mimi, berryRightNow);
			positionCount++;
		}
		
		if(strawberry1.getImage() == null && strawberry2.getImage() == null
				&& strawberry3.getImage() == null){
			
			endOfGame = true;	
			
			Text t = new Text (10, 20, "Congratulations, Mimi is back home!");
			Text t2 = new Text (10, 50, "Click to Exit");
			
			t.setFont(Font.font ("Verdana", 20));
			t.setFill(Color.ROYALBLUE);
			
			t2.setFont(Font.font ("Verdana", 20));
			t2.setFill(Color.ROYALBLUE);
			
			root.getChildren().add(t);
			root.getChildren().add(t2);
			
		}
		
	}
	
    // What to do each time a key is pressed
    private void handleKeyInput (KeyCode code) {
        switch (code) {
            case RIGHT:
                mimi.setX(mimi.getX() + KEY_INPUT_SPEED);
                break;
            case LEFT:
                mimi.setX(mimi.getX() - KEY_INPUT_SPEED);
                break;
            case UP:
                mimi.setY(mimi.getY() - KEY_INPUT_SPEED);
                break;
            case DOWN:
                mimi.setY(mimi.getY() + KEY_INPUT_SPEED);
                break;
            default:
                // do nothing
        }
    }
    
    // What to do each time a mouse is pressed
    private void handleMouseInput (double x, double y) {
        if (strawberry2.contains(x, y)) {
        	//IMPLEMENT END-OF-GAME
        	endOfGame = true;	
            Platform.exit();
        }
    }
	
}
