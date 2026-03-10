package no.hvl.dat110.broker;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import no.hvl.dat110.common.TODO;
import no.hvl.dat110.common.Logger;
import no.hvl.dat110.messages.CreateTopicMsg;
import no.hvl.dat110.messagetransport.Connection;

public class Storage {

	// data structure for managing subscriptions
	// maps from a topic to set of subscribed users
	protected ConcurrentHashMap<String, Set<String>> subscriptions;
	
	// data structure for managing currently connected clients
	// maps from user to corresponding client session object
	
	protected ConcurrentHashMap<String, ClientSession> clients;

	public Storage() {
		subscriptions = new ConcurrentHashMap<String, Set<String>>();
		clients = new ConcurrentHashMap<String, ClientSession>();
	}

	public Collection<ClientSession> getSessions() {
		return clients.values();
	}

	public Set<String> getTopics() {

		return subscriptions.keySet();

	}

	// get the session object for a given user
	// session object can be used to send a message to the user
	
	public ClientSession getSession(String user) {

		ClientSession session = clients.get(user);

		return session;
	}

	public Set<String> getSubscribers(String topic) {

		return (subscriptions.get(topic));

	}

	public void addClientSession(String user, Connection connection) {

		// See ClientSession class
		ClientSession session = new ClientSession(user, connection);
		clients.put(user, session);

		Logger.log("Client Sessions: " + clients.size());
		
	}

	public void removeClientSession(String user) {

		// and remove client session for user from the storage
		ClientSession session = clients.get(user);

		if (session != null) {
			session.disconnect();
		}

		clients.remove(user);
		Logger.log("Client sessions: " + clients.size());
	}

	public void createTopic(String topic) {

		// creates topic in the storage
		subscriptions.putIfAbsent(topic, ConcurrentHashMap.newKeySet());

		Logger.log("Topic: " + subscriptions.size());
	
	}

	public void deleteTopic(String topic) {

		// deletes topic from the storage
		subscriptions.remove(topic);

		Logger.log("Topic: " + subscriptions.size());
		
	}

	public void addSubscriber(String user, String topic) {

		// adds the user as subscriber to the topic
		Set<String> subs = subscriptions.get(topic);

		if (subs != null) {
			subs.add(user);

			Logger.log("Subscribers: " + topic + " : "+ subs.size());
		}
	}

	public void removeSubscriber(String user, String topic) {

		// removes the user as subscriber to the topic
		Set<String> subs = subscriptions.get(topic);

		if (subs != null) {
			subs.remove(user);

			Logger.log("Subscribers: " + topic + " : "+ subs.size());
		}

	}
}
