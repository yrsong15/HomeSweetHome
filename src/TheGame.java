import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;


public class TheGame {
	public static final String TITLE = "Home Sweet Home";
	public static final int SIZE = 600;
	public static final int KEY_INPUT_SPEED = 50;    //fix later
	public static final double GROWTH_RATE = 1.1;
	
	private static final int RAT_SPEED = 60;
	private static final int OBJECT_SIZE = 20;
	private static final int JAR_SPEED = 100;
	private static final int TAXI_SPEED = 30;
	
	private static final int MIMI_SIZE = 50;
	private static final int HOME_SIZE = 50;
	private static final int NUMBER_OF_OBJECTS = 5;
	private static final int NUMBER_OF_OBSTACLES = 20;
	
	private SplashPage page;
	private Scene myScene;
	
	private ImageView mimi, home;
	private ArrayList<ImageView> rats, jarsOfJam, taxis;
	private int[] ratDirection, jarDirection, taxiDirection;
	
	private Group root;
	private int level;
	
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
			root.getChildren().add(showMessage("Click to Proceed to Level One"));
		}
		if(level == 2){	
			gameLevelOne(elapsedTime);
		}
		
		if(level == 3){
			setLevelTwo();
		}
		
		if(level == 4){
			gameLevelTwo(elapsedTime);
		}
		if(level == 9){
			root.getChildren().add(showMessage
					("The rats got Mimi!\n\nGet well soon :("));
		}
		if(level == 10){
			root.getChildren().add(showMessage
					("You're DONE!!!!\n\nClick to exit."));
		}
		if(level == 11){
			Platform.exit();
		}
	}

	private Text showMessage(String msg) {
		root.getChildren().clear();
		Text showthis = page.message(SIZE, SIZE, msg);
		return showthis;
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
		Text firstmsg = page.message(SIZE, SIZE, "Click anywhere to proceed to game");
		root.getChildren().add(firstmsg);
		level = 0;
		
		ratDirection = initializeDirection(ratDirection, NUMBER_OF_OBSTACLES);
		jarDirection = initializeDirection(jarDirection, NUMBER_OF_OBJECTS);
		taxiDirection = initializeDirection(taxiDirection, NUMBER_OF_OBJECTS);
		
		jarsOfJam = makeObjects(NUMBER_OF_OBJECTS, "jam.png");
		taxis = makeObjects(NUMBER_OF_OBJECTS, "taxi.gif");
		rats = makeObjects(NUMBER_OF_OBSTACLES, "rats.png");
		
		Image dog = new Image(getClass().getClassLoader().getResourceAsStream("dog.gif"));
		mimi = new ImageView(dog);
		
		Image house = new Image(getClass().getClassLoader().getResourceAsStream("home.png"));
		home = new ImageView(house);
	
		setUpMap();
	}
	
	public void setUpMap(){

		int[] positions = generateRandom(2, 0, SIZE-MIMI_SIZE);
		setPosition(mimi, MIMI_SIZE, MIMI_SIZE, positions[0], positions[1]);
		
		setObjects(rats, NUMBER_OF_OBSTACLES, OBJECT_SIZE);
		setObjects(jarsOfJam, NUMBER_OF_OBJECTS, OBJECT_SIZE);
		setObjects(taxis, NUMBER_OF_OBJECTS, OBJECT_SIZE);
		
	}
	
	private int[] initializeDirection(int[] array, int num) {
		array = new int[num];
		for(int i=0;i<array.length;i++){
			array[i] = 1;
		}
		return array;
	}
	
	public void setObjects(ArrayList<ImageView> list, int numOfObjs, int size){
		int count = 0;
		int[] positions = generateRandom(numOfObjs*2, 0, SIZE - size);
		while(count < list.size()){
			ImageView objRightNow = list.get(count);
			setPosition(objRightNow, size, size, positions[count], positions[count+1]);
			count++;
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
		if(temp.getX() > tempScene.getWidth() - OBJECT_SIZE || temp.getX() < 0){
			direction *= -1;
		}
		if(temp.getY() > tempScene.getHeight() - OBJECT_SIZE || temp.getY() < 0){
			direction *= -1;
		}
		return direction;
	}
	
	public void eatObjects(ImageView character, ImageView object){
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
	
	public boolean reachHome(ImageView character, ImageView object){
		boolean val = false;
		if(character.getBoundsInParent().intersects(object.getBoundsInParent())){
			val = true;
		}
		return val;
	}
	
	public void moveObjectives(ImageView object, int direction, int xy, 
			int objSpeed, double elapsedTime){
		if(xy%2 == 0){
			object.setX(object.getX() + direction * objSpeed * elapsedTime);
		}
		else{
			object.setY(object.getY() + direction * objSpeed * elapsedTime);
		}
	}
	
	public ArrayList<ImageView> makeObjects(int num, String filename){
		ArrayList<ImageView> list = new ArrayList<>();
		for(int i=0;i<num;i++){
			Image obj = new Image(getClass().getClassLoader().
					getResourceAsStream(filename));
			list.add(new ImageView(obj));
		}
		return list;
	}
	
	public void gameLevelOne(double elapsedTime){
		
		root.getChildren().clear();
		root.getChildren().add(mimi);
		root.getChildren().addAll(jarsOfJam);
		root.getChildren().addAll(taxis);
		
		runLevelOne(jarsOfJam, jarDirection, JAR_SPEED, elapsedTime);
		runLevelOne(taxis, taxiDirection, TAXI_SPEED, elapsedTime);
		
		boolean mugged = false;
		for(ImageView taxi : taxis){
			if(taxi.getImage() == null){
				mugged = true;
				break;
			}
		}
		
		boolean allGone = true;
		for(ImageView jar : jarsOfJam){
			if(jar.getImage() != null){
				allGone = false;
				break;
			}
		}
		if(allGone) level = 3;
		if(mugged) level = 9;
	}
	
	public void setLevelTwo(){
		root.getChildren().clear();
		setPosition(mimi, MIMI_SIZE, MIMI_SIZE, 0, 0);
		root.getChildren().add(mimi);
		Text text3 = page.message(SIZE, SIZE, "You've cleared Level One!\n\nClick to proceed.");
		root.getChildren().add(text3);
		setPosition(home, HOME_SIZE, HOME_SIZE, SIZE-HOME_SIZE, SIZE-HOME_SIZE);
		root.getChildren().add(home);
	}
	
	public void gameLevelTwo(double elapsedTime){
		root.getChildren().clear();
		root.getChildren().add(mimi);
		root.getChildren().addAll(rats);
		root.getChildren().add(home);
		
		runLevelOne(rats, ratDirection, RAT_SPEED, elapsedTime);
		
		boolean out = false;
		for(ImageView rat : rats){
			if(rat.getImage() == null){
				out = true;
				break;
			}
		}
		if(out){
			level = 9;
		}
		
		if(reachHome(mimi, home)){
			level = 10;
		}
		
		
	}

	private void runLevelOne(ArrayList<ImageView> list, int[] directions, int speed, double elapsedTime) {
		int count = 0;
		while(count < list.size()){
			ImageView objRightNow = list.get(count);
			moveObjectives(objRightNow, directions[count], count, 
					speed, elapsedTime);
			directions[count] = keepInBounds(myScene, objRightNow, 
					directions[count]);
			eatObjects(mimi, objRightNow);
			count++;
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
    	if(level == 9){
    		level = 11;
    		return;
    	}
    	//The CHEAT CODE
    	if(x < 100 && y < 100){
    		level = 10;
    	}
        if (mimi.contains(x, y)) {
            level = 3;
        }
    }
	
}
