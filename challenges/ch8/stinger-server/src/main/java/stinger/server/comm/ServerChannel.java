package stinger.server.comm;

import stinger.server.commands.GenericCommandType;
import stinger.server.storage.GenericProductType;
import stinger.server.util.KnownTypes;
import stingerlib.commands.CommandDefinition;
import stingerlib.commands.CommandSerializer;
import stingerlib.commands.ParametersSerializer;
import stingerlib.logging.Logger;
import stingerlib.net.MessageType;
import stingerlib.net.StreamConnection;
import stingerlib.storage.ProductSerializer;
import stingerlib.storage.StoredProduct;

import java.io.Closeable;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class ServerChannel implements Closeable {

    private final StreamConnection mConnection;
    private final Supplier<List<? extends CommandDefinition>> mCommandsSupplier;
    private final Consumer<StoredProduct> mProductConsumer;
    private final Logger mLogger;

    private final DataOutput mOutput;
    private final DataInput mInput;

    private final CommandSerializer mCommandSerializer;
    private final ProductSerializer mProductSerializer;

    public ServerChannel(StreamConnection connection,
                         Supplier<List<? extends CommandDefinition>> commandsSupplier,
                         Consumer<StoredProduct> productConsumer,
                         KnownTypes<GenericCommandType, Integer> commandTypes,
                         KnownTypes<GenericProductType, Integer> productTypes,
                         Logger logger) throws IOException {
        mConnection = connection;
        mCommandsSupplier = commandsSupplier;
        mProductConsumer = productConsumer;
        mLogger = logger;

        mOutput = new DataOutputStream(mConnection.outputStream());
        mInput = new DataInputStream(mConnection.inputStream());

        mCommandSerializer = new CommandSerializer(commandTypes::getFromKey, new ParametersSerializer());
        mProductSerializer = new ProductSerializer(productTypes::getFromKey);
    }

    public boolean handleNextRequest() throws IOException {
        int requestInt = mInput.readInt();
        MessageType messageType = MessageType.fromInt(requestInt);

        switch (messageType) {
            case NEW_PRODUCT:
                mProductConsumer.accept(readProduct());
                break;
            case REQUEST_COMMANDS:
                List<? extends CommandDefinition> commandDefinitions = mCommandsSupplier.get();
                sendCommands(commandDefinitions);
                break;
            case DONE:
                return false;
        }

        return true;
    }

    private void sendCommands(List<? extends CommandDefinition> commandDefinitions) throws IOException {
        mOutput.writeInt(commandDefinitions.size());

        for (CommandDefinition commandDefinition : commandDefinitions) {
            mLogger.info("Sending command %s", commandDefinition);
            mCommandSerializer.serialize(mOutput, commandDefinition);
        }
    }

    private StoredProduct readProduct() throws IOException {
        StoredProduct product = mProductSerializer.deserialize(mInput);
        mLogger.info("Received product %s", product.getId());
        return product;
    }

    @Override
    public void close() throws IOException {
        mConnection.close();
    }
}
