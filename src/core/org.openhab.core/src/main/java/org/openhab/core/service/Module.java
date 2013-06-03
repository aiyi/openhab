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

	private String name;
	private MutablePicoContainer container;

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}
	
	public void setContainer(MutablePicoContainer container) {
		this.container = container;
	}

	public MutablePicoContainer getContainer() {
		return container;
	}

	public void configure(Properties config) {
		if (config != null) {
			updated(config);
		}
	}

	public abstract void updated(Properties config);
	
	public abstract void start();

	public void stop() {
	}

	protected void addComponent(Object key, Object component, Parameter... params) {
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
