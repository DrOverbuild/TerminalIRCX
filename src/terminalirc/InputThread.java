/*
 $ Copyright (c) 2014 Jasper Reddin
 $ All rights reserved.
 */
package terminalirc;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.pircbotx.hooks.WaitForQueue;
import org.pircbotx.hooks.events.ConnectEvent;

/**
 *
 * @author jasper
 */
public class InputThread implements Runnable{
	
	Client client;

	public InputThread(Client client) {
		this.client = client;
	}

	@Override
	public void run() {
		try {
			WaitForQueue queue = new WaitForQueue(client.getConnection());
			queue.waitFor(ConnectEvent.class);
			queue.close();
			TerminalIRC.updatePrompt();
			while(true){
				String input = TerminalIRC.readLine();
				if(!input.startsWith("/")){
					if(TerminalIRC.channel.equals("")){
						TerminalIRC.printlnWithoutStashing("You are not a part of any channel.");
					}else{
						client.sendMessage(TerminalIRC.channel, input);
					}
				}else {
					try{
						client.onCommand(input);
					}catch(Exception e){
						System.out.print("Unable to process command: ");
						e.printStackTrace();
					}
				}
			}
		} catch (InterruptedException ex) {
			Logger.getLogger(InputThread.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}
