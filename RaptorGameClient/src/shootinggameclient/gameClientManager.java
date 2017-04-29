package shootinggameclient;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.util.*;

public class gameClientManager implements Runnable{
  
    
  private String title;
  private int width;
  private int height;
  private Thread  thread;
  private boolean running;
  private BufferStrategy buffer;
  private Graphics g;
  private int y=0;
  //private boolean start;
    
  private Player player; 
  private Opponent opponent;
  public static ArrayList<Bullet> bullet; //add bullets in Player
  public static ArrayList<Opponent_Bullet> op_bullet; //add bullets in opponent
  private ArrayList<Enemy> enemies; //Add enemies
  
    private long current ;
    private long delay;
    private int score;
    private long now;
    private boolean game_start=true;
    private int health;
    
    private Display display;
    public static final int gameWidth = 800;
    public static final int gameHeight = 750;
    
    private int opponent_change_x;
    private int opponent_change_y;
    private int opponent_shoot;
    private int enemy_x;
    private int enemy_y;
    private int opponent_health;
    private int opponent_score;
    
    BufferedReader inFromServer; //read from server
    DataOutputStream outToServer ; //send to server
    
    //Class that handles client data transfer
    public gameClientManager(String title,int width,int height){
    this.title = title;
       this.width = width;
       this.height = height;
            try{
            InetAddress ip = InetAddress.getLocalHost();
            Socket n = new Socket(ip, 9555); // Creating a socket at port 9555 to read from server
            inFromServer = new BufferedReader(new InputStreamReader(n.getInputStream())); // Input from server
            outToServer = new DataOutputStream(n.getOutputStream()); //Output to server
        }
        catch(Exception e){
            System.out.println("Socket problem");
        }
    }

    public void init(){  
        
       display = new Display(title,width,height); //Display
        loadImage.init(); //LoadImage
        
        player = new Player((0),(gameHeight/2)); // Init Player as player 1
        player.init();
        
        opponent = new Opponent((600),(gameHeight/2)); // Init Opponent as player 2
        opponent.init();
        
        bullet = new ArrayList<Bullet>(); 
        op_bullet = new ArrayList<Opponent_Bullet>();
        enemies = new ArrayList<Enemy>();
        current = System.nanoTime();
        now = System.nanoTime();
        delay = 1200;
        score = 0;
        health = 15;
    }
    
      public synchronized void start(){
        running = true; // start the thread, run function call
        thread = new Thread(this);
        thread.start();
     }
     
     public synchronized void stop(){
        if(!(running))
            return;
            running = false; //stop run
        try {
           thread.join(); // stop thread
       } catch (InterruptedException e) {
           e.printStackTrace();
       }
     }

    
    public void tick(){ //50 fps rate e update hoy
        try{
            
            //get server info
            opponent_health=Integer.parseInt(inFromServer.readLine());//player 2 life
            opponent_score=Integer.parseInt(inFromServer.readLine());//player 2 score
            enemy_x=Integer.parseInt(inFromServer.readLine());//random generated enemy x
            enemy_y=Integer.parseInt(inFromServer.readLine());//enemy y
            opponent_change_x=Integer.parseInt(inFromServer.readLine());//player 2 x
            opponent_change_y=Integer.parseInt(inFromServer.readLine());//player 2 y
            opponent_shoot=Integer.parseInt(inFromServer.readLine()); //did plyer 2 press enter to shoot
        }
        catch(Exception e){
        }
                  
        int px = player.getX();
        int py = player.getY();
        int p_fire = player.getFire();
        
        try{//send to server
            outToServer.writeBytes(String.valueOf(health)+'\n');//player 1 life 
            outToServer.writeBytes(String.valueOf(score)+'\n'); //player 1 score
            outToServer.writeBytes(String.valueOf(px)+'\n'); //player 1 x
            outToServer.writeBytes(String.valueOf(py)+'\n'); //player 1 y
            outToServer.writeBytes(String.valueOf(p_fire)+'\n');
        }
        catch(Exception e){            
        }
   if(health==0) //life over
        {
            game_start=false; //all rendering false
        }   
        
    if(game_start){
        
      //Generate enemy
      long breaks = (System.nanoTime()-current)/1000000;
      if(breaks > delay){    //1200 nanosec delayte add hoy     
         if(health>0){
            enemies.add(new Enemy(enemy_x,enemy_y)); //add enemy at co-ordinate from server
                }
           current = System.nanoTime();
        }
        
        player.tick(); //player 1 update at 50 fps
        opponent.tick(opponent_change_x, opponent_change_y,opponent_shoot); //player 2 update
         
      //Create Bullet
      for(int i = 0; i<bullet.size();i++){
         bullet.get(i).tick();
      }
      
       //Create Opponent Bullet
      for(int i = 0; i<op_bullet.size();i++){
         op_bullet.get(i).tick();
      }
      
      //Create Enemy
      for(int i = 0; i<enemies.size(); i++){
        enemies.get(i).tick();
            }
        }
    
    }
    
    public void render(Graphics  g){
        
        //Displaying the frame canvas
         buffer = display.getCanvas().getBufferStrategy();
       if(buffer == null){
         display.getCanvas().createBufferStrategy(3);
         return;
       }
       
      //Draw the frame
      g = buffer.getDrawGraphics();
      g.clearRect(0, 0, width, height);
     
      //Draw Screen Objects
     g.drawImage(loadImage.background,0,0, gameWidth, gameHeight,null);
     g.drawImage(loadImage.score_panel,0,750, 800, 150,null);
        
        
     if(game_start){ //player 1 life not over
       player.render(g);
       
       if(opponent_health>0)// player 2 life not over
       {
        opponent.render(g);
        //Opponent Bullet
       for(int  i=0;i<op_bullet.size(); i++){
           op_bullet.get(i).render(g);
       }
       
       //Remove out of screen bullet
       for(int i = 0;i< op_bullet.size();i++){
           if(op_bullet.get(i).getX()>=800){
              op_bullet.remove(i);
              i--;
              
          } 
       }
       }
       
       // Bullet
       for(int  i=0;i<bullet.size(); i++){
           bullet.get(i).render(g);
       }
       
       //Remove out of screen bullet
       for(int i = 0;i< bullet.size();i++){
           if(bullet.get(i).getX()>=800){
              bullet.remove(i);
              i--;
              
          } 
       }
       
       //Keep Enemy within screen
       for(int i = 0;i<enemies.size();i++){
           if(!(enemies.get(i).getX()<=100
                 ||enemies.get(i).getX()>=700
                  ||enemies.get(i).getY()>= 750 - 140)){

                        enemies.get(i).render(g);      
                    } 
        }
       
       //Player1 co-ordinates
        int px = player.getX();
        int py = player.getY();
            
        //Player 2 co-ordinates
        int ox = opponent.getX();
        int oy = opponent.getY();
        
        //Collision with player 2, life --
          if( px < ox + 200 && px + 200 > ox &&
                       py < oy + 140 && py + 140 > oy ){
                       health--;
                    }
       
          //Enemy collisions
        for(int i = 0; i < enemies.size();i++){
            int ex = enemies.get(i).getX();
            int ey = enemies.get(i).getY(); 
                  
            //Enemy with player 2, remove enemy
            if( ox < ex + 120 && ox + 200 > ex &&
                       oy < ey + 120 && oy + 140 > ey ){
                       enemies.remove(i);
                       i--;
                    }
                    
            //Enemy player1 collision, life-- remove enemy
            if( px < ex + 120 && px + 200 > ex &&
                       py < ey + 120 && py + 140 > ey ){
                       enemies.remove(i);
                       i--;
                       health--;
                       if(health<=0){
                           enemies.removeAll(enemies);
                           game_start = false;

                       }
                    }
                    
            //Enemy bullet collision
                    for(int j = 0 ; j<bullet.size() ;j++){   
                        int bx = bullet.get(j).getX();
                        int by = bullet.get(j).getY();

                        if(ex < bx + 20 && 
                                ex + 120 >bx && ey< by + 20
                                        && ey +  120> by ){
                                    enemies.remove(i);
                                    i--;
                                    bullet.remove(j);
                                    j--;
                                    score = score + 5;//increase score

                             }
                    }

                    //Show player 1 and 2 score and life
                   g.setColor(Color.CYAN);
                   g.setFont(new Font("arial",Font.BOLD, 18));
                   g.drawString("Your Score : "+score, 55,810);
                   g.drawString("Opponent Score : "+opponent_score, 55,840);
                   g.drawString("You : "+health, 540,815);
                   g.drawString("Opponent : "+opponent_health, 515,845);
              }
        }
     else{
                    //Game over conditions         
                    g.setColor(Color.WHITE);
                    g.setFont(new Font("arial",Font.BOLD, 40));
                    
                    //Both players dead, compare score declare winner
                      if(opponent_health==0 && health==0)
                    {
                        if(score>opponent_score){
                            g.drawString(" Your Win! :D ", gameWidth/2-150,gameHeight/2-100);
                            g.drawString("Opponent Score: "+opponent_score, gameWidth/2-150,gameHeight/2-50);
                        }
                           else if(score==opponent_score){
                            g.drawString(" Its a TIE! :D ", gameWidth/2-150,gameHeight/2-100);
                            g.drawString("Opponent Score: "+opponent_score, gameWidth/2-150,gameHeight/2-50);
                        }
                        else
                        {
                           g.drawString(" Your LOSE! :( ", gameWidth/2-150,gameHeight/2-100); 
                           g.drawString("Opponent Score: "+opponent_score, gameWidth/2-150,gameHeight/2-50);
                        }

                    }
                    
                    //Player 1 dead, show score, wait for player 2 to finish
                    g.drawString("Your Score: "+score, gameWidth/2-150,gameHeight/2);
     }
  
     buffer.show();
     g.dispose();
     
    }
    
      public void run() {
        init();//Iitialize
        int fps = 50; //Number of frames per sec - 50
        double timePerTick = 1000000000/fps; //1 frame time
        double delta = 0; //For break between each frame
        long current = System.nanoTime();
        
         while(running){
       delta = delta + (System.nanoTime()-current)/timePerTick; //Time passed since starting divided by 1 frame time added to delta
            current = System.nanoTime();
            if(delta>=1){
              tick(); //Update frame
              render(g); //Render frame objects
               delta--;
            }

    
        }
    }
    
    public static void main(String[] args) {
    gameClientManager game = new gameClientManager("Raptor Game",800,900);  
    game.start();

    }

}
