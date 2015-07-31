package org.cxio.aspects.datamodels;

import org.cxio.core.CxConstants;
import org.cxio.core.interfaces.AspectElement;

/**
 * This is used to represent a Cytoscape node aspect element.
 *
 *
 * @author cmzmasek
 *
 */
public final class NodesElement implements AspectElement {

    public final static String ID   = CxConstants.ID;
    public final static String NAME = "nodes";
    private final String       _id;

    public NodesElement(final long id) {
        _id = String.valueOf(id);
    }

    public NodesElement(final String id) {
        _id = id;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        return (o instanceof NodesElement) && _id.equals(((NodesElement) o).getId());
    }

    @Override
    final public String getAspectName() {
        return NodesElement.NAME;
    }

    final public String getId() {
        return _id;
    }

    @Override
    public int hashCode() {
        return _id.hashCode();
    }

    @Override
    public String toString() {
        return _id;
    }

}
