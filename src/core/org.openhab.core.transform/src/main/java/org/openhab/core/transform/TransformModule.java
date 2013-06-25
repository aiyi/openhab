package org.openhab.core.transform;

import java.util.Properties;

import org.openhab.core.service.Module;
import org.openhab.core.transform.internal.service.MapTransformationService;
import org.openhab.core.transform.internal.service.RegExTransformationService;
import org.openhab.core.transform.internal.service.XPathTransformationService;
import org.openhab.core.transform.internal.service.XsltTransformationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TransformModule extends Module {

	private static Logger logger = LoggerFactory.getLogger(TransformModule.class);

	public static final String TRANSFORM_FOLDER_NAME = "transform";
	
	@Override
	public void configure(Properties config) {
		super.configure(config);
		addComponent(MapTransformationService.class);
		addComponent(RegExTransformationService.class);
		addComponent(XPathTransformationService.class);
		addComponent(XsltTransformationService.class);
	}

	@Override
	public void start() {
		logger.info("Module {} started", getName());
	}

	public static TransformationService getServiceReference(String type) {
		String key = "org.openhab.core.transform.internal.service." + type + "TransformationService";
		return (TransformationService)getComponent(key);
	}
	
}
