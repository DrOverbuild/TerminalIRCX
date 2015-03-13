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
	public void execute(String[] args) {
		if(args.length == 1){
			connection.getConnection().sendIRC().changeNick(args[0]);
			TerminalIRC.nick = args[0];
			TerminalIRC.updatePrompt();
		}else{
			TerminalIRC.printlnWithoutStashing("Usage: /nick <new nick>");
		}
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
	public String getDesc() {
		return "Changes your nickname.";
	}
	
}
