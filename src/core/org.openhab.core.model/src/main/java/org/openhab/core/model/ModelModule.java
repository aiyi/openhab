package org.openhab.core.model;

import java.util.Properties;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.openhab.core.model.internal.GenericItemProvider;
import org.openhab.core.model.internal.ModelRepositoryImpl;
import org.openhab.core.model.internal.SitemapProviderImpl;
import org.openhab.core.model.internal.folder.FolderObserver;
import org.openhab.core.model.sitemap.Sitemap;
import org.openhab.core.service.Module;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ModelModule extends Module {

	private static Logger logger = LoggerFactory.getLogger(ModelModule.class);

	@Override
	public void configure(Properties config) {
		addComponent("model.folderobserver", FolderObserver.class);
		addComponent("generic.itemprovider", GenericItemProvider.class);
		addComponent("sitemapprovider", SitemapProviderImpl.class);
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
		
		Sitemap sitemap = getComponent(SitemapProvider.class).getSitemap("demo");
		try {
			JAXBContext context = JAXBContext.newInstance(Sitemap.class);
			Marshaller m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			m.marshal(sitemap, System.out);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}
}
