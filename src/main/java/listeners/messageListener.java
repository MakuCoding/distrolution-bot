package listeners;

import main.main;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import main.commandHandler;
import support.supportAccept;
import util.timeThread;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class messageListener extends ListenerAdapter {

    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String msg = event.getMessage().getContent();
        if (event.getMessage().getEmbeds().size() == 0) {
            String prefix = event.getMessage().getContent().substring(0, main.statics.getPrefix().length());
            if (prefix.equalsIgnoreCase(main.statics.getPrefix()) && event.getMessage().getAuthor().getId() != event.getJDA().getSelfUser().getId()) {
                commandHandler.handleCommand(commandHandler.parser.parse(event.getMessage().getContent().toLowerCase(), event));
            }
        }

        if (event.getAuthor().getId() == event.getJDA().getSelfUser().getId()) {

            boolean bol = false;

            if (msg.toLowerCase().contains(" Nachricht gelöscht!".toLowerCase()) || msg.toLowerCase().contains(" Nachrichten gelöscht!".toLowerCase()) || (msg.toLowerCase().contains(" Nachrichten von ".toLowerCase()) && msg.toLowerCase().contains(" gelöscht!".toLowerCase()))) {
                timeThread time = new timeThread(5000, event);
                time.start();
            }
            if (msg.toLowerCase().contains(" zu spielen!".toLowerCase()) || msg.toLowerCase().contains("anstatt dem Spiel ".toLowerCase())) {
                timeThread time = new timeThread(30000, event);
                time.run();
            }
            if (msg.toLowerCase().contains("Titel wurde zur Wiedergabeliste hinzugefügt".toLowerCase()) || msg.toLowerCase().contains("Titel aus der Wiedergabeliste entfernt".toLowerCase())) {
                timeThread time = new timeThread(10000, event);
                time.run();
            }
            if (event.getMessage().getEmbeds().size() > 0) {
                bol = false;
                try {
                    bol = event.getMessage().getEmbeds().get(0).getDescription().contains("Du musst eine Zahl zwischen 1 und 100 eingeben!");
                } catch (NullPointerException e) {
                    bol = false;
                }
                if (bol) {
                    timeThread time = new timeThread(10000, event);
                    time.run();
                }
                try {
                    bol = event.getMessage().getEmbeds().get(0).getDescription().contains("Du hast nicht die Berechtigung dazu ");
                } catch (NullPointerException e) {
                    bol = false;
                }
                if (bol) {
                    timeThread time = new timeThread(10000, event);
                    time.run();
                }
                bol = false;
                try {
                    bol = event.getMessage().getEmbeds().get(0).getDescription().contains("Dieser Titel ist nicht in der Wiedergabeliste!");
                } catch (NullPointerException e) {
                    bol = false;
                }
                if (bol) {
                    timeThread time = new timeThread(10000, event);
                    time.run();
                }
                bol = false;
                try {
                    bol = event.getMessage().getEmbeds().get(0).getDescription().contains("Kein Track mit dieser Bezeichnung gefunden!");
                } catch (NullPointerException e) {
                    bol = false;
                }
                if (bol) {
                    timeThread time = new timeThread(10000, event);
                    time.run();
                }
            }
        }

        for (String s:main.statics.getBlacklist()) {
            if (event.getMessage().getContent().toLowerCase().contains(s.toLowerCase())) {
                event.getMessage().delete().queue();
                event.getChannel().sendMessage(":warning: Keine Kraftausdrücke " + event.getAuthor().getAsMention() + "! :angry:").queue();
            }
        }

        if (!(event.getAuthor() == event.getJDA().getSelfUser())) {
            for (User u:event.getMessage().getMentionedUsers()) {
                Member m = event.getGuild().getMember(u);
                if (m.getOnlineStatus() == OnlineStatus.DO_NOT_DISTURB) {
                    event.getChannel().sendMessage(":warning: " + m.getAsMention() + " will momentan nicht gestört werden!").queue();
                } else if (m.getOnlineStatus() == OnlineStatus.OFFLINE) {
                    event.getChannel().sendMessage(":warning:" + m.getAsMention() + " ist offline!").queue();
                }
            }
        }

    }

    public static Map<User, supportAccept> supporter = new HashMap<>();

    public void onPrivateMessageReceived(PrivateMessageReceivedEvent event) {

        if (supporter.containsKey(event.getAuthor()) && event.getMessage().getContent().equalsIgnoreCase("ja")) {
            supporter.get(event.getAuthor()).setSuccess(true);
        } else if (supporter.containsKey(event.getAuthor()) && event.getMessage().getContent().equalsIgnoreCase("nein")) {
            supporter.get(event.getAuthor()).setSuccess(false);
        }

    }

}