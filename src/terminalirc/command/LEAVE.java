package terminalirc.command;

import terminalirc.Client;

/**
 * Created by jasper on 1/30/16.
 */
public class LEAVE implements Command {

	Client c;

	public LEAVE(Client c) {
		this.c = c;
	}

	@Override
	public boolean execute(String[] args) {
		if(args.length == 1){
			c.parseCommand("/channel").execute(new String[]{"leave",args[0]});
		}else if(args.length == 0){
			c.parseCommand("/channel").execute(new String[]{"leave"});
		}

		return true;
	}

	@Override
	public String getName() {
		return "leave";
	}

	@Override
	public String getShortcut() {
		return "leave";
	}

	@Override
	public String getUsage() {
		return "/leave <channel> or /leave";
	}

	@Override
	public String getDesc() {
		return "Leaves a channel.";
	}
}
