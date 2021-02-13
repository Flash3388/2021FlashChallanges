package stinger.commands;

import stinger.commands.impl.GetFileCommand;
import stinger.commands.impl.PutFileCommand;

public enum CommandType {
    PUT_FILE(1) {
        @Override
        public Command createCommand() {
            return new PutFileCommand();
        }
    },
    GET_FILE(2) {
        @Override
        public Command createCommand() {
            return new GetFileCommand();
        }
    }
    ;

    private final int mIntValue;

    CommandType(int intValue) {
        mIntValue = intValue;
    }

    public int intValue() {
        return mIntValue;
    }

    public abstract Command createCommand();

    public static CommandType fromInt(int value) {
        for (CommandType type : values()) {
            if (type.intValue() == value) {
                return type;
            }
        }

        throw new EnumConstantNotPresentException(CommandType.class,
                String.valueOf(value));
    }
}
