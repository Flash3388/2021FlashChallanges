package stinger.server.comm;

import stinger.server.Environment;
import stingerlib.net.Connector;
import stingerlib.net.StreamConnection;
import stingerlib.net.TcpServerConnector;

import java.io.Closeable;
import java.io.IOException;
import java.net.SocketAddress;

public class Communicator implements Closeable {

    private final Connector<StreamConnection> mConnector;

    public Communicator(Connector<StreamConnection> connector) {
        mConnector = connector;
    }

    public Communicator(SocketAddress bindAddress) throws IOException {
        this(new TcpServerConnector(bindAddress));
    }

    public void handleNextClient(Environment environment) throws IOException {
        StreamConnection connection = mConnector.connect(1000);
        doTransaction(environment, connection);
    }

    @Override
    public void close() throws IOException {
        mConnector.close();
    }

    private void doTransaction(Environment environment, StreamConnection connection) throws IOException {
        try (ServerChannel channel = openChannel(environment, connection)) {
            while (channel.handleNextRequest());
        }
    }

    private ServerChannel openChannel(Environment environment, StreamConnection connection) throws IOException {
        return new ServerChannel(connection,
                environment.getCommandQueue()::getAllAndClear,
                environment.getStorage()::save,
                environment.getCommandTypes(),
                environment.getProductTypes());
    }
}
