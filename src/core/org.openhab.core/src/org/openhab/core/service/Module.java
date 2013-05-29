package org.openhab.core.service;

import java.util.List;
import java.util.Properties;

import org.picocontainer.MutablePicoContainer;

/**
 * 
 * @author Jesse
 * @since 2.0.0
 */
abstract public class Module {

	private MutablePicoContainer container;

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

	public abstract void stop();

	protected void addComponent(Object component) {
		container.addComponent(component);
	}
	
	protected void removeComponent(Object component) {
		container.removeComponent(component);
	}

	protected <T> T getComponent(Class<T> component) {
		return container.getComponent(component);
	}

	public List<Object> getComponents() {
		return container.getComponents();
	}
	
}
