/*
 $ Copyright (c) 2014 Jasper Reddin
 $ All rights reserved.
 */
package terminalirc.command;

import terminalirc.Client;

/**
 *
 * @author jasper
 */
public class R implements Command{

	Client client;

	public R(Client client) {
		this.client = client;
	}
	
	@Override
	public void execute(String[] args) {
		if(args.length<1){
			terminalirc.TerminalIRC.printlnWithoutStashing("Usage: /r <message>");
			return;
		}
		
		if(client.getLastPMSender() == null){
			terminalirc.TerminalIRC.printlnWithoutStashing("No one has sent you a message!");
			return;
		}
		
		StringBuilder message = new StringBuilder();
		
		for(int i = 0; i<args.length-1;i++){
			message.append(args[i]).append(" ");
		}
		
		message.append(args[args.length-1]);
		client.getLastPMSender().send().message(message.toString());
	}

	@Override
	public String getName() {
		return "r";
	}

	@Override
	public String getShortcut() {
		return "r";
	}

	@Override
	public String getDesc() {
		return "Replies to the last Private Message received.";
	}
	
}
