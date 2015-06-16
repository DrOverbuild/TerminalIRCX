/*
 $ Copyright (c) 2014 Jasper Reddin
 $ All rights reserved.
 */
package eventhandling;

import com.google.common.collect.ImmutableSortedSet;
import org.pircbotx.PircBotX;
import org.pircbotx.User;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.ActionEvent;
import org.pircbotx.hooks.events.JoinEvent;
import org.pircbotx.hooks.events.KickEvent;
import org.pircbotx.hooks.events.MessageEvent;
import org.pircbotx.hooks.events.NickAlreadyInUseEvent;
import org.pircbotx.hooks.events.NickChangeEvent;
import org.pircbotx.hooks.events.OpEvent;
import org.pircbotx.hooks.events.PartEvent;
import org.pircbotx.hooks.events.PrivateMessageEvent;
import org.pircbotx.hooks.events.QuitEvent;
import org.pircbotx.hooks.events.TopicEvent;
import org.pircbotx.hooks.events.UserListEvent;
import terminalirc.Client;
import terminalirc.TerminalIRC;

/**
 *
 * @author jasper
 */
public class EventHandler extends ListenerAdapter<PircBotX>{
	
	Client client;

	public void setClient(Client client) {
		this.client = client;
	}

	public Client getClient() {
		return client;
	}

	@Override
	public void onMessage(MessageEvent<PircBotX> event) throws Exception {
		TerminalIRC.println(TerminalIRC.timeStamp() + " " + event.getChannel().getName() + " <" + event.getUser().getNick() + "> " + event.getMessage());
	}

	@Override
	public void onAction(ActionEvent<PircBotX> event) throws Exception {
		TerminalIRC.println(TerminalIRC.timeStamp() + " " + event.getChannel().getName() + " * " + event.getUser().getNick() + " " + event.getAction());
	}

	@Override
	public void onNickChange(NickChangeEvent<PircBotX> event) throws Exception {
		TerminalIRC.println(TerminalIRC.timeStamp() + " " + event.getOldNick() + " is now known as " + event.getNewNick());
	}

	@Override
	public void onNickAlreadyInUse(NickAlreadyInUseEvent<PircBotX> event) throws Exception {
		TerminalIRC.println(TerminalIRC.timeStamp() + " " + event.getUsedNick()+" is already logged on. Using "+event.getAutoNewNick()+" instead.");
	}

	@Override
	public void onJoin(JoinEvent<PircBotX> event) throws Exception {
		TerminalIRC.println(TerminalIRC.timeStamp() + " " + event.getUser().getNick() + " has joined " + event.getChannel().getName());
	}

	@Override
	public void onKick(KickEvent<PircBotX> event) throws Exception {
		TerminalIRC.println(TerminalIRC.timeStamp() + " " + event.getUser().getNick() + " kicked " + event.getRecipient().getNick() + " (" + event.getReason() + ")");
	}

	@Override
	public void onQuit(QuitEvent<PircBotX> event) throws Exception {
		TerminalIRC.println(TerminalIRC.timeStamp() + " " + event.getUser().getNick() + " left the server (" + event.getReason() + ")");
	}

	@Override
	public void onUserList(UserListEvent<PircBotX> event) throws Exception {
		StringBuilder sentence = new StringBuilder("Users currently connected to " + event.getChannel().getName() + ": ");
		ImmutableSortedSet<User> users = event.getUsers();
		for(User u : users){
			if(event.getChannel().isOp(u)){
				sentence.append("@");
			}else if(event.getChannel().isOwner(u)){
				sentence.append("&");
			}
			sentence.append(u.getNick()).append(" ");
		}
		TerminalIRC.println(sentence.toString());
	}

	@Override
	public void onPart(PartEvent<PircBotX> event) throws Exception {
		TerminalIRC.println(TerminalIRC.timeStamp() + " " + event.getUser().getNick() + " left " + event.getChannel().getName());
	}

	@Override
	public void onTopic(TopicEvent<PircBotX> event) throws Exception {
		if(event.isChanged()){
			TerminalIRC.println(TerminalIRC.timeStamp() + " " + event.getUser().getNick() + " set the topic of " + event.getChannel().getName() + " to " + event.getTopic());
		}else{
			TerminalIRC.println("The topic of " + event.getChannel().getName() + " is " + event.getTopic());
			TerminalIRC.println("The topic was set by " + event.getUser().getNick());
		}
	}

	@Override
	public void onPrivateMessage(PrivateMessageEvent<PircBotX> event) throws Exception {
		TerminalIRC.println(TerminalIRC.timeStamp() + " " + event.getUser().getNick() + " whispers to you: " + event.getMessage());
		getClient().setLastPMSender(event.getUser());
	}

	@Override
	public void onOp(OpEvent<PircBotX> event) throws Exception {
		TerminalIRC.println(TerminalIRC.timeStamp() + " " + event.getChannel().getName() + " "
				+ event.getUser().getNick() + " has opped " 
				+ event.getRecipient().getNick());
	}
	
}
