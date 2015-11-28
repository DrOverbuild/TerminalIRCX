/*
 $ Copyright (c) 2014 Jasper Reddin
 $ All rights reserved.
 */
package terminalirc.command;

import com.google.common.collect.ImmutableSortedSet;
import org.pircbotx.Channel;
import org.pircbotx.User;
import terminalirc.Client;
import terminalirc.TerminalIRC;

/**
 *
 * @author jasper
 */
public class LISTUSERS implements Command{
	
	Client c;

	public LISTUSERS(Client c) {
		this.c = c;
	}

	@Override
	public boolean execute(String[] args) {
		Channel channel = c.getUserChannelDao().getChannel(TerminalIRC.channel);
		ImmutableSortedSet<User> users = c.getUserChannelDao().getUsers(channel);
		StringBuilder sentence = new StringBuilder("Users currently connected to " + TerminalIRC.channel + ": ");
		for(User u : users){
			if(channel.isOp(u)){
				sentence.append("@");
			}else if(channel.isOwner(u)){
				sentence.append("&");
			}
			sentence.append(u.getNick()).append(" ");
		}
		TerminalIRC.printlnWithoutStashing(sentence.toString());
		return true;
	}

	@Override
	public String getName() {
		return "listusers";
	}

	@Override
	public String getShortcut() {
		return "users";
	}

	@Override
	public String getUsage() {
		return "/listusers";
	}

	@Override
	public String getDesc() {
		return "Lists the users connected to the current channel";
	}
	
}
