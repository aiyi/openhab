package org.openhab.core.persist;

import java.util.Properties;

import org.openhab.core.persist.extensions.PersistenceExtensions;
import org.openhab.core.persist.internal.PersistenceManager;
import org.openhab.core.service.Module;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class PersistModule extends Module {
	
	private static Logger logger = LoggerFactory.getLogger(PersistModule.class);

	public PersistModule() {
		setSimpleName("persist");
	}
	
	@Override
	public void configure(Properties config) {
		super.configure(config);
		addComponent(PersistenceManager.class);
		addComponent(PersistenceExtensions.class);
	}

	@Override
	public void start() {
		logger.info("Module {} started", getName());
		
		getComponent(PersistenceManager.class).activate();
		updated(getInitConfig());
	}

	@Override
	public void updated(Properties config) {
		logger.debug("Configuration updated {}", config.toString());

		getComponent(PersistenceExtensions.class).updated(config);
	}
}
