package tk.screenshotedit;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tk.screenshotedit.forms.Setting;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * User: mavolkov
 * Date: 16.10.13
 */
public class App extends Application {
    private final String ICON = "tk/screenshotedit/resources/icon.png";
    private final String ICON_DESIBLE = "tk/screenshotedit/resources/icon_d.png";
    private Logger logger = LoggerFactory.getLogger(App.class);
    private TrayIcon trayIcon;
    private static ResourceBundle rb;
  private Service service;


    public static void main(String[] args) {
   rb = ResourceBundle.getBundle("tk\\screenshotedit\\resources\\lang.messages", Locale.getDefault());
    Application.launch(args);
  }

  @Override
  public void start(Stage stage) throws Exception {
    service = new Service();
    createTrayIcon(stage);
    //service.enableHotKey();
    Platform.setImplicitExit(false);
    Scene scene = new Scene(new Setting(),800, 600);
    stage.setScene(scene);
  }

  /**
   * Create system tray
   * @param stage
   */
  private void createTrayIcon(final Stage stage) {
    if (SystemTray.isSupported()) {
      final SystemTray tray = SystemTray.getSystemTray();
      // create a popup menu
      final PopupMenu popup = new PopupMenu();
      // create item
      final MenuItem showItem = new MenuItem(rb.getString("tray.showLast"));
      final MenuItem startItem = new MenuItem(rb.getString("tray.start"));
      final MenuItem stopItem = new MenuItem(rb.getString("tray.stop"));
      final MenuItem settingsItem = new MenuItem(rb.getString("tray.setting"));
      final MenuItem closeItem = new MenuItem(rb.getString("tray.close"));

      startItem.setEnabled(false);
      stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
        @Override
        public void handle(WindowEvent t) {
          hide(stage);
        }
      });
      //show last
      ActionListener showListener = new ActionListener() {
        @Override
        public void actionPerformed(java.awt.event.ActionEvent e) {
          service.openPaint();
        }
      };
      //enable hot key
      ActionListener startListener = new ActionListener() {
        @Override
        public void actionPerformed(java.awt.event.ActionEvent e) {
          service.enableHotKey();
          startItem.setEnabled(false);
          stopItem.setEnabled(true);
          tray.getTrayIcons()[0].setImage(getIcon(ICON));
        }
      };
      //disable hot key
      ActionListener stopListener = new ActionListener() {
        @Override
        public void actionPerformed(java.awt.event.ActionEvent e) {
          service.disableHotKey();
          startItem.setEnabled(true);
          stopItem.setEnabled(false);
          tray.getTrayIcons()[0].setImage(getIcon(ICON_DESIBLE));
        }
      };

      //about
      ActionListener settingsListener = new ActionListener() {
        @Override
        public void actionPerformed(java.awt.event.ActionEvent e) {
          Platform.runLater(new Runnable() {

              @Override
              public void run() {
                  stage.show();
              }
          });
        }
      };

      //close
      ActionListener closeListener = new ActionListener() {
        @Override
        public void actionPerformed(java.awt.event.ActionEvent e) {
          System.exit(0);
        }
      };




      // add listener's
      showItem.addActionListener(showListener);
      startItem.addActionListener(startListener);
      stopItem.addActionListener(stopListener);
      settingsItem.addActionListener(settingsListener);
      closeItem.addActionListener(closeListener);

      popup.add(showItem);
      popup.add(startItem);
      popup.add(stopItem);
      popup.add(settingsItem);
      popup.add(closeItem);
      trayIcon = new TrayIcon(getIcon(ICON), "Screen shot edit", popup);
      trayIcon.addActionListener(settingsListener);
      try {
        tray.add(trayIcon);
      } catch (AWTException e) {
        logger.error("ERROR", e);
      }
    }
  }

  private void hide(final Stage stage) {
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        if (SystemTray.isSupported()) {
          stage.hide();
        } else {
          System.exit(0);
        }
      }
    });
  }

  /**
   * Get icon for Systemtray
   * @return
   */
  private Image getIcon(String fileName) {
    Image image = null;
    try {

      logger.info("path :"+getClass().getClassLoader().getResource("/"));
      File img = new File(getFileUrl(fileName).getFile());
      image = ImageIO.read(img);
    } catch (Exception ex) {
     logger.error("ERROR",ex);
    }
    return image;
  }

    /**
     * URL file
      * @param fileName
     * @return
     */
  private URL getFileUrl(String fileName){
      return getClass().getClassLoader().getResource(fileName);
  }

  public static ResourceBundle getResourceBundle(){
    return rb;
  }
}
