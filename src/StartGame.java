import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

public class StartGame {
	private static final int FRAMES_PER_SECOND = 60;
    private static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    private static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
	
	StartGame(Stage s){
		Stage myStage = s;
		TheGame myGame = new TheGame();
		
		Scene scene = myGame.init(myGame.getSize(), myGame.getSize());
		myStage.setScene(scene);
		myStage.setTitle(myGame.getTitle());
		myStage.show();
		
		KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY),
				e -> myGame.step(SECOND_DELAY));
		
		Timeline animation = new Timeline();
		animation.setCycleCount(Timeline.INDEFINITE);
		animation.getKeyFrames().add(frame);
		animation.play();	
	}
}
