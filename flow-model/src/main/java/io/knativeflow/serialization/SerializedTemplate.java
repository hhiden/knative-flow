package io.knativeflow.serialization;

import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.fabric8.kubernetes.api.model.KubernetesResource;
import io.knativeflow.model.ProcessorNode;
import java.util.Map;

@JsonDeserialize(
        using = JsonDeserializer.None.class
)
public class SerializedTemplate implements KubernetesResource {

    private String displayName;

    private String description;

    private String kSVCName;


    private Map<String, String> settings;

    @Override
    public String toString() {
        return "SerializedTemplate{" +
                ", displayName='" + displayName + '\'' +
                ", description='" + description + '\'' +
                ", settings=" + settings +
                '}';
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Map<String, String> getSettings() {
        return settings;
    }

    public void setSettings(Map<String, String> settings) {
        this.settings = settings;
    }

    public ProcessorNode createProcessorNode() {
        ProcessorNode node = new ProcessorNode();
        node.setSettings(settings);
        node.setDisplayName(displayName);
        node.setkSVCName(kSVCName);
        return node;
    }
}
