package cxio.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ CartesianLayoutFragmentReaderTest.class, CartesianLayoutFragmentWriterTest.class, CxParserTest.class,
        EdgeElementTest.class, EdgeAttributesFragmentReaderTest.class, EdgeAttributesFragmentWriterTest.class,
        EdgesFragmentReaderTest.class, EdgesFragmentWriterTest.class, NodeElementTest.class,
        NodeAttributesFragmentReaderTest.class, NodeAttributesFragmentWriterTest.class, NodesFragmentReaderTest.class,
        NodesFragmentWriterTest.class, RoundTripTest.class })
public class AllTests {

}
