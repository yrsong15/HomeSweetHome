/**
 * This is the main function that is to be run for 
 * my Compsci 308 Game project, "Home Sweet Home".
 * 
 * @author Ray Song
 */

import javafx.application.Application;
import javafx.stage.Stage;
	 
public class Main extends Application {
	
	@Override
	public void start(Stage s){
		new StartGame(s);
    }
	
	public static void main(String[] args){
		launch(args);
	}
}