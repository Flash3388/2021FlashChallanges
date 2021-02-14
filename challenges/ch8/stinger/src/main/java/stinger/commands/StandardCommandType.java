package stinger.commands;

import stinger.commands.impl.GetFileCommand;
import stinger.commands.impl.PutFileCommand;
import stinger.commands.impl.StopCommand;
import stingerlib.commands.CommandType;

public enum StandardCommandType implements CommandType {
    STOP(0) {
        @Override
        public Command createCommand() {
            return new StopCommand();
        }
    },
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

    StandardCommandType(int intValue) {
        mIntValue = intValue;
    }

    @Override
    public int intValue() {
        return mIntValue;
    }

    public abstract Command createCommand();

    public static StandardCommandType fromInt(int value) {
        for (StandardCommandType type : values()) {
            if (type.intValue() == value) {
                return type;
            }
        }

        throw new EnumConstantNotPresentException(StandardCommandType.class,
                String.valueOf(value));
    }
}
