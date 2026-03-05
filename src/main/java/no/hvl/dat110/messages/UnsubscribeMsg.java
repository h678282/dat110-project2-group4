package no.hvl.dat110.messages;

public class UnsubscribeMsg extends Message {

	// message sent from client to unsubscribe on a topic 

    public UnsubscribeMsg(String user, String topic) {
        super(MessageType.UNSUBSCRIBE, user);
        this.topic = topic;
    }

	// Implement object variables - a topic is required
    private String topic;

	// Complete the constructor, get/set-methods, and toString method
	// as described in the project text

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getTopic() {
        return topic;
    }

    @Override
    public String toString() {
        return "UnsubscribeMsg [topic= " + topic + "]" + super.toString();
    }
	
}
