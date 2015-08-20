package org.cxio.aspects.readers;

import java.io.IOException;

import org.cxio.aspects.datamodels.AbstractAttributesElement;
import org.cxio.aspects.datamodels.AbstractAttributesElement.ATTRIBUTE_TYPE;
import org.cxio.aspects.datamodels.NetworkAttributesElement;
import org.cxio.core.interfaces.AspectElement;

import com.fasterxml.jackson.databind.node.ObjectNode;

public final class NetworkAttributesFragmentReader extends AbstractFragmentReader {

    public static NetworkAttributesFragmentReader createInstance() {
        return new NetworkAttributesFragmentReader();
    }

    private NetworkAttributesFragmentReader() {
        super();
    }

    @Override
    public final String getAspectName() {
        return NetworkAttributesElement.NAME;
    }

    @Override
    protected final AspectElement readElement(final ObjectNode o) throws IOException {
        ATTRIBUTE_TYPE type = ATTRIBUTE_TYPE.STRING;
        if (o.has(AbstractAttributesElement.ATTR_TYPE)) {
            type = AbstractAttributesElement.toType(ParserUtils.getTextValueRequired(o, AbstractAttributesElement.ATTR_TYPE));
        }
        return new NetworkAttributesElement(ParserUtils.getAsStringListRequired(o, AbstractAttributesElement.ATTR_PROPERTY_OF),
                                            ParserUtils.getTextValueRequired(o, AbstractAttributesElement.ATTR_NAME),
                                            ParserUtils.getAsStringList(o, AbstractAttributesElement.ATTR_VALUES),
                                            type);
    }

}
