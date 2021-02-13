package stinger.commands;

import stinger.Module;

public interface CommandExecutor extends Module {

    void addCommand(Command command);
}
