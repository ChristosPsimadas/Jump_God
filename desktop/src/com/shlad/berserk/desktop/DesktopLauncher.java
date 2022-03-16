package com.shlad.berserk.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.shlad.berserk.Berserk;

public class DesktopLauncher
{
	public static void main (String[] arg)
	{
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		
		config.height = 416;
		config.width = 800;
		
		new LwjglApplication(new Berserk(), config);
	}
}
