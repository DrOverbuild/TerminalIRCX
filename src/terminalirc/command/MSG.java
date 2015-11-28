/*
 $ Copyright (c) 2014 Jasper Reddin
 $ All rights reserved.
 */
package terminalirc.command;

import org.pircbotx.User;
import terminalirc.Client;
import terminalirc.TerminalIRC;

/**
 *
 * @author jasper
 */
public class MSG implements Command{

	Client client;

	public MSG(Client client) {
		this.client = client;
	}
	
	@Override
	public boolean execute(String[] args) {
		if(args.length<2){
			return false;
		}
		
		User u = client.getUserChannelDao().getUser(args[0]);
		client.setLastPMSender(u);
		
		StringBuilder message = new StringBuilder();
		
		for(int i = 1; i<args.length-1;i++){
			message.append(args[i]).append(" ");
		}
		
		message.append(args[args.length-1]);
		
		terminalirc.TerminalIRC.printlnWithoutStashing("You said to " + client.getLastPMSender().getNick() + ": " + message.toString());
		u.send().message(message.toString());

		return true;
	}

	@Override
	public String getName() {
		return "msg";
	}

	@Override
	public String getShortcut() {
		return "w";
	}

	@Override
	public String getUsage() {
		return "/msg <nick of recipient> <message>";
	}

	@Override
	public String getDesc() {
		return "Sends a user a Private Message";
	}
	
}
