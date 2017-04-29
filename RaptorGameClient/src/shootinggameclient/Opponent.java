package shootinggameclient;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Opponent{

   
     private int x;
       private int y;
       private boolean opponent_fire;
      
       private long current ;
       private long delay;
      
    public Opponent(int x,int y){
       this.x = x;
       this.y = y;
    }
    public void init(){
    current = System.nanoTime();
    delay = 100;
    }
        
    public void tick(int x_, int y_, int fire){
        //Set coordinate to the values recived from server to show 
        x=x_;
        y=y_; 
        
        if(fire==1)
        {
             long breaks = (System.nanoTime()-current)/1000000;
           if(breaks > delay){
          gameClientManager.op_bullet.add(new Opponent_Bullet(x-10,y+70));//create bullet in front of server opponent
                }
        current = System.nanoTime();
            }
        }
   
    public void render(Graphics g){ 
        g.drawImage(loadImage.opponent,x,y, 200, 140,null);
    }
    
     //return y coordinate
    public int getY(){
       return y;
    }
    //return y coordinate
    public int getX(){
       return x;
    }

}
