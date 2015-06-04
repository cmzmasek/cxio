package cxio.test;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import cxio.AspectElement;
import cxio.CartesianLayoutElement;
import cxio.CartesianLayoutFragmentWriter;
import cxio.CxConstants;
import cxio.JsonWriter;

public class CartesianLayoutFragmentWriterTest {

    @Test
    public void test() throws IOException {

        final List<AspectElement> l0 = new ArrayList<AspectElement>();
        final OutputStream out0 = new ByteArrayOutputStream();
        final JsonWriter t0 = JsonWriter.createInstance(out0);

        final CartesianLayoutFragmentWriter w0 = CartesianLayoutFragmentWriter.createInstance();

        t0.start();
        w0.write(l0, t0);
        t0.end();

        assertEquals("[{\"" + CxConstants.CARTESIAN_LAYOUT + "\":[]}]", out0.toString());

        final CartesianLayoutElement c0 = new CartesianLayoutElement("00", 0, 0);
        final CartesianLayoutElement c1 = new CartesianLayoutElement("01", 1, 2);
        final CartesianLayoutElement c2 = new CartesianLayoutElement("02", 3, 4);

        final List<AspectElement> l1 = new ArrayList<AspectElement>();
        l1.add(c0);
        l1.add(c1);
        l1.add(c2);

        final OutputStream out1 = new ByteArrayOutputStream();
        final JsonWriter t1 = JsonWriter.createInstance(out1);

        final CartesianLayoutFragmentWriter w1 = CartesianLayoutFragmentWriter.createInstance();

        t1.start();
        w1.write(l1, t1);
        t1.end();

        assertEquals(
                "[{\"cartesianLayout\":[{\"node\":\"00\",\"x\":\"0\",\"y\":\"0\"},{\"node\":\"01\",\"x\":\"1\",\"y\":\"2\"},{\"node\":\"02\",\"x\":\"3\",\"y\":\"4\"}]}]",
                out1.toString());

    }

}
