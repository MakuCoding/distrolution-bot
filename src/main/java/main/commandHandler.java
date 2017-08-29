package main;
import commands.Command;

import java.util.Calendar;
import java.util.HashMap;

public class commandHandler {

    public static final commandParser parser = new commandParser();
    public static HashMap<String, Command> commands = new HashMap<>();

    public static void handleCommand(commandParser.commandContainer cmd) {

        if (commands.containsKey(cmd.invoke)) {
            Calendar cal = Calendar.getInstance();
            System.out.println("[" + String.format("%02d:%02d:%02d", cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), cal.get(Calendar.SECOND)) + "] [COMMANDS] Command \"" + cmd.invoke + "\" wurde erkannt!");
            boolean safe = commands.get(cmd.invoke).called(cmd.args, cmd.event);

            if (!safe) {
                commands.get(cmd.invoke).action(cmd.args, cmd.event);
                commands.get(cmd.invoke).executed(safe, cmd.event);
            } else {
                commands.get(cmd.invoke).executed(safe, cmd.event);
            }

        }

    }

}