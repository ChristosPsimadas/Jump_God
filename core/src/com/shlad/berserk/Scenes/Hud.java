package com.shlad.berserk.Scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.shlad.berserk.Berserk;

public class Hud implements Disposable
{
    public Stage stage;
    //new viewport because I want the HUD to stay on the screen, while the actual world itself changes
    //So it'd be on a new viewport
    private Viewport viewport;
    
    private Integer worldTimer;
    private float timeCount;
    private Integer score;
    
    Label countDownLabel;
    Label scoreLabel;
    Label timeLabel;
    Label levelLabel;
    Label worldLabel;
    Label characterLabel;
    
    public Hud(SpriteBatch sb)
    {
        worldTimer = 300;
        timeCount = 0;
        score = 0;
        
        viewport = new FitViewport(Berserk.V_WIDTH, Berserk.V_HEIGHT, new OrthographicCamera());
        //Stage is like an empty box and needs organization like a table
        stage = new Stage(viewport, sb);
        
        //Table is the way to organize stage
        Table table = new Table();
        table.top();
        //Table is size of stage.
        table.setFillParent(true);
        
        //Format the string to a string      03 is the amount of digits
        countDownLabel = new Label(String.format("%03d", worldTimer),
                                   new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        
        scoreLabel = new Label(String.format("%06d", score),
                               new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        
        timeLabel = new Label("TIME", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        levelLabel = new Label("1-1", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        worldLabel = new Label("WORLD", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        characterLabel = new Label("GUTS", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        
        table.add(characterLabel).expandX().padTop(10);
        table.add(worldLabel).expandX().padTop(10);
        table.add(timeLabel).expandX().padTop(10);
        
        table.row();
        table.add(scoreLabel).expandX();
        table.add(levelLabel).expandX();
        table.add(countDownLabel).expandX();
        
        stage.addActor(table);
    }
    
    
    @Override
    public void dispose()
    {
        stage.dispose();
    }
}
