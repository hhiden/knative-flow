package io.knativeflow.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a connection "port" that can send / receive events
 *
 * @author hhiden
 */
public abstract class ProcessorPort {
    protected ProcessorNode parent;
    protected List<ProcessorLink> links = new ArrayList<>();

    public ProcessorPort() {
    }

    public List<ProcessorLink> getLinks() {
        return links;
    }

    public void setLinks(List<ProcessorLink> links) {
        this.links = links;
    }

    public ProcessorNode getParent() {
        return parent;
    }

    public void setParent(ProcessorNode parent) {
        this.parent = parent;
    }


    public abstract void addLink(ProcessorLink link);

}
