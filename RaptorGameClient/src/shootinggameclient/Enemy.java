/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shootinggameclient;
import java.awt.Color;
import java.awt.Graphics;

public class Enemy {
    private int x ;
    private int y;
   
    public Enemy(int x, int y){
       this.x = x;
       this.y = y;
    }
   
    public void tick(){
    y+=5; //Enemy comes down every fps
    }
    public void render(Graphics g){
     g.drawImage(loadImage.enemy,x,y, 120, 120,null);
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
