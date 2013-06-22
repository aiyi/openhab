package org.openhab.io.rest;

import java.util.Properties;

import org.openhab.core.service.Module;
import org.openhab.io.rest.internal.RESTApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RestModule extends Module {

	private static Logger logger = LoggerFactory.getLogger(RestModule.class);

	@Override
	public void configure(Properties config) {
		super.configure(config);
		addComponent(RESTApplication.class);
	}

	@Override
	public void start() {
		logger.info("Module {} started", getName());
	}

}
