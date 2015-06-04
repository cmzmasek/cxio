package cxio;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;

public class CartesianLayoutFragmentReader implements AspectFragmentReader {

    private static final boolean STRICT = true;

    @Override
    public String getAspectName() {
        return Cx.CARTESIAN_LAYOUT;
    }

    @Override
    public List<AspectElement> readAspectFragment(final JsonParser jp) throws IOException {
        JsonToken t = jp.nextToken();
        if (t != JsonToken.START_ARRAY) {
            throw new IOException("malformed cx json in '" + Cx.EDGES + "'");
        }
        final List<AspectElement> layout_aspects = new ArrayList<AspectElement>();
        while (t != JsonToken.END_ARRAY) {
            if (t == JsonToken.START_OBJECT) {
                String node_id = null;
                int x = -Integer.MAX_VALUE;
                int y = -Integer.MAX_VALUE;
                while (jp.nextToken() != JsonToken.END_OBJECT) {
                    final String namefield = jp.getCurrentName();
                    jp.nextToken(); // move to value
                    if (Cx.NODE.equals(namefield)) {
                        node_id = jp.getText().trim();
                    }
                    else if (Cx.X.equals(namefield)) {

                        x = jp.getValueAsInt();
                    }
                    else if (Cx.Y.equals(namefield)) {
                        y = jp.getValueAsInt();
                    }
                    else if (STRICT) {
                        throw new IOException("malformed cx json: unrecognized field '" + namefield + "'");
                    }
                }
                if (Util.isEmpty(node_id)) {
                    throw new IOException("malformed cx json: node id in cartesian layout is missing");
                }
                if (x == -Integer.MAX_VALUE) {
                    throw new IOException("malformed cx json: x coordinate in cartesian layout is missing");
                }
                if (y == -Integer.MAX_VALUE) {
                    throw new IOException("malformed cx json: y coordinate in cartesian layout is missing");
                }
                layout_aspects.add(new CartesianLayoutElement(node_id, x, y));
            }
            t = jp.nextToken();
        }

        return layout_aspects;
    }

}
