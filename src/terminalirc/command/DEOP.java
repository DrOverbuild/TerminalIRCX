/*
 $ Copyright (c) 2014 Jasper Reddin
 $ All rights reserved.
 */
package terminalirc.command;

import org.pircbotx.Channel;
import terminalirc.Client;
import terminalirc.TerminalIRC;

/**
 *
 * @author jasper
 */
public class DEOP implements Command{
	
	Client connection;

	public DEOP(Client connection) {
		this.connection = connection;
	}

	@Override
	public void execute(String[] args) {
		if(args.length == 1){
			Channel channel = connection.getConnection().getUserChannelDao().getChannel(TerminalIRC.channel);
			channel.send().deOp(connection.getConnection().getUserChannelDao().getUser(args[0]));
		}else{
			TerminalIRC.printlnWithoutStashing("Usage: /deop <op name>");
		}
	}

	@Override
	public String getName() {return "deop";}

	@Override
	public String getShortcut() {return "deop";}

	@Override
	public String getDesc() {return "Deops a user from the channel.";}
	
}
