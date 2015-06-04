package cxio.test;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;

public class RoundTripTest {

    @Test
    public void testRoundTrip0() throws IOException {
        final String a0 = "[]";
        final String res = Util.cyCxRoundTrip(a0);
        assertEquals(a0, res);
    }

    @Test
    public void testRoundTrip1() throws IOException {

        final String a0 = "[{\"nodes\":[{\"@id\":\"_0\"},{\"@id\":\"_1\"},{\"@id\":\"_2\"},{\"@id\":\"_3\"},{\"@id\":\"_5\"},"
                + "{\"@id\":\"_4\"},{\"@id\":\"_6\"},{\"@id\":\"_7\"}]},"
                + "{\"edges\":[{\"@id\":\"e0\",\"source\":\"_0\",\"target\":\"_1\"},"
                + "{\"@id\":\"e1\",\"source\":\"_1\",\"target\":\"_2\"},"
                + "{\"@id\":\"e2\",\"source\":\"_4\",\"target\":\"_5\"},"
                + "{\"@id\":\"e3\",\"source\":\"_6\",\"target\":\"_7\"}]},"
                + "{\"cartesianLayout\":[{\"node\":\"_0\",\"x\":\"123\",\"y\":\"456\"},"
                + "{\"node\":\"_1\",\"x\":\"3\",\"y\":\"4\"},{\"node\":\"_2\",\"x\":\"5\",\"y\":\"6\"}]},"
                + "{\"nodeAttributes\":[{\"@id\":\"_na0\",\"nodes\":[\"_0\",\"_1\"],"
                + "\"attributes\":{\"PSIMI_25_aliases\":[\"322397\",\"80961\"],"
                + "\"entrez_gene_locuslink\":[\"322397\",\"one more\"],"
                + "\"name\":[\"_322397\"]}},{\"@id\":\"_na1\",\"nodes\":[\"_2\"],"
                + "\"attributes\":{\"key\":[\"value\"]}},{\"@id\":\"_na2\","
                + "\"nodes\":[\"_3\"]},{\"@id\":\"_na3\",\"nodes\":[\"_33\"]}]},"
                + "{\"edgeAttributes\":[{\"@id\":\"_ea0\",\"edges\":[\"_e0\",\"_e22\"],"
                + "\"attributes\":{\"PSIMI_25_detection_method\":[\"genetic interference\"],"
                + "\"interaction\":[\"479019\",\"one more\"],\"name\":[\"768303 (479019) 791595\"]}}]}]";

        final String res = Util.cyCxRoundTrip(a0);

        assertEquals(a0, res);

    }

}
