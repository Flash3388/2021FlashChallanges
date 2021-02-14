package stingerlib.commands;

public class ParamNotFoundException extends CommandException {

    public ParamNotFoundException(String key) {
        super(key);
    }
}
