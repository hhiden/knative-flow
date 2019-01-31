package io.knativeflow.serialization;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.fabric8.kubernetes.api.model.KubernetesResource;
import io.knativeflow.model.ProcessorFlow;
import io.knativeflow.model.ProcessorLink;
import io.knativeflow.model.ProcessorNode;
import io.knativeflow.model.ProcessorOutputPort;


import java.util.ArrayList;
import java.util.List;

/**
 * Serialized form of flow
 *
 * @author hhiden
 */
@JsonDeserialize(
        using = JsonDeserializer.None.class
)
public class SerializedFlow implements KubernetesResource {

    @JsonIgnore
    private ProcessorFlow flow;

    private String name;

    private List<SerializedNode> nodes = new ArrayList<>();
    private List<SerializedLink> links = new ArrayList<>();


    public SerializedFlow() {
    }

    public SerializedFlow(ProcessorFlow flow) {
        this.flow = flow;
        this.name = flow.getName();
        for (ProcessorNode n : flow.getNodes()) {
            nodes.add(new SerializedNode(n));
            if(n.hasOutput()){
                ProcessorOutputPort output = n.getOutput();
    
                for (ProcessorLink link : output.getLinks()) {
                    links.add(new SerializedLink(link));
                }
            }
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<SerializedNode> getNodes() {
        return nodes;
    }

    public List<SerializedLink> getLinks() {
        return links;
    }

    public void setLinks(List<SerializedLink> links) {
        this.links = links;
    }

    public void setNodes(List<SerializedNode> nodes) {
        this.nodes = nodes;
    }

    @Override
    public String toString() {
        return "SerializedFlow{" +
                "flow=" + flow +
                ", name='" + name + '\'' +
                ", nodes=" + nodes +
                ", links=" + links +
                '}';
    }
}