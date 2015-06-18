package org.cxio.aspects.writers;

import java.io.IOException;
import java.util.List;

import org.cxio.aspects.datamodels.NodesElement;
import org.cxio.core.CxConstants;
import org.cxio.core.JsonWriter;
import org.cxio.core.interfaces.AspectElement;
import org.cxio.core.interfaces.AspectFragmentWriter;

public class NodesFragmentWriter implements AspectFragmentWriter {

    public static NodesFragmentWriter createInstance() {
        return new NodesFragmentWriter();
    }

    private NodesFragmentWriter() {
    }

    private final void addNode(final String node_id, final JsonWriter w) throws IOException {
        w.writeStartObject();
        w.writeStringField(CxConstants.ID, node_id);
        w.writeEndObject();
    }

    @Override
    public final void write(final List<AspectElement> node_aspects, final JsonWriter w) throws IOException {
        if (node_aspects == null) {
            return;
        }
        w.startArray(NodesElement.NODES);
        for (final AspectElement node_aspect : node_aspects) {
            addNode(((NodesElement) node_aspect).getId(), w);
        }
        w.endArray();
    }

    @Override
    public String getAspectName() {
        return NodesElement.NODES;
    }

}
