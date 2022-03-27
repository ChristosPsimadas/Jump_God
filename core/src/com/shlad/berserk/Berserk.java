package com.shlad.berserk;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.shlad.berserk.Screens.PlayScreen;

public class Berserk extends Game
{
	public static final int V_WIDTH = 400;
	public static final int V_HEIGHT = 208;
	//pixels per meter
	public static final float PPM = 100;
	
	public SpriteBatch batch;
	
	
	@Override
	public void create ()
	{
		batch = new SpriteBatch();
		setScreen(new PlayScreen(this));
	}

	@Override
	public void render ()
	{
		//Delegates render to whichever screen is currently up
		super.render();
	}
	
	@Override
	public void dispose ()
	{
		batch.dispose();
	}
	
	public String toString()
	{
		return "Theres not much to say about this, it's a game class";
	}
	
	public boolean equals(Object other)
	{
		if (other != null)
		{
			return false;
		}
		return false;
		//No game classes are equal to each other. Not even itself.
	}
}
