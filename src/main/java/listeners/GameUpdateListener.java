package listeners;

import net.dv8tion.jda.core.events.user.UserGameUpdateEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.util.Calendar;

public class GameUpdateListener extends ListenerAdapter{


    public void onUserGameUpdate(UserGameUpdateEvent event) {

        String name = event.getGuild().getMemberById(event.getUser().getId()).getNickname() != null ? event.getGuild().getMemberById(event.getUser().getId()).getNickname():event.getGuild().getMemberById(event.getUser().getId()).getEffectiveName();
        String pg = "";
        String ag = "";
        try {
            pg = event.getPreviousGame().getName();
        } catch (Exception e) {
            pg = "";
        }
        try {
            ag = event.getGuild().getMemberById(event.getUser().getId()).getGame().getName();
        } catch (Exception e) {
            ag = "";
        }

        if (pg == "") {
            event.getGuild().getTextChannelsByName("lobby", true).get(0).sendMessage(name + " hat angefangen " + ag + " zu spielen!").queue();
        }
        if (ag == "") {
            event.getGuild().getTextChannelsByName("lobby",true).get(0).sendMessage(name + " hat aufgehört " + pg + " zu spielen!").queue();
        }
        if (ag != "") {
            if (pg != "") {
                event.getGuild().getTextChannelsByName("lobby", true).get(0).sendMessage(name + " spielt jetzt das Spiel " + ag + " anstatt dem Spiel " + pg + "!").queue();
            }

        }

        Calendar cal = Calendar.getInstance();
        System.out.println("[" + String.format("%02d:%02d:%02d", cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), cal.get(Calendar.SECOND)) + "] [LISTENER] Spiel Update von " + name + " erkannt");

    }
}
