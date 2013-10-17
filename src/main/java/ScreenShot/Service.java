package ScreenShot;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


/**
 * User: mavolkov
 * Date: 17.10.13
 */
public class Service {
  private Logger logger = LoggerFactory.getLogger(Service.class);

  /**
   * create screen shot
   */
  public void createScreen() {
    Dimension d = new Dimension(Toolkit.getDefaultToolkit().getScreenSize());
    int width = (int) d.getWidth();
    int height = (int) d.getHeight();
    try {
      Robot robot = new Robot();
      Image capturedScreen = robot.createScreenCapture(new Rectangle(0, 0, width, height));
      BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
      Graphics g = bi.createGraphics();
      g.drawImage(capturedScreen, 0, 0, width, height, null);
      String fileNameToSaveTo = getPath(TimeStampStr.getInstance().getCurValue());
      writeImage(bi, fileNameToSaveTo, "png");
      logger.info("Screen shot saved to " + fileNameToSaveTo);
    } catch (Exception e) {
      logger.error("ERROR", e);
    }
  }


  private void writeImage(BufferedImage img, String fileLocation, String extension) {
    try {
      File outputfile = new File(fileLocation);
      ImageIO.write(img, extension, outputfile);
    } catch (IOException e) {
      logger.error("ERROR", e);
    }
  }

  /**
   * Open last screen shot
   */
  public void openPaint() {
    try {
      Runtime rt = Runtime.getRuntime();
      String command = "mspaint \"" + getPath(TimeStampStr.getInstance().getLastValue()) + "\"";
      logger.info(command);
      rt.exec(command);
    } catch (IOException e) {
      logger.error("ERROR", e);
    }
  }

  /**
   * enable global hotkey
   */
  public void enableHotKey() {
    try {
      GlobalScreen.registerNativeHook();
      GlobalScreen.getInstance().addNativeKeyListener(new GlobalKeyListener(this));
      logger.info("Enable global hot key");
    } catch (NativeHookException e) {
      logger.error("ERROR", e);
    }
  }

  /**
   * enable global hotkey
   */
  public void disableHotKey() {
    GlobalScreen.unregisterNativeHook();
    logger.info("Disable global hot key");
  }

  private String getPath(String timeStampStr) {
    return System.getProperty("java.io.tmpdir") + "\\" + timeStampStr + ".png";
  }
}
