package org.cxio.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;
import java.util.SortedMap;

import org.cxio.aspects.datamodels.AbstractAttributesElement.ATTRIBUTE_TYPE;
import org.cxio.aspects.datamodels.EdgeAttributesElement;
import org.cxio.core.CxReader;
import org.cxio.core.interfaces.AspectElement;
import org.cxio.util.AspectFragmentReaderManager;
import org.junit.Test;

public class EdgeAttributesFragmentReaderTest {

    @Test
    public void test() throws IOException {
        final String t0 = "["
                + "{\"nodes_we_ignore\":[{\"@id\":\"_0\"},{\"@id\":\"_1\"},{\"@id\":\"_2\"},{\"@id\":\"_3\"}]},"
                + "{\"nodes\":[{\"@id\":\"_0\"},{\"@id\":\"_1\"},{\"@id\":\"_2\"},{\"@id\":\"_3\"}]},"
                + "{\"edges\":[{\"@id\":\"e0\",\"source\":\"_0\",\"target\":\"_1\"},{\"@id\":\"e1\",\"source\":\"_1\",\"target\":\"_2\"}]},"
                + "{\"nodeIdentities\":[{\"@id\":\"ni0\",\"nodes\":\"_0\",\"represents\":\"name is zero\"},{\"@id\":\"ni1\",\"node\":\"_1\",\"represents\":\"name is one\"}]},"
                + "{\"edgeIdentities\":[{\"@id\":\"ei0\",\"edges\":\"e0\",\"relationship\":\"BEL:INCREASES\"},{\"@id\":\"ei1\",\"edge\":\"e1\",\"relationship\":\"BEL:DECREASES\"}]},"
                + "{\"elementProperties\":[{\"@id\":\"ep0\",\"elementId\":\"_0\",\"property\":\"property zero\",\"value\":\"value is zero\"},{\"@id\":\"ep1\",\"elementId\":\"_1\",\"property\":\"propery one\",\"value\":\"value is one\"}]},"
                + "{\"functionTerms\":[{\"@id\":\"ft0\",\"function\":\"functions zero\",\"parameters\":[\"HGNC:FAS\",\"HGNC:MAPK1\"]},{\"@id\":\"ft1\",\"function\":\"functions one\",\"parameters\":[\"HGNC:FAS\",\"HGNC:MAPK1\"]}]},"
                + "{\"weHaveNodesAndEdges\":[{\"nodes\":[{\"@id\":\"_0\"},{\"@id\":\"_1\"}]}]},"
                + "{\"weHaveNodesAndEdges\":[{\"edges\":[{\"@id\":\"e0\",\"source\":\"_0\",\"target\":\"_1\"}]}]},"
                + "{\"weHaveNodesToo\":[{\"nodes\":\"nodes\"}]},"
                + "{\"weHaveEdgesToo\":[{\"edges\":\"edges\"}]},"
                + "{\"nodes\":[{\"@id\":\"_5\"}]},"
                + "{\"edges\":[{\"@id\":\"e2\",\"source\":\"_4\",\"target\":\"_5\"}]},"
                + "{\"edges\":[{\"@id\":\"e3\",\"source\":\"_6\",\"target\":\"_7\"}]},"
                + "{\"cartesianLayout\":[{\"node\":\"_0\",\"x\":\"123\",\"y\":\"456\"}]},"
                + "{\"nodes\":[{\"@id\":\"_4\"}]},"
                + "{\"nodes\":[{\"@id\":\"_6\"}]},"
                + "{\"cartesianLayout\":[{\"node\":\"_1\",\"x\":\"3\",\"y\":\"4\"},{\"node\":\"_2\",\"x\":\"5\",\"y\":\"6\"}]},"
                + "{\"nodes\":[{\"@id\":\"_7\"}]},"
                + "{\"edgeAttributes\":[{\"@id\":\"_ea0\",\"edges\":[\"_e38\", \"_e39\"], \"attributes\":{\"interaction\":[\"479019\", \"one more\"],\"name\":[\"768303 (479019) 791595\"],\"PSIMI_25_detection_method\":[\"genetic interference\"]}}]},"
                + "{\"edgeAttributes\":[{\"@id\":\"_ea1\",\"edges\":[\"_e22\", \"_e33\", \"_e44\"]}]},"
                + "{\"edgeAttributes\":[{\"@id\":\"_ea2\",\"edges\":[\"_e38\"], \"attributes\":{\"deleted\":[\"true\"]}, \"types\":{\"deleted\":\"boolean\"}}]}"
                + "]";

        final CxReader p = CxReader.createInstance(t0, AspectFragmentReaderManager.createInstance()
                .getAvailableAspectFragmentReaders());
        final SortedMap<String, List<AspectElement>> r0 = CxReader.parseAsMap(p);

        assertTrue("failed to parse " + EdgeAttributesElement.NAME + " aspect",
                r0.containsKey(EdgeAttributesElement.NAME));
        assertFalse("failed to parse " + EdgeAttributesElement.NAME + " aspect", r0.get(EdgeAttributesElement.NAME)
                .isEmpty());
        assertTrue("failed to get expected number of " + EdgeAttributesElement.NAME + " aspects",
                r0.get(EdgeAttributesElement.NAME).size() == 3);

        final List<AspectElement> aspects = r0.get(EdgeAttributesElement.NAME);

        final EdgeAttributesElement ea1 = (EdgeAttributesElement) aspects.get(0);
        assertTrue(ea1.getId().equals("_ea0"));
        assertTrue(ea1.getEdges().size() == 2);
        assertTrue(ea1.getAttributes().size() == 3);

        assertTrue(ea1.getEdges().contains("_e38"));
        assertTrue(ea1.getEdges().contains("_e39"));
        assertTrue(ea1.getValues("interaction").size() == 2);
        assertTrue(ea1.getValues("PSIMI_25_detection_method").size() == 1);
        assertTrue(ea1.getValues("name").size() == 1);
        assertTrue(ea1.getValues("interaction").contains("479019"));
        assertTrue(ea1.getValues("interaction").contains("one more"));
        assertTrue(ea1.getValues("PSIMI_25_detection_method").contains("genetic interference"));
        assertTrue(ea1.getValues("name").contains("768303 (479019) 791595"));

        final EdgeAttributesElement ea2 = (EdgeAttributesElement) aspects.get(1);
        assertTrue(ea2.getId().equals("_ea1"));
        assertTrue(ea2.getEdges().size() == 3);
        assertTrue(ea2.getAttributes().size() == 0);
        assertTrue(ea2.getEdges().contains("_e22"));
        assertTrue(ea2.getEdges().contains("_e33"));
        assertTrue(ea2.getEdges().contains("_e44"));

        final EdgeAttributesElement ea3 = (EdgeAttributesElement) aspects.get(2);
        assertTrue(ea3.getType("deleted") == ATTRIBUTE_TYPE.BOOLEAN);

    }

}
