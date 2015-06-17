package org.cxio.aspects.datamodels;

import org.cxio.core.interfaces.AspectElement;

public final class EdgesElement implements AspectElement {

    final private String       id;
    final private String       source;
    final private String       target;
    public final static String TARGET_NODE_ID = "target";
    public final static String SOURCE_NODE_ID = "source";
    final public static String EDGES          = "edges";

    public EdgesElement(final String id, final String source, final String target) {
        this.id = id;
        this.source = source;
        this.target = target;
    }

    public EdgesElement(final long id, final String source, final String target) {
        this.id = String.valueOf(id);
        this.source = source;
        this.target = target;
    }

    public EdgesElement(final long id, final long source, final long target) {
        this.id = String.valueOf(id);
        this.source = String.valueOf(source);
        this.target = String.valueOf(target);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        return (o instanceof EdgesElement) && id.equals(((EdgesElement) o).getId());

    }

    @Override
    public String getAspectName() {
        return EdgesElement.EDGES;
    }

    public final String getId() {
        return id;
    }

    public final String getSource() {
        return source;
    }

    public final String getTarget() {
        return target;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(getId());
        sb.append(" ");
        sb.append(getSource());
        sb.append(" ");
        sb.append(getTarget());
        return sb.toString();
    }

}
