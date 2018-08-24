/*

 */
package support;

import abstractthings.GameObject;
import otherobjects.BasicBlock;
import enemies.Goomba;
import items.PuttyGun;
import items.YellowCoin;
import java.util.HashMap;
import abstractthings.Placeable;
import abstractthings.Spawnable;
import enemies.BlueSnifit;
import enemies.Duplicatotron;
import enemies.Gordo;
import enemies.RedSnifit;
import enemies.Slime;
import enemies.Spiny;
import otherobjects.WoodenDoor;
import enemies.SubspaceSpawner;
import enemies.Thwimp;
import gui.MakerFrame;
import items.Item;
import items.Material;
import java.util.ArrayList;
import java.util.Collection;
import paths.CirclePath;
import paths.Path;
import paths.SimpleLinePath;
import player.Player;


// if a new Placeable is made, update this class accordingly
public class PrototypeProvider { //singleton,instantiated in main method**************
    private static PrototypeProvider instance;
    private static HashMap<String, Placeable> prototypes;
    private static HashMap<String,Path> paths;
    private static HashMap<Integer,Item>[] itemValues;

    public static void instantiate() {
        instance = new PrototypeProvider();
    }

    

    

    

    

    
    
    private PrototypeProvider(){//these instances are only used for copying. They are never put in the field
        paths = new HashMap<>();
        paths.put(CirclePath.class.getSimpleName(), new CirclePath(0,0,false));
        paths.put(SimpleLinePath.class.getSimpleName(), new SimpleLinePath(0,0));
        
        
        prototypes = new HashMap<>();
        prototypes.put(Player.class.getSimpleName(), Player.getPlayer());
        prototypes.put(Goomba.class.getSimpleName(),new Goomba());
        prototypes.put(YellowCoin.class.getSimpleName(),new YellowCoin());
        prototypes.put(PuttyGun.class.getSimpleName(),new PuttyGun());
        prototypes.put(BasicBlock.class.getSimpleName(),new BasicBlock());
        prototypes.put(WoodenDoor.class.getSimpleName(), new WoodenDoor());
        prototypes.put(SubspaceSpawner.class.getSimpleName(), new SubspaceSpawner());
        prototypes.put(BlueSnifit.class.getSimpleName(), new BlueSnifit());
        prototypes.put(Spiny.class.getSimpleName(), new Spiny());
        prototypes.put(Slime.class.getSimpleName(), new Slime());
        prototypes.put(Duplicatotron.class.getSimpleName(), new Duplicatotron());
        prototypes.put(Gordo.class.getSimpleName(), new Gordo());
        prototypes.put(RedSnifit.class.getSimpleName(), new RedSnifit());
        prototypes.put(Thwimp.class.getSimpleName(), new Thwimp());
        
        
        
        
        //lets the game know which items are worth what
        itemValues = new HashMap[Material.values().length];
        for (int i = 0; i < Material.values().length; i ++){
            itemValues[i] = new HashMap<>();
        }
        
        itemValues[YellowCoin.TYPE.ordinal()].put(YellowCoin.VALUE, (Item) getOriginal(YellowCoin.class.getSimpleName()));
        
        
        
        for (Placeable p : prototypes.values()){
            GameObject g = (GameObject) p;
            g.pauseAnimations();
            
        }
    }
    
    public static Placeable getPrototype(String token) {
        return (Placeable) prototypes.get(token).getPrototype();
    }
    
    public static Placeable getOriginal(String token){// for agents so that a hard disk read isn't required for every instance
        return prototypes.get(token);
    }
    

    public static void populateContents(MakerFrame.MakerPanel p) {
        
        for (Placeable object : instance.prototypes.values()){
            p.addToContentPanel((GameObject)object);
        }
        
    }
    
    public static ArrayList<GameObject> getPrototypes(){
        Collection<Placeable> protos = instance.prototypes.values();
        ArrayList<GameObject> ans = new ArrayList<>();
        for (Placeable p : protos){
            GameObject g = (GameObject) p;
            ans.add(g);
        }
        return ans;
    }
    
    public static Path getPath(String pathName) {
        return (Path) paths.get(pathName).getCopy();
    }
    
    public static Path getDefaultPath() {
        return paths.get("CirclePath");
    }
    
    public static ArrayList<Path> getPaths(){
        Collection<Path> paths = instance.paths.values();
        ArrayList<Path> ans = new ArrayList<>();
        for (Path p : paths){
            ans.add((Path)p.getCopy());
        }
        return ans;
    }
    
    public static String[] getPathNames(){
        ArrayList<Path> p = getPaths();
        String[] names = new String[instance.paths.size()];
        for (int i = 0; i < names.length; i++){
            names[i] = p.get(i).getId();
        }
        return names;
    }
    
    public static Spawnable getDefaultSpawnPrototype() {
        return (Spawnable) prototypes.get("Goomba");
    }
    
    public static Item materialAmountToItem(Material m, int denomination) {
        try{
            return (Item) itemValues[m.ordinal()].get(denomination).getPrototype();
        }
        catch(NullPointerException e){
            return null;
        }
    }
    
}
