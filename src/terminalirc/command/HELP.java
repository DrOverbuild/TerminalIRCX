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
public class HELP implements Command{
	
	Client client;

	public HELP(Client client) {
		this.client = client;
	}

	@Override
	public void execute(String[] args) {
		
	}

	@Override
	public String getName() {
		return "help";
	}

	@Override
	public String getShortcut() {
		return "help";
	}

	@Override
	public String getDesc() {
		return "Lists all the commands.";
	}
	
}
