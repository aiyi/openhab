//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.06.28 at 11:27:14 PM CST 
//


package org.openhab.core.model.items;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ModelItemType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ModelItemType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Switch"/>
 *     &lt;enumeration value="Rollershutter"/>
 *     &lt;enumeration value="Number"/>
 *     &lt;enumeration value="String"/>
 *     &lt;enumeration value="Dimmer"/>
 *     &lt;enumeration value="Contact"/>
 *     &lt;enumeration value="DateTime"/>
 *     &lt;enumeration value="Color"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ModelItemType")
@XmlEnum
public enum ModelItemType {

    @XmlEnumValue("Switch")
    SWITCH("Switch"),
    @XmlEnumValue("Rollershutter")
    ROLLERSHUTTER("Rollershutter"),
    @XmlEnumValue("Number")
    NUMBER("Number"),
    @XmlEnumValue("String")
    STRING("String"),
    @XmlEnumValue("Dimmer")
    DIMMER("Dimmer"),
    @XmlEnumValue("Contact")
    CONTACT("Contact"),
    @XmlEnumValue("DateTime")
    DATE_TIME("DateTime"),
    @XmlEnumValue("Color")
    COLOR("Color");
    private final String value;

    ModelItemType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ModelItemType fromValue(String v) {
        for (ModelItemType c: ModelItemType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
