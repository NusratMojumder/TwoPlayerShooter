/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shootinggameclient;

import java.awt.Color;
import java.awt.Graphics;

public class Bullet {

    private int x;
    private int y;
    private int speed;
   
    public Bullet(int x, int y)
    {
       this.x = x;
       this.y = y;
       speed = 10;
      
    }
    public void tick()
    {
     x += speed;  //Bullet goes forward
    }
    
    //return y coordinate
    public int getY(){
       return y;
    }
    //return y coordinate
    public int getX(){
       return x;
    }
   
    public void render(Graphics g){
      g.drawImage(loadImage.bullet,x,y, 20, 20,null);
    }
}