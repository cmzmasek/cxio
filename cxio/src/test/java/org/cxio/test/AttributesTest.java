package org.cxio.test;

import static org.junit.Assert.assertTrue;

import org.cxio.aspects.datamodels.AbstractAttributesAspectElement.ATTRIBUTE_DATA_TYPE;
import org.cxio.aspects.datamodels.HiddenAttributesElement;
import org.junit.Test;

public class AttributesTest {

    @Test
    public void test() {
        final HiddenAttributesElement a = HiddenAttributesElement.createInstanceWithSingleValue("subnetwork", "name", null, ATTRIBUTE_DATA_TYPE.STRING);

        assertTrue(a.isSingleValue() == true);
        assertTrue(a.getValue() == null);
        assertTrue(a.getValuesAsString().equals("null"));

        final HiddenAttributesElement aa = HiddenAttributesElement.createInstanceWithSingleValue("subnetwork", "name", "null", ATTRIBUTE_DATA_TYPE.STRING);

        assertTrue(aa.isSingleValue() == true);
        assertTrue(aa.getValue() == null);
        assertTrue(aa.getValuesAsString().equals("null"));

        final HiddenAttributesElement aaa = HiddenAttributesElement.createInstanceWithSingleValue("subnetwork", "name", "\"null\"", ATTRIBUTE_DATA_TYPE.STRING);

        assertTrue(aaa.isSingleValue() == true);
        assertTrue(aaa.getValue() != null);
        assertTrue(aaa.getValuesAsString().equals("\"null\""));

        final String b_s = "\"v\"";
        final HiddenAttributesElement b = HiddenAttributesElement.createInstanceWithSingleValue("subnetwork", "name", b_s, ATTRIBUTE_DATA_TYPE.STRING);

        assertTrue(b.isSingleValue() == true);
        assertTrue(b.getValue().equals("v"));
        assertTrue(b.getValuesAsString().equals(b_s));

        final HiddenAttributesElement bb = HiddenAttributesElement.createInstanceWithSingleValue("subnetwork", "name", "\"true\"", ATTRIBUTE_DATA_TYPE.BOOLEAN);

        assertTrue(bb.isSingleValue() == true);
        assertTrue(bb.getValue().equals("true"));
        assertTrue(bb.getValuesAsString().equals("\"true\""));

        final HiddenAttributesElement c = HiddenAttributesElement.createInstanceWithMultipleValues("subnetwork", "name", null, ATTRIBUTE_DATA_TYPE.LIST_OF_STRING);
        assertTrue(c.isSingleValue() == false);
        assertTrue(c.getValues() == null);
        assertTrue(c.getValuesAsString().equals("null"));

        final HiddenAttributesElement d = HiddenAttributesElement.createInstanceWithMultipleValues("subnetwork", "name", "[]", ATTRIBUTE_DATA_TYPE.LIST_OF_STRING);
        assertTrue(d.isSingleValue() == false);
        assertTrue(d.getValues() != null);
        assertTrue(d.getValuesAsString().equals("[]"));

        final String e_s = "[\"a\"]";
        final HiddenAttributesElement e = HiddenAttributesElement.createInstanceWithMultipleValues("subnetwork", "name", e_s, ATTRIBUTE_DATA_TYPE.LIST_OF_STRING);
        assertTrue(e.isSingleValue() == false);
        assertTrue(e.getValues() != null);
        assertTrue(e.getValuesAsString().equals(e_s));

        final String f_s = "[\"a\",null,\"\",null,\"b \"]";
        final HiddenAttributesElement f = HiddenAttributesElement.createInstanceWithMultipleValues("subnetwork", "name", f_s, ATTRIBUTE_DATA_TYPE.LIST_OF_STRING);
        assertTrue(f.isSingleValue() == false);
        assertTrue(f.getValues() != null);
        assertTrue(f.getValuesAsString().equals(f_s));

        final String f_s2 = " [  \"a\"  ,      null   ,  \"\"   , null ,  \"b \"    ]   ";
        final HiddenAttributesElement g = HiddenAttributesElement.createInstanceWithMultipleValues("subnetwork", "name", f_s2, ATTRIBUTE_DATA_TYPE.LIST_OF_STRING);
        assertTrue(g.isSingleValue() == false);
        assertTrue(g.getValues() != null);
        assertTrue(g.getValuesAsString().equals(f_s));

        final String g_s = "[\"1.3\",null,null,\"1.88\"]";
        final HiddenAttributesElement h = HiddenAttributesElement.createInstanceWithMultipleValues("subnetwork", "name", g_s, ATTRIBUTE_DATA_TYPE.LIST_OF_DOUBLE);
        assertTrue(h.isSingleValue() == false);
        assertTrue(h.getValues() != null);
        assertTrue(h.getValuesAsString().equals(g_s));
        System.out.println(h.getValuesAsString());
    }

}