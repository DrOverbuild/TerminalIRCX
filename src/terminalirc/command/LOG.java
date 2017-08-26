package terminalirc.command;

import terminalirc.Client;

public class LOG implements Command {
    Client c;

    public LOG(Client c) {
        this.c = c;
    }

    @Override
    public boolean execute(String[] args) {
        return false;
    }

    @Override
    public String getName() {
        return "log";
    }

    @Override
    public String getShortcut() {
        return "log";
    }

    @Override
    public String getUsage() {
        return "/log <channel> [ON|OFF] <file path>";
    }

    @Override
    public String getDesc() {
        return "Logs channel messages to individual text files.";
    }
}
