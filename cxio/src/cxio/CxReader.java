package cxio;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

public final class CxReader {

    private JsonParser                                  jp;
    private JsonToken                                   token;
    private boolean                                     was_in_recognized_aspect;
    private int                                         level;
    private List<AspectElement>                         current;
    private final Object                                input;
    private final HashMap<String, AspectFragmentReader> aspect_handlers;

    public final List<AspectElement> getNext() throws IOException {
        if (token == null) {
            throw new IllegalStateException("this should never have happened: token is null");
        }
        if (jp == null) {
            throw new IllegalStateException("this should never have happened: json parser is null");
        }
        final List<AspectElement> prev = current;
        current = null;
        while ((token != JsonToken.END_ARRAY) || (jp.getCurrentName() != null)) {
            List<AspectElement> aspects = null;
            final String name = jp.getCurrentName();
            was_in_recognized_aspect = false;
            if ((level == 2) && (token == JsonToken.FIELD_NAME) && (name != null)) {
                if (aspect_handlers.containsKey(name)) {
                    was_in_recognized_aspect = true;
                    aspects = aspect_handlers.get(name).readAspectFragment(jp);
                }
            }
            if (was_in_recognized_aspect && (jp.getCurrentToken() != JsonToken.END_ARRAY)
                    && (jp.getCurrentToken() != JsonToken.END_OBJECT)) {
                throw new IllegalStateException("this should never have happened (likely cause: problem with '" + name
                        + "' aspect handler)");
            }
            if ((token == JsonToken.START_ARRAY) || (token == JsonToken.START_OBJECT)) {
                level++;
            }
            else if ((token == JsonToken.END_ARRAY) || (token == JsonToken.END_OBJECT)) {
                level--;
                if (level < 1) {
                    throw new IllegalStateException("this should never have happened");
                }
            }
            token = jp.nextToken();
            if (aspects != null) {
                current = aspects;
                return prev;
            }
        }
        jp.close();
        return prev;
    }

    public final boolean hasNext() throws IOException {
        return current != null;
    }

    public final void reset() throws IOException {
        if (input == null) {
            throw new IllegalStateException("input for cx parser is null");
        }
        if ((aspect_handlers == null) || aspect_handlers.isEmpty()) {
            throw new IllegalStateException("aspect handlers are null or empty");
        }
        token = null;
        was_in_recognized_aspect = false;
        level = 0;
        current = null;
        jp = createJsonParser(input);
        token = jp.nextToken();
        if (token != JsonToken.START_ARRAY) {
            throw new IllegalStateException("illegal cx json format: expected to start with an array");
        }
        getNext();
    }

    /*
     * Convenience method. Returns a sorted map of lists of aspects, where the
     * keys are the names of the aspect. Takes a CxParser as argument.
     */
    public static SortedMap<String, List<AspectElement>> parseAsMap(final CxReader p) throws IOException {
        if (p == null) {
            throw new IllegalArgumentException("parser is null");
        }
        final SortedMap<String, List<AspectElement>> all_aspects = new TreeMap<String, List<AspectElement>>();
        p.reset();
        while (p.hasNext()) {
            final List<AspectElement> aspects = p.getNext();
            if ((aspects != null) && !aspects.isEmpty()) {
                final String name = aspects.get(0).getAspectName();
                if (!all_aspects.containsKey(name)) {
                    all_aspects.put(name, aspects);
                }
                else {
                    all_aspects.get(name).addAll(aspects);
                }
            }
        }
        p.reset();
        return all_aspects;
    }

    public final static CxReader createInstance(final File file, final Set<AspectFragmentReader> aspect_handlers)
            throws IOException {
        return new CxReader(file, aspect_handlers);
    }

    public final static CxReader createInstance(final InputStream input_stream,
            final Set<AspectFragmentReader> aspect_handlers) throws IOException {
        return new CxReader(input_stream, aspect_handlers);
    }

    public final static CxReader createInstance(final Reader reader, final Set<AspectFragmentReader> aspect_handlers)
            throws IOException {
        return new CxReader(reader, aspect_handlers);
    }

    public final static CxReader createInstance(final String string, final Set<AspectFragmentReader> aspect_handlers)
            throws IOException {
        return new CxReader(string, aspect_handlers);
    }

    public final static CxReader createInstance(final URL url, final Set<AspectFragmentReader> aspect_handlers)
            throws IOException {
        return new CxReader(url, aspect_handlers);
    }

    private final static JsonParser createJsonParser(final Object input) throws IOException {
        final JsonFactory f = new JsonFactory();
        JsonParser jp = null;
        if (input instanceof String) {
            jp = f.createJsonParser((String) input);
        }
        else if (input instanceof File) {
            jp = f.createJsonParser((File) input);
        }
        else if (input instanceof InputStream) {
            jp = f.createJsonParser((InputStream) input);
        }
        else if (input instanceof Reader) {
            jp = f.createJsonParser((Reader) input);
        }
        else if (input instanceof URL) {
            jp = f.createJsonParser((URL) input);
        }
        else {
            throw new IllegalStateException("cx parser does not know how to handle input of type " + input.getClass());
        }
        return jp;
    }

    private final static HashMap<String, AspectFragmentReader> setupAspectHandlers(
            final Set<AspectFragmentReader> aspect_handlers) {
        if ((aspect_handlers == null) || aspect_handlers.isEmpty()) {
            throw new IllegalArgumentException("aspect handlers are null or empty");
        }
        final HashMap<String, AspectFragmentReader> ahs = new HashMap<String, AspectFragmentReader>();
        for (final AspectFragmentReader aspect_handler : aspect_handlers) {
            ahs.put(aspect_handler.getAspectName(), aspect_handler);
        }
        return ahs;
    }

    private CxReader(final File file, final Set<AspectFragmentReader> aspect_handlers) throws IOException {
        if (file == null) {
            throw new IllegalArgumentException("cx input file is null");
        }
        input = file;
        this.aspect_handlers = setupAspectHandlers(aspect_handlers);
        reset();
    }

    private CxReader(final InputStream input_stream, final Set<AspectFragmentReader> aspect_handlers)
            throws IOException {
        if (input_stream == null) {
            throw new IllegalArgumentException("cx input stream is null");
        }
        input = input_stream;
        this.aspect_handlers = setupAspectHandlers(aspect_handlers);
        reset();
    }

    private CxReader(final Reader reader, final Set<AspectFragmentReader> aspect_handlers) throws IOException {
        if (reader == null) {
            throw new IllegalArgumentException("cx input reader is null");
        }
        input = reader;
        this.aspect_handlers = setupAspectHandlers(aspect_handlers);
        reset();
    }

    private CxReader(final String string, final Set<AspectFragmentReader> aspect_handlers) throws IOException {
        if (string.trim().isEmpty()) {
            throw new IllegalArgumentException("cx input is null or empty");
        }
        input = string.trim();
        this.aspect_handlers = setupAspectHandlers(aspect_handlers);
        reset();
    }

    private CxReader(final URL url, final Set<AspectFragmentReader> aspect_handlers) throws IOException {
        if (url == null) {
            throw new IllegalArgumentException("cx input url is null");
        }
        input = url;
        this.aspect_handlers = setupAspectHandlers(aspect_handlers);
        reset();
    }

}
