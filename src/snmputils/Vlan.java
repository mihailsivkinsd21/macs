/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package snmputils;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author vv
 */
public class Vlan {
    private Integer id;
    private List<Integer> unTaggedPorts = new ArrayList<Integer>();
    private List<Integer> allPorts = new ArrayList<Integer>();
    private List<Integer> taggedPorts = new ArrayList<Integer>();

    public Vlan(ArrayList<Integer> a, ArrayList<Integer> b) throws UnknownHostException{
        setAllPorts(a);
        setUnTaggedPorts(b);
        
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public List<Integer> getTaggedPorts(){
        List<Integer> tagged = new ArrayList<Integer>();
        tagged.addAll(allPorts);
        tagged.removeAll(unTaggedPorts);
        return tagged;
    }
    
    public List<Integer> getUnTaggedPorts() {
        List<Integer> untagged = new ArrayList<Integer>();
        untagged.addAll(allPorts);
        untagged.removeAll(taggedPorts);
        return untagged;
    }

    private void setUnTaggedPorts(List<Integer> unTaggedPorts) {
        this.unTaggedPorts = unTaggedPorts;
    }

    public List<Integer> getAllPorts() {
        return allPorts;
    }

    private void setAllPorts(List<Integer> allPorts) {
        this.allPorts = allPorts;
    }

    private void setTaggedPorts(ArrayList<Integer> c) {
        this.taggedPorts = taggedPorts;
    }
}
