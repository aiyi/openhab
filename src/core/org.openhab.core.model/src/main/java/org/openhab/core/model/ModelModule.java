package org.openhab.core.model;

import java.util.Properties;

import org.openhab.core.model.internal.GenericItemProvider;
import org.openhab.core.model.internal.ModelRepositoryImpl;
import org.openhab.core.model.internal.SitemapProviderImpl;
import org.openhab.core.model.internal.folder.FolderObserver;
import org.openhab.core.service.Module;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ModelModule extends Module {

	private static Logger logger = LoggerFactory.getLogger(ModelModule.class);

	public ModelModule() {
		setSimpleName("model");
	}
	
	@Override
	public void configure(Properties config) {
		super.configure(config);
		addComponent(FolderObserver.class);
		addComponent(GenericItemProvider.class);
		addComponent(SitemapProviderImpl.class);
		addComponent(ModelRepositoryImpl.class);
	}

	@Override
	public void start() {
		logger.info("Module {} started", getName());

		updated(getInitConfig());
	}

	@Override
	public void updated(Properties config) {
		logger.debug("Configuration updated {}", config.toString());

		getComponent(FolderObserver.class).updated(config);
	}
}
