package stinger;

import stinger.commands.Executable;
import stinger.commands.impl.GetFileCommand;
import stinger.commands.impl.PutFileCommand;
import stingerlib.commands.Parameters;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class OnStart {

    public static void onStart(StingerEnvironment environment) {
        environment.getCommandQueue().addCommand(new Executable(
                new PutFileCommand(),
                new Parameters.Builder()
                    .putString("path", "/tmp/testfile")
                    .putString("data", Base64.getEncoder().encodeToString(
                            "helloasdasdasda".getBytes(StandardCharsets.UTF_8)
                    ))
                    .build()
        ));
        environment.getCommandQueue().addCommand(new Executable(
                new GetFileCommand(),
                new Parameters.Builder()
                        .putString("path", "/tmp/testfile")
                        .build()
        ));
    }
}
