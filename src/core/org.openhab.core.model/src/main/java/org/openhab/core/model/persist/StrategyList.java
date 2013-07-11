//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.07.08 at 10:25:17 PM CST 
//


package org.openhab.core.model.persist;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for StrategyList complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="StrategyList">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice maxOccurs="unbounded" minOccurs="0">
 *         &lt;element name="strategy" type="{}Strategy"/>
 *         &lt;element name="cronStrategy" type="{}CronStrategy"/>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "StrategyList", propOrder = {
    "strategiesAndCronStrategies"
})
public class StrategyList {

    @XmlElements({
        @XmlElement(name = "strategy"),
        @XmlElement(name = "cronStrategy", type = CronStrategy.class)
    })
    protected List<Strategy> strategiesAndCronStrategies;

    /**
     * Gets the value of the strategiesAndCronStrategies property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the strategiesAndCronStrategies property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getStrategiesAndCronStrategies().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Strategy }
     * {@link CronStrategy }
     * 
     * 
     */
    public List<Strategy> getStrategiesAndCronStrategies() {
        if (strategiesAndCronStrategies == null) {
            strategiesAndCronStrategies = new ArrayList<Strategy>();
        }
        return this.strategiesAndCronStrategies;
    }

}