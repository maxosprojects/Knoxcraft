/**
 * Sample program testing setPos and setDir in Turtle3D.  
 * 
 * TODO: test setDir when decided on argument type
 */

package edu.knoxcraft.turtle3d;

import net.canarymod.api.world.position.Direction;

public class Turtle3DTest5
{
    public static void main(String[] args) {
        Turtle3D t=Turtle3D.createTurtle("PosSetter");

        //setPos
        t.forward(10);

        int[] pos = {0, 0, 3};
        t.setPosition(pos);
        t.forward(10);

        int[] pos2 = {-1, 1, -1};
        t.setPosition(pos2);
        t.forward(10);

        //setDir   
        t.setDirection(Direction.NORTH);
        t.forward(10);
        t.setDirection(Direction.EAST);
        t.forward(10);
        t.setDirection(Direction.SOUTH);
        t.forward(10);

        System.out.println(t.getScript().toJSONString());
    }
}