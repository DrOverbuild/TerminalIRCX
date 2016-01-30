package terminalirc.command;

import terminalirc.Client;

/**
 * Created by jasper on 1/30/16.
 */
public class SWITCH implements Command {

	Client c;

	public SWITCH(Client c) {
		this.c = c;
	}

	@Override
	public boolean execute(String[] args) {
		if(args.length == 1){
			c.parseCommand("/channel").execute(new String[]{"switch",args[0]});
			return true;
		}
		return false;
	}

	@Override
	public String getName() {
		return "switch";
	}

	@Override
	public String getShortcut() {
		return "s";
	}

	@Override
	public String getUsage() {
		return "/switch <channe>";
	}

	@Override
	public String getDesc() {
		return "Switches current channel.";
	}
}
