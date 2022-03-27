package com.shlad.berserk.Tools;


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
import com.shlad.berserk.Screens.PlayScreen;


public class Timer implements Disposable
{

    public Stage stage;
    private final Viewport viewport;
    
    private Integer time;
    float timeCheckNum;
    
    private Label countDownLabel;
    
    public Timer(SpriteBatch sb)
    {
        time = 0;
        timeCheckNum = 0;
        viewport = new FitViewport(Berserk.V_WIDTH, Berserk.V_HEIGHT, new OrthographicCamera());
        
        stage = new Stage(viewport, sb);
    
        Table table = new Table();
        table.top();
        
        table.setFillParent(true);
        
        countDownLabel = new Label(String.format("%03d", time), new Label.LabelStyle(new BitmapFont(), Color.GRAY));
        
        table.add(countDownLabel).padTop(10).padRight(350);
        
        stage.addActor(table);
    }
    
    public void setTime(float dt)
    {
        timeCheckNum += dt;
        if (timeCheckNum >= 1f && !PlayScreen.won)
        {
            time++;
            timeCheckNum = 0;
        }
        countDownLabel.setText(String.format("%03d", time));
    }
    
    public Integer getTime()
    {
        return time;
    }
    
    @Override
    public void dispose()
    {
        stage.dispose();
    }
}
