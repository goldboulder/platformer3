/*

 */
package support;

import player.Player;
import abstractthings.GameObject;
import abstractthings.Placeable;
import otherobjects.Door;
import otherobjects.BasicBlock;
import otherobjects.Block;
import enemies.Goomba;
import items.PuttyGun;
import items.YellowCoin;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Timer;
import projectiles.BouncyBullet;
import stat_effects.WalkSpeedDown;


public class PlayScene implements ActionListener{
    // death animation: remove all behaviors/continuous actions, hitboxes, show death animation, then delete
    
    public static final int GAME_TICK = 25;// move to super class that also has maker panel. keep constant here
    public static final double D_TIME = GAME_TICK/1000.0;
    
    
    private String levelName = "";
    private Camera camera;// offset for player to control camera
    private GameObjectList gameObjects;// seperate Player? its own class to separate player, enemies, items, solid objects, and hitsplats
    // all collidables hitboxes as one hitbox?****
    private Queue<GameObject> spawnQueue;
    private Queue<GameObject> deleteQueue;
    
    private InputHandler inputHandler;
    private double initialZoom;
    
    private Timer gameTimer;
    
    
    
    public PlayScene(String fileName, Point2D.Double playerPosition){// if a player is not in the file, use this position instead
        initialization();
        try {
            loadLevel(fileName,playerPosition);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PlayScene.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void loadLevel(String fileName, Point2D.Double playerPosition) throws FileNotFoundException{
        gameObjects.clear();
        spawnQueue.clear();
        deleteQueue.clear();
        Scanner sc = new Scanner(new File("levels/" + fileName + ".txt"));
        String[] tokens;
        while (sc.hasNext()){
            tokens = sc.nextLine().split(" ");
            Placeable p = PrototypeProvider.getPrototype(tokens[0]);//gets a new copy of a prototype, then changes some attributes based on string
            p.readFileString(tokens);
            gameObjects.add((GameObject)p);
        }
        // if not contatins avatar, add it, set camera
        if (!gameObjects.hasPlayer()){
            gameObjects.add(Player.getPlayer());
        }
        if (playerPosition != null){
            Player.getPlayer().setPosition(playerPosition);
        }
        
        camera.setCenter(Player.getPlayer().getPosition());
        
        levelName = fileName;
        
    }
    
    public String getLevelName(){
        return levelName;
    }
    
    public void initialization(){
        Player.newPlayer();
        gameTimer = new Timer(GAME_TICK,this);
        gameTimer.setActionCommand("gameTimer");
        gameTimer.start();
        initialZoom = 25;
        gameObjects = new GameObjectList();
        spawnQueue = new LinkedList<>();
        deleteQueue = new LinkedList<>();
        camera = new Camera(0,0,initialZoom,initialZoom*0.9);// paramether?
        inputHandler = new InputHandler(camera);
    }
    
    
    private void doorCheck(){//check for any isActive doors, if one is found, switch levels***********
        Door doorToGoThrough = null;
        for (GameObject g : gameObjects.getDoors()){// method in gameObjectList for casting?
            Door d = (Door) g;
            if (d.isActive()){
                d.deactivate();
                doorToGoThrough = d;
                break;
            }
        }
        if (doorToGoThrough != null){
            doorToGoThrough.goThrough(this);
        }
    }
    
    
    public void gameTick(){
        for (GameObject o : gameObjects){
            o.act(gameObjects,inputHandler);
            
        }
        
        manageGameObjects();
        
        adjustCamera(inputHandler);
        inputHandler.resetMovement();
        doorCheck();
    }
    
    private void manageGameObjects(){
        //determines what to add and delete from gameObjects. avoids ConcurrentModificationException
        for (GameObject o : gameObjects){
            while (o.hasStuffToSpawn()){
                spawnQueue.add(o.getThingToSpawn());
            }
            if (o.isDeleteReady()){
                deleteQueue.add(o);
            }
        }
        
        //actually adds and deletes objects from gameObjects
        while(!spawnQueue.isEmpty()){
            gameObjects.add(spawnQueue.poll());
        }
        while(!deleteQueue.isEmpty()){
            gameObjects.remove(deleteQueue.poll());
        }
    }
    
    
    public void paintScene(Graphics g, int screenWidth, int screenHeight){
        Graphics2D g2 = (Graphics2D)g;
        g2.clearRect(-1000000, -1000000,2000000,2000000);//background?
        
        camera.transform(g2,screenWidth,screenHeight);
        
        gameObjects.draw(g,false);
        
        g.dispose();//?
    }
    
    public void adjustCamera(InputHandler i){
        if (i.keyPressed(KeyEvent.VK_O)){
            camera.zoom(Camera.DEFAULT_ZOOM_SCALE);// zoom in
            //inputHandler.setZoom(camera.getXSize(), camera.getYSize());
        }
        if (i.keyPressed(KeyEvent.VK_L)){
            camera.zoom(1/Camera.DEFAULT_ZOOM_SCALE); // zoom out
            //inputHandler.setZoom(camera.getXSize(), camera.getYSize());
        }
        if (i.keyPressed(KeyEvent.VK_UP)){
            camera.translate(0,-camera.getYSize()*Camera.DEFAULT_SCROLL_PERCENT); // scroll up
        }
        if (i.keyPressed(KeyEvent.VK_DOWN)){
            camera.translate(0,camera.getYSize()*Camera.DEFAULT_SCROLL_PERCENT); // scroll down
        }
        if (i.keyPressed(KeyEvent.VK_LEFT)){
            camera.translate(-camera.getXSize()*Camera.DEFAULT_SCROLL_PERCENT,0); // scroll left
        }
        if (i.keyPressed(KeyEvent.VK_RIGHT)){
            camera.translate(camera.getXSize()*Camera.DEFAULT_SCROLL_PERCENT,0); // scroll right
        }
        
        
        keepPlayerInSight();
        
        
    }
    
    // keep the player in view of the camera
    private void keepPlayerInSight(){
        //make these static?
        double baseScroll = 0.07;
        double scrollSpeedBoost = 3;
        double px = camera.percentOnScreenX(Player.getPlayer().getCenter().x);
        double py = camera.percentOnScreenY(Player.getPlayer().getCenter().y);
        double leftBound = 0.4;
        double rightBound = 0.6;
        double upBound = 0.4;
        double downBound = 0.7;
        
        // if player is at the edge of the screen, movethe camera so the player is away from the edge.
        // the closer the player is to the edge, the faster the camera will move.
        if(py > downBound){
            camera.translatePercent(0, (scrollSpeedBoost*(py-downBound)+ baseScroll)/GAME_TICK);
        }
        if(py < upBound){
            camera.translatePercent(0, -(scrollSpeedBoost*(upBound-py)+ baseScroll)/GAME_TICK);
        }
        if(px < leftBound){
            camera.translatePercent(-(scrollSpeedBoost*(leftBound-px)+ baseScroll)/GAME_TICK, 0);
        }
        if(px > rightBound){
            camera.translatePercent((scrollSpeedBoost*(px-rightBound)+ baseScroll)/GAME_TICK, 0);
        }
    }
    
    
    
    public InputHandler getInputHandler(){
        return inputHandler;
    }
    
    public Player getPlayer(int i){
        return gameObjects.getPlayer(i);
    }
    
    
    public void triggerPause(){
        if (gameTimer.isRunning()){
            pause();
        }
        else{
            unpause();
        }
    }
    
    
    public void pause(){
        gameTimer.stop();
        for (GameObject g : gameObjects){
            g.pauseAnimations();
            
        }
    }
    
    
    public void unpause(){
        gameTimer.start();
        for (GameObject g : gameObjects){
            g.unPauseAnimations();
        }
    }
    
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("gameTimer")){
            gameTick();
        }
    }

}
