package com.shlad.berserk.Sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import com.shlad.berserk.Berserk;
import com.shlad.berserk.Screens.PlayScreen;

public class Mario extends Sprite
{
    public World world;
    public Body b2body;
    private TextureRegion marioStand;
    
    public Mario(World world, PlayScreen screen)
    {
        //on the spritemap mario is called little mario
        super(screen.getAtlas().findRegion("little_mario"));
        
        this.world = world;
        defineMario();
        
        marioStand = new TextureRegion(getTexture(), 1, 1, 16, 16);
        setBounds(0, 0, 16 / Berserk.PPM, 16 / Berserk.PPM);
        setRegion(marioStand);
        //mario extends sprite which extends Textureregion so it fulfills the req because it takes a textureregion
    }
    
    public void update(float dt)
    {
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
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
