package io.knativeflow.model;

/**
 * An input port
 *
 * @author hhiden
 */
public class ProcessorInputPort extends ProcessorPort {

    public ProcessorInputPort() {
        super();
    }

    @Override
    public void addLink(ProcessorLink link) {
        if (links.isEmpty()) {
            links.add(link);
        }
    }

}