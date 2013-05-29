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
package org.openhab.core.internal.events;

import net.engio.mbassy.bus.BusConfiguration;
import net.engio.mbassy.bus.MBassador;
import net.engio.mbassy.listener.Handler;

import org.openhab.core.events.EventBus;
import org.openhab.core.events.Event;
import org.openhab.core.events.EventSubscriber;
import org.openhab.core.types.Command;
import org.openhab.core.types.EventType;
import org.openhab.core.types.State;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This is the implementation of the {@link EventBus} interface.
 * Through it, events can be sent to the EventBus service in
 * order to broadcast them.
 * 
 * @author Jesse
 * @since 2.0.0
 */
public class EventBusImpl implements EventBus {

	private static final Logger logger = LoggerFactory.getLogger(EventBusImpl.class);

	private MBassador<Event> eventBus;

	public EventBusImpl() {
		eventBus = new MBassador<Event>(BusConfiguration.Default());
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.openhab.core.internal.events.EventBus#addSubscriber(org.openhab
	 * .core.events.EventSubscriber)
	 */
	public void addSubscriber(EventSubscriber subscriber) {
		EventHandler handler = new EventHandler(subscriber);
		eventBus.subscribe(handler);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.openhab.core.internal.events.EventBus#sendCommand(org.openhab
	 * .core.items.GenericItem, org.openhab.core.datatypes.DataType)
	 */
	public void sendCommand(String itemName, Command command) {
		if (command != null) {
			eventBus.publish(createCommandEvent(itemName, command));
		} else {
			logger.warn("given command is NULL, couldn't send command to '{}'", itemName);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.openhab.core.internal.events.EventBus#postCommand(org.openhab
	 * .core.items.GenericItem, org.openhab.core.datatypes.DataType)
	 */
	public void postCommand(String itemName, Command command) {
		if (command != null) {
			eventBus.publishAsync(createCommandEvent(itemName, command));
		} else {
			logger.warn("given command is NULL, couldn't post command to '{}'", itemName);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.openhab.core.internal.events.EventBus#postUpdate(org.openhab
	 * .core.items.GenericItem, org.openhab.core.datatypes.DataType)
	 */
	public void postUpdate(String itemName, State newState) {
		if (newState != null) {
			eventBus.publishAsync(createUpdateEvent(itemName, newState));
		} else {
			logger.warn("given new state is NULL, couldn't post update for '{}'", itemName);
		}
	}

	private Event createUpdateEvent(String itemName, State newState) {
		return new Event(EventType.UPDATE, itemName, newState);
	}

	private Event createCommandEvent(String itemName, Command command) {
		return new Event(EventType.COMMAND, itemName, command);
	}

	
	public class EventHandler {

		EventSubscriber subscriber;
		
		public EventHandler(EventSubscriber subscriber) {
			this.subscriber = subscriber;
		}
		
		@Handler
		public void handleEvent(Event event) {  
			String itemName = (String) event.getItemName();
			EventType eventType = event.getEventType();
			
			if(eventType == EventType.UPDATE) {
				State newState = (State) event.getEventData();
				if(newState!=null) subscriber.receiveUpdate(itemName, newState);
			}
			else if(eventType == EventType.COMMAND) {
				Command command = (Command) event.getEventData();
				if(command!=null) subscriber.receiveCommand(itemName, command);
			}
			else {
				return; // we have received an event with an invalid event type
			}
		}
	}
	
}
