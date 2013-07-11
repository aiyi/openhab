//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.07.08 at 10:25:17 PM CST 
//


package org.openhab.core.model.persist;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.openhab.core.model.persist package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.openhab.core.model.persist
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link PersistenceModel }
     * 
     */
    public PersistenceModel createPersistenceModel() {
        return new PersistenceModel();
    }

    /**
     * Create an instance of {@link StrategyList }
     * 
     */
    public StrategyList createStrategyList() {
        return new StrategyList();
    }

    /**
     * Create an instance of {@link FilterList }
     * 
     */
    public FilterList createFilterList() {
        return new FilterList();
    }

    /**
     * Create an instance of {@link ConfigList }
     * 
     */
    public ConfigList createConfigList() {
        return new ConfigList();
    }

    /**
     * Create an instance of {@link Filter }
     * 
     */
    public Filter createFilter() {
        return new Filter();
    }

    /**
     * Create an instance of {@link Strategy }
     * 
     */
    public Strategy createStrategy() {
        return new Strategy();
    }

    /**
     * Create an instance of {@link TimeFilter }
     * 
     */
    public TimeFilter createTimeFilter() {
        return new TimeFilter();
    }

    /**
     * Create an instance of {@link PersistenceConfiguration }
     * 
     */
    public PersistenceConfiguration createPersistenceConfiguration() {
        return new PersistenceConfiguration();
    }

    /**
     * Create an instance of {@link CronStrategy }
     * 
     */
    public CronStrategy createCronStrategy() {
        return new CronStrategy();
    }

    /**
     * Create an instance of {@link ThresholdFilter }
     * 
     */
    public ThresholdFilter createThresholdFilter() {
        return new ThresholdFilter();
    }

}