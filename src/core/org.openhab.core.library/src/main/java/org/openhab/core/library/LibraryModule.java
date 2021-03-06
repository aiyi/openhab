package org.openhab.core.library;

import java.util.Properties;

import org.openhab.core.library.internal.CoreItemFactory;
import org.openhab.core.service.Module;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LibraryModule extends Module {

	private static Logger logger = LoggerFactory.getLogger(LibraryModule.class);

	@Override
	public void configure(Properties config) {
		super.configure(config);
		addComponent(CoreItemFactory.class);
	}

	@Override
	public void start() {
		logger.info("Module {} started", getName());
	}
}
