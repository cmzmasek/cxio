package org.cxio.tools;

import java.util.HashSet;
import java.util.Set;

import org.cxio.aspects.readers.CartesianLayoutFragmentReader;
import org.cxio.aspects.readers.EdgeAttributesFragmentReader;
import org.cxio.aspects.readers.EdgesFragmentReader;
import org.cxio.aspects.readers.NodeAttributesFragmentReader;
import org.cxio.aspects.readers.NodesFragmentReader;
import org.cxio.core.interfaces.AspectFragmentReader;

public final class AspectFragmentReaderManager {

    public static AspectFragmentReaderManager createInstance() {
        return new AspectFragmentReaderManager();
    }

    public final Set<AspectFragmentReader> getAvailableAspectFragmentReaders() {
        final AspectFragmentReader node_handler = NodesFragmentReader.createInstance();
        final AspectFragmentReader edge_handler = EdgesFragmentReader.createInstance();
        final AspectFragmentReader cartesian_layout_handler = CartesianLayoutFragmentReader.createInstance();
        final AspectFragmentReader edge_attributes_handler = EdgeAttributesFragmentReader.createInstance();
        final AspectFragmentReader node_attributes_handler = NodeAttributesFragmentReader.createInstance();
        final Set<AspectFragmentReader> aspect_handlers = new HashSet<AspectFragmentReader>();
        aspect_handlers.add(node_handler);
        aspect_handlers.add(edge_handler);
        aspect_handlers.add(cartesian_layout_handler);
        aspect_handlers.add(edge_attributes_handler);
        aspect_handlers.add(node_attributes_handler);
        return aspect_handlers;
    }

}
