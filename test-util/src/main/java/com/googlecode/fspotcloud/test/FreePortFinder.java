package com.googlecode.fspotcloud.test;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * @author zorzella
 */
public class FreePortFinder {

  public static final int findFreePort() {
    for(int i = 8000; i < 8100; i++) {
      ServerSocket socket;
      try {
        socket = new ServerSocket(i);
        socket.close();
        return i;
      } catch (IOException portInUse) {
          break;
      }
    }
    throw new RuntimeException("Can't find a free port");
  }

}
