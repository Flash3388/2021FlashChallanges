package stinger.commands;

import stinger.StingerEnvironment;

public class Executable {

    private final Command mCommand;
    private final Parameters mParameters;

    public Executable(Command command, Parameters parameters) {
        mCommand = command;
        mParameters = parameters;
    }

    public void execute(StingerEnvironment environment) throws CommandException {
        mCommand.execute(environment, mParameters);
    }

    @Override
    public String toString() {
        return String.format("Executable{commandType=%s}", mCommand.getClass().getName());
    }
}
