package stinger.comm;

import stinger.commands.Command;
import stinger.commands.CommandType;
import stinger.commands.Executable;
import stinger.commands.Parameters;
import stinger.storage.Product;
import stinger.storage.StoredProduct;
import stinger.util.IoStreams;

import java.io.Closeable;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Transaction implements Closeable {

    private static final int HEADER_COMMANDS = 1;
    private static final int HEADER_PRODUCT = 5;
    private static final int HEADER_COMPLETE = 10;

    private final StreamConnection mConnection;
    private final DataOutput mOutput;
    private final DataInput mInput;

    public Transaction(StreamConnection connection) throws IOException {
        mConnection = connection;
        mOutput = new DataOutputStream(mConnection.outputStream());
        mInput = new DataInputStream(mConnection.inputStream());
    }

    public void sendProduct(StoredProduct product) throws IOException {
        byte[] data;
        try (InputStream inputStream = product.open()) {
            data = IoStreams.readAll(inputStream);
        }

        mOutput.writeInt(HEADER_PRODUCT);
        mOutput.writeUTF(product.getId());
        mOutput.writeInt(product.getType().intValue());
        mOutput.writeInt(data.length);
        mOutput.write(data);
    }

    public List<Executable> readCommands() throws IOException {
        mOutput.writeInt(HEADER_COMMANDS);
        int commandCount = mInput.readInt();
        List<Executable> commands = new ArrayList<>();
        for (int i = 0; i < commandCount; i++) {
            commands.add(readCommand());
        }

        return commands;
    }

    private Executable readCommand() throws IOException {
        int commandTypeInt = mInput.readInt();
        CommandType commandType = CommandType.fromInt(commandTypeInt);
        Command command = commandType.createCommand();

        int paramCount = mInput.readInt();

        Map<String, Object> params = new HashMap<>();
        for (int i = 0; i < paramCount; i++) {
            String key = mInput.readUTF();
            Object data = readTypedData();
            params.put(key, data);
        }

        return new Executable(command, new Parameters(params));
    }

    private Object readTypedData() throws IOException {
        int type = mInput.readInt();
        switch (type) {
            case 0:
                return mInput.readUTF();
            case 1:
                return mInput.readInt();
            case 2:
                return mInput.readDouble();
            default:
                throw new IOException("unknown type: " + type);
        }
    }

    @Override
    public void close() throws IOException {
        mOutput.writeInt(HEADER_COMPLETE);
        mConnection.close();
    }
}
