//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.06.28 at 11:27:14 PM CST 
//


package org.openhab.core.model.items;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ModelNormalItem complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ModelNormalItem">
 *   &lt;complexContent>
 *     &lt;extension base="{}ModelItem">
 *       &lt;attribute name="type" use="required" type="{}ModelItemType" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ModelNormalItem")
public class ModelNormalItem
    extends ModelItem
{

    @XmlAttribute(name = "type", required = true)
    protected ModelItemType type;

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link ModelItemType }
     *     
     */
    public ModelItemType getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link ModelItemType }
     *     
     */
    public void setType(ModelItemType value) {
        this.type = value;
    }

}
