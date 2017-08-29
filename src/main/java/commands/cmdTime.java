package commands;

import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.util.Calendar;
import java.util.Formatter;

public class cmdTime implements Command{


    @Override
    public boolean called(String[] args, GuildMessageReceivedEvent event) {
        return false;
    }

    @Override
    public void action(String[] args, GuildMessageReceivedEvent event) {
        StringBuilder dateBuilder = new StringBuilder();
        StringBuilder timeBuilder = new StringBuilder();
        Formatter dateForm = new Formatter(dateBuilder);
        Formatter timeForm = new Formatter(timeBuilder);
        Calendar cal = Calendar.getInstance();
        dateForm.format("%02d.%02d.%d", cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.YEAR));
        timeForm.format("%02d:%02d:%02d", cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), cal.get(Calendar.SECOND));

        event.getChannel().sendMessage("Datum: " + dateBuilder.toString() +
        "\nUhrzeit: " + timeBuilder.toString()).queue();

        event.getMessage().delete().queue();
    }

    @Override
    public void executed(boolean success, GuildMessageReceivedEvent event) {
        Calendar cal = Calendar.getInstance();
        if (!success) System.out.println("[" + String.format("%02d:%02d:%02d", cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), cal.get(Calendar.SECOND)) + "] [COMMANDS] Command \"time\" erfolgreich ausgeführt!");
        else System.err.println("[" + String.format("%02d:%02d:%02d", cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), cal.get(Calendar.SECOND)) + "] [COMMANDS] Fehler in Command \"time\"");
    }

    @Override
    public String help() {
        return null;
    }
}
