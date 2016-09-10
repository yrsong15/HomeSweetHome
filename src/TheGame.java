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
	public static final int KEY_INPUT_SPEED = 50; 
	
	private static final int MIMI_SIZE = 50;
	private static final int HOME_SIZE = 30;
	private static final int RAT_SIZE = 30;
	private static final int JAR_SIZE = 20;
	private static final int TAXI_SIZE = 40;
	
	private static final int RAT_SPEED = 60;
	private static final int JAR_SPEED = 40;
	private static final int TAXI_SPEED = 100;
	
	private static final int NUMBER_OF_JARS = 5;
	private static final int NUMBER_OF_TAXIS = 12;
	private static final int NUMBER_OF_RATS = 30;
	
	private SplashPage page;
	private Scene myScene;
	private Timer timer;
	
	private ImageView mimi, home;
	private ArrayList<ImageView> rats, jarsOfJam, taxis;
	private int[] ratDirection, jarDirection, taxiDirection;
	
	private Group root;
	private int level;
	private boolean noJailBreak = true;
	
	public Scene init(int width, int height){
		initializeMap();
		myScene = new Scene(root, width, height, Color.BEIGE);
		myScene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
		myScene.setOnMouseClicked(e -> handleMouseInput(e.getX(), e.getY()));
		return myScene;
	}
	
	public void step(double elapsedTime){
//		System.out.println(level);
		System.out.println(timer.getTime());
		if(level == 1){	
			gameLevelOne(elapsedTime);
		}
		
		if(level == 2){
			setLevelTwo();
		}
		
		if(level == 3){
			gameLevelTwo(elapsedTime);
		}
		if(level == 8){
			root.getChildren().add(showMessage
					("Mimi didn't watch where she was going!\n\nGet well soon :("
					,Color.MAROON));
		}
		
		if(level == 9){
			root.getChildren().add(showMessage
					("The rats got Mimi!\n\nGet well soon :("
					, Color.MAROON));
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
	
	private Text showMessage(String msg, Color c) {
		root.getChildren().clear();
		Text showthis = page.message(SIZE, SIZE, msg, c);
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
		
		timer =  new Timer();
		
		Text firstmsg = page.message(SIZE, SIZE, 
				"Click anywhere to proceed to game");
		root.getChildren().add(firstmsg);
		level = 0;
		
		ratDirection = initializeDirection(ratDirection, NUMBER_OF_RATS);
		jarDirection = initializeDirection(jarDirection, NUMBER_OF_JARS);
		taxiDirection = initializeDirection(taxiDirection, NUMBER_OF_TAXIS);
		
		jarsOfJam = makeObjects(NUMBER_OF_JARS, "jam.png");
		taxis = makeObjects(NUMBER_OF_TAXIS, "taxi.gif");
		rats = makeObjects(NUMBER_OF_RATS, "rats.png");
		
		Image dog = new Image(getClass().getClassLoader().getResourceAsStream("dog.gif"));
		mimi = new ImageView(dog);
		
		Image house = new Image(getClass().getClassLoader().getResourceAsStream("home.png"));
		home = new ImageView(house);
	
		setUpMap();
	}
	
	public void setUpMap(){	
		setObjects(rats, NUMBER_OF_RATS, RAT_SIZE);
		setObjects(jarsOfJam, NUMBER_OF_JARS, JAR_SIZE);
		setObjects(taxis, NUMBER_OF_TAXIS, TAXI_SIZE);
		setMimiPosition(taxis);
		setMimiPosition(jarsOfJam);	
	}

	private void setMimiPosition(ArrayList<ImageView> list) {
		int[] mimiPosition = generateRandom(2, 0, SIZE-MIMI_SIZE);
		for(ImageView elem : list){
			if(Math.abs(elem.getX() - mimiPosition[0]) < MIMI_SIZE * 4){
				mimiPosition[0] = ThreadLocalRandom.current().nextInt(0, SIZE-MIMI_SIZE + 1);
			}
			if(Math.abs(elem.getY() - mimiPosition[1]) < MIMI_SIZE * 4){
				mimiPosition[1] = ThreadLocalRandom.current().nextInt(0, SIZE-MIMI_SIZE + 1);
			}
		}
		setPosition(mimi, MIMI_SIZE, MIMI_SIZE, mimiPosition[0], mimiPosition[1]);
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
	
	public int keepInBounds(Scene scene, ImageView temp, int direction, int size){
		Scene tempScene = scene;
		if(temp.getX() > tempScene.getWidth() - size || temp.getX() < 0){
			direction *= -1;
		}
		if(temp.getY() > tempScene.getHeight() - size || temp.getY() < 0){
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
		if(xy%4 == 0){
			object.setX(object.getX() + direction * objSpeed * elapsedTime);
		}
		else if(xy%4 == 1){
			object.setY(object.getY() + direction * objSpeed * elapsedTime);
		}
		else if(xy%4 == 3){
			object.setX(object.getX() + direction * objSpeed * elapsedTime);
			object.setY(object.getY() + direction * objSpeed * elapsedTime);
		}
		else{
			object.setX(object.getX() - direction * objSpeed * elapsedTime);
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
		
		runGameLoop(jarsOfJam, jarDirection, JAR_SPEED, JAR_SIZE, elapsedTime);
		runGameLoop(taxis, taxiDirection, TAXI_SPEED, JAR_SIZE, elapsedTime);
		
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
		if(allGone) level = 2;
		if(noJailBreak && mugged) level = 8;
	}
	
	public void setLevelTwo(){
		root.getChildren().clear();
		setPosition(mimi, MIMI_SIZE, MIMI_SIZE, 0, 0);
		root.getChildren().add(mimi);
		Text text3 = page.message(SIZE, SIZE, 
				"You've cleared Level One!\n\nClick to proceed.");
		root.getChildren().add(text3);
		setPosition(home, HOME_SIZE, HOME_SIZE, SIZE-HOME_SIZE, SIZE-HOME_SIZE);
		root.getChildren().add(home);
	}
	
	public void gameLevelTwo(double elapsedTime){
		root.getChildren().clear();
		root.getChildren().add(mimi);
		root.getChildren().addAll(rats);
		root.getChildren().add(home);
		
		runGameLoop(rats, ratDirection, RAT_SPEED, RAT_SIZE, elapsedTime);
		
		boolean out = false;
		for(ImageView rat : rats){
			if(rat.getImage() == null){
				out = true;
				break;
			}
		}
		if(noJailBreak && out){
			level = 9;
		}
		
		if(reachHome(mimi, home)){
			level = 10;
		}
		
		
	}

	private void runGameLoop(ArrayList<ImageView> list, int[] directions, 
			int speed, int size, double elapsedTime) {
		int count = 0;
		while(count < list.size()){
			ImageView objRightNow = list.get(count);
			moveObjectives(objRightNow, directions[count], count, 
					speed, elapsedTime);
			directions[count] = keepInBounds(myScene, objRightNow, 
					directions[count], size);
			eatObjects(mimi, objRightNow);
			count++;
		}
	}
	
    private void handleKeyInput (KeyCode code) {
        switch (code) {
            case RIGHT:
                mimi.setX(mimi.getX() + KEY_INPUT_SPEED);
                if(noJailBreak && mimi.getX() > SIZE - MIMI_SIZE){
                	mimi.setX(SIZE-MIMI_SIZE);
                }
                break;
            case LEFT:
                mimi.setX(mimi.getX() - KEY_INPUT_SPEED);
                if(noJailBreak && mimi.getX() < 0){
                	mimi.setX(0);
                }
                break;
            case UP:
                mimi.setY(mimi.getY() - KEY_INPUT_SPEED);
                if(noJailBreak && mimi.getY() < 0){
                	mimi.setY(0);
                }
                break;
            case DOWN:
                mimi.setY(mimi.getY() + KEY_INPUT_SPEED);
                if(noJailBreak && mimi.getY() > SIZE - MIMI_SIZE){
                	mimi.setY(SIZE-MIMI_SIZE);
                }
                break;
                
            //Jailbreak Cheat Code: Press Q
            case Q:
            	System.out.println("Cheat Code - Jailbreak Enabled!");
            	noJailBreak = false;
            	break;
            default: // do nothing
        }
    }
    
    
    private void handleMouseInput (double x, double y) {
    	if(level == 0 || level == 2 || level == 10){
    		level++;
    		return;
    	}
    	if(level == 8 || level == 9){
    		level = 11;
    		return;
    	}
    	
    	//Cheat Code
    	if(x < 30 && y < 30){
    		System.out.println("Cheat Code - You Win!");
    		level = 10;
    	}
        if (mimi.contains(x, y)) {
        	System.out.println("Cheat Code - Proceed to Level Two");
            level = 2;
        }
    }
	
}
