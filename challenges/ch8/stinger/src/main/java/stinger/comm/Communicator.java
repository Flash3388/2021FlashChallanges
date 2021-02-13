package stinger.comm;

import stinger.StingerEnvironment;

public interface Communicator {

    TransactionResult doTransaction(StingerEnvironment environment) throws CommunicationException;
}
