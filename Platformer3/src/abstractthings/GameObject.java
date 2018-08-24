
package abstractthings;

import actions.Action;
import actions.ContinuousActionList;
import actions.FollowPath;
import actions.PushOutAction;
import actions.PushUpSemiSolid;
import interactions.Interaction;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.LinkedList;
import java.util.Queue;
import support.PlayScene;
import support.HitBox;
import animations.GraphicProvider;
import interactions.ApplyStatusEffect;
import interactions.ChildKilled;
import interactions.ParentKilled;
import interactions.WasBouncedOffOf;
import items.UniqueItem;
import items.UpgradeItem;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import paths.Path;
import player.Inventory;
import stat_effects.StatEffect;
import support.GameObjectList;
import support.InputHandler;
import support.OtherThings;
import stat_effects.StatEffectHandler;
import support.ImagePool;
import support.PrototypeProvider;


public abstract class GameObject {
    protected Point2D.Double position;
    protected Point2D.Double velocity;
    protected GraphicProvider graphics;
    protected Queue<GameObject> spawnQueue;
    protected ContinuousActionList actionList;
    protected Point2D size;
    protected HitBox hitBox;// collection of Rectangle2Ss, initiated later
    protected boolean facingRight;
    protected double rotation;// affects image, not hitBox
    protected boolean deleteReady;
    protected Inventory inventory;
    protected StatEffectHandler statEffectHandler;
    protected GameObject parent;
    protected LinkedList<GameObject> children;
    protected int maxChildren;
    protected boolean pushable = true;//can be pushed by blocks
    protected GameObject justBouncedOff;
    
    
    
    
    public GameObject(){
        velocity = new Point2D.Double(0,0);
        graphics = new GraphicProvider();
        spawnQueue = new LinkedList<>();
        actionList = new ContinuousActionList();
        statEffectHandler = new StatEffectHandler();
        inventory = new Inventory();
        children = new LinkedList<>();
        fillInventory();
        maxChildren = 1;//default
        
        
    }
    
    public void draw(Graphics g){
        drawFull(g);
    }
    
    protected void drawWithoutRotation(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        g2.translate(position.getX(), position.getY());
        g2.scale(size.getX(),size.getY());
        
        drawImage(g2);
        
        g2.scale(1/size.getX(),1/size.getY());
        g2.translate(-position.getX(), -position.getY());
    }
    
    protected void drawWithoutFlipping(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        g2.translate(position.getX(), position.getY());
        g2.scale(size.getX(),size.getY());
        Point2D.Double relCenter = hitBox.getRelitiveCenter();// only do if rotation != 0?
        g2.rotate(getRotation(),relCenter.x,relCenter.y);
        
        g.drawImage(getImage(), 0, 0, 1, 1, null);
        
        g2.rotate(-getRotation(),relCenter.x,relCenter.y);
        g2.scale(1/size.getX(),1/size.getY());
        g2.translate(-position.getX(), -position.getY());
    }
    
    protected void drawPlain(Graphics g){
        Graphics2D g2 = (Graphics2D) g;//drawPlainPicture here?
        g2.translate(position.getX(), position.getY());
        g2.scale(size.getX(),size.getY());
        
        g.drawImage(getImage(), 0, 0, 1, 1, null);
        
        g2.scale(1/size.getX(),1/size.getY());
        g2.translate(-position.getX(), -position.getY());
    }
    
    protected static void drawPlainPicture(Graphics g, double x, double y, double xSize, double ySize, BufferedImage image, double opacity){//opacity doesn't work
        Graphics2D g2 = (Graphics2D) g;
        
        g2.translate(x, y);
        g2.scale(xSize,ySize);
        
        
        //float[] scales = { 1f, 1f, 1f, (float) opacity };// ignores scaling?
        //float[] offsets = new float[]{10,10,10,10};
        //RescaleOp rop = new RescaleOp(scales, offsets, null);
        //g2.drawImage(image, rop, 0, 0);
        
        
        g.drawImage(image, 0, 0, 1, 1, null);
        
        g2.scale(1/xSize,1/ySize);
        g2.translate(-x, -y);
    }
    
    private static final double STRING_WIDTH_APPROX = 0.14;//same as in percentBar?
    protected static void drawString(Graphics g, double centerX, double centerY, double size, String str){//opacity doesn't work
        Graphics2D g2 = (Graphics2D) g;
        centerX-= STRING_WIDTH_APPROX * str.length();
        centerY+= size/2;
        
        g2.translate(centerX, centerY);
        g2.scale(size,size);
        
        g.setFont(new Font("Arial", Font.BOLD, 1));
        g.drawString(str, 0, 0);
        
        g2.scale(1/size,1/size);
        g2.translate(-centerX, -centerY);
    }
    
    protected void drawFull(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        
        g2.translate(position.getX(), position.getY());
        g2.scale(size.getX(),size.getY());
        Point2D.Double relCenter = hitBox.getRelitiveCenter();// only do if rotation != 0?
        g2.rotate(getRotation(),relCenter.x,relCenter.y);
        
        drawImage(g2);
        
        g2.rotate(-getRotation(),relCenter.x,relCenter.y);
        g2.scale(1/size.getX(),1/size.getY());
        g2.translate(-position.getX(), -position.getY());
    }
    
    public void drawImage(Graphics g){
        if (facingRight){
            g.drawImage(getImage(), 1, 0, -1, 1, null);
        }
        else{
            g.drawImage(getImage(), 0, 0, 1, 1, null);
        }
    }
    
    public void drawEditInfo(Graphics g){
        //nothing, can be overritten
    }
    
    public boolean isPushable(){
        return pushable;
    }
    
    public void setPushable(boolean b){
        this.pushable = b;
    }
    
    public void setPosition(double x, double y){
        changePosition(x-position.getX(), y-position.getY());
        hitBox.move(x-position.getX(), y-position.getY());
    }
    
    public void setPosition(Point2D p){
        changePosition(p.getX() - position.getX(),p.getY() - position.getY());
    }
    
    public void changePosition(double dx, double dy){
        position.setLocation(position.getX() + dx, position.getY() + dy);
        hitBox.move(dx, dy);
    }
    
    public double getXSize(){
        return size.getX();
    }
    
    public double getYSize(){
        return size.getY();
    }
    
    public Point2D getSize(){
        return new Point2D.Double(size.getX(),size.getY());
    }
    
    public void setSize(double sx, double sy){
        this.size = new Point2D.Double(sx,sy);
        hitBox = new HitBox(position,size,getHitboxShape());
    }
    
    public HitBox.Shape getHitboxShape(){
        return hitBox.getShape();
    }
    
    public void snapToGrid(double gridResolution) {
        double dx = -(position.getX()%gridResolution);
        double dy = -(position.getY()%gridResolution);
        changePosition(dx,dy);
    }
    
    // moves the object so that its center is where the upper left corner used to be
    public void goToCenterOfMouse() {
        changePosition(-size.getX()/2,-size.getY()/2);
    }
    
    public void setVelocity(double x, double y){
        getVelocity().setLocation(x,y);
    }
    
    public void setXVelocity(double x){
        getVelocity().setLocation(x,getVelocity().getY());
    }
    
    public void setYVelocity(double y){
        getVelocity().setLocation(getVelocity().getX(),y);
    }
    
    public void changeVelocity(double dx, double dy){
        setVelocity(getVelocity().getX() + dx, getVelocity().getY() + dy);
    }
    
    public BufferedImage getImage(){
        return graphics.getImage();
    }
    
    public void pauseAnimations(){
        graphics.pauseAnimations();
    }
    
    public void unPauseAnimations(){
        graphics.unPauseAnimations();
    }
    
    
    public void act(GameObjectList o, InputHandler i){
        statEffectHandler.applyEffects(this);
        changePosition(getVelocity().getX()*PlayScene.D_TIME,getVelocity().getY()*PlayScene.D_TIME);
        actionList.performContinuousActions(this, o, statEffectHandler);
        inventory.itemCoolDownTick();
    }
    
    public void react(GameObject g, Interaction i){
        if (i instanceof ApplyStatusEffect){
            ApplyStatusEffect a = (ApplyStatusEffect)i;
            addEffect(a.getEffect());
        }
        if (i instanceof ChildKilled){
            children.remove(g);
        }
    }
    
    
    public boolean colliding(GameObject e){
        return getHitBox().colliding(e.getHitBox());
    }


    public HitBox getHitBox() {
        return hitBox;
    }


    public void setHitBox(HitBox hitBox) {
        this.hitBox = hitBox;
    }

    
    public Point2D getVelocity() {
        return velocity;
    }
    
    public double getSpeed(){
        return Math.hypot(velocity.x, velocity.y);
    }
    
    public double getVelocityAngle(){
        return Math.atan2(velocity.y, velocity.x);
    }

    public double getRotation() {
        return rotation;
    }


    public void setRotation(double rotation) {
        this.rotation = rotation;
    }

    public boolean isFacingRight() {
        return facingRight;
    }
    
    public void setFaceRight(boolean b) {
        facingRight = b;
    }
    
    public void faceOtherWay(){
        facingRight = !facingRight;
    }
    
    // uses direction
    public void bounceRotation(double angle, GameObject bouncee){//changes direction only
        double collisionAngle = rotation - angle;
        rotation = rotation - 2 * collisionAngle;
        
        justBouncedOff = bouncee;
        bouncee.react(this, new WasBouncedOffOf());
    }
    /*
    public void bounceVelocity(double angle, GameObject bouncee) {//changes velocity only
        System.out.println(velocity.x);
        double speed = getSpeed();
        double collisionAngle = getVelocityAngle() - angle;
        double newAngle = getVelocityAngle() - 2 * collisionAngle;
        
        setXVelocity(speed * Math.cos(newAngle));
        setYVelocity(speed * Math.sin(newAngle));
        
        justBouncedOff = bouncee;
        bouncee.react(this, new WasBouncedOffOf());
        System.out.println("new" + velocity.x);
    }
    */
    public double angleTo(GameObject target){
        return angleTo(target.getCenter());
    }
    
    public double angleTo(Point2D point){
        Point2D thisCenter = getCenter();
        return Math.atan2(thisCenter.getY() - point.getY(),point.getX()-thisCenter.getX());
    }
    
    public Point2D.Double getCenter(){
        return new Point2D.Double(position.getX() + size.getX()/2,position.getY() + size.getY()/2);
    }
    
    public void setCenter(double x, double y){
        setPosition(x,y);
        goToCenterOfMouse();
    }
    
    public void setCenter(Point2D.Double point){
        setCenter(point.x, point.y);
    }
    
    public void setCenter(GameObject g){
        setCenter(g.getCenter());
    }
    
    public Point2D.Double getPosition(){
        return position;
    }
    
    public Point2D.Double getUpperLeftCorner(){
        return new Point2D.Double(position.getX(),position.getY());
    }
    
    public Point2D.Double getUpperRightCorner(){
        return new Point2D.Double(position.getX() + size.getX(),position.getY());
    }
    
    public Point2D.Double getLowerRightCorner(){
        return new Point2D.Double(position.getX() + size.getX(),position.getY() + size.getY());
    }
    
    public Point2D.Double getLowerLeftCorner(){
        return new Point2D.Double(position.getX(),position.getY() + size.getY());
    }
    
    public boolean hasStuffToSpawn() {
        return !spawnQueue.isEmpty();
    }

    public GameObject getThingToSpawn() {
        return (spawnQueue.poll());
    }
    
    public void delete(){
        
        deleteReady = true;
        if (parent != null){
            parent.react(this, new ChildKilled());
        }
        dropInventory();
        for (GameObject child : children){
            child.react(this, new ParentKilled());
        }
        
    }
    
    public boolean isDeleteReady(){
        return deleteReady;
    }
    
    public void setDeleteReady(boolean d){
        deleteReady = d;
    }
    
    public void fillInventory(){
        //nothing, overwritten by most enemies
    }
    
    public void dropInventory(){
        inventory.dropAll(this);
    }
    
    
    private static Action pushOut = new PushOutAction("pushout");
    private static Action semiSolid = new PushUpSemiSolid("pushoutSemiSolid");
    
    public boolean isSolid(){
        return (actionList.hasAction(pushOut) || actionList.hasAction(semiSolid));
    }
    
    
    public boolean onGround(GameObjectList o) {
        HitBox h = new HitBox(position.getX(),position.getY()+size.getY(),size.getX(),0.01);
        
        for (GameObject collider : o){
            if (collider.isSolid() && collider != this){ //&& h.colliding(collider.hitBox)){
                HitBox h2 = new HitBox(collider.position.getX(),collider.position.getY(),collider.size.getX(),0.1);// last didget being too small caused false negatives
                if (h.colliding(h2)){
                    return true;
                }
            }
            
        }
        
        return false;
    }
    
    public static final double REASONABLE_PERCENT_VERTICAL = 0.15;
    public static final double REASONABLE_PERCENT_HORIZANTAL = 0.20;// gridSize?
    public boolean reasonablyVertical(GameObject other) {
        double thisLowRange = position.getX() + size.getX()*REASONABLE_PERCENT_VERTICAL;
        double thisHighRange = position.getX() + size.getX()*(1-REASONABLE_PERCENT_VERTICAL);
        if (OtherThings.rangesColliding(other.position.getX(),other.position.getX()+other.size.getX(),thisLowRange,thisHighRange)){
            return true;
        }
        double otherLowRange = other.position.getX() + other.size.getX()*REASONABLE_PERCENT_VERTICAL;
        double otherHighRange = other.position.getX() + other.size.getX()*(1-REASONABLE_PERCENT_VERTICAL);
        if (OtherThings.rangesColliding(position.getX(),position.getX()+other.size.getX(),otherLowRange,otherHighRange)){
            return true;
        }
        return false;
    }

    public boolean reasonablyHorizontal(GameObject other) {//enemies triggering hitWall when they land on many block floor with high velocity?
        double thisLowRange = position.getY() + size.getY()*REASONABLE_PERCENT_HORIZANTAL;
        double thisHighRange = position.getY() + size.getY()*(1-REASONABLE_PERCENT_HORIZANTAL);
        if (OtherThings.rangesColliding(other.position.getY(),other.position.getY()+other.size.getY(),thisLowRange,thisHighRange)){
            return true;
        }
        double otherLowRange = other.position.getY() + other.size.getY()*REASONABLE_PERCENT_HORIZANTAL;
        double otherHighRange = other.position.getY() + other.size.getY()*(1-REASONABLE_PERCENT_HORIZANTAL);
        if (OtherThings.rangesColliding(position.getY(),position.getY()+other.size.getY(),otherLowRange,otherHighRange)){
            return true;
        }
        return false;
    }

    public boolean hasAction(Action a){
        return actionList.hasAction(a);
    }

    public void spawnObject(GameObject g) {
        spawnQueue.add(g);
        children.add(g);
        g.parent = this;
    }
    
    public Inventory getInventory() {
        return inventory;
    }
    
    public void addEffect(StatEffect s){
        statEffectHandler.addEffect(this,s);
    }
    
    public void clearStatEffects(){
        statEffectHandler.clearEffects(this);
    }
    
    //copies fields that could change while in play
    public void copyFields(GameObject g){
        setPosition(g.position.x,g.position.y);
        velocity = new Point2D.Double(g.velocity.x,g.velocity.y);
        //graphics = (GraphicProvider) g.graphics.getCopy();// already copied?
        
        spawnQueue = new LinkedList<>();
        for (GameObject q : g.spawnQueue){
            Copyable c = (Copyable)q;
            spawnQueue.add((GameObject)(c.getCopy()));
        }
        //copy size?
        this.facingRight = g.facingRight;
        this.rotation = g.rotation;
        this.deleteReady = g.deleteReady;
        this.inventory = (Inventory) g.inventory.getCopy();
        this.statEffectHandler = (StatEffectHandler) g.statEffectHandler.getCopy();
        //parent and children are not copied
        
        
    }
    
    public int getNumChildren() {
        return children.size();
    }
    
    
    public int getMaxChildren() {
        return maxChildren;
    }

    
    public void setMaxChildren(int max) {
        maxChildren = max;
    }
    

    public void collectUpgradeItem(UpgradeItem u) {
        //nothing, can be overritten. Player overrides this
    }

    public void doScheduledAction(GameObjectList o, String command) {
        //nothing, can be overritten.
    }
    
    public String getId() {
        return this.getClass().getSimpleName();
    }
    
    public String fileStringStart(){
        return getId() + " " + Double.toString(position.getX()) + " " + Double.toString(position.getY());
    }
    
    public BufferedImage getProfileImage(){
        return ImagePool.getPicture("Default Image");//overwritten
    }

    public void useItem(UniqueItem u) {
        u.useItem(this);
    }
    
    public boolean hasPathAction(){
        return actionList.hasAction(FollowPath.ID);
    }
    
    public void setPath(Path path, double speed, Point2D.Double origin) {
        actionList.replaceActionNow(new FollowPath(FollowPath.ID,path,speed,origin));
        setVelocity(0,0);
    }
    
    public void setPath(Path path, double speed) {
        actionList.replaceActionNow(new FollowPath(FollowPath.ID,path,speed,getCenter()));
        setVelocity(0,0);
        this.pushable = false;
    }
    
    public void setPath(String[] params){// 0:pathName, 1: speed, 2...: path params
        String pathName = params[0];
        double pathSpeed = Double.parseDouble(params[1]);
        String[] pathParameters = new String[params.length - 2];
        for (int i = 0; i < pathParameters.length; i ++){
            pathParameters[i] = params[i+2];
        }
        
        Path path = PrototypeProvider.getPath(pathName);
        path.buildPath(pathParameters);
            
        actionList.replaceActionNow(new FollowPath(FollowPath.ID,path,pathSpeed,getCenter()));
        setVelocity(0,0);
        this.pushable = false;
    }
    
    public void possiblyBecomePushable(){
        try{
            GameObject g = (GameObject) PrototypeProvider.getOriginal(this.getClass().getSimpleName());
            if (g.pushable){
                pushable = true;
            }
        }
        catch(Exception e){
            //nothing
        }
    }
    
    public GameObject getCopy() {//copy any unique fields here, all in one method?
        GameObject g = (GameObject) PrototypeProvider.getPrototype(getId());
        g.copyFields(this);
        return g;
    }

    

    

    
    
}
