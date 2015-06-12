package org.cxio;

public final class NodesElement implements AspectElement {

    private final String id;

    public NodesElement(final String id) {
        this.id = id;
    }

    public NodesElement(final long id) {
        this.id = String.valueOf(id);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        return (o instanceof NodesElement) && id.equals(((NodesElement) o).getId());

    }

    @Override
    final public String getAspectName() {
        return CxConstants.NODES;
    }

    final public String getId() {
        return id;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return id;
    }

}
