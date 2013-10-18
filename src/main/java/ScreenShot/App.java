package ScreenShot;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * User: mavolkov
 * Date: 16.10.13
 */
public class App extends Application {
  private final String ICON = "/resource/icon.png";
  private final String ICON_DESIBLE = "/resource/icon_d.png";
  private Logger logger = LoggerFactory.getLogger(App.class);
  private TrayIcon trayIcon;
  private Service service;

  public static void main(String[] args) {
    Application.launch(args);
  }

  @Override
  public void start(Stage stage) throws Exception {
    service = new Service();
    createTrayIcon(stage);
    service.enableHotKey();
    Platform.setImplicitExit(false);
    Scene scene = new Scene(new Group(), 800, 600);
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
      final MenuItem showItem = new MenuItem("Show last");
      final MenuItem startItem = new MenuItem("Start");
      final MenuItem stopItem = new MenuItem("Stop");
      final MenuItem aboutItem = new MenuItem("About");
      final MenuItem closeItem = new MenuItem("Close");

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
      ActionListener aboutListener = new ActionListener() {
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
      aboutItem.addActionListener(aboutListener);
      closeItem.addActionListener(closeListener);

      popup.add(showItem);
      popup.add(startItem);
      popup.add(stopItem);
      popup.add(aboutItem);
      popup.add(closeItem);
      trayIcon = new TrayIcon(getIcon(ICON), "Screen shot edit", popup);
      trayIcon.addActionListener(showListener);
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
      URL url = getClass().getResource(fileName);
      logger.info("path :"+getClass().getResource(""));
      File img = new File(url.getFile());
      image = ImageIO.read(img);
    } catch (Exception ex) {
     logger.error("ERROR",ex);
    }
    return image;
  }
}
