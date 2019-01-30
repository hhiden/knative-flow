package io.knativeflow.model;

/**
 * An output
 *
 * @author hhiden
 */
public class ProcessorOutputPort extends ProcessorPort {

    public ProcessorOutputPort() {
        super();
    }

    @Override
    public void addLink(ProcessorLink link) {
        links.add(link);
    }

}