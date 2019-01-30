package io.knativeflow.model;

import io.knativeflow.serialization.SerializedFlow;
import io.knativeflow.serialization.SerializedNode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Contains a connected graph of processor nodes
 *
 * @author hhiden
 */
public class ProcessorFlow implements Serializable {

    private List<ProcessorNode> nodes = new ArrayList<>();
    private String name;

    public ProcessorFlow() {
    }

    public ProcessorFlow(SerializedFlow flow) {

        this.setName(flow.getName());
        
        // Add the nodes
        flow.getNodes().stream()
                .map(SerializedNode::createNode)
                .forEach(this::addProcessorNode);

        // Connect them together
        flow.getLinks().forEach(link -> this.linkNodes(link.getSourceUuid(), link.getTargetUuid()));

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ProcessorNode> getNodes() {
        return nodes;
    }

    public void setNodes(List<ProcessorNode> nodes) {
        this.nodes = nodes;
    }

    public void addProcessorNode(ProcessorNode node) {
        if (node.getUuid() == null || node.getUuid().isEmpty()) {
            node.setUuid(UUID.randomUUID().toString());
        }
        node.setParent(this);
        nodes.add(node);
    }

    public void linkNodes(String sourceUuid, String targetUuid) {
        linkNodes(getNodeByUuid(sourceUuid), getNodeByUuid(targetUuid));
    }

    public void linkNodes(ProcessorNode source, ProcessorNode target) {
        ProcessorOutputPort sourcePort = source.getOutput();
        ProcessorInputPort targetPort = target.getInput();
        if (sourcePort != null && targetPort != null) {
            ProcessorLink link = new ProcessorLink();
            link.setSource(sourcePort);
            link.setTarget(targetPort);
            sourcePort.addLink(link);
            targetPort.addLink(link);
        }
    }

    /**
     * Returns the links so that nodes can be reconnected
     */
    public List<ProcessorLink> getLinks() {
        List<ProcessorLink> links = new ArrayList<>();
        for (ProcessorNode n : nodes) {
            // Add the output links for each node
            ProcessorOutputPort output = n.getOutput();
            for (ProcessorLink link : output.getLinks()) {
                links.add(link);
            }
        }
        return links;
    }

    public ProcessorNode getNodeByUuid(String uuid) {
        for (ProcessorNode n : nodes) {
            if (n.getUuid().equals(uuid)) {
                return n;
            }
        }
        return null;
    }
}