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
	private final String TITLE = "Home Sweet Home";
	private final int SIZE = 600;
	
	private final int HOME_SIZE = 30;
	private final int RAT_SIZE = 25;
	private final int JAR_SIZE = 20;
	private final int TAXI_SIZE = 40;
	private final int ITEM_SIZE = 20;
	
	private final int RAT_SPEED = 60;
	private final int JAR_SPEED = 40;
	private final int TAXI_SPEED = 100;
	private final int ITEM_SPEED = 300;
	
	private final int NUMBER_OF_JARS = 5;
	private final int NUMBER_OF_TAXIS = 10;
	private final int NUMBER_OF_RATS = 30;
	
	private final float TIME_LIMIT_ONE = 15f;
	private final float TIME_LIMIT_TWO = 20f;

	private int KEY_INPUT_SPEED = 50;
	private int MIMI_SIZE = 50;
	
	private SplashPage page;
	private Scene myScene;
	private Timer timer;
	
	private ImageView mimi, home, star, potion;
	private ArrayList<ImageView> rats, jarsOfJam, taxis, items;
	private int[] ratDirection, jarDirection, taxiDirection, itemDirection;
	
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
		
		if(level == 1){
			timer.resetTimer();
			level++;
		}
		if(level == 2){	
			gameLevelOne(elapsedTime);
		}
		
		if(level == 3){
			initLevelTwo();
			level++;
		}
		
		
		if(level == 4){
			showLevelTwo();
		}
		
		if(level == 5){
			timer.resetTimer();
			level++;
		}
		
		if(level == 6){
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
		
		if(level == 99){
			root.getChildren().add(showMessage
					("Time out! Mimi has to run faster :("
					, Color.MAROON));
		}
		
		if(level == 100){
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
		
		jarDirection = initializeDirection(jarDirection, NUMBER_OF_JARS);
		taxiDirection = initializeDirection(taxiDirection, NUMBER_OF_TAXIS);
		
		jarsOfJam = makeObjects(NUMBER_OF_JARS, "jam.png");
		taxis = makeObjects(NUMBER_OF_TAXIS, "taxi.gif");
		
		mimi = initSingleObj("dog.gif");
		home = initSingleObj("home.png");
	
		setUpMap();
	}

	private ImageView initSingleObj(String filename) {
		Image image = new Image(getClass().getClassLoader().getResourceAsStream(filename));
		ImageView character = new ImageView(image);
		return character;
	}
	
	public void setUpMap(){
		setObjects(jarsOfJam, NUMBER_OF_JARS, JAR_SIZE);
		setObjects(taxis, NUMBER_OF_TAXIS, TAXI_SIZE);
		setMimiPosition(taxis);
	}

	private void setMimiPosition(ArrayList<ImageView> list) {
		int[] mimiPosition = generateRandom(2, 0, SIZE-MIMI_SIZE);
		for(int i=0;i<list.size();i++){
			if(Math.abs(list.get(i).getX() - mimiPosition[0]) < MIMI_SIZE){
				mimiPosition[0] = ThreadLocalRandom.current().nextInt(0, SIZE-MIMI_SIZE + 1);
				i = 0;
			}
			if(Math.abs(list.get(i).getY() - mimiPosition[1]) < MIMI_SIZE){
				mimiPosition[1] = ThreadLocalRandom.current().nextInt(0, SIZE-MIMI_SIZE + 1);
				i = 0;
			}
		}
		setPosition(mimi, MIMI_SIZE, MIMI_SIZE, mimiPosition[0], mimiPosition[1]);
	}
	
	private void setMimiToCorner(ArrayList<ImageView> list) {
		int x = 0;
		int y = 0;
		for(int i=0;i<list.size();i++){
			if(list.get(i).getX() < MIMI_SIZE){
				list.get(i).setX(ThreadLocalRandom.current().nextInt
						(MIMI_SIZE, SIZE-MIMI_SIZE + 1));
				i = 0;
			}
			if(list.get(i).getY() < MIMI_SIZE){
				list.get(i).setY(ThreadLocalRandom.current().nextInt
						(MIMI_SIZE, SIZE-MIMI_SIZE + 1));
				i = 0;
			}
		}
		setPosition(mimi, MIMI_SIZE, MIMI_SIZE, x, y);
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
	
	public ImageView minimize(ImageView temp){
		temp.setFitWidth(10);
		temp.setFitHeight(10);
		return temp;
	}
	
	public int[] generateRandom(int elems, int min, int max){
		int[] temp = new int[elems];
		for(int i=0;i<elems;i++){
			temp[i] = ThreadLocalRandom.current().nextInt(min, max + 1);
		}
		return temp;
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
			object.setY(object.getY() + direction * objSpeed * elapsedTime);
		}
		else if(xy%4 == 1){
			object.setX(object.getX() - direction * objSpeed * elapsedTime);
			object.setY(object.getY() + direction * objSpeed * elapsedTime);
		}
		else if(xy%4 == 2){
			object.setX(object.getX() + direction * objSpeed * elapsedTime);
		}
		else{
			object.setY(object.getY() + direction * objSpeed * elapsedTime);
		}
	}
	
	public void bounceObjectives(ImageView object, int[] xyDir, int xy, 
			int objSpeed, double elapsedTime){
		int xDir = xyDir[0];
		int yDir = xyDir[1];
		if(xy%4 == 0){
			object.setX(object.getX() + xDir * objSpeed * elapsedTime);
			object.setY(object.getY() + yDir * objSpeed * elapsedTime);
		}
		else if(xy%4 == 1){
			object.setX(object.getX() - xDir * objSpeed * elapsedTime);
			object.setY(object.getY() + yDir * objSpeed * elapsedTime);
		}
		else if(xy%4 == 2){
			object.setX(object.getX() + xDir * objSpeed * elapsedTime);
		}
		else{
			object.setY(object.getY() + yDir * objSpeed * elapsedTime);
		}
	}
	
	public int keepInBounds(Scene scene, ImageView temp, int direction, int size){
		if(temp.getX() > scene.getWidth() - size || temp.getX() < 0){
			direction *= -1;
		}
		if(temp.getY() > scene.getHeight() - size || temp.getY() < 0){
			direction *= -1;
		}
		return direction;
	}
	
	public int[] bounceInBounds(Scene scene, ImageView temp, int xDir, int yDir, int size){
		int[] xydir = {xDir, yDir};
		if(temp.getX() > scene.getWidth() - size || temp.getX() < 0){
			xydir[0] *= -1;
		}
		if(temp.getY() > scene.getHeight() - size || temp.getY() < 0){
			xydir[1] *= -1;
		}
		return xydir;
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
		root.getChildren().add(page.formattedMessage(SIZE/60, SIZE/20, 
				"Time Left: " + timer.countdown(TIME_LIMIT_ONE), Color.MAROON));
		
		if(!noJailBreak){
			root.getChildren().add(page.formattedMessage(SIZE/2, SIZE/20, 
					"JAILBREAK ENABLED", Color.RED));
		}
		
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
	
		if(allGone) level = 3;
		if(timer.getTimeValue() >= TIME_LIMIT_ONE) level = 99;
		if(noJailBreak && mugged) level = 8;
	}
	
	public void initLevelTwo(){
		ratDirection = initializeDirection(ratDirection, NUMBER_OF_RATS*2);
		itemDirection = initializeDirection(itemDirection, 4);
		rats = makeObjects(NUMBER_OF_RATS, "rats.png");
		setObjects(rats, NUMBER_OF_RATS, RAT_SIZE);
		star = initSingleObj("star.png");
		potion = initSingleObj("potion.png");
		setItemPosition();
		items = new ArrayList<ImageView>();
		items.add(star);
		items.add(potion);
	}
	
	public void setItemPosition(){
		int[] itemPosition = generateRandom(4, 0, SIZE-ITEM_SIZE);
		setPosition(star, ITEM_SIZE, ITEM_SIZE, itemPosition[0], itemPosition[1]);
		setPosition(potion, ITEM_SIZE, ITEM_SIZE, itemPosition[2], itemPosition[3]);
	}
	
	public void showLevelTwo(){
		root.getChildren().clear();
		setMimiToCorner(rats);
		root.getChildren().add(mimi);
		Text text3 = page.message(SIZE, SIZE, 
				"You've cleared Level One!\n\nClick to proceed.");
		root.getChildren().add(text3);
		setPosition(home, HOME_SIZE, HOME_SIZE, SIZE-HOME_SIZE, SIZE-HOME_SIZE);
		root.getChildren().add(home);
		timer.resetTimer();
	}
	
	public void gameLevelTwo(double elapsedTime){
		root.getChildren().clear();
		root.getChildren().add(mimi);
		root.getChildren().addAll(rats);
		root.getChildren().add(home);
		root.getChildren().addAll(items);
		root.getChildren().add(page.formattedMessage(SIZE/60, SIZE/20, 
				"Time Left: " + timer.countdown(20), Color.MAROON));
		
		if(!noJailBreak){
			root.getChildren().add(page.formattedMessage(SIZE/2, SIZE/20, 
					"JAILBREAK ENABLED", Color.RED));
			
		}
		
		runLevelTwo(rats, ratDirection, RAT_SPEED, RAT_SIZE, elapsedTime);
		runLevelTwo(items, itemDirection, ITEM_SPEED, ITEM_SIZE, elapsedTime);
		
		boolean invincible = false;
		
		boolean out = false;
		for(ImageView rat : rats){
			if(rat.getImage() == null){
				if(!invincible){
					out = true;
					break;
				}
			}
		}
		
		if(!invincible && star.getImage() == null){
			invincible = true;
		}
		
		if(potion.getImage() == null){
			mimi = minimize(mimi);
			MIMI_SIZE = 10;
		}
			
		if(timer.getTimeValue() >= TIME_LIMIT_TWO) level = 99;
		
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
	
	private void runLevelTwo(ArrayList<ImageView> list, int[] directions, 
			int speed, int size, double elapsedTime) {
		int count = 0;
		while(count < list.size()){
			ImageView objRightNow = list.get(count);
			int[] tempDir = new int[2];
			
			System.arraycopy(directions, count*2, tempDir, 0, 2);
			
			bounceObjectives(objRightNow, tempDir, count, 
					speed, elapsedTime);
			System.arraycopy(bounceInBounds(myScene, objRightNow, 
					directions[count*2], directions[count*2+1], size), 
					0, tempDir, 0, tempDir.length);
			eatObjects(mimi, objRightNow);
			
			System.arraycopy(tempDir, 0, directions, count*2, 2);
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
            	noJailBreak = !noJailBreak;
            	if(noJailBreak){
            		System.out.println("Jailbreak Disabled!");
            	}
            	else{
            		System.out.println("Jailbreak Enabled!");
            	}
            	break;
            default: // do nothing
        }
    }
    
    
    private void handleMouseInput (double x, double y) {
    	if(level == 0 || level == 4){
    		level++;
    		return;
    	}
    	if(level == 8 || level == 9 || level == 10 || level == 99){
    		level = 100;
    		return;
    	}
    	
        if (mimi.contains(x, y)) {
        	System.out.println("Cheat Code - Proceed to Level Two");
            level = 3;
        }
    }
	
}
