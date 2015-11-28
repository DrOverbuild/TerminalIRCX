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
public class NICK implements Command{
	Client connection;

	public NICK(Client connection) {
		this.connection = connection;
	}

	@Override
	public boolean execute(String[] args) {
		if(args.length == 1){
			connection.getConnection().sendIRC().changeNick(args[0]);
			TerminalIRC.nick = args[0];
			TerminalIRC.updatePrompt();
		}else{
			return false;
		}
		return true;
	}

	@Override
	public String getName() {
		return "nick";
	}

	@Override
	public String getShortcut() {
		return "nick";
	}

	@Override
	public String getUsage() {
		return "/nick <new nick>";
	}

	@Override
	public String getDesc() {
		return "Changes your nickname.";
	}
	
}
