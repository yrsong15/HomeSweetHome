import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import javafx.util.Duration;
	 
public class Main extends Application {
	
	public static final int SIZE = 400;
	public static final int FRAMES_PER_SECOND = 60;
    private static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    private static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
	 
    private StartMessage startMessage;
	private TryGame myGame;
	private EndMessage endMessage; 
	private FlowPane pane1, pane2;
	private Stage mainstage;
	private Scene scene1, scene2, scene3;
	
	@Override
	public void start(Stage s){
//		new FirstClass();
		mainstage = s;
		startMessage = new StartMessage();
//		endMessage = new EndMessage();
		
		pane1=new FlowPane();
	
//		scene1 = startMessage.init(pane1, SIZE, SIZE);
		scene1 = startMessage.init(SIZE, SIZE);

		s.setScene(scene1);
		s.show();
		
		scene1.setOnMouseClicked(e -> handleMouseInput(e.getX(), e.getY()));
		
		
		myGame = new TryGame();
		scene2 = myGame.init(SIZE, SIZE);
		s.setTitle(myGame.getTitle());
		
		KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY),
				e -> myGame.step(SECOND_DELAY));
		
		Timeline animation = new Timeline();
		animation.setCycleCount(Timeline.INDEFINITE);
		animation.getKeyFrames().add(frame);
		animation.play();	

	}
	
    private void handleMouseInput (double x, double y) {
    	
    	//add if statement to check that this came from scene1
        mainstage.setScene(scene2);
        
//    	mainstage.close();
    }
	
	public static void main(String[] args){
		launch(args);
	}
}


//Scene intro = startMessage.init(SIZE, SIZE);
//s.setScene(intro);
//s.show();
//
//KeyFrame introframe = new KeyFrame(Duration.millis(MILLISECOND_DELAY),
//		e -> startMessage.step(SECOND_DELAY));
//
//Timeline introanimation = new Timeline();
//introanimation.setCycleCount(Timeline.INDEFINITE);
//introanimation.getKeyFrames().add(introframe);
//introanimation.play();