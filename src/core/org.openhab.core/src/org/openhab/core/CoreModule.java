package org.openhab.core;

import java.util.Properties;

import org.openhab.core.events.EventBus;
import org.openhab.core.internal.events.EventBusImpl;
import org.openhab.core.internal.items.ItemRegistryImpl;
import org.openhab.core.internal.items.ItemUpdater;
import org.openhab.core.items.ItemRegistry;
import org.openhab.core.service.Module;

public class CoreModule extends Module {

	@Override
	public void configure(Properties config) {
		super.configure(config);
		addComponent(EventBusImpl.class);
		addComponent(ItemRegistryImpl.class);
		addComponent(ItemUpdater.class);
	}

	@Override
	public void updated(Properties config) {
		
	}
	
	@Override
	public void start() {
		EventBus eventBus = getComponent(EventBus.class);
		ItemRegistry itemRegistry = getComponent(ItemRegistry.class);
		ItemUpdater itemUpdater = getComponent(ItemUpdater.class);
		
        System.out.println(eventBus.toString());
        System.out.println(itemRegistry.toString());
        System.out.println(itemUpdater.toString());
	}

	@Override
	public void stop() {
		removeComponent(EventBusImpl.class);
		removeComponent(ItemRegistryImpl.class);
		removeComponent(ItemUpdater.class);
	}
	
}
