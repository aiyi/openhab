package org.openhab.core.service;

import java.util.List;
import java.util.Properties;

import org.picocontainer.MutablePicoContainer;
import org.picocontainer.Parameter;

/**
 * 
 * @author Jesse
 * @since 2.0.0
 */
abstract public class Module {

	private String name = null;
	
	private MutablePicoContainer container;

	private Properties initConfig = null;

	public String getName() {
		return this.getClass().getPackage().getName();
	}

	protected void setSimpleName(String name) {
		this.name = name;
	}
	
	public String getSimpleName() {
		if (name == null) {
			return getName();
		}
		return name;
	}
	
	public Properties getInitConfig() {
		return initConfig;
	}
	
	public void setContainer(MutablePicoContainer container) {
		this.container = container;
	}

	public void configure(Properties config) {
		if (config != null) {
			initConfig = new Properties();
			initConfig.putAll(config);
		}
	}
	
	public abstract void start();

	public void stop() {
		// do nothing
	}

	public void updated(Properties config) {
		// do nothing
	}
	
	protected void addComponent(Object component, Parameter... params) {
		String key = component.toString().substring(6);
		container.addComponent(key, component, params);
	}

	protected <T> T getComponent(Class<T> type) {
		return container.getComponent(type);
	}
	
	protected Object getComponent(Object key) {
		return container.getComponent(key);
	}
	
	protected void removeComponent(Object component) {
		container.removeComponent(component);
	}

	public List<Object> getComponents() {
		return container.getComponents();
	}
	
}
