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
		mainstage = s;
		startMessage = new StartMessage();
		myGame = new TryGame();
		endMessage = new EndMessage();
		s.setTitle(myGame.getTitle());
		
		pane1=new FlowPane();
	    pane2=new FlowPane();
		
//		Scene intro = startMessage.init(SIZE, SIZE);
//		s.setScene(intro);
//		s.show();
//		
//		KeyFrame introframe = new KeyFrame(Duration.millis(MILLISECOND_DELAY),
//				e -> startMessage.step(SECOND_DELAY));
//		
//		Timeline introanimation = new Timeline();
//		introanimation.setCycleCount(Timeline.INDEFINITE);
//		introanimation.getKeyFrames().add(introframe);
//		introanimation.play();
		
	
		scene1 = startMessage.init(pane1, SIZE, SIZE);
		scene2 = myGame.init(SIZE, SIZE);
		scene3 = endMessage.init(pane2, SIZE, SIZE);
			
		scene1.setOnMouseClicked(e -> handleMouseInput(e.getX(), e.getY()));
		
		s.setScene(scene1);
		s.show();
		
		KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY),
				e -> myGame.step(SECOND_DELAY));
		
		Timeline animation = new Timeline();
		animation.setCycleCount(Timeline.INDEFINITE);
		animation.getKeyFrames().add(frame);
		animation.play();	
		

		

	}
	
    private void handleMouseInput (double x, double y) {
        mainstage.setScene(scene2);
    }
	
	public static void main(String[] args){
		launch(args);
	}
}