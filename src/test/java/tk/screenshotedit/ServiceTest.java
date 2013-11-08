package tk.screenshotedit;

import junit.framework.Assert;
import org.junit.Test;
import tk.screenshotedit.Service;
import tk.screenshotedit.TimeStampStr;

import java.io.File;

/**
 * User: mavolkov
 * Date: 17.10.13
 */
public class ServiceTest {
  @Test
  public void createScreenShotTest() throws InterruptedException{
    Service service = new Service();
    Assert.assertTrue(service.createScreen());
    String path = System.getProperty("java.io.tmpdir") + TimeStampStr.getInstance().getLastValue() + ".png";
    File screen = new File(path);
    Assert.assertTrue(screen.exists());
    Thread.sleep(1000);
  }

  @Test
  public void createOnlyOneScreenPerSecond(){
    Service service = new Service();
    Assert.assertTrue(service.createScreen());
    Assert.assertFalse(service.createScreen());
  }
}
