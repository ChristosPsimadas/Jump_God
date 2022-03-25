package com.shlad.berserk.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.shlad.berserk.Berserk;
import com.shlad.berserk.Sprites.JumpGod;
import com.shlad.berserk.Tools.B2WorldCreator;

public class PlayScreen implements Screen
{
    private final Berserk game;
    private final TextureAtlas atlas;
    
    private final OrthographicCamera gameCam;
    private final Viewport gamePort;
    
    //Tiled map stuff
    private final TmxMapLoader mapLoader;
    private final TiledMap map2;
    private final OrthogonalTiledMapRenderer renderer;
    
    //Box2d stuff
    private final World world;
    //shows us fixtures and rigid bodies, so we see what's going on
    private final Box2DDebugRenderer b2dr;
    
    private final JumpGod player;

    private float xImpulseJump;
    private float yImpulseJump;
    
    private final float maxXJumpvelocity = 7f;
    private final float maxYJumpvelocity = 4.8f;
    
    private String previousKeyState = "released";

    private final Music backgroundMusic;
    private final Texture backgroundTexture;
    
    public PlayScreen(Berserk game)
    {
        atlas = new TextureAtlas("jumpking.pack");
        
        
        this.game = game;
        gameCam = new OrthographicCamera();
        gamePort = new FitViewport(Berserk.V_WIDTH / Berserk.PPM, Berserk.V_HEIGHT / Berserk.PPM, gameCam);
        mapLoader = new TmxMapLoader();
        map2 = mapLoader.load("mapWIP.tmx");
        renderer = new OrthogonalTiledMapRenderer(map2, 1 / Berserk.PPM);
        
        backgroundTexture = new Texture("stringstar fields/background_0.png");
        backgroundTexture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        
        //Set gamecam to be centered at the start of the map
        //still should use this even tho it's centered on jumpKing because it centers the y height
        gameCam.position.set(gamePort.getWorldWidth() / 2.0f, gamePort.getWorldHeight() / 2.0f, 0);
        
        //Gravity , sleep objects at rest
        world = new World(new Vector2(0, -10), true);
        b2dr = new Box2DDebugRenderer();
        
        new B2WorldCreator(world, map2);
    
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("backgroundSound.mp3"));
        backgroundMusic.setLooping(true);
        backgroundMusic.play();
        
        player = new JumpGod(world, this);
        
    }
    
    public TextureAtlas getAtlas()
    {
        return atlas;
    }
    
    @Override
    public void show()
    {
    
    }
    
    public void handleInput(float deltaTime)
    {
        //No moving in the air

        if (Gdx.input.isKeyPressed(Input.Keys.D) && (player.b2body.getLinearVelocity().x <= 1.8f)
                && (!player.isJumping()) && (player.b2body.getLinearVelocity().y == 0))
        {
            //Move a set speed, no acceleration
            player.b2body.setLinearVelocity(new Vector2(1.1f, 0));
        }
    
    
        if (Gdx.input.isKeyPressed(Input.Keys.A) && (player.b2body.getLinearVelocity().x >= -1.8f)
                && (!player.isJumping()) && (player.b2body.getLinearVelocity().y == 0))
        {
            //move a set speed, no acceleration
            player.b2body.setLinearVelocity(new Vector2(-1.1f, 0));
        }
        
        if (Gdx.input.isKeyPressed(Input.Keys.W) && (player.b2body.getLinearVelocity().y == 0))
        {
            //Make the setJumping boolean true
            //So that you can't move left or right when charging your jump
            player.setJumping(true);
            
            //Make your character stop completely in place when charging a jump
            player.b2body.setLinearVelocity(0,0);
            
            //This is what charges the jump
            yImpulseJump += 8.9 * deltaTime;
            
            //Change previous state to held, to know when a key is released
            previousKeyState = "held";
            
            //Make the max jumping power 5.4
            if (yImpulseJump > maxYJumpvelocity) {yImpulseJump = maxYJumpvelocity;}
            
            //Make it such that you can charge left and right as well
            if (Gdx.input.isKeyPressed(Input.Keys.D))
            {
                xImpulseJump += 5.2 * deltaTime;
                if (xImpulseJump > maxXJumpvelocity) {xImpulseJump = maxXJumpvelocity;}
            }
    
            if (Gdx.input.isKeyPressed(Input.Keys.A))
            {
                xImpulseJump -= 5.2 * deltaTime;
                if (xImpulseJump < -maxXJumpvelocity) {xImpulseJump = -maxXJumpvelocity;}
            }
        }
        else if(previousKeyState.equals("held"))
        {
            previousKeyState = "released";
            player.b2body.applyLinearImpulse(new Vector2(xImpulseJump, yImpulseJump), player.b2body.getWorldCenter(), true);
            xImpulseJump = 0;
            yImpulseJump = 0;
            player.setJumping(false);
        }

    }
    
    //update the game world
    public void update(float deltaTime)
    {
        player.update(deltaTime);
        //user input first
        handleInput(deltaTime);
        
        
        //Makes it such that the camera follows the player, but only in multiple-of-4 increments
        //For example, if jumpKing is at position 3, then the remainder is 3. The game cam gets set to 2 + 3 - 3, because
        //jumpKing has not left the screen yet since leaving the screen means passing the 4th mark.
        gameCam.position.x = 2 + (int) player.b2body.getPosition().x - (int) player.b2body.getPosition().x % 4;
        //gameCam.position.y = (int) player.b2body.getPosition().y - ((((int) player.b2body.getPosition().y * 100) % 208) / 100);
        
        //Since the height of one screen is 2.08, and we cannot modulo a float, we convert it to an integer 208 by multiplying everything by 100
        //Then do all modulo divisions, and then divide by 100 at the end
        gameCam.position.y = 1.04f + (((player.b2body.getPosition().y * 100) - (player.b2body.getPosition().y * 100 % 208)) / 100);
        
        //60 fps, how many times to calculate velocity and position
        //higher num = more precise but slow
        world.step(1/60f, 6, 2);
        
        gameCam.update();
        renderer.setView(gameCam);
    }
    
    @Override
    public void render(float delta)
    {
        //separate update logic from render
        update(delta);
        
        //Clear the screen and make it light blue
        Gdx.gl.glClearColor(0.35f, 0.35f, 0.8f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    
        game.batch.setProjectionMatrix(gameCam.combined);
        game.batch.begin();
        game.batch.draw(backgroundTexture, 0, gameCam.position.y - 1.04f, gameCam.viewportWidth, gameCam.viewportHeight);
        game.batch.end();
        //render the game map
        renderer.render();
        
        //render the physics lines
        //b2dr.render(world, gameCam.combined);
        
        
        game.batch.begin();
        player.draw(game.batch);
        game.batch.end();

    }
    
    @Override
    public void resize(int width, int height)
    {
        gamePort.update(width, height);
    }
    
    @Override
    public void pause()
    {
    
    }
    
    @Override
    public void resume()
    {
    
    }
    
    @Override
    public void hide()
    {
    
    }
    
    @Override
    public void dispose()
    {
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
        backgroundMusic.dispose();
        backgroundTexture.dispose();
    }
}
