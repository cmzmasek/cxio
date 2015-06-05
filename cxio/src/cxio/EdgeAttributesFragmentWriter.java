package cxio;

import java.io.IOException;
import java.util.List;
import java.util.Map.Entry;

public class EdgeAttributesFragmentWriter implements AspectFragmentWriter {
    public static EdgeAttributesFragmentWriter createInstance() {
        return new EdgeAttributesFragmentWriter();
    }

    private EdgeAttributesFragmentWriter() {
    }

    private final void addEdgeAttributesAspect(final EdgeAttributesElement ea, final JsonWriter w) throws IOException {
        w.writeStartObject();
        w.writeStringField(CxConstants.ID, ea.getId());
        w.writeList(CxConstants.EDGES, ea.getEdges());
        if ((ea.getAttributes() != null) && !ea.getAttributes().isEmpty()) {
            w.writeObjectFieldStart(CxConstants.ATTRIBUTES);
            for (final Entry<String, List<String>> a : ea.getAttributes().entrySet()) {
                w.writeList(a.getKey(), a.getValue());
            }
            w.writeEndObject();
        }
        w.writeEndObject();
    }

    @Override
    public void write(final List<AspectElement> edge_attributes_aspects, final JsonWriter w) throws IOException {
        if (edge_attributes_aspects == null) {
            return;
        }
        w.startArray(CxConstants.EDGE_ATTRIBUTES);
        for (final AspectElement edge_attributes_aspect : edge_attributes_aspects) {
            final EdgeAttributesElement ea = (EdgeAttributesElement) edge_attributes_aspect;
            addEdgeAttributesAspect(ea, w);
        }
        w.endArray();
    }
    
    @Override
    public String getAspectName() {
        return CxConstants.EDGE_ATTRIBUTES;
    }

}
