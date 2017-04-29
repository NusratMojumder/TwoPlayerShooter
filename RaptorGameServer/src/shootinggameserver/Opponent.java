package shootinggameserver;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Opponent{

   
     private int x;
       private int y;
       private boolean up,down,left,right;
       private boolean fire;
      
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
        //set coordiates using vlue of client coordinate
        x=x_;
        y=y_;
     
         if(fire==1){
           long breaks = (System.nanoTime()-current)/1000000;
           if(breaks > delay){
          gameServerManager.op_bullet.add(new Opponent_Bullet(x+200,y+70));//add bullet for client player
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
