package io.knativeflow.crds;

import io.fabric8.kubernetes.client.CustomResource;
import io.knativeflow.serialization.SerializedFlow;


public class Flow extends CustomResource {

    private SerializedFlow spec;

    @Override
    public String toString() {
        return "Flow{" +
                "apiVersion='" + getApiVersion() + '\'' +
                ", metadata=" + getMetadata() +
                ", spec=" + spec +
                '}';
    }

    public SerializedFlow getSpec() {
        return spec;
    }

    public void setSpec(SerializedFlow spec) {
        this.spec = spec;
    }
}
