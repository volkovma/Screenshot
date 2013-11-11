package tk.screenshotedit.forms;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tk.screenshotedit.App;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;


/**
 * User: mavolkov
 * Date: 01.11.13
 */
public class Setting extends BorderPane {
  private Logger logger = LoggerFactory.getLogger(Setting.class);
  private String tag = Setting.class.getClass().getCanonicalName();
  private File editorFile = null;
  //GUI
  private Stage stage;
  private ResourceBundle rb;
  private Button save;
  private Button exit;
  private HBox hbButton;
  private VBox vbFields;
  private HBox hbEditorLabel;
  private VBox vbChooseEditor;
  private RadioButton editorDefault;
  private RadioButton editorMSPaint;
  private RadioButton editorUser;
  private ImageView editorDefaultImg;
  private FileChooser editorFileChooser;
  private Button openFileChooserDialog;
  private TextField tfEditorOther;


  public Setting(Stage stage) {
    rb = App.getResourceBundle();
    this.stage = stage;
    initElements();
    addActionEvent();
    hbButton.getChildren().addAll(save, exit);
    setCenter(vbFields);
    setBottom(hbButton);
  }

  /**
   * Init GUI element
   */
  private void initElements() {
    //Center
    vbFields = new VBox();
    vbFields.setPadding(new Insets(20));
    //Editor
    hbEditorLabel = new HBox();
    hbEditorLabel.setAlignment(Pos.CENTER);
    hbEditorLabel.getChildren().add(new Label(rb.getString("editor.choce")));
    HBox hbEditorTwo = new HBox();
    hbEditorTwo.setPadding(new Insets(0, 20, 0, 20));
    editorDefaultImg = new ImageView();
    editorDefaultImg.setImage(new Image("tk/screenshotedit/resources/paint-48.png"));
    vbChooseEditor = new VBox();
    editorDefault = new RadioButton(rb.getString("editor.default"));
    editorDefault.setSelected(true);
    editorMSPaint = new RadioButton(rb.getString("editor.mspaint"));
    editorUser = new RadioButton(rb.getString("editor.user"));
    vbChooseEditor.setSpacing(10);
    HBox hbEditorUser = new HBox();
    hbEditorUser.setPadding(new Insets(0,20,0,20));
    editorFileChooser = new FileChooser();
    editorFileChooser.setTitle(rb.getString("editor.file.chooser.title"));
    openFileChooserDialog = new Button(rb.getString("editor.file.chooser"));
    openFileChooserDialog.setDisable(true);
    tfEditorOther = new TextField();
    tfEditorOther.setDisable(true);
    if (editorFile != null) {
      tfEditorOther.setText(editorFile.getName());
    }
    hbEditorUser.getChildren().addAll(tfEditorOther, openFileChooserDialog);
    vbChooseEditor.getChildren().addAll(editorDefault, editorMSPaint, editorUser);
    hbEditorTwo.getChildren().addAll(editorDefaultImg, vbChooseEditor, hbEditorUser);

    vbFields.getChildren().addAll(hbEditorLabel, hbEditorTwo);
    //bottom
    hbButton = new HBox();
    hbButton.setAlignment(Pos.CENTER);
    hbButton.setSpacing(10);
    hbButton.setPadding(new Insets(0, 20, 10, 20));
    exit = new Button(rb.getString("settings.exit"));
    save = new Button(rb.getString("settings.save"));
    save.setMaxWidth(Double.MAX_VALUE);
    exit.setMaxWidth(Double.MAX_VALUE);
    logger.debug("Init all Setting element");
  }

  /**
   * Add event listener
   */
  private void addActionEvent() {
    save.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        System.out.println("Hello World");
        Setting.this.stage.hide();
      }
    });
    exit.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent actionEvent) {
        Setting.this.stage.hide();
      }
    });
    openFileChooserDialog.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent actionEvent) {
        editorFile = editorFileChooser.showOpenDialog(stage);
        if (editorFile != null) {
          tfEditorOther.setText(editorFile.getName());
        }
      }
    });
  }
}
