package andre.rodrigues.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import andre.rodrigues.Main;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title ="Master Coder";
		config.width = 1280;
		config.height = 720;
		
		new LwjglApplication(new Main(), config);
	}
}