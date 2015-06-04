package cxio;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;

public class NodeAttributesFragmentReader implements AspectFragmentReader {
    private static final boolean STRICT = true;

    @Override
    public String getAspectName() {
        return Cx.NODE_ATTRIBUTES;
    }

    @Override
    public List<AspectElement> readAspectFragment(final JsonParser jp) throws IOException {
        JsonToken t = jp.nextToken();
        if (t != JsonToken.START_ARRAY) {
            throw new IOException("malformed cx json in '" + Cx.EDGES + "'");
        }
        final List<AspectElement> na_aspects = new ArrayList<AspectElement>();
        while (t != JsonToken.END_ARRAY) {
            if (t == JsonToken.START_OBJECT) {
                String id = null;
                List<String> nodes = null;
                final SortedMap<String, List<String>> attributes = new TreeMap<String, List<String>>();
                while (jp.nextToken() != JsonToken.END_OBJECT) {
                    final String namefield = jp.getCurrentName();
                    jp.nextToken(); // move to value
                    if (Cx.ID.equals(namefield)) {
                        id = jp.getText().trim();
                    }
                    else if (Cx.NODES.equals(namefield)) {
                        nodes = Util.parseSimpleList(jp, t);
                    }
                    else if (Cx.ATTRIBUTES.equals(namefield)) {
                        while (jp.nextToken() != JsonToken.END_OBJECT) {
                            jp.nextToken(); // move to value
                            attributes.put(jp.getCurrentName(), Util.parseSimpleList(jp, t));
                        }
                    }
                    else if (STRICT) {
                        throw new IOException("malformed cx json: unrecognized field '" + namefield + "'");
                    }
                }
                if (Util.isEmpty(id)) {
                    throw new IOException("malformed cx json: attribute id in node attributes is missing");
                }
                if ((nodes == null) || nodes.isEmpty()) {
                    throw new IOException("malformed cx json: node ids in node attributes are missing");
                }
                na_aspects.add(new NodeAttributesElement(id, nodes, attributes));
            }
            t = jp.nextToken();
        }
        return na_aspects;
    }
}
