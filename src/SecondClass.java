import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SecondClass extends Stage {
Label x = new Label("Second stage");
VBox y = new VBox();

	SecondClass(){
    y.getChildren().add(x);
    
    this.setScene(new Scene(y, 300, 300));
    this.show();
   }    
}