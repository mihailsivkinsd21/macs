/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package test;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 *
 * @author jelis
 */
public class PropertySupport {

    private PropertyChangeSupport propertySupport;

    public PropertySupport() {
        propertySupport = new PropertyChangeSupport(this);
    }

    public void printPropertyChangeListeners(){
        PropertyChangeListener[] listeners = propertySupport.getPropertyChangeListeners();
        for (int i = 0; i < listeners.length; i++) {
            Object listener = listeners[i];
            System.out.println("Listener: " + listener);
        }
    }

    public void removeAllListeners(){
        PropertyChangeListener[] listeners = propertySupport.getPropertyChangeListeners();
        for (int i = 0; i < listeners.length; i++){
            //System.out.println("Remove Listener: " + listeners[i].toString());
            propertySupport.removePropertyChangeListener(listeners[i]);
        }
    }

    
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        //System.out.println("Add property listener: " + listener);
        propertySupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertySupport.removePropertyChangeListener(listener);
    }

    protected void firePropertyChange(){
        firePropertyChange(null, null, null);
    }

    protected void firePropertyChange(String propName){
        firePropertyChange(propName, null, null);
    }

    protected void firePropertyChange(String propName, Object newValue){
        firePropertyChange(propName, null, newValue);
    }

    public void firePropertyChange(String propName, Object oldValue, Object newValue){

        //System.out.println("Fire property change : " + propName + " OLD: " + oldValue + " NEW: " + newValue + " Object: " + this);
        //printPropertyChangeListeners();
        if (propertySupport.hasListeners(propName)){
            propertySupport.firePropertyChange(propName, oldValue, newValue);
        }
    }
}
