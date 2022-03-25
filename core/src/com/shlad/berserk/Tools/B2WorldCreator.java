package com.shlad.berserk.Tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.shlad.berserk.Berserk;

public class B2WorldCreator
{
    public B2WorldCreator(World world, TiledMap map)
    {
        //Defining the attributes of a body
        BodyDef bDef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;
        
        fdef.restitution = 0f;
    
        //3 is wall
        //4 is Floor
        for (MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class))
        {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            bDef.type = BodyDef.BodyType.StaticBody;
            bDef.position.set((rect.getX() + rect.getWidth() / 2) / Berserk.PPM,
                              (rect.getY() + rect.getHeight() / 2) / Berserk.PPM);
            
            body = world.createBody(bDef);

            shape.setAsBox((rect.getWidth() / 2) / Berserk.PPM,
                           (rect.getHeight() / 2) / Berserk.PPM);
            fdef.shape = shape;
            fdef.restitution = 0.5f;
            body.createFixture(fdef);
        }
    
        for (MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class))
        {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            bDef.type = BodyDef.BodyType.StaticBody;
            bDef.position.set((rect.getX() + rect.getWidth() / 2) / Berserk.PPM,
                              (rect.getY() + rect.getHeight() / 2) / Berserk.PPM);
        
            body = world.createBody(bDef);
        
            shape.setAsBox((rect.getWidth() / 2) / Berserk.PPM,
                           (rect.getHeight() / 2) / Berserk.PPM);
            fdef.shape = shape;
            fdef.restitution = 0.3f;
            fdef.friction = 1f;
            
            body.createFixture(fdef);
        }
    }
}
