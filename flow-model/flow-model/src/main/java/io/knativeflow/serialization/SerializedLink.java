package io.knativeflow.serialization;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.knativeflow.model.ProcessorLink;


/**
 * @author hhiden
 */
public class SerializedLink {
    @JsonIgnore
    private ProcessorLink link;

    private String sourceUuid;
    private String targetUuid;

    public SerializedLink() {
    }

    public SerializedLink(ProcessorLink link) {
        setLink(link);
    }

    public void setLink(ProcessorLink link) {
        this.link = link;
        this.sourceUuid = link.getSource().getParent().getUuid();
        this.targetUuid = link.getTarget().getParent().getUuid();
    }

    public String getSourceUuid() {
        return sourceUuid;
    }

    public void setSourceUuid(String sourceUuid) {
        this.sourceUuid = sourceUuid;
    }

    public String getTargetUuid() {
        return targetUuid;
    }

    public void setTargetUuid(String targetUuid) {
        this.targetUuid = targetUuid;
    }

    @Override
    public String toString() {
        return "SerializedLink{" +
                "link=" + link +
                ", sourceUuid='" + sourceUuid + '\'' +
                ", targetUuid='" + targetUuid + "\'}";
    }
}
