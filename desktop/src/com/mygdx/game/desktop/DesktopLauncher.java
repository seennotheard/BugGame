package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.BugGameClient;
import com.mygdx.game.Drop;
import com.mygdx.game.TopDownScreen;
import com.mygdx.game.Drop;
import java.awt.Toolkit;
public class DesktopLauncher {

   public static void main (String[] arg) {
      LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
      config.title = "BugGame";
      config.width = 800;
      config.height = 480;
      new LwjglApplication(new BugGameClient(), config);
   }
}