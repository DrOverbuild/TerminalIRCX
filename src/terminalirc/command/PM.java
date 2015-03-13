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
public class PM implements Command{

	Client client;

	public PM(Client client) {
		this.client = client;
	}
	
	@Override
	public void execute(String[] args) {
		if(args.length<2){
			TerminalIRC.printlnWithoutStashing("Usage: /pm <nick of recipient> <message>");
			return;
		}
		User u = client.getUserChannelDao().getUser(args[0]);
		
		StringBuilder message = new StringBuilder();
		
		for(int i = 1; i<args.length-1;i++){
			message.append(args[i]).append(" ");
		}
		
		message.append(args[args.length-1]);
		
		u.send().message(message.toString());
		
	}

	@Override
	public String getName() {
		return "pm";
	}

	@Override
	public String getShortcut() {
		return "w";
	}

	@Override
	public String getDesc() {
		return "Sends a user a Private Message";
	}
	
}
