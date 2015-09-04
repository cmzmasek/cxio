package org.cxio.aspects.readers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.cxio.aspects.datamodels.AnonymousElement;
import org.cxio.core.interfaces.AspectElement;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 *  This is for parsing "anonymous", general aspect element fragments.
 *
 * @author cmzmasek
 *
 */
public final class AnonymousFragmentReader extends AbstractFragmentReader {

    private boolean          _is_list;
    private String           _name;
    private final JsonParser _jp;

    public final static AnonymousFragmentReader createInstance() {
        return new AnonymousFragmentReader();
    }

    public final static AnonymousFragmentReader createInstance(final JsonParser jp) {
        return new AnonymousFragmentReader(jp);
    }

    public final static AnonymousFragmentReader createInstance(final JsonParser jp, final String name) {
        return new AnonymousFragmentReader(jp, name);
    }

    private AnonymousFragmentReader() {
        _name = null;
        _m = new ObjectMapper();
        _jp = null;
        _is_list = false;

    }

    private AnonymousFragmentReader(final JsonParser jp) {
        _name = null;
        _m = new ObjectMapper();
        _jp = jp;
        _is_list = false;
    }

    private AnonymousFragmentReader(final JsonParser jp, final String name) {
        _name = name;
        _m = new ObjectMapper();
        _jp = jp;
        _is_list = false;
    }

    @Override
    public final String getAspectName() {
        return _name;
    }

    public final boolean isList() {
        return _is_list;
    }

    @Override
    public final List<AspectElement> readAspectFragment(final JsonParser jp) throws IOException {
        JsonToken t = jp.nextToken();
        _is_list = false;
        if (t == JsonToken.START_ARRAY) {
            _is_list = true;
        }
        final List<AspectElement> elements = new ArrayList<AspectElement>();
        if (_is_list) {
            while (t != JsonToken.END_ARRAY) {
                if (t == JsonToken.START_OBJECT) {
                    final AnonymousElement e = new AnonymousElement(_name, (ObjectNode) _m.readTree(jp));
                    if (e != null) {
                        elements.add(e);
                    }
                }
                t = jp.nextToken();
            }
        }
        else {
            final AnonymousElement e = new AnonymousElement(_name, (ObjectNode) _m.readTree(jp));
            if (e != null) {
                elements.add(e);
            }
            t = jp.nextToken();
        }
        return elements;
    }

    public final void setAspectName(final String name) {
        _name = name;
    }

    @Override
    public final AspectElement readElement(final ObjectNode o) throws IOException {
        return new AnonymousElement(_name, o);
    }
}
