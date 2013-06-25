/**
 * openHAB, the open Home Automation Bus.
 * Copyright (C) 2010-2013, openHAB.org <admin@openhab.org>
 *
 * See the contributors.txt file in the distribution for a
 * full listing of individual contributors.
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation; either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see <http://www.gnu.org/licenses>.
 *
 * Additional permission under GNU GPL version 3 section 7
 *
 * If you modify this Program, or any covered work, by linking or
 * combining it with Eclipse (or a modified version of that library),
 * containing parts covered by the terms of the Eclipse Public License
 * (EPL), the licensors of this Program grant you additional permission
 * to convey the resulting work.
 */
package org.openhab.core.model.internal;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.sax.SAXSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import org.openhab.core.config.ConfigManager;
//--->[Shenglong]
//import org.eclipse.core.runtime.ListenerList;
//<---
import org.openhab.core.model.EventType;
import org.openhab.core.model.Model;
import org.openhab.core.model.ModelRepository;
import org.openhab.core.model.ModelRepositoryChangeListener;
import org.openhab.core.model.items.ItemModel;
import org.openhab.core.model.sitemap.LinkableWidget;
import org.openhab.core.model.sitemap.Sitemap;
import org.openhab.core.model.sitemap.Widget;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.InputSource;

public class ModelRepositoryImpl implements ModelRepository {

	private static final Logger logger = LoggerFactory.getLogger(ModelRepositoryImpl.class);
	private Map<String, Model> modelMap = Collections.synchronizedMap(new HashMap<String, Model>());
	// --->[Shenglong] replace with JDK CopyOnWriteArrayList
	// private final ListenerList listeners = new ListenerList();
	// <---
	private final List<ModelRepositoryChangeListener> listeners = new CopyOnWriteArrayList<ModelRepositoryChangeListener>();

	public Model createModel(String name) {
		String modelType = null;
		String modelDir = null;
		Class<?> modelClass = null;
		
		if (name.endsWith("items")) {
			modelType = "items";
			modelDir = "items";
			modelClass = ItemModel.class;
		}
		else if (name.endsWith("sitemap")) {
			modelType = "sitemap";
			modelDir = "sitemaps";
			modelClass = Sitemap.class;
		}
		
		if (modelType == null)
			return null;
		
		try {
			File source = new File(ConfigManager.getConfigFolder() + "/" + modelDir + "/" + name);
			String schemaLanguage = XMLConstants.W3C_XML_SCHEMA_NS_URI;
            SchemaFactory schemaFactory = SchemaFactory.newInstance(schemaLanguage);
            Schema schema = schemaFactory.newSchema(new File("src/core/org.openhab.core.model/src/main/resources/schema/" + modelType + ".xsd"));
            Validator validator = schema.newValidator();
            InputSource inputSource = new InputSource(new FileInputStream(source));
            validator.validate(new SAXSource(inputSource));
            
			JAXBContext context = JAXBContext.newInstance(modelClass);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			Model model = (Model)unmarshaller.unmarshal(source);
			
			if (model instanceof Sitemap) {
				for (Widget widget : ((Sitemap)model).getChildren()) {
					widget.setParent(model);
					associateParentWidget(widget);
				}
			}
			
            modelMap.put(name, model);
			logger.debug("Configuration model '{}' created.", name);
			return model;
		} catch (Exception e) {
			//logger.error("Failed to create model '{}'.", name, e);
			logger.error(e.getMessage());
		}
		
		return null;
	}

	public void associateParentWidget(Widget widget) {
		if (widget instanceof LinkableWidget) {
			for (Widget child : ((LinkableWidget)widget).getChildren()) {
				child.setParent(widget);
				associateParentWidget(child);
			}
		}
	}
	
	public Model getModel(String name) {
		synchronized (modelMap) {
			Model model = modelMap.get(name);
			return model;
		}
	}

	public boolean addOrRefreshModel(String name, InputStream inputStream) {
		Model model = getModel(name);
		if (model == null) {
			synchronized (modelMap) {
				// try again to retrieve the resource as it might have been
				// created by now
				model = getModel(name);
				if (model == null) {
					// seems to be a new file
					model = createModel(name);
					if (model != null) {
						notifyListeners(name, EventType.ADDED);
						return true;
					}
					else
						logger.warn("Configuration model '" + name + "' cannot be parsed correctly!");
				}
			}
		} else {
			synchronized (modelMap) {
				modelMap.remove(name);
				model = createModel(name);
				if (model != null) {
					notifyListeners(name, EventType.MODIFIED);
					return true;
				}
				else
					logger.warn("Configuration model '" + name + "' cannot be parsed correctly!");
			}
		}
		return false;
	}

	public boolean removeModel(String name) {
		Model model = getModel(name);
		if (model != null) {
			synchronized (modelMap) {
				modelMap.remove(name);
				notifyListeners(name, EventType.REMOVED);
				return true;
			}
		} else {
			return false;
		}
	}

	public Collection<String> getAllModelNamesOfType(final String modelType) {
		Collection<String> matchedModels = new ArrayList<String>();
		synchronized (modelMap) {
			for (String name : modelMap.keySet()) {
				if (name.endsWith(modelType)) {
					matchedModels.add(name);
				}
			}
		}
		return matchedModels;
	}

	public void addModelRepositoryChangeListener(ModelRepositoryChangeListener listener) {
		listeners.add(listener);
	}

	public void removeModelRepositoryChangeListener(ModelRepositoryChangeListener listener) {
		listeners.remove(listener);
	}

	private void notifyListeners(String name, EventType type) {
		// --->[Shenglong]
		// for(Object listener : listeners.getListeners()) {
		// ModelRepositoryChangeListener changeListener =
		// (ModelRepositoryChangeListener) listener;
		// changeListener.modelChanged(name, type);
		// }
		// <---
		for (ModelRepositoryChangeListener listener : listeners) {
			listener.modelChanged(name, type);
		}
	}

}
