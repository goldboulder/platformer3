/*

 */
package actions;

import abstractthings.GameObject;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Set;
import paths.Path;
import stat_effects.StatEffectHandler;
import support.GameObjectList;


public class ContinuousActionList {
    
    protected HashMap<String,Action> continuousActions;// hash map instead? not big enough?
    private LinkedList<Action> addQueue;
    private LinkedList<Action> removeQueue;
    
    public ContinuousActionList(){
        continuousActions = new HashMap<>();
        addQueue = new LinkedList<>();
        removeQueue = new LinkedList<>();
    }
    
    
    public void performContinuousActions(GameObject owner, GameObjectList o, StatEffectHandler s){
        Set<String> set = continuousActions.keySet();
        String[] set2 = new String[set.size()];
        set2 = set.toArray(set2);
            
        for (String str : set2){
            Action a = continuousActions.get(str);
            if (a != null){// in case an action gets deleted during the loop
                a.attemptAction(owner,o,s);
            }
            
            if (a instanceof SelfDeletingAction){
                SelfDeletingAction d = (SelfDeletingAction) a;
                if (d.deleteReady()){//deleteReady for other actions = false**
                    removeQueue.add(a);
                }
            }

        }
        
        while (!removeQueue.isEmpty()){
            Action a = removeQueue.poll();
            continuousActions.remove(a.getCommand());
        }
        
        while(!addQueue.isEmpty()){
            Action a = addQueue.poll();
            continuousActions.put(a.getCommand(),a);
        }

    }
    
    public void addAction(Action a){// no repeats?
        addQueue.add(a);
        continuousActions.put(a.getCommand(),a);//bypasing queue?***
    }
    
    public void addActionNow(Action a){
        continuousActions.put(a.getCommand(),a);
    }
    
    public FollowPath getPathAction() {
        return (FollowPath) continuousActions.get(FollowPath.ID);
    }
    
    public Path getPath(){
        return getPathAction().getPath();
    }
    
    public void removeAction(String str){// no repeats?
        continuousActions.remove(str);
    }
    
    public void removeAction(Action a){
        continuousActions.remove(a.getCommand());
    }
    
    
    
    public boolean hasAction(Action a){
        for (Action action : continuousActions.values()){
            if (action.getClass() == a.getClass()){//?????
                return true;
            }
        }
        return false;
    }
    
    public boolean hasAction(String str){
        return continuousActions.containsKey(str);
    }
    
    public void replaceAction(Action a){
        Action nullCheck = continuousActions.get(a.getCommand());
        if(nullCheck != null){
            removeQueue.add(nullCheck);
        }
        addQueue.add(a);
    }
    
    public void replaceActionNow(Action a) {// replaces action without the queue buffer
        continuousActions.remove(a.getCommand());
        continuousActions.put(a.getCommand(), a);
    }
    
    // disable actions for a short time
    public void stallAction(String key, double time){// in seconds
        continuousActions.get(key).stall(time);
    }

    

    

    
    
}
