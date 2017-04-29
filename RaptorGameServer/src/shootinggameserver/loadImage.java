/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shootinggameserver;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class loadImage {

    public static BufferedImage background;
    public static BufferedImage player, enemy, bullet, score_panel,opponent;
   
    public static void init()
    {
    background = imageLoader("/res/background_1.png");
    player = imageLoader("/res/player.png");  
    opponent = imageLoader("/res/opponent.png");
    enemy = imageLoader("/res/enemy.png"); 
    bullet = imageLoader("/res/bullet.png"); 
    score_panel = imageLoader("/res/score_panel.png");
    }
   
     public static BufferedImage imageLoader(String path){
           try {
           return 
                ImageIO.read(loadImage.class.
                getResource(path));
       } catch (IOException e) {
           e.printStackTrace();
           System.exit(1);
       }
           return null;     
     }
   
}