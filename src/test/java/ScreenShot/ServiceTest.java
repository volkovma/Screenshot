package ScreenShot;

import junit.framework.Assert;
import org.junit.Test;

import java.io.File;

/**
 * User: mavolkov
 * Date: 17.10.13
 */
public class ServiceTest {
  @Test
  public void createScreenShotTest(){
    Service service = new Service();
    service.createScreen();
    String path = System.getProperty("java.io.tmpdir") + TimeStampStr.getInstance().getLastValue() + ".png";
    File screen = new File(path);
    Assert.assertTrue(screen.exists());
  }
}
