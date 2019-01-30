package io.knativeflow.model;

import java.util.HashMap;
import java.util.Map;

/**
 * This class represents a node that processes CloudEvents. It contains
 * details of input and output connections and the code to instantiate
 * when being deployed
 *
 * @author hhiden
 */
public class ProcessorNode extends ProcessorObject {
    /**
     * Input Port
     */
    private ProcessorInputPort input = null;

    /**
     * Output Ports
     */
    private ProcessorOutputPort output = null;

    /**
     * Image name
     */
    private String kSVCName = "processor";

    /**
     * Unique ID of the node
     */
    private String uuid;

    /**
     * Runtime settings
     */
    private Map<String, String> settings = new HashMap<>();

    /**
     * Parent flow
     */
    private ProcessorFlow parent;

    private String displayName = "NODE";

    public ProcessorNode() {
    }

    public ProcessorFlow getParent() {
        return parent;
    }

    public void setParent(ProcessorFlow parent) {
        this.parent = parent;
    }

    public void createInput(){
        if(input==null){
            input = new ProcessorInputPort();
            input.setParent(this);
        }
    }
    
    public void createOutput(){
        if(output==null){
            output = new ProcessorOutputPort();
            output.setParent(this);
        }
    }
    
    public ProcessorInputPort getInput() {
        return input;
    }

    public ProcessorOutputPort getOutput() {
        return output;
    }

    public String getkSVCName() {
        return kSVCName;
    }

    public void setkSVCName(String kSVCName) {
        this.kSVCName = kSVCName;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Map<String, String> getSettings() {
        return settings;
    }

    public void setSettings(Map<String, String> settings) {
        this.settings = settings;
    }
    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
    
    public boolean hasInput(){
        return input!=null;
    }
    
    public boolean hasOutput(){
        return output!=null;
    }
}