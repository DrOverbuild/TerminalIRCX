package terminalirc.command;

import terminalirc.Client;

/**
 * Created by jasper on 1/30/16.
 */
public class JOIN implements Command {
	Client c;

	public JOIN(Client c) {
		this.c = c;
	}

	@Override
	public boolean execute(String[] args) {
		if(args.length == 1) {
			c.parseCommand("/channel").execute(new String[]{"join",args[0]});
			return true;
		}else{
			return false;
		}
	}

	@Override
	public String getName() {
		return "join";
	}

	@Override
	public String getShortcut() {
		return "j";
	}

	@Override
	public String getUsage() {
		return "/join <channel>";
	}

	@Override
	public String getDesc() {
		return "Joins a channel.";
	}
}
