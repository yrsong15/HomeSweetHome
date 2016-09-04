import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

import java.util.EventObject;
import java.util.concurrent.ThreadLocalRandom;


public class TryGame {
	public static final String TITLE = "Home Sweet Home";
	public static final int KEY_INPUT_SPEED = 10;
	public static final double GROWTH_RATE = 1.1;
	private static final int BERRY_SPEED = 60;
	private static final int BERRY_SIZE = 20;
	
	private Scene myScene;
	private ImageView mimi;
	private ImageView strawberry1;
	private ImageView strawberry2;
	private ImageView strawberry3;
	private int direction1 = 1;
	private int direction2 = 1;
	private int direction3 = 1;
	private int berriesEaten = 0;
	
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
			berriesEaten += 1;
			return;
		}
	}
	
	public Scene init(int width, int height){
		Group root = new Group();
		myScene = new Scene(root, width, height, Color.BEIGE);
		Image dog = new Image(getClass().getClassLoader().getResourceAsStream("dog.png"));
		Image berry1 = new Image(getClass().getClassLoader().getResourceAsStream("strawberry.jpg"));
		Image berry2 = new Image(getClass().getClassLoader().getResourceAsStream("strawberry.jpg"));
		Image berry3 = new Image(getClass().getClassLoader().getResourceAsStream("strawberry.jpg"));
		mimi = new ImageView(dog);
		strawberry1 = new ImageView(berry1);
		strawberry2 = new ImageView(berry2);
		strawberry3 = new ImageView(berry3);
		
		int[] positions = generateRandom(8, 0, 380);
		
		setPosition(mimi, 50, 50, positions[0],
				positions[1]);

		setPosition(strawberry1, BERRY_SIZE, BERRY_SIZE, positions[6], positions[7]);
		setPosition(strawberry2, BERRY_SIZE, BERRY_SIZE, positions[2], positions[3]);
		setPosition(strawberry3, BERRY_SIZE, BERRY_SIZE, positions[4], positions[5]);
		
		root.getChildren().add(strawberry1);
		root.getChildren().add(strawberry2);
		root.getChildren().add(strawberry3);
		root.getChildren().add(mimi);
		
		myScene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
		myScene.setOnMouseClicked(e -> handleMouseInput(e.getX(), e.getY()));
		
		return myScene;
	}
	
	public void step(double elapsedTime){
		strawberry1.setX(strawberry1.getX() + direction1 * BERRY_SPEED * elapsedTime);
		strawberry2.setY(strawberry2.getY() + direction2 * BERRY_SPEED * elapsedTime);
		strawberry3.setX(strawberry3.getX() + direction3 * BERRY_SPEED * elapsedTime);
		direction1 = keepInBounds(myScene, strawberry1, direction1);
		direction2 = keepInBounds(myScene, strawberry2, direction2);
		direction3 = keepInBounds(myScene, strawberry3, direction3);
		
		eatBerries(mimi, strawberry1);
		eatBerries(mimi, strawberry2);
		eatBerries(mimi, strawberry3);
		
		if(strawberry1.getImage() == null && strawberry2.getImage() == null
				&& strawberry3.getImage() == null){
			
			Platform.exit();
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
        if (mimi.contains(x, y)) {
            mimi.setScaleX(mimi.getScaleX() * GROWTH_RATE);
            mimi.setScaleY(mimi.getScaleY() * GROWTH_RATE);
        }
    }
	
}
