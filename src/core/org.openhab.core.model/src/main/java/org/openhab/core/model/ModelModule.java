package org.openhab.core.model;

import java.util.Properties;

import org.openhab.core.model.internal.GenericItemProvider;
import org.openhab.core.model.internal.ModelRepositoryImpl;
import org.openhab.core.model.internal.folder.FolderObserver;
import org.openhab.core.service.Module;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ModelModule extends Module {

	private static Logger logger = LoggerFactory.getLogger(ModelModule.class);

	@Override
	public void configure(Properties config) {
		addComponent("model.folderobserver", FolderObserver.class);
		addComponent("generic.itemprovider", GenericItemProvider.class);
		addComponent("model.repository", ModelRepositoryImpl.class);
		super.configure(config);
	}

	@Override
	public void updated(Properties config) {
		logger.debug("Configuration updated {}.", config.toString());
		getComponent(FolderObserver.class).updated(config);
	}

	@Override
	public void start() {
		logger.info("Module '{}' has been started.", getName());
	}
}
