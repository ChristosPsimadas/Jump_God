package com.shlad.berserk.Tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.shlad.berserk.Berserk;
import com.shlad.berserk.Sprites.Brick;
import com.shlad.berserk.Sprites.Coin;

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
    
        //2 is wall
        //3 is Floor
        //4 is ceiling
        
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
    
        //There must be a corresponding fixture and body for every time in the layers
        //Eg the pipes, coins and ground
        //get the layers from the tiled map, get the second index (ground), get the objects in there,
        //then get them by type which is all just rectangles.
        //MapObject because there may be multiple types of objects
    
        //2 IS GROUND
        //3 IS PIPES
        //4 IS BRICKS
        //5 IS COINS
//        for (MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class))
//        {
//            Rectangle rect = ((RectangleMapObject) object).getRectangle();
//            bDef.type = BodyDef.BodyType.StaticBody;
//            bDef.position.set((rect.getX() + rect.getWidth() / 2) / Berserk.PPM,
//                              (rect.getY() + rect.getHeight() / 2) / Berserk.PPM);
//
//            body = world.createBody(bDef);
//
//            shape.setAsBox((rect.getWidth() / 2) / Berserk.PPM,
//                           (rect.getHeight() / 2) / Berserk.PPM);
//            fdef.shape = shape;
//            fdef.friction = 1f;
//            body.createFixture(fdef);
//        }
//
//        for (MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class))
//        {
//            Rectangle rect = ((RectangleMapObject) object).getRectangle();
//            bDef.type = BodyDef.BodyType.StaticBody;
//            bDef.position.set((rect.getX() + rect.getWidth() / 2) / Berserk.PPM,
//                              (rect.getY() + rect.getHeight() / 2) / Berserk.PPM);
//
//            body = world.createBody(bDef);
//
//            shape.setAsBox((rect.getWidth() / 2) / Berserk.PPM ,
//                           (rect.getHeight() / 2) / Berserk.PPM);
//            fdef.shape = shape;
//            fdef.restitution = 0.8f;
//            body.createFixture(fdef);
//        }
//
//        for (MapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class))
//        {
//            Rectangle rect = ((RectangleMapObject) object).getRectangle();
//
//            new Brick(world, map, rect);
//        }
//
//        for (MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class))
//        {
//            Rectangle rect = ((RectangleMapObject) object).getRectangle();
//
//            new Coin(world, map, rect);
//        }
        
        
        
    }
}
