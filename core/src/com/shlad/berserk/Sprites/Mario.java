package com.shlad.berserk.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.shlad.berserk.Berserk;
import com.shlad.berserk.Screens.PlayScreen;
import com.sun.jndi.ldap.Ber;

public class Mario extends Sprite
{

    public enum AnimationState {FALLING, JUMPING, STANDING, RUNNING, CHARGING, DOWNED};
    public AnimationState currentState;
    public AnimationState previousState;
    
    public World world;
    public Body b2body;
    private TextureRegion marioStand;
    private TextureRegion marioJump;
    private TextureRegion marioBouncing;
    private TextureRegion marioFall;
    private TextureRegion marioDowned;
    
    private Animation<TextureRegion> marioCharging;
    private Animation<TextureRegion> marioRun;
    private float stateTimer;
    private boolean runningRight;
    private boolean jumping;
    
    public Mario(World world, PlayScreen screen)
    {
        //on the spritemap mario is called little mario
        super(screen.getAtlas().findRegion("jumpking"));
        
        this.world = world;
        
        currentState = AnimationState.STANDING;
        previousState = AnimationState.STANDING;
        stateTimer = 0;
        runningRight = true;
        
        Array<TextureRegion> frames = new Array<TextureRegion>();
        for(int i = 1; i < 4; i++) {frames.add(new TextureRegion(getTexture(), 1 + i * 32, 1, 32, 32));}
        marioRun = new Animation<TextureRegion>(0.1f, frames);
        frames.clear();
    
        for(int i = 1; i < 4; i++) {frames.add(new TextureRegion(getTexture(), 129, 1, 32, 32));}
        marioCharging = new Animation<TextureRegion>(0.1f, frames);
        frames.clear();
        
        marioFall =     new TextureRegion(getTexture(), 193, 1, 32, 32);
        
        marioJump =     new TextureRegion(getTexture(), 161, 1, 32, 32);
        
        marioBouncing = new TextureRegion(getTexture(), 256, 1, 32, 32);
    
        marioStand =    new TextureRegion(getTexture(), 1  , 1, 32, 32);
        
        marioDowned =   new TextureRegion(getTexture(), 225, 1, 32, 32);
        
        defineMario();
        
        setBounds(0, 0, 32 / Berserk.PPM, 32 / Berserk.PPM);
        setRegion(marioStand);
        //mario extends sprite which extends Texture region, so it fulfills the req because it takes a textureregion
    }
    
    public void update(float dt)
    {
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2 + 3/ Berserk.PPM);
        setRegion(getFrame(dt));
    }
    
    public TextureRegion getFrame(float dt)
    {
        currentState = getAnimationState();
        
        TextureRegion region;
        switch (currentState)
        {
            case JUMPING:
                //statetimer determines which of the frames that the animation is currently in
                region = marioJump;
                break;
                
            case RUNNING:
                region = marioRun.getKeyFrame(stateTimer, true);
                break;
                
            case FALLING:
                region = marioFall;
                break;
                
            case DOWNED:
                region = marioDowned;
                break;
                
            case CHARGING:
                region = marioCharging.getKeyFrame(stateTimer, true);
                break;
                
            case STANDING:
            default:
                region = marioStand;
                break;
        }
        //region.isFlipx() returns if the actual sprite is flipped
        if((b2body.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX())
        {
            region.flip(true, false);
            runningRight = false;
            
        }
        //Hes facing left but running right
        else if ((b2body.getLinearVelocity().x > 0 || runningRight) && region.isFlipX())
        {
            region.flip(true, false);
            runningRight = true;
        }
        //does current state equal previous state? if it does statetimer + dt, if not statetimer = 0
        //resets timer at animation switch
        stateTimer = currentState == previousState ? stateTimer + dt : 0;
        previousState = currentState;
        return region;
    }
    
    public AnimationState getAnimationState()
    {
        if (b2body.getLinearVelocity().y > 0)
            return AnimationState.JUMPING;
        
        else if (b2body.getLinearVelocity().y < 0)
            return AnimationState.FALLING;
        
        else if (b2body.getLinearVelocity().x != 0)
            return AnimationState.RUNNING;
        
        else if (Gdx.input.isKeyPressed(Input.Keys.W))
            return AnimationState.CHARGING;
        
        else if (b2body.getLinearVelocity().y == 0 && previousState == AnimationState.JUMPING)
            return AnimationState.DOWNED;
        
        else
            return AnimationState.STANDING;
    }
    
    public void defineMario()
    {
        BodyDef bdef = new BodyDef();
        bdef.position.set(200 / Berserk.PPM, 40 / Berserk.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        
        b2body = world.createBody(bdef);
        
        
        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(12 / Berserk.PPM);
        fdef.friction = 1f;

        fdef.shape = shape;
        b2body.createFixture(fdef);
        
    }
    
    public boolean isJumping()
    {
        return jumping;
    }
    
    public void setJumping(boolean jumping)
    {
        this.jumping = jumping;
    }
}
