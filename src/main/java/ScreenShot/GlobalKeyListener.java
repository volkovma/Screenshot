package ScreenShot;

import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User: mavolkov
 * Date: 17.10.13
 */
public class GlobalKeyListener implements NativeKeyListener {
  private Logger logger = LoggerFactory.getLogger(GlobalKeyListener.class);
  private Service service;

  public GlobalKeyListener(Service service) {
    this.service = service;
  }

  @Override
  public void nativeKeyPressed(NativeKeyEvent nativeKeyEvent) {
    logger.debug("Press key:"+ nativeKeyEvent.getKeyCode());
    if (nativeKeyEvent.getKeyCode() == NativeKeyEvent.VK_PRINTSCREEN) {
      if(service.createScreen()){
        service.openPaint();
      }
    }
  }

  @Override
  public void nativeKeyReleased(NativeKeyEvent nativeKeyEvent) {
  }

  @Override
  public void nativeKeyTyped(NativeKeyEvent nativeKeyEvent) {
  }
}
