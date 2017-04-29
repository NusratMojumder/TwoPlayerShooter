/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shootinggameserver;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Player implements KeyListener{

   
     private int x;
       private int y;
       private boolean up,down,left,right;
       private int fire;
      
       private long current ;
       private long delay;
      
    public Player(int x,int y){
       this.x = x;
        this.y = y;
    }
    public void init(){
    Display.frame.addKeyListener(this);
    current = System.nanoTime();
    delay = 100;
    }
    
    public void tick(){
        
           if(up){
     
           if(y>=0){
           y -= 5;
       }
        }
           
       if(down){
           if(y<= 750-160){
           y += 5;
           }
       }

        if(left){
     
           if(x>=0){
           x -= 5;
            }
        }
        
        if(right){
     
           if(x<=800-205){
           x += 5;
            }
        }
      
       if(fire==1){
           long breaks = (System.nanoTime()-current)/1000000;
           if(breaks > delay){
          gameServerManager.bullet.add(new Bullet(x-10,y+70));//add bullrt
                }
        current = System.nanoTime();
            }  
    }
   
    public void render(Graphics g){ 
        g.drawImage(loadImage.player,x,y, 200, 140,null);
    }
    
    public void keyPressed(KeyEvent e) {
       int source = e.getKeyCode();
       if(source == KeyEvent.VK_UP){
           up = true;
       }
       if(source == KeyEvent.VK_DOWN){
           down = true;
       }

       if(source == KeyEvent.VK_LEFT){
           left = true;
       }
       if(source == KeyEvent.VK_RIGHT){
           right = true;
          
       }

        if(source == KeyEvent.VK_SPACE){
           fire = 1;
        }
    }
     
    public void keyReleased(KeyEvent e) {
       int source = e.getKeyCode();
       if(source == KeyEvent.VK_UP){
           up = false;
       }
       if(source == KeyEvent.VK_DOWN){
           down = false;
          
       }
          if(source == KeyEvent.VK_LEFT){
           left = false;
       }
       if(source == KeyEvent.VK_RIGHT){
           right = false;
          
       }
       if(source == KeyEvent.VK_SPACE){
           fire = 0;
       }
    }
     
    public void keyTyped(KeyEvent e) {
      
    }
  //return y coordinate
    public int getY(){
       return y;
    }
    //return y coordinate
    public int getX(){
       return x;
    }
    //return if shot
       public int getFire(){
       return fire;
    }
}
