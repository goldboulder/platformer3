/*

 */
package support;

import otherobjects.HitSplat;
import abstractthings.GameObject;
import otherobjects.Door;
import enemies.Enemy;
import items.Item;
import java.awt.Graphics;
import java.util.Iterator;
import java.util.LinkedList;
import player.Player;
import projectiles.Projectile;


public class GameObjectList implements Iterable<GameObject>{//list too?

    private LinkedList<GameObject> players;//players get processed first. they matter most
    private LinkedList<GameObject> enemies;
    private LinkedList<GameObject> projectiles;
    private LinkedList<GameObject> items;
    private LinkedList<GameObject> collidables; // solid objects are put later in the list
                                                // so collision detection can happen at the end
                                                // of a frame. This way, the objects will all be
                                                // in a valid spot when it's time to render the scene.
    private LinkedList<GameObject> hitSplats;// particles too?
    private LinkedList<GameObject> doors;
    private LinkedList<GameObject> others;
    
    
    public GameObjectList(){
        players = new LinkedList();
        enemies = new LinkedList();
        projectiles = new LinkedList();
        items = new LinkedList();
        collidables = new LinkedList();
        hitSplats = new LinkedList();
        doors = new LinkedList();
        others = new LinkedList();
        
    }
    
    
    public void add(GameObject g){
        if (g instanceof  Player){
            players.add(g);
            return;
        }
        
        if (g.isSolid()){
            collidables.add(g);
            return;
        }
        
        if (g instanceof  Enemy){
            enemies.add(g);
            return;
        }
        
        if (g instanceof  Projectile){
            projectiles.add(g);
            return;
        }
        
        if (g instanceof  Item){
            items.add(g);
            return;
        }
        
        if (g instanceof  HitSplat){
            hitSplats.add(g);
            return;
        }
        if (g instanceof  Door){
            doors.add(g);
            return;
        }
        others.add(g);
    }
    
    public void remove(GameObject g){
        
        if (g == null) return;
        
        if (g instanceof  Player){
            players.remove(g);
            return;
        }
        
        if (g.isSolid()){
            if (!collidables.remove(g)){//object loses solidity?
                if (!enemies.remove(g)){
                    others.remove(g);
                }
            }
            return;
        }
        
        if (g instanceof  Projectile){
            projectiles.remove(g);
            return;
        }
        
        if (g instanceof  Enemy){
            enemies.remove(g);
            return;
        }
        
        if (g instanceof  Item){
            items.remove(g);
            return;
        }
        
        if (g instanceof  HitSplat){
            hitSplats.remove(g);
            return;
        }
        
        if (g instanceof  Door){
            doors.remove(g);
            return;
        }
        
        others.remove(g);
    }
    
    public boolean hasPlayer(){
        return !players.isEmpty();
    }
    
    
    @Override
    public Iterator iterator() {// for game tick
        return new GameTickIterator();
    }
    
    public Iterator getItemIterator(){
        return items.iterator();
    }
    
    public LinkedList<GameObject> getDoors(){
        return doors;
    }

    public Player getPlayer(int i) {
        return (Player) players.get(i);
    }

    public void removePlayers() {
        players.clear();
    }

    public int size() {
        int sum = 0;
        sum += players.size();
        sum += enemies.size();
        sum += projectiles.size();
        sum += items.size();
        sum += collidables.size();
        return sum;
    }

    public void clear() {
        players.clear();
        enemies.clear();
        projectiles.clear();
        items.clear();
        collidables.clear();
        hitSplats.clear();
        doors.clear();
        others.clear();
    }

    
    private class GameTickIterator implements Iterator<GameObject>{
        
        LinkedList<Iterator<GameObject>> iteratorList;
        
        public GameTickIterator(){
            iteratorList = new LinkedList();
            iteratorList.add(players.iterator());
            iteratorList.add(enemies.iterator());
            iteratorList.add(projectiles.iterator());
            iteratorList.add(items.iterator());
            iteratorList.add(collidables.iterator());
            iteratorList.add(hitSplats.iterator());
            iteratorList.add(doors.iterator());
            iteratorList.add(others.iterator());
        }

        @Override
        public boolean hasNext() {//gets ready for next object too. returns true when done, false if empty
            while(!iteratorList.isEmpty() && !iteratorList.get(0).hasNext()){
                iteratorList.remove(0);
            }
            
            return !iteratorList.isEmpty();
        }

        @Override
        public GameObject next() {
            GameObject g = iteratorList.get(0).next();
            
            return g;
        }
        
    }
    
    public void draw(Graphics g, boolean drawEdits) {
        for (GameObject o : doors){o.draw(g);}
        for (GameObject o : collidables){o.draw(g);}
        for (GameObject o : enemies){o.draw(g);}
        for (GameObject o : projectiles){o.draw(g);}
        for (GameObject o : items){o.draw(g);}
        for (GameObject o : players){o.draw(g);}
        for (GameObject o : others){o.draw(g);}
        for (GameObject o : hitSplats){o.draw(g);}
        
        if (drawEdits){
            for (GameObject o : doors){o.drawEditInfo(g);}
            for (GameObject o : collidables){o.drawEditInfo(g);}
            for (GameObject o : enemies){o.drawEditInfo(g);}
            for (GameObject o : projectiles){o.drawEditInfo(g);}
            for (GameObject o : items){o.drawEditInfo(g);}
            for (GameObject o : players){o.drawEditInfo(g);}
            for (GameObject o : others){o.drawEditInfo(g);}
            for (GameObject o : hitSplats){o.drawEditInfo(g);}
        }
    }
    
}
