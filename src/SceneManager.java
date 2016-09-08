import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

public class SceneManager {
	public static final int SIZE = 400;
	public static final int FRAMES_PER_SECOND = 60;
    private static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    private static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
	
	private Scene myScene;
	private Stage myStage;
	private Intro intro;
	private int sceneNumber;
	
	SceneManager(Stage s){
		myStage = s;
		sceneNumber = 0;

			intro = new Intro();
			Scene scene1 = intro.init(SIZE, SIZE);
			myStage.setScene(scene1);
			myStage.setTitle(intro.getTitle());
			myStage.show();
			
			KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY),
					e -> intro.step());
			
			Timeline animation = new Timeline();
			animation.setCycleCount(Timeline.INDEFINITE);
			animation.getKeyFrames().add(frame);
			animation.play();
		
		System.out.println(intro.isClicked());
		
		if(intro.isClicked()){
			System.out.println("It's really clicked!");
		}
		
		
//		init();
	}
	
	public void init(){
		
		Group root = new Group();
		
		
		Platform.exit();
	}
	

}
