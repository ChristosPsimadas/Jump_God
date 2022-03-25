package com.shlad.berserk.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.shlad.berserk.Berserk;

public class DesktopLauncher
{
	public static void main (String[] arg)
	{
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		//Aspect ratio -> 25x13
		config.height = 585;
		config.width = 1125;
		config.title = "Jump God";
		
		new LwjglApplication(new Berserk(), config);
	}
}
