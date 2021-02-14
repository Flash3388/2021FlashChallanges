package stinger;

import stingerlib.logging.Logger;

import java.util.Set;

public class Stinger {

    private final Set<Module> mModules;
    private final StingerEnvironment mEnvironment;

    public Stinger(Set<Module> modules, StingerEnvironment environment) {
        mModules = modules;
        mEnvironment = environment;
    }

    public void start() {
        Logger logger = mEnvironment.getLogger();
        logger.info("Stinger start");

        for (Module module : mModules) {
            logger.info("Starting module %s", module.getClass().getName());
            module.start(mEnvironment);
        }

        OnStart.onStart(mEnvironment);

        while (!mEnvironment.getControl().isInShutdown()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    public void stop() {
        Logger logger = mEnvironment.getLogger();

        for (Module module : mModules) {
            logger.info("Stopping module %s", module.getClass().getName());
            module.stop();
        }

        logger.info("Stinger stop");
    }
}
