package stinger.comm;

import stinger.StingerEnvironment;
import stinger.commands.Executable;
import stinger.storage.StorageException;
import stinger.storage.StoredProduct;

import java.io.IOException;
import java.net.SocketAddress;
import java.util.Iterator;
import java.util.List;

public class StandardCommunicator implements Communicator {

    private final Connector<StreamConnection> mConnector;

    public StandardCommunicator(Connector<StreamConnection> connector) {
        mConnector = connector;
    }

    public StandardCommunicator(SocketAddress endPoint) {
        this(new TcpClientConnector(endPoint, 100));
    }

    @Override
    public TransactionResult doTransaction(StingerEnvironment environment) throws CommunicationException {
        try (Transaction transaction = openTransaction()) {
            List<Executable> commands = transaction.readCommands();

            Iterator<StoredProduct> productIterator = environment.getStorage().storedProducts();
            while (productIterator.hasNext()) {
                StoredProduct product = productIterator.next();
                transaction.sendProduct(product);
                productIterator.remove();
            }

            return new TransactionResult(commands);
        } catch (StorageException | IOException e) {
            throw new CommunicationException(e);
        }
    }

    private Transaction openTransaction() throws IOException {
        StreamConnection connection = mConnector.connect(100);
        try {
            return new Transaction(connection);
        } catch (IOException e) {
            connection.close();
            throw e;
        }
    }
}
