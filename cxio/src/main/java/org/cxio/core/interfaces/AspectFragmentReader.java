package org.cxio.core.interfaces;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonParser;

/**
 * The interface for Aspect Fragment readers (Aspect Fragments are lists of
 * Aspect Elements). Each AspectElement implementation needs to have a
 * corresponding AspectFragmentReader.
 *
 * @author cmzmasek
 *
 */
public interface AspectFragmentReader {
    
    /**
     * This returns the name of the Aspect a AspectFragmentReader can read.
     *
     * @return the name of the Aspect this AspectFragmentReader can read
     */
    public String getAspectName();

    
    /**
     * This returns the time stamp of the Aspect previously read by this AspectFragmentReader.
     * 
     * @return the time stamp of the Aspect previously read
     */
    public String getTimeStamp();

    /**
     * This is the main method of AspectFragmentReaders. It takes a JsonParser
     * from Jackson Faster XML library
     * http://fasterxml.github.io/jackson-core/javadoc
     * /2.0.0/com/fasterxml/jackson/core/JsonParser.html and returns a list of
     * AspectElements (a aspect fragment).
     *
     * @param jp
     * @return
     * @throws IOException
     */
    public List<AspectElement> readAspectFragment(final JsonParser jp) throws IOException;
}
