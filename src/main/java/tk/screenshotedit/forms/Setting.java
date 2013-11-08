package tk.screenshotedit.forms;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import tk.screenshotedit.App;

import java.util.ResourceBundle;


/**
 * User: mavolkov
 * Date: 01.11.13
 */
public class Setting extends BorderPane {
    private ResourceBundle rb;
    private Button save;
    private Button exit;
    private HBox hbButton;
    private VBox vbFields;
    public Setting() {
//        rb = ResourceBundle.getBundle("tk\\screenshotedit\\resources\\lang.messages", Locale.ENGLISH);
        rb = App.getResourceBundle();
        initElements();
        save.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                System.out.println("Hello World");
            }
        });
        hbButton.getChildren().addAll(save, exit);
        setCenter(vbFields);
        setBottom(hbButton);
    }

    private void initElements(){
        exit = new Button(rb.getString("settings.exit"));
        save = new Button(rb.getString("settings.save"));
        save.setMaxWidth(Double.MAX_VALUE);
        exit.setMaxWidth(Double.MAX_VALUE);
        hbButton =  new HBox();
        hbButton.setAlignment(Pos.CENTER);
        hbButton.setSpacing(10);
        hbButton.setPadding(new Insets(0, 20, 10, 20));
        vbFields = new VBox();
        vbFields.setPadding(new Insets(0,20, 10, 20));
    }
}
