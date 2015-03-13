/*
 $ Copyright (c) 2014 Jasper Reddin
 $ All rights reserved.
 */
package terminalirc.command;

import terminalirc.Client;
import terminalirc.TerminalIRC;

/**
 *
 * @author jasper
 */
public class TOPIC implements Command{
	
	Client c;

	public TOPIC(Client c) {
		this.c = c;
	}
	
	@Override
	public void execute(String[] args) {
		if(args.length > 0){
			String s = "";
			for (int i = 0; i < args.length-1; i++) {
				s = s+args[i] + " ";
			}
			s = s+args[args.length - 1];
			
			c.getUserChannelDao().getChannel(TerminalIRC.channel).send().setTopic(s);
		}else{
			TerminalIRC.printlnWithoutStashing("Usage: /topic <new topic>");
		}
	}

	@Override
	public String getName() {
		return "topic";
	}

	@Override
	public String getShortcut() {
		return "topic";
	}

	@Override
	public String getDesc() {
		return "Changes the topic of the channel.";
	}
}
