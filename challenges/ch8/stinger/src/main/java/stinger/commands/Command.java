package stinger.commands;

import stinger.StingerEnvironment;

public interface Command {

    void execute(StingerEnvironment environment, Parameters parameters) throws CommandException;
}
