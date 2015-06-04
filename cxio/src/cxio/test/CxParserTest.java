package cxio.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;

import org.junit.Test;

import cxio.AspectElement;
import cxio.AspectFragmentReaderManager;
import cxio.CartesianLayoutElement;
import cxio.Cx;
import cxio.CxParser;
import cxio.EdgeElement;
import cxio.NodeElement;

public class CxParserTest {

    @Test
    public void testEmpty1() throws IOException {

        final String j = "[]";
        final CxParser p = CxParser.createInstance(j, AspectFragmentReaderManager.createInstance()
                .getAvailableAspectReaders());

        assertFalse(p.hasNext());
        assertEquals(p.getNext(), null);
        assertEquals(p.getNext(), null);
        assertEquals(p.getNext(), null);
        p.reset();
        assertEquals(p.getNext(), null);
        assertFalse(p.hasNext());
        assertEquals(p.getNext(), null);
        assertEquals(p.getNext(), null);

    }

    @Test
    public void testEmpty2() throws IOException {
        final String j = "[{}]";
        final CxParser p = CxParser.createInstance(j, AspectFragmentReaderManager.createInstance()
                .getAvailableAspectReaders());

        assertFalse(p.hasNext());
        assertEquals(p.getNext(), null);
        assertEquals(p.getNext(), null);
        assertEquals(p.getNext(), null);
        p.reset();
        assertEquals(p.getNext(), null);
        assertFalse(p.hasNext());
        assertEquals(p.getNext(), null);
        assertEquals(p.getNext(), null);

    }
    
    @Test
    public void test1() throws IOException {
        final String j = "[{\"key\":\"value\"}]";
        final CxParser p = CxParser.createInstance(j, AspectFragmentReaderManager.createInstance()
                .getAvailableAspectReaders());

        assertFalse(p.hasNext());
        assertEquals(p.getNext(), null);
        assertEquals(p.getNext(), null);
        assertEquals(p.getNext(), null);
        p.reset();
        assertEquals(p.getNext(), null);
        assertFalse(p.hasNext());
        assertEquals(p.getNext(), null);
        assertEquals(p.getNext(), null);

    }

    @Test
    public void test2() throws IOException {
        final String j = "[{\"nodes_we_ignore\":[{\"@id\":\"_0\"},{\"@id\":\"_1\"},{\"@id\":\"_2\"},{\"@id\":\"_3\"}]}]";
        final CxParser p = CxParser.createInstance(j, AspectFragmentReaderManager.createInstance()
                .getAvailableAspectReaders());

        assertFalse(p.hasNext());
        assertEquals(p.getNext(), null);
        assertEquals(p.getNext(), null);
        assertEquals(p.getNext(), null);
        p.reset();
        assertEquals(p.getNext(), null);
        assertFalse(p.hasNext());
        assertEquals(p.getNext(), null);
        assertEquals(p.getNext(), null);

    }

    @Test
    public void test3() throws IOException {
        final String j = "[{\"nodes\":[{\"@id\":\"_0\"},{\"@id\":\"_1\"},{\"@id\":\"_2\"},{\"@id\":\"_3\"}]}]";
        final CxParser p = CxParser.createInstance(j, AspectFragmentReaderManager.createInstance()
                .getAvailableAspectReaders());

        assertTrue(p.hasNext());
        final List<AspectElement> x = p.getNext();
        assertFalse(x == null);
        assertFalse(p.hasNext());
        assertEquals(p.getNext(), null);
        assertEquals(p.getNext(), null);
        assertFalse(p.hasNext());
        assertTrue(x.size() == 4);
        assertTrue("failed to get expected NodeAspect instance", x.get(0) instanceof NodeElement);
        assertTrue("failed to get expected " + Cx.NODES + " aspect", x.contains(new NodeElement("_0")));
        assertTrue("failed to get expected " + Cx.NODES + " aspect", x.contains(new NodeElement("_1")));
        assertTrue("failed to get expected " + Cx.NODES + " aspect", x.contains(new NodeElement("_2")));
        assertTrue("failed to get expected " + Cx.NODES + " aspect", x.contains(new NodeElement("_3")));
        p.reset();
        assertTrue(p.hasNext());
        final List<AspectElement> y = p.getNext();
        assertFalse(y == null);
        assertFalse(p.hasNext());
        assertEquals(p.getNext(), null);
        assertEquals(p.getNext(), null);
        assertFalse(p.hasNext());
        assertTrue(y.size() == 4);
        assertTrue("failed to get expected NodeAspect instance", y.get(0) instanceof NodeElement);
        assertTrue("failed to get expected " + Cx.NODES + " aspect", y.contains(new NodeElement("_0")));
        assertTrue("failed to get expected " + Cx.NODES + " aspect", y.contains(new NodeElement("_1")));
        assertTrue("failed to get expected " + Cx.NODES + " aspect", y.contains(new NodeElement("_2")));
        assertTrue("failed to get expected " + Cx.NODES + " aspect", y.contains(new NodeElement("_3")));

    }

    @Test
    public void test4() throws IOException {
        final String j = "["
                + "{\"nodes\":[{\"@id\":\"_0\"},{\"@id\":\"_1\"},{\"@id\":\"_2\"},{\"@id\":\"_3\"}]},"
                + "{\"edges\":[{\"@id\":\"e0\",\"source\":\"_0\",\"target\":\"_1\"},{\"@id\":\"e1\",\"source\":\"_1\",\"target\":\"_2\"}]}"
                + "]";
        final CxParser p = CxParser.createInstance(j, AspectFragmentReaderManager.createInstance()
                .getAvailableAspectReaders());
        assertTrue(p.hasNext());
        final List<AspectElement> x = p.getNext();
        assertFalse(x == null);
        assertTrue(p.hasNext());
        assertTrue(x.size() == 4);
        assertTrue("failed to get expected NodeAspect instance", x.get(0) instanceof NodeElement);
        assertTrue("failed to get expected " + Cx.NODES + " aspect", x.contains(new NodeElement("_0")));
        assertTrue("failed to get expected " + Cx.NODES + " aspect", x.contains(new NodeElement("_1")));
        assertTrue("failed to get expected " + Cx.NODES + " aspect", x.contains(new NodeElement("_2")));
        assertTrue("failed to get expected " + Cx.NODES + " aspect", x.contains(new NodeElement("_3")));
        final List<AspectElement> e = p.getNext();
        assertFalse(e == null);
        assertFalse(p.hasNext());
        assertTrue(e.size() == 2);
        assertTrue("failed to get expected EdgeAspect instance", e.get(0) instanceof EdgeElement);
        assertFalse(p.hasNext());
        assertEquals(p.getNext(), null);
        assertEquals(p.getNext(), null);
        assertFalse(p.hasNext());

        p.reset();
        assertTrue(p.hasNext());
        final List<AspectElement> x1 = p.getNext();
        assertFalse(x1 == null);
        assertTrue(p.hasNext());
        assertTrue(p.hasNext());
        assertTrue(x1.size() == 4);
        assertTrue("failed to get expected NodeAspect instance", x1.get(0) instanceof NodeElement);
        assertTrue("failed to get expected " + Cx.NODES + " aspect", x.contains(new NodeElement("_0")));
        assertTrue("failed to get expected " + Cx.NODES + " aspect", x.contains(new NodeElement("_1")));
        assertTrue("failed to get expected " + Cx.NODES + " aspect", x.contains(new NodeElement("_2")));
        assertTrue("failed to get expected " + Cx.NODES + " aspect", x.contains(new NodeElement("_3")));
        final List<AspectElement> e1 = p.getNext();
        assertFalse(e1 == null);
        assertFalse(p.hasNext());
        assertTrue(e1.size() == 2);
        assertTrue("failed to get expected EdgeAspect instance", e1.get(0) instanceof EdgeElement);
        assertFalse(p.hasNext());
        assertEquals(p.getNext(), null);
        assertEquals(p.getNext(), null);
        assertFalse(p.hasNext());

    }

    @Test
    public void test5() throws IOException {
        final String j = "["
                + "{\"key\":\"value\"},"
                + "{\"nodes\":[{\"@id\":\"_0\"},{\"@id\":\"_1\"},{\"@id\":\"_2\"},{\"@id\":\"_3\"}]},"
                + "{\"key\":\"value\"},"
                + "{\"edges\":[{\"@id\":\"e0\",\"source\":\"_0\",\"target\":\"_1\"},{\"@id\":\"e1\",\"source\":\"_1\",\"target\":\"_2\"}]},"
                + "{\"key\":\"value\"}" + "]";
        final CxParser p = CxParser.createInstance(j, AspectFragmentReaderManager.createInstance()
                .getAvailableAspectReaders());

        assertTrue(p.hasNext());
        final List<AspectElement> x = p.getNext();
        assertFalse(x == null);
        assertTrue(p.hasNext());
        assertTrue(x.size() == 4);
        assertTrue("failed to get expected NodeAspect instance", x.get(0) instanceof NodeElement);
        assertTrue("failed to get expected " + Cx.NODES + " aspect", x.contains(new NodeElement("_0")));
        assertTrue("failed to get expected " + Cx.NODES + " aspect", x.contains(new NodeElement("_1")));
        assertTrue("failed to get expected " + Cx.NODES + " aspect", x.contains(new NodeElement("_2")));
        assertTrue("failed to get expected " + Cx.NODES + " aspect", x.contains(new NodeElement("_3")));
        final List<AspectElement> e = p.getNext();
        assertFalse(e == null);
        assertFalse(p.hasNext());
        assertTrue(e.size() == 2);
        assertTrue("failed to get expected EdgeAspect instance", e.get(0) instanceof EdgeElement);
        assertFalse(p.hasNext());
        assertEquals(p.getNext(), null);
        assertEquals(p.getNext(), null);
        assertFalse(p.hasNext());

        p.reset();
        p.reset();
        p.reset();

        assertTrue(p.hasNext());
        final List<AspectElement> x1 = p.getNext();
        assertFalse(x1 == null);
        assertTrue(p.hasNext());
        assertTrue(p.hasNext());
        assertTrue(x1.size() == 4);
        assertTrue("failed to get expected NodeAspect instance", x1.get(0) instanceof NodeElement);
        assertTrue("failed to get expected " + Cx.NODES + " aspect", x.contains(new NodeElement("_0")));
        assertTrue("failed to get expected " + Cx.NODES + " aspect", x.contains(new NodeElement("_1")));
        assertTrue("failed to get expected " + Cx.NODES + " aspect", x.contains(new NodeElement("_2")));
        assertTrue("failed to get expected " + Cx.NODES + " aspect", x.contains(new NodeElement("_3")));
        final List<AspectElement> e1 = p.getNext();
        assertFalse(e1 == null);
        assertFalse(p.hasNext());
        assertTrue(e1.size() == 2);
        assertTrue("failed to get expected EdgeAspect instance", e1.get(0) instanceof EdgeElement);
        assertFalse(p.hasNext());
        assertEquals(p.getNext(), null);
        assertEquals(p.getNext(), null);
        assertFalse(p.hasNext());

    }

    @Test
    public void test6() throws IOException {
        final String j = "["

                + "{\"nodes\":[{\"@id\":\"_0\"},{\"@id\":\"_1\"},{\"@id\":\"_2\"},{\"@id\":\"_3\"}]},"
                + "{\"key\":\"value\"},"
                + "{\"edges\":[{\"@id\":\"e0\",\"source\":\"_0\",\"target\":\"_1\"},{\"@id\":\"e1\",\"source\":\"_1\",\"target\":\"_2\"}]},"
                + "{\"key\":\"value\"}" + "]";
        final CxParser p = CxParser.createInstance(j, AspectFragmentReaderManager.createInstance()
                .getAvailableAspectReaders());

        assertTrue(p.hasNext());
        final List<AspectElement> x = p.getNext();
        assertFalse(x == null);
        assertTrue(p.hasNext());
        assertTrue(x.size() == 4);
        assertTrue("failed to get expected NodeAspect instance", x.get(0) instanceof NodeElement);
        assertTrue("failed to get expected " + Cx.NODES + " aspect", x.contains(new NodeElement("_0")));
        assertTrue("failed to get expected " + Cx.NODES + " aspect", x.contains(new NodeElement("_1")));
        assertTrue("failed to get expected " + Cx.NODES + " aspect", x.contains(new NodeElement("_2")));
        assertTrue("failed to get expected " + Cx.NODES + " aspect", x.contains(new NodeElement("_3")));
        final List<AspectElement> e = p.getNext();
        assertFalse(e == null);
        assertFalse(p.hasNext());
        assertTrue(e.size() == 2);
        assertTrue("failed to get expected EdgeAspect instance", e.get(0) instanceof EdgeElement);
        assertFalse(p.hasNext());
        assertEquals(p.getNext(), null);
        assertEquals(p.getNext(), null);
        assertFalse(p.hasNext());

        p.reset();
        assertTrue(p.hasNext());
        final List<AspectElement> x1 = p.getNext();
        assertFalse(x1 == null);
        assertTrue(p.hasNext());
        assertTrue(p.hasNext());
        assertTrue(x1.size() == 4);
        assertTrue("failed to get expected NodeAspect instance", x1.get(0) instanceof NodeElement);
        assertTrue("failed to get expected " + Cx.NODES + " aspect", x.contains(new NodeElement("_0")));
        assertTrue("failed to get expected " + Cx.NODES + " aspect", x.contains(new NodeElement("_1")));
        assertTrue("failed to get expected " + Cx.NODES + " aspect", x.contains(new NodeElement("_2")));
        assertTrue("failed to get expected " + Cx.NODES + " aspect", x.contains(new NodeElement("_3")));
        final List<AspectElement> e1 = p.getNext();
        assertFalse(e1 == null);
        assertFalse(p.hasNext());
        assertTrue(e1.size() == 2);
        assertTrue("failed to get expected EdgeAspect instance", e1.get(0) instanceof EdgeElement);
        assertFalse(p.hasNext());
        assertEquals(p.getNext(), null);
        assertEquals(p.getNext(), null);
        assertFalse(p.hasNext());

    }

    @Test
    public void test7() throws IOException {
        final String j = "["
                + "{\"key\":\"value\"},"
                + "{\"nodes\":[{\"@id\":\"_0\"},{\"@id\":\"_1\"},{\"@id\":\"_2\"},{\"@id\":\"_3\"}]},"

                + "{\"edges\":[{\"@id\":\"e0\",\"source\":\"_0\",\"target\":\"_1\"},{\"@id\":\"e1\",\"source\":\"_1\",\"target\":\"_2\"}]},"
                + "{\"key\":\"value\"}" + "]";
        final CxParser p = CxParser.createInstance(j, AspectFragmentReaderManager.createInstance()
                .getAvailableAspectReaders());

        assertTrue(p.hasNext());
        final List<AspectElement> x = p.getNext();
        assertFalse(x == null);
        assertTrue(p.hasNext());
        assertTrue(x.size() == 4);
        assertTrue("failed to get expected NodeAspect instance", x.get(0) instanceof NodeElement);
        assertTrue("failed to get expected " + Cx.NODES + " aspect", x.contains(new NodeElement("_0")));
        assertTrue("failed to get expected " + Cx.NODES + " aspect", x.contains(new NodeElement("_1")));
        assertTrue("failed to get expected " + Cx.NODES + " aspect", x.contains(new NodeElement("_2")));
        assertTrue("failed to get expected " + Cx.NODES + " aspect", x.contains(new NodeElement("_3")));
        final List<AspectElement> e = p.getNext();
        assertFalse(e == null);
        assertFalse(p.hasNext());
        assertTrue(e.size() == 2);
        assertTrue("failed to get expected EdgeAspect instance", e.get(0) instanceof EdgeElement);
        assertFalse(p.hasNext());
        assertEquals(p.getNext(), null);
        assertEquals(p.getNext(), null);
        assertFalse(p.hasNext());

        p.reset();
        assertTrue(p.hasNext());
        final List<AspectElement> x1 = p.getNext();
        assertFalse(x1 == null);
        assertTrue(p.hasNext());
        assertTrue(p.hasNext());
        assertTrue(x1.size() == 4);
        assertTrue("failed to get expected NodeAspect instance", x1.get(0) instanceof NodeElement);
        assertTrue("failed to get expected " + Cx.NODES + " aspect", x.contains(new NodeElement("_0")));
        assertTrue("failed to get expected " + Cx.NODES + " aspect", x.contains(new NodeElement("_1")));
        assertTrue("failed to get expected " + Cx.NODES + " aspect", x.contains(new NodeElement("_2")));
        assertTrue("failed to get expected " + Cx.NODES + " aspect", x.contains(new NodeElement("_3")));
        final List<AspectElement> e1 = p.getNext();
        assertFalse(e1 == null);
        assertFalse(p.hasNext());
        assertTrue(e1.size() == 2);
        assertTrue("failed to get expected EdgeAspect instance", e1.get(0) instanceof EdgeElement);
        assertFalse(p.hasNext());
        assertEquals(p.getNext(), null);
        assertEquals(p.getNext(), null);
        assertFalse(p.hasNext());

    }

    @Test
    public void test8() throws IOException {
        final String j = "["
                + "{\"key\":\"value\"},"
                + "{\"nodes\":[{\"@id\":\"_0\"},{\"@id\":\"_1\"},{\"@id\":\"_2\"},{\"@id\":\"_3\"}]},"
                + "{\"key\":\"value\"},"
                + "{\"edges\":[{\"@id\":\"e0\",\"source\":\"_0\",\"target\":\"_1\"},{\"@id\":\"e1\",\"source\":\"_1\",\"target\":\"_2\"}]}"
                + "]";
        final CxParser p = CxParser.createInstance(j, AspectFragmentReaderManager.createInstance()
                .getAvailableAspectReaders());
        assertTrue(p.hasNext());
        final List<AspectElement> x = p.getNext();
        assertFalse(x == null);
        assertTrue(p.hasNext());
        assertTrue(x.size() == 4);
        assertTrue("failed to get expected NodeAspect instance", x.get(0) instanceof NodeElement);
        assertTrue("failed to get expected " + Cx.NODES + " aspect", x.contains(new NodeElement("_0")));
        assertTrue("failed to get expected " + Cx.NODES + " aspect", x.contains(new NodeElement("_1")));
        assertTrue("failed to get expected " + Cx.NODES + " aspect", x.contains(new NodeElement("_2")));
        assertTrue("failed to get expected " + Cx.NODES + " aspect", x.contains(new NodeElement("_3")));
        final List<AspectElement> e = p.getNext();
        assertFalse(e == null);
        assertFalse(p.hasNext());
        assertTrue(e.size() == 2);
        assertTrue("failed to get expected EdgeAspect instance", e.get(0) instanceof EdgeElement);
        assertFalse(p.hasNext());
        assertEquals(p.getNext(), null);
        assertEquals(p.getNext(), null);
        assertFalse(p.hasNext());

        p.reset();
        assertTrue(p.hasNext());
        assertTrue(p.hasNext());
        assertTrue(p.hasNext());
        final List<AspectElement> x1 = p.getNext();
        assertFalse(x1 == null);
        assertTrue(p.hasNext());
        assertTrue(p.hasNext());
        assertTrue(x1.size() == 4);
        assertTrue("failed to get expected NodeAspect instance", x1.get(0) instanceof NodeElement);
        assertTrue("failed to get expected " + Cx.NODES + " aspect", x.contains(new NodeElement("_0")));
        assertTrue("failed to get expected " + Cx.NODES + " aspect", x.contains(new NodeElement("_1")));
        assertTrue("failed to get expected " + Cx.NODES + " aspect", x.contains(new NodeElement("_2")));
        assertTrue("failed to get expected " + Cx.NODES + " aspect", x.contains(new NodeElement("_3")));
        final List<AspectElement> e1 = p.getNext();
        assertFalse(e1 == null);
        assertFalse(p.hasNext());
        assertFalse(p.hasNext());
        assertTrue(e1.size() == 2);
        assertTrue("failed to get expected EdgeAspect instance", e1.get(0) instanceof EdgeElement);
        assertFalse(p.hasNext());
        assertEquals(p.getNext(), null);
        assertEquals(p.getNext(), null);
        assertFalse(p.hasNext());

    }

    @Test
    public void test9() throws IOException {
        final String j = "["
                + "{\"nodes_we_ignore\":[{\"@id\":\"_0\"},{\"@id\":\"_1\"},{\"@id\":\"_2\"},{\"@id\":\"_3\"}]},"
                + "{\"nodes\":[{\"@id\":\"_0\"},{\"@id\":\"_1\"},{\"@id\":\"_2\"},{\"@id\":\"_3\"}]},"
                + "{\"edges\":[{\"@id\":\"e0\",\"source\":\"_0\",\"target\":\"_1\"},{\"@id\":\"e1\",\"source\":\"_1\",\"target\":\"_2\"}]},"
                + "{\"nodeIdentities\":[{\"@id\":\"ni0\",\"nodes\":\"_0\",\"represents\":\"name is zero\"},{\"@id\":\"ni1\",\"node\":\"_1\",\"represents\":\"name is one\"}]},"
                + "{\"edgeIdentities\":[{\"@id\":\"ei0\",\"edge\":\"e0\",\"relationship\":\"BEL:INCREASES\"},{\"@id\":\"ei1\",\"edge\":\"e1\",\"relationship\":\"BEL:DECREASES\"}]},"
                + "{\"elementProperties\":[{\"@id\":\"ep0\",\"elementId\":\"_0\",\"property\":\"propery zero\",\"value\":\"value is zero\"},{\"@id\":\"ep1\",\"elementId\":\"_1\",\"property\":\"propery one\",\"value\":\"value is one\"}]},"
                + "{\"functionTerms\":[{\"@id\":\"ft0\",\"function\":\"functions zero\",\"parameters\":[\"HGNC:FAS\",\"HGNC:MAPK1\"]},{\"@id\":\"ft1\",\"function\":\"functions one\",\"parameters\":[\"HGNC:FAS\",\"HGNC:MAPK1\"]}]},"
                + "{\"nodes\":[{\"@id\":\"_5\"}]},"
                + "{\"edges\":[{\"@id\":\"e2\",\"source\":\"_4\",\"target\":\"_5\"}]},"
                + "{\"edges\":[{\"@id\":\"e3\",\"source\":\"_6\",\"target\":\"_7\"}]},"
                + "{\"nodes\":[{\"@id\":\"_4\"}]},"
                + "{\"nodes\":[{\"@id\":\"_6\"}]},"
                + "{\"nodes\":[{\"@id\":\"_7\"}]},"
                + "{\"xyz\":{\"nodes\":\"_7\"}},"
                + "{\"abc\":[{\"nodes\":[1,2]}]},"
                + "{\"nmq\":[{\"nodes\":[{\"a\":[1,2,3]},{\"b\":[4,5,{\"id\":\"y\"}]}]}]},"
                + "{\"table\":[{\"row 0\":[\"00\",\"10\"],\"row 1\":[\"01\",\"11\"],\"row 2\":[\"02\",\"12\"]}]},"
                + "{\"table\":[{\"row 0\":[\"00\",\"10\" ,\"20\"],\"row 1\":[\"01\",\"11\",\"21\"]}]},"
                + "{\"cartesianLayout\":[{\"node\":\"_0\",\"x\":\"123\",\"y\":\"456\"}]},"
                + "{\"cartesianLayout\":[{\"node\":\"_1\",\"x\":\"3\",\"y\":\"4\"},{\"node\":\"_2\",\"x\":\"5\",\"y\":\"6\"}]}"
                + "]";

        final CxParser p = CxParser.createInstance(j, AspectFragmentReaderManager.createInstance()
                .getAvailableAspectReaders());
        //
        assertTrue(p.hasNext());
        List<AspectElement> x = p.getNext();
        assertFalse(x == null);
        assertTrue(x.size() == 4);
        assertTrue("failed to get expected Aspect instance", x.get(0) instanceof NodeElement);
        //
        assertTrue(p.hasNext());
        x = p.getNext();
        assertFalse(x == null);
        assertTrue(x.size() == 2);
        assertTrue("failed to get expected Aspect instance", x.get(0) instanceof EdgeElement);
        //
        assertTrue(p.hasNext());
        x = p.getNext();
        assertFalse(x == null);
        assertTrue(x.size() == 1);
        assertTrue("failed to get expected Aspect instance", x.get(0) instanceof NodeElement);
        //
        assertTrue(p.hasNext());
        x = p.getNext();
        assertFalse(x == null);
        assertTrue(x.size() == 1);
        assertTrue("failed to get expected Aspect instance", x.get(0) instanceof EdgeElement);
        //
        assertTrue(p.hasNext());
        x = p.getNext();
        assertFalse(x == null);
        assertTrue(x.size() == 1);
        assertTrue("failed to get expected Aspect instance", x.get(0) instanceof EdgeElement);
        //
        assertTrue(p.hasNext());
        x = p.getNext();
        assertFalse(x == null);
        assertTrue(x.size() == 1);
        assertTrue("failed to get expected Aspect instance", x.get(0) instanceof NodeElement);
        //
        assertTrue(p.hasNext());
        x = p.getNext();
        assertFalse(x == null);
        assertTrue(x.size() == 1);
        assertTrue("failed to get expected Aspect instance", x.get(0) instanceof NodeElement);
        //
        assertTrue(p.hasNext());
        x = p.getNext();
        assertFalse(x == null);
        assertTrue(x.size() == 1);
        assertTrue("failed to get expected Aspect instance", x.get(0) instanceof NodeElement);
        //
        assertTrue(p.hasNext());
        x = p.getNext();
        assertFalse(x == null);
        assertTrue(x.size() == 1);
        assertTrue("failed to get expected Aspect instance", x.get(0) instanceof CartesianLayoutElement);
        //
        assertTrue(p.hasNext());
        x = p.getNext();
        assertFalse(x == null);
        assertTrue(x.size() == 2);
        assertTrue("failed to get expected Aspect instance", x.get(0) instanceof CartesianLayoutElement);
        //
        assertFalse(p.hasNext());
        assertEquals(p.getNext(), null);
        //
        p.reset();
        assertTrue(p.hasNext());
        x = p.getNext();
        assertFalse(x == null);
        assertTrue(x.size() == 4);
        assertTrue("failed to get expected Aspect instance", x.get(0) instanceof NodeElement);
        p.reset();
        assertTrue(p.hasNext());
        assertTrue(p.hasNext());
        x = p.getNext();
        assertFalse(x == null);
        assertTrue(x.size() == 4);
        assertTrue("failed to get expected Aspect instance", x.get(0) instanceof NodeElement);

    }

}
