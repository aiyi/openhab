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
package org.openhab.io.rest.internal;

import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.core.Application;

import org.openhab.core.events.EventBus;
import org.openhab.core.items.ItemRegistry;
import org.openhab.io.rest.internal.resources.ItemResource;
import org.openhab.io.rest.internal.resources.RootResource;
//import org.openhab.io.rest.internal.resources.SitemapResource;
import org.openhab.core.model.ModelRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This is the main component of the REST API; it gets all required services injected,
 * registers itself as a servlet on the HTTP service and adds the different rest resources
 * to this service.
 * 
 * @author Kai Kreuzer
 * @since 0.8.0
 */
public class RESTApplication extends Application {

	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(RESTApplication.class);

	static private EventBus eventBus;
	
	static private ItemRegistry itemRegistry;

	static private ModelRepository modelRepository;

	public RESTApplication() {
		
	}
	
	public RESTApplication(EventBus eventBus, ItemRegistry itemRegistry, ModelRepository modelRepository) {
		RESTApplication.eventBus = eventBus;
		RESTApplication.itemRegistry = itemRegistry;
		RESTApplication.modelRepository = modelRepository;
	}

	static public EventBus getEventBus() {
		return eventBus;
	}

	static public ItemRegistry getItemRegistry() {
		return itemRegistry;
	}
	
	static public ModelRepository getModelRepository() {
		return modelRepository;
	}
	
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> result = new HashSet<Class<?>>();
        result.add(RootResource.class);
        result.add(ItemResource.class);
        //result.add(SitemapResource.class);
        return result;
    }
}
