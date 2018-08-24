/*

 */
package actions;


public interface SelfDeletingAction {
    boolean deleteReady();
    String getCommand();
}
