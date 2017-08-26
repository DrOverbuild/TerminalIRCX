/*
 $ Copyright (c) 2014 Jasper Reddin
 $ All rights reserved.
 */
package terminalirc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import org.pircbotx.PircBotX;
import org.pircbotx.User;
import org.pircbotx.UserChannelDao;
import terminalirc.command.Command;

/**
 *
 * @author jasper
 */
public class Client{
	
	private ArrayList<Command> commands = new ArrayList<>();
	private PircBotX connection;
	private User lastPMSender = null;

	// LOGGING
//	private Map<String, >

	public Client(PircBotX connection) {
		this.connection = connection;
	}

	public PircBotX getConnection() {
		return connection;
	}

	public User getLastPMSender() {
		return lastPMSender;
	}

	public void setLastPMSender(User lastPMSender) {
		this.lastPMSender = lastPMSender;
	}
	
	public UserChannelDao getUserChannelDao(){
		return getConnection().getUserChannelDao();
	}
	
	public void sendMessage(String channel, String message){
		getUserChannelDao().getChannel(channel).send().message(message);
	}
	
	public void addCommand(Command c){
		commands.add(c);
	}
	
	public ArrayList<Command> getCommands(){
		return commands;
	}
	
	public Command parseCommand(String str){
		// Get first word of string
		String commandString = str.split(" ")[0];
		
		// Remove initial slash if present
		if(commandString.startsWith("/")){
			commandString = commandString.substring(1, commandString.length());
		}
		
		// Find the command
		for(Command c : commands){
			if(commandString.equalsIgnoreCase(c.getName())||commandString.equalsIgnoreCase(c.getShortcut())){
				return c;
			}
		}
		return null;
	}
	
	public static String[] parseArgs(String userInput){
		ArrayList<String> list = new ArrayList<>();
		list.addAll(Arrays.asList(userInput.split("\\s")));
		
		list.remove(0);
		
		String[] args = new String[list.size()];
		
		return list.toArray(args);
	}
	
	public void onCommand(String txt){
		Command command = parseCommand(txt);
		
		if(command == null){
			TerminalIRC.printlnWithoutStashing("Unknown command. Type /help for a list of commands.");
			return;
		}
		
		String[] args = parseArgs(txt);
		
		if(!command.execute(args)){
			TerminalIRC.printlnWithoutStashing("Usage: " + command.getUsage());
		}
		
	}
	
	public boolean isConnectedToChannel(String channel){
		String c = channel;
		if(!c.startsWith("#")){
			c = "#" + c;
		}
		return connection.getUserChannelDao().channelExists(c);
	}
}
