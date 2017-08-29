package commands;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.OffsetDateTime;
import java.util.*;
import java.util.List;

public class cmdClear implements Command {
    @Override
    public boolean called(String[] args, GuildMessageReceivedEvent event) {
        return false;
    }

    @Override
    public void action(String[] args, GuildMessageReceivedEvent event) {
        Calendar cal = Calendar.getInstance();
        MessageEmbed builded = new EmbedBuilder().setColor(Color.RED).setDescription("Du musst eine Zahl zwischen 1 und 100 oder den Namen eines Mitgliedes dieses Servers!").build();
        boolean bol = false;
        if (args.length == 1) {

            MessageHistory history = new MessageHistory(event.getChannel());
            List<Message> msgs = new ArrayList<>();
            Integer i;
            try {i = Integer.parseInt(args[0]);} catch (Exception e) {i = 0;}
            if (i != 0) {
                if (i > 1) {
                    event.getMessage().delete().queue();
                    try {
                        msgs = history.retrievePast(i + 1).complete();
                        event.getChannel().deleteMessages(msgs).queue();
                        bol = true;
                    } catch (Exception e) {
                        System.err.println("[" + String.format("%02d:%02d:%02d", cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), cal.get(Calendar.SECOND)) + "] [COMMAND] Fehler: " + e +
                                "\n[" + String.format("%02d:%02d:%02d", cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), cal.get(Calendar.SECOND)) + "] [COMMAND] Details: " + e.getMessage());
                        bol = false;
                    }
                } else {
                    bol = false;
                }
                if (i == 1) {
                    try {
                        msgs = history.retrievePast(2).complete();
                        event.getChannel().deleteMessages(msgs).queue();
                        bol = true;
                    } catch (Exception e) {
                        System.err.println("[" + String.format("%02d:%02d:%02d", cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), cal.get(Calendar.SECOND)) + "] [COMMAND] Fehler: " + e +
                                "\n[" + String.format("%02d:%02d:%02d", cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), cal.get(Calendar.SECOND)) + "] [COMMAND] Details: " + e.getMessage());
                        bol = false;
                    }
                }
                if (bol) {
                    if (i == 1) {
                        event.getChannel().sendMessage("Eine Nachricht gelöscht!").queue();
                        System.out.println("[" + String.format("%02d:%02d:%02d", cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), cal.get(Calendar.SECOND)) + "] [COMMANDS] Command \"clear\" erfolgreich ausgeführt!");
                    } else {
                        event.getChannel().sendMessage(i + " Nachrichten gelöscht!").queue();
                        System.out.println("[" + String.format("%02d:%02d:%02d", cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), cal.get(Calendar.SECOND)) + "] [COMMANDS] Command \"clear\" erfolgreich ausgeführt!");
                    }

                } else {
                    event.getChannel().sendMessage(builded).queue();
                    event.getMessage().delete().queue();
                    System.err.println("[" + String.format("%02d:%02d:%02d", cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), cal.get(Calendar.SECOND)) + "] [COMMANDS] Fehler in Command \"clear\"");
                }
            } else {
                User usr = null;
                try {usr = event.getGuild().getMembersByEffectiveName(args[0],true).get(0).getUser();} catch (Exception e) {usr = null;}
                if (usr == null) {
                    try {usr = event.getGuild().getMembersByName(args[0], true).get(0).getUser();} catch (Exception e) {usr = null;}
                } if (usr == null) {
                    try {usr = event.getGuild().getMembersByNickname(args[0], true).get(0).getUser();} catch (Exception e) {usr = null;}
                } if (usr == null) {
                    usr = event.getMessage().getMentionedUsers().get(0);
                } if (usr != null) {
                    msgs = history.retrievePast(100).complete();
                    int anzahl = 0;
                    for (Message msg:msgs) {
                        if (msg.getAuthor() == usr) {
                            try {msg.delete().queue();anzahl++;} catch (Exception e) {break;}
                        }
                    }
                    event.getMessage().delete().queue();
                    event.getChannel().sendMessage(anzahl + " Nachrichten von " + usr.getAsMention() + " gelöscht!").queue();
                } else if (usr == null) {
                    event.getChannel().sendMessage(builded).queue();
                    event.getMessage().delete().queue();
                    System.err.println("[" + String.format("%02d:%02d:%02d", cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), cal.get(Calendar.SECOND)) + "] [COMMANDS] Fehler in Command \"clear\"");
                }
            }
        } else {
            event.getChannel().sendMessage(builded).queue();
            event.getMessage().delete().queue();
            System.err.println("[" + String.format("%02d:%02d:%02d", cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), cal.get(Calendar.SECOND)) + "] [COMMANDS] Fehler in Command \"clear\"");
        }
    }

    @Override
    public void executed(boolean success, GuildMessageReceivedEvent event) {

    }

    @Override
    public String help() {
        return null;
    }
}
