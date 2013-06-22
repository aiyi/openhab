package org.openhab.core;

import java.util.Properties;

import org.openhab.core.internal.binding.AutoUpdateBinding;
import org.openhab.core.internal.binding.AutoUpdateGenericBindingProvider;
import org.openhab.core.internal.events.EventBusImpl;
import org.openhab.core.internal.items.ItemRegistryImpl;
import org.openhab.core.internal.items.ItemUpdater;
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
		addComponent(AutoUpdateBinding.class);
		addComponent(AutoUpdateGenericBindingProvider.class);
	}

	@Override
	public void start() {
		logger.info("Module {} started", getName());
	}

}
