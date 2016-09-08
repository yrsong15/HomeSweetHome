import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;


public class TheGame {
	public static final String TITLE = "Home Sweet Home";
	public static final int SIZE = 400;
	public static final int KEY_INPUT_SPEED = 50;    //fix later
	public static final double GROWTH_RATE = 1.1;
	private static final int BERRY_SPEED = 60;
	private static final int BERRY_SIZE = 20;
	private static final int MIMI_SIZE = 50;
	private static final int NUMBER_OF_OBJECTS = 5;
	
	private SplashPage page;
	private Scene myScene;
	
	private ImageView mimi;
	private ArrayList<ImageView> strawberries;
	private ArrayList<ImageView> jarsOfJam;
	private int[] directions;
	
	private Group root;
	private int level;
	private Text firstmsg, lvlonemsg, text3, endmsg;
	
	public Scene init(int width, int height){
		initializeMap();
		myScene = new Scene(root, width, height, Color.BEIGE);
		myScene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
		myScene.setOnMouseClicked(e -> handleMouseInput(e.getX(), e.getY()));
		return myScene;
	}
	
	public void step(double elapsedTime){
		System.out.println(level);
		if(level == 1){
			root.getChildren().clear();
			lvlonemsg = page.message(SIZE, SIZE, "Start of Level One -  click again");
			root.getChildren().add(lvlonemsg);
		}
		
		if(level == 2){	
			gameLevelOne(elapsedTime);
		}
		
		if(level == 3){
			root.getChildren().remove(text3);
			text3 = page.message(SIZE, SIZE, "You've cleared Level One! Click to proceed");
			root.getChildren().add(text3);
		}
		
		if(level == 4){
			gameLevelTwo(elapsedTime);
		}
		
		if(level == 10){
			root.getChildren().clear();
			endmsg = page.message(SIZE, SIZE, "You've DONE!!!! Click to exit");
			root.getChildren().add(endmsg);
		}

		if(level == 11){
			Platform.exit();
		}
	}
	
	public String getTitle(){
		return TITLE;
	}
	
	public int getSize(){
		return SIZE;
	}
	
	public void initializeMap(){
		root = new Group();
		page = new SplashPage();
		firstmsg = page.message(SIZE, SIZE, "Click anywhere to proceed to game");
		root.getChildren().add(firstmsg);
		level = 0;
		
		directions = new int[NUMBER_OF_OBJECTS];
		for(int i=0;i<directions.length;i++){
			directions[i] = 1;
		}
		
		strawberries = makeBerries(NUMBER_OF_OBJECTS);
		jarsOfJam = makeJars(NUMBER_OF_OBJECTS);
		
		Image dog = new Image(getClass().getClassLoader().getResourceAsStream("dog.gif"));
		mimi = new ImageView(dog);
	
		setCharactersOnMap();
	}
	
	public void setCharactersOnMap(){
		int[] positions = generateRandom(NUMBER_OF_OBJECTS*2 + 2, 0, 380);
		int[] jarPositions = generateRandom(NUMBER_OF_OBJECTS*2, 0, 380);
		
		setPosition(mimi, MIMI_SIZE, MIMI_SIZE, positions[0], positions[1]);
		
		int berryNumCount = 0;
		while(berryNumCount < strawberries.size()){
			ImageView berryRightNow = strawberries.get(berryNumCount);
			setPosition(berryRightNow, BERRY_SIZE, BERRY_SIZE, 
					positions[berryNumCount*2 + 2], positions[berryNumCount*2 + 3]);
			berryNumCount++;
		}
		
		int jarNumCount = 0;
		while(jarNumCount < jarsOfJam.size()){
			ImageView jarRightNow = jarsOfJam.get(jarNumCount);
			setPosition(jarRightNow, BERRY_SIZE, BERRY_SIZE,
					jarPositions[jarNumCount], jarPositions[jarNumCount+1]);
			jarNumCount++;
		}
		
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
	
	public void eatJam(ImageView character, ImageView object){
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
	
	public ArrayList<ImageView> makeBerries(int num){
		ArrayList<ImageView> bucketOfBerries = new ArrayList<>();
		for(int i=0;i<num;i++){
			Image berry = new Image(getClass().getClassLoader().
					getResourceAsStream("strawberry.png"));
			bucketOfBerries.add(new ImageView(berry));
		}
		return bucketOfBerries;
	}
	
	public ArrayList<ImageView> makeJars(int num){
		ArrayList<ImageView> jars = new ArrayList<>();
		for(int i=0;i<num;i++){
			Image jar = new Image(getClass().getClassLoader().
					getResourceAsStream("jam.png"));
			jars.add(new ImageView(jar));
		}
		return jars;
	}
	
	public void gameLevelOne(double elapsedTime){
		root.getChildren().clear();
		root.getChildren().add(mimi);
		root.getChildren().addAll(strawberries);
		int berryNumCount = 0;
		while(berryNumCount < strawberries.size()){
			ImageView berryRightNow = strawberries.get(berryNumCount);
			moveBerries(berryRightNow, directions[berryNumCount], berryNumCount, elapsedTime);
			directions[berryNumCount] = keepInBounds(myScene, berryRightNow, 
					directions[berryNumCount]);
			eatBerries(mimi, berryRightNow);
			berryNumCount++;
		}
		
		boolean allGone = true;
		for(ImageView berry : strawberries){
			if(berry.getImage() != null){
				allGone = false;
				break;
			}
		}
		if(allGone){
			level = 3;
		}
		
	}
	
	public void gameLevelTwo(double elapsedTime){
		
		root.getChildren().clear();
		root.getChildren().add(mimi);
		root.getChildren().addAll(jarsOfJam);
		
		int jarNumCount = 0;
		while(jarNumCount < jarsOfJam.size()){
			ImageView jarRightNow = jarsOfJam.get(jarNumCount);
			moveBerries(jarRightNow, directions[jarNumCount], jarNumCount, elapsedTime);
			directions[jarNumCount] = keepInBounds(myScene, jarRightNow, 
					directions[jarNumCount]);
			eatBerries(mimi, jarRightNow);
			jarNumCount++;
		}
		
		boolean allGone = true;
		for(ImageView jar : jarsOfJam){
			if(jar.getImage() != null){
				allGone = false;
				break;
			}
		}
		
		if(allGone){
			level = 10;
		}

	}


	
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
            default: // do nothing
        }
    }
    
    
    private void handleMouseInput (double x, double y) {
    	if(level == 0 || level == 1 || level == 3 || level == 10){
    		level++;
    		return;
    	}
    	
    	
    	//The CHEAT CODE
//        if (strawberry2.contains(x, y)) {
//            level = 10;
//        }

    }
	
}
