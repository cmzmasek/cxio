package org.cxio.aspects.datamodels;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to represent a Cytoscape node attribute aspect element.
 *
 *
 * @author cmzmasek
 *
 */
public final class NodeAttributesElement extends AbstractAttributesElement {

    public final static String NAME = "nodeAttributes";

    public NodeAttributesElement(final List<String> property_of, final String name, final List<String> values) {
        _property_of = property_of;
        _name = name;
        _values = values;
        _type = ATTRIBUTE_TYPE.STRING;
    }

    public NodeAttributesElement(final List<String> property_of, final String name, final List<String> values, final ATTRIBUTE_TYPE type) {
        _property_of = property_of;
        _name = name;
        _values = values;
        _type = type;
    }

    public NodeAttributesElement(final String property_of, final String name, final String value, final ATTRIBUTE_TYPE type) {
        _property_of = new ArrayList<String>();
        _property_of.add(property_of);
        _name = name;
        _values = new ArrayList<String>();
        _values.add(value);
        _type = type;
    }

    public NodeAttributesElement(final String property_of, final String name, final List<String> values, final ATTRIBUTE_TYPE type) {
        _property_of = new ArrayList<String>();
        _property_of.add(property_of);
        _name = name;
        _values = values;
        _type = type;
    }

    @Override
    public String getAspectName() {
        return NAME;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("property of nodes: ");
        sb.append(_property_of);
        sb.append("\n");
        sb.append("name : ");
        sb.append(_name);
        sb.append("\n");
        sb.append("values: ");
        sb.append(_values);
        sb.append("\n");
        sb.append("type : ");
        sb.append(_type.toString());
        sb.append("\n");
        return sb.toString();
    }

}
