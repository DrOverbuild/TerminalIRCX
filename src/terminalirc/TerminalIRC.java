/*
 $ Copyright (c) 2014 Jasper Reddin
 $ All rights reserved.
 */
package terminalirc;

import eventhandling.EventHandler;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import jline.console.ConsoleReader;
import jline.console.CursorBuffer;
import org.pircbotx.Configuration;
import org.pircbotx.PircBotX;
import org.pircbotx.hooks.WaitForQueue;
import org.pircbotx.hooks.events.DisconnectEvent;
import terminalirc.command.CHANNEL;
import terminalirc.command.DEOP;
import terminalirc.command.HELP;
import terminalirc.command.LINE;
import terminalirc.command.LISTUSERS;
import terminalirc.command.MSG;
import terminalirc.command.NICK;
import terminalirc.command.QUIT;
import terminalirc.command.R;
import terminalirc.command.TOPIC;


/**
 *
 * @author jasper
 */
public class TerminalIRC {
	
	public static String nick = "";
	/**
	 * The IP Address of the server
	 */
	public static String hostname = "";
	public static String channel = "";
	public static String login = "";
	public static String password = "";
	
	public static boolean verbose = false;
	public static CursorBuffer stashed;
	
	public static ConsoleReader console;

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) throws Exception {
		if(!parseArgs(args)){
			System.out.println("Usage: java -jar TerminalIRC.jar <server ip> <login> <channel>");
			System.out.println("       or");
			System.out.println("       java -jar TerminalIRC.jar <server ip> <login> <channel> <password>");
			return;
		}
		
		System.out.println("Starting...");
		
		console = new ConsoleReader();
		nick = login;
		
		EventHandler handler = new EventHandler();
		
		Configuration.Builder builder =	new Configuration.Builder()
			.setName(nick) //Nick of the client. CHANGE IN YOUR CODE
			.setLogin(nick) //Login part of hostmask, eg name:login@host
			.setFinger("Connected via TerminalIRCX") // Set finger
			.setRealName(nick) // Set Nick
			.setAutoNickChange(true) //Automatically change nick when the current one is in use
			.setServer(hostname,6667) //The server were connecting to
			.addAutoJoinChannel(channel) //Join #pircbotx channel on connect
			.addListener(handler);
			
		if(!password.equals("")){
			builder.setNickservPassword(password);
		}
		
		Configuration<PircBotX> config = builder.buildConfiguration(); //Create an immutable configuration from this builder
		
		PircBotX myBot = new PircBotX(config);
		
		printlnWithoutStashing("Loading commands...");
		
		// Now start our client up.
        Client client = new Client(myBot);
		client.addCommand(new CHANNEL(client));
		client.addCommand(new DEOP(client));
		client.addCommand(new HELP(client));
		client.addCommand(new LINE());
		client.addCommand(new LISTUSERS(client));
		client.addCommand(new NICK(client));
		client.addCommand(new MSG(client));
		client.addCommand(new QUIT(client));
		client.addCommand(new R(client));
		client.addCommand(new TOPIC(client));
		
		handler.setClient(client);
		
		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			client.getConnection().stopBotReconnect();
			try{
			client.getConnection().sendIRC().quitServer();
			}catch(RuntimeException e){
				return;
			}
			WaitForQueue queue = new WaitForQueue(client.getConnection());
			try {
				queue.waitFor(DisconnectEvent.class);
			} catch (InterruptedException ex) {
				Logger.getLogger(TerminalIRC.class.getName()).log(Level.SEVERE, null, ex);
			}
			queue.close();
		}));
		
		InputThread inputThread = new InputThread(client);
		new Thread(inputThread).start();
		
		printlnWithoutStashing("Connecting to " + hostname + "...");
		
		myBot.startBot();
	}
	
	public static boolean parseArgs(String[] args){
		if(args.length<3){
			return false;
		}
		hostname = args[0];
		login = args[1];
		channel = args[2].toLowerCase();
		if(!channel.startsWith("#")){
			channel = "#" + channel;
		}
		if(args.length == 4){
			password = args[3];
		}
		return true;
	}
	
	public static String readLine(){
		String toReturn = "";
		try {
			toReturn = console.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return toReturn;
	}
	
	public synchronized static void printlnNWW(String str){
		stashLine();
		try {
			console.println(str);
		} catch (IOException ex) {
			Logger.getLogger(TerminalIRC.class.getName()).log(Level.SEVERE, null, ex);
		}
		unstashLine();
	}

	public synchronized static void println() {
		try {
			console.println();
		} catch (IOException ex) {
			Logger.getLogger(TerminalIRC.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	
	public synchronized static void printlnWithoutStashingNWW(String str) {
		try {
			console.println(str);
		} catch (IOException ex) {
			Logger.getLogger(TerminalIRC.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	
	private static void stashLine() {
		stashed = console.getCursorBuffer().copy();
		 try {
		    console.getOutput().write("\u001b[1G\u001b[K");
		    console.flush();
		} catch (IOException e) {
			 // ignore
		}
	}
	
	private static void unstashLine() {
		try {
		    console.resetPromptLine(console.getPrompt(),
		    stashed.toString(), stashed.cursor);
		} catch (IOException e) {
			// ignore
		}
	}
	
	public synchronized static void println(String str){
		int width = console.getTerminal().getWidth();
		if(str.length()<=width){
			printlnNWW(str);
			return;
		}
		
		int i = width-1;
		
		for(;i>=0;i--){
			if(str.charAt(i)==' '){
				String printWhat = str.substring(0,i);
				printlnNWW(printWhat);
				break;
			}
		}
		println(str.substring(i+1));
	}
	
	public synchronized static void printlnWithoutStashing(String str){
		int width = console.getTerminal().getWidth();
		if(str.length()<=width){
			printlnWithoutStashingNWW(str);
			return;
		}
		
		int i = width-1;
		
		for(;i>=0;i--){
			if(str.charAt(i)==' '){
				String printWhat = str.substring(0,i);
				printlnWithoutStashingNWW(printWhat);
				break;
			}
		}
		printlnWithoutStashing(str.substring(i+1));
	}

	public static void updatePrompt() {
		console.setPrompt(channel + " <" + nick + "> ");
	}
	
	public static String timeStamp(){
		ZonedDateTime date = ZonedDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss a");
		return date.format(formatter);
	}
}
