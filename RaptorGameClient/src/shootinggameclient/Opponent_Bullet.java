package shootinggameclient;

import java.awt.Color;
import java.awt.Graphics;

public class Opponent_Bullet {

    private int x;
    private int y;
    private int speed;
   
    public Opponent_Bullet(int x, int y)
    {
       this.x = x;
       this.y = y;
       speed = 10;
      
    }
    
     public void tick()
    {    
     x -= speed;  //opponent bullet goes opposite direction
    }
    
    public int getY(){
       return y;
    }
    
    public int getX(){
       return x;
    }
   
    public void render(Graphics g){
      g.drawImage(loadImage.bullet,x,y, 20, 20,null);
    }
}