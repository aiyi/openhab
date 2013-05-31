package org.openhab.core;

import java.util.Properties;

import org.openhab.core.events.EventBus;
import org.openhab.core.internal.events.EventBusImpl;
import org.openhab.core.internal.items.ItemRegistryImpl;
import org.openhab.core.internal.items.ItemUpdater;
import org.openhab.core.items.ItemRegistry;
import org.openhab.core.service.Module;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CoreModule extends Module {

	private static Logger logger = LoggerFactory.getLogger(CoreModule.class);
	
	@Override
	public void configure(Properties config) {
		super.configure(config);
		addComponent(EventBusImpl.class);
		addComponent(ItemRegistryImpl.class);
		addComponent(ItemUpdater.class);
	}

	@Override
	public void updated(Properties config) {
		logger.debug("Configuration updated {}.", config.toString());
	}
	
	@Override
	public void start() {
		EventBus eventBus = getComponent(EventBus.class);
		ItemRegistry itemRegistry = getComponent(ItemRegistry.class);
		ItemUpdater itemUpdater = getComponent(ItemUpdater.class);
		
		logger.debug("Component instance '{}'.", eventBus.toString());
		logger.debug("Component instance '{}'.", itemRegistry.toString());
		logger.debug("Component instance '{}'.", itemUpdater.toString());
	}

	@Override
	public void stop() {
		removeComponent(EventBusImpl.class);
		removeComponent(ItemRegistryImpl.class);
		removeComponent(ItemUpdater.class);
	}
	
}
