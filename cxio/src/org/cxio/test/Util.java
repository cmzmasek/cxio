package org.cxio.test;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.SortedMap;

import org.cxio.AspectElement;
import org.cxio.AspectFragmentReaderManager;
import org.cxio.CartesianLayoutFragmentWriter;
import org.cxio.CxConstants;
import org.cxio.CxReader;
import org.cxio.CxWriter;
import org.cxio.EdgeAttributesFragmentWriter;
import org.cxio.EdgesFragmentWriter;
import org.cxio.NodeAttributesFragmentWriter;
import org.cxio.NodesFragmentWriter;



final class TestUtil {

    final static String cyCxRoundTrip(final String input_cx) throws IOException {
        final CxReader p = CxReader.createInstance(input_cx, AspectFragmentReaderManager
                .createInstance().getAvailableAspectFragmentReaders());
        final SortedMap<String, List<AspectElement>> res = CxReader.parseAsMap(p);

        final OutputStream out = new ByteArrayOutputStream();

        final CxWriter w = CxWriter.createInstance(out);
        w.addAspectFragmentWriter(NodesFragmentWriter.createInstance());
        w.addAspectFragmentWriter(EdgesFragmentWriter.createInstance());
        w.addAspectFragmentWriter(CartesianLayoutFragmentWriter.createInstance());
        w.addAspectFragmentWriter(NodeAttributesFragmentWriter.createInstance());
        w.addAspectFragmentWriter(EdgeAttributesFragmentWriter.createInstance());

        w.start();
        w.write(res.get(CxConstants.NODES));
        w.write(res.get(CxConstants.EDGES));
        w.write(res.get(CxConstants.CARTESIAN_LAYOUT));
        w.write(res.get(CxConstants.NODE_ATTRIBUTES));
        w.write(res.get(CxConstants.EDGE_ATTRIBUTES));
        w.end();

        return out.toString();
    }
}
