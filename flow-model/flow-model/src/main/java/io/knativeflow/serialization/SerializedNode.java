package io.knativeflow.serialization;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.knativeflow.model.ProcessorNode;
import java.util.HashMap;
import java.util.Map;

/**
 * Serialized form of node
 *
 * @author hhiden
 */
public class SerializedNode {
    @JsonIgnore
    private ProcessorNode node;
    
    private boolean inputPresent = false;
    private boolean outputPresent = false;
    private String uuid;
    private String displayName;
    private String kSVCName;

    private Map<String, String> settings = new HashMap<>();

    public SerializedNode() {
    }

    public SerializedNode(ProcessorNode node) {
        this.node = node;
        displayName = node.getDisplayName();
        uuid = node.getUuid();
        inputPresent = node.hasInput();
        outputPresent = node.hasOutput();

        this.kSVCName = node.getkSVCName();
        for (String key : node.getSettings().keySet()) {
            settings.put(key, node.getSettings().get(key));
        }
    }

    public ProcessorNode createNode() {
        ProcessorNode node = new ProcessorNode();

        node.setUuid(uuid);
        node.setSettings(settings);
        node.setDisplayName(displayName);
        node.setkSVCName(kSVCName);
        if(inputPresent){
            node.createInput();
        }
        
        if(outputPresent){
            node.createOutput();
        }
        return node;
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

    public String getkSVCName() {
        return kSVCName;
    }

    public void setkSVCName(String kSVCName) {
        this.kSVCName = kSVCName;
    }

    public boolean isInputPresent() {
        return inputPresent;
    }

    public boolean isOutputPresent() {
        return outputPresent;
    }

    public void setInputPresent(boolean inputPresent) {
        this.inputPresent = inputPresent;
    }

    public void setOutputPresent(boolean outputPresent) {
        this.outputPresent = outputPresent;
    }
    
    @Override
    public String toString() {
        return "SerializedNode{" +
                "node=" + node +
                ", uuid='" + uuid + '\'' +
                ", displayName='" + displayName + '\'' +
                ", kSVCName='" + kSVCName + '\'' +
                ", settings=" + settings +
                '}';
    }
}
