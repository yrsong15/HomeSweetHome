import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class FirstClass extends Stage{
Button openOther = new Button("Open other Stage");
HBox x = new HBox();

	FirstClass(){
    x.getChildren().add(openOther);
    this.setScene(new Scene(x, 300, 300));
    this.show();

    openOther.setOnAction(new EventHandler<ActionEvent>() {
        public void handle(ActionEvent t) {
            new SecondClass();
        }//end action
    });
    }
}