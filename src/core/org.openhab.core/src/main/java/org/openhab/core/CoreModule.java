package org.openhab.core;

import java.util.Properties;

import org.openhab.core.events.EventBus;
import org.openhab.core.binding.AutoUpdateBindingProvider;
import org.openhab.core.binding.BindingConfigReader;
import org.openhab.core.internal.binding.AutoUpdateBinding;
import org.openhab.core.internal.binding.AutoUpdateGenericBindingProvider;
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
		addComponent("eventbus", EventBusImpl.class);
		addComponent("itemregistry", ItemRegistryImpl.class);
		addComponent("itemupdater", ItemUpdater.class);
		addComponent("autoupdate.binding", AutoUpdateBinding.class);
		addComponent("autoupdate.genericbindingprovider", AutoUpdateGenericBindingProvider.class);
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
		AutoUpdateBinding autoUpdateBinding = getComponent(AutoUpdateBinding.class);
		AutoUpdateBindingProvider autoUpdateBindingProvider = (AutoUpdateBindingProvider) getComponent("autoupdate.genericbindingprovider");
		BindingConfigReader bindingConfigReader = (BindingConfigReader) getComponent("autoupdate.genericbindingprovider");

		logger.debug("Component instance '{}'.", eventBus.toString());
		logger.debug("Component instance '{}'.", itemRegistry.toString());
		logger.debug("Component instance '{}'.", itemUpdater.toString());
		logger.debug("Component instance '{}'.", autoUpdateBinding.toString());
		logger.debug("Component instance '{}'.", autoUpdateBindingProvider.toString());
		logger.debug("Component instance '{}'.", bindingConfigReader.toString());

		logger.info("Module '{}' has been started.", getName());
	}

}
