package com.shlad.berserk.Sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.shlad.berserk.Berserk;
import com.shlad.berserk.Screens.PlayScreen;

public class Mario extends Sprite
{

    public enum AnimationState {FALLING, JUMPING, STANDING, RUNNING};
    public AnimationState currentState;
    public AnimationState previousState;
    
    public World world;
    public Body b2body;
    private TextureRegion marioStand;
    
    private Animation<TextureRegion> marioRun;
    private Animation<TextureRegion> marioJump;
    private float stateTimer;
    private boolean runningRight;
    
    public Mario(World world, PlayScreen screen)
    {
        //on the spritemap mario is called little mario
        super(screen.getAtlas().findRegion("little_mario"));
        
        this.world = world;
        
        currentState = AnimationState.STANDING;
        previousState = AnimationState.STANDING;
        stateTimer = 0;
        runningRight = true;
        
        Array<TextureRegion> frames = new Array<TextureRegion>();
        for(int i = 1; i < 4; i++) {frames.add(new TextureRegion(getTexture(), 1 + i * 16, 1, 16, 16));}
        marioRun = new Animation<TextureRegion>(0.1f, frames);
        frames.clear();
        
        for(int i = 4; i < 6; i++) {frames.add(new TextureRegion(getTexture(), 1 + i * 16, 1, 16, 16));}
        marioJump = new Animation<TextureRegion>(0.1f, frames);
        frames.clear();
    
        marioStand = new TextureRegion(getTexture(), 1, 1, 16, 16);
        
        defineMario();
        
        setBounds(0, 0, 16 / Berserk.PPM, 16 / Berserk.PPM);
        setRegion(marioStand);
        //mario extends sprite which extends Texture region, so it fulfills the req because it takes a textureregion
    }
    
    public void update(float dt)
    {
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
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
                region = marioJump.getKeyFrame(stateTimer);
                break;
                
            case RUNNING:
                region = marioRun.getKeyFrame(stateTimer, true);
                break;
                
            case FALLING:
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
        //does current state equal previous state? if it does statetimer += dt, if not statetimer = 0
        //resets timer at animation switch
        stateTimer = currentState == previousState ? stateTimer + dt : 0;
        previousState = currentState;
        return region;
    }
    
    public AnimationState getAnimationState()
    {
        if (b2body.getLinearVelocity().y > 0 || (b2body.getLinearVelocity().y < 0 && previousState == AnimationState.JUMPING))
            return AnimationState.JUMPING;
        else if (b2body.getLinearVelocity().y < 0)
            return AnimationState.FALLING;
        else if (b2body.getLinearVelocity().x != 0)
            return AnimationState.RUNNING;
        else
            return AnimationState.STANDING;
    }
    
    public void defineMario()
    {
        BodyDef bdef = new BodyDef();
        bdef.position.set(32 / Berserk.PPM, 32 / Berserk.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        
        b2body = world.createBody(bdef);
        
        
        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6 / Berserk.PPM);

        fdef.shape = shape;
        b2body.createFixture(fdef);
        
        
    }
}
