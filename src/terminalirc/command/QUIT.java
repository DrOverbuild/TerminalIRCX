/*
 $ Copyright (c) 2014 Jasper Reddin
 $ All rights reserved.
 */
package terminalirc.command;

import jline.TerminalFactory;
import org.pircbotx.hooks.WaitForQueue;
import org.pircbotx.hooks.events.DisconnectEvent;
import terminalirc.Client;

/**
 *
 * @author jasper
 */
public class QUIT implements Command{

	public QUIT(Client c) {
		this.c = c;
	}

	Client c;
	
	@Override
	public boolean execute(String[] args) {
		try {
			System.out.println("Shutting down...");
			c.getConnection().stopBotReconnect();
			c.getConnection().sendIRC().quitServer();
			WaitForQueue queue = new WaitForQueue(c.getConnection());
			queue.waitFor(DisconnectEvent.class);
			queue.close();
			try {
				TerminalFactory.get().restore();
			} catch(Exception e) {
				e.printStackTrace();
			}
			
		} catch(InterruptedException ex) {
        } finally{
			System.exit(0);
		}
		return true;
	}

	@Override
	public String getName() {
		return "quit";
	}

	@Override
	public String getShortcut() {
		return "exit";
	}

	@Override
	public String getUsage() {
		return "/quit";
	}

	@Override
	public String getDesc() {
		return "Quits the IRC client.";
	}
}
