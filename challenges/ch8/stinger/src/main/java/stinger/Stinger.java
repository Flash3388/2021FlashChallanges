package stinger;

import java.util.Set;

public class Stinger {

    private final Set<Module> mModules;
    private final StingerEnvironment mEnvironment;

    public Stinger(Set<Module> modules, StingerEnvironment environment) {
        mModules = modules;
        mEnvironment = environment;
    }

    public void start() {
        for (Module module : mModules) {
            module.start(mEnvironment);
        }
    }

    public void stop() {
        for (Module module : mModules) {
            module.stop();
        }
    }
}
