/*
 $ Copyright (c) 2014 Jasper Reddin
 $ All rights reserved.
 */
package terminalirc.command;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.pircbotx.Channel;
import org.pircbotx.hooks.WaitForQueue;
import org.pircbotx.hooks.events.PartEvent;
import terminalirc.Client;
import terminalirc.TerminalIRC;

/**
 *
 * @author jasper
 */
public class CHANNEL implements Command{
	
	Client c;

	public CHANNEL(Client c) {
		this.c = c;
	}
	
	

	@Override
	public boolean execute(String[] args) {
		if(args.length == 1){
			if(args[0].equalsIgnoreCase("leave")){
				this.execute(new String[]{"leave",TerminalIRC.channel});
			}else if (args[0].equalsIgnoreCase("list")){
				StringBuilder sentence = new StringBuilder("Channels you are currently connected to: ");
				for (Channel channel:c.getConnection().getUserBot().getChannels()){
					sentence.append(channel.getName()).append(" ");
				}
				TerminalIRC.printlnWithoutStashing(sentence.toString());
				return true;
			}
		}
		
		if(args.length != 2){
			return false;
		}
		
		String newChannel = args[1].toLowerCase();
		
		if(!newChannel.startsWith("#")){
			newChannel = "#" + newChannel;
		}
		
		if(args[0].equalsIgnoreCase("switch")){
			if(c.isConnectedToChannel(newChannel)){
				terminalirc.TerminalIRC.channel = newChannel;
				TerminalIRC.printlnWithoutStashing("You are now chatting in " + newChannel);
				TerminalIRC.updatePrompt();
				return true;
			}
			TerminalIRC.println("You have not joined that channel. Type /channel join " + newChannel + " to join.");
		}else if(args[0].equalsIgnoreCase("join")){
			if(c.isConnectedToChannel(newChannel)){
				TerminalIRC.println("You have already joined " + newChannel + ". Type /channel switch " + newChannel + " to start chatting in that channel.");
			}else{
				c.getConnection().sendIRC().joinChannel(newChannel);
				terminalirc.TerminalIRC.channel = newChannel;
				TerminalIRC.printlnWithoutStashing("You are now chatting in " + newChannel);
				TerminalIRC.updatePrompt();
			}
		}else if(args[0].equalsIgnoreCase("leave")){
			if(c.isConnectedToChannel(newChannel)){
				c.getConnection().getUserChannelDao().getChannel(newChannel).send().part();
				WaitForQueue queue = new WaitForQueue(c.getConnection());
				try {
					queue.waitFor(PartEvent.class);
				} catch (InterruptedException ex) {
					Logger.getLogger(CHANNEL.class.getName()).log(Level.SEVERE, null, ex);
				}
				queue.close();
				if(terminalirc.TerminalIRC.channel.equalsIgnoreCase(newChannel)){
					if(c.getConnection().getUserChannelDao().getAllChannels().size()==0){
						terminalirc.TerminalIRC.channel = "";
						TerminalIRC.printlnWithoutStashing("You have not joined any channel.");
						TerminalIRC.updatePrompt();
						return true;
					}
					terminalirc.TerminalIRC.channel = c.getConnection().getUserChannelDao().getAllChannels().first().getName();
					TerminalIRC.println("You are now chatting in " + terminalirc.TerminalIRC.channel);
					TerminalIRC.updatePrompt();
				}
			}
		}else {
			return false;
		}
		return true;
	}

	@Override
	public String getName() {
		return "channel";
	}

	@Override
	public String getShortcut() {
		return "channel";
	}

	@Override
	public String getUsage() {
		return "/channel <switch|join|leave|list> <channel>";
	}

	@Override
	public String getDesc() {
		return "Lists, switches, joins, or leaves a channel";
	}
	
}
