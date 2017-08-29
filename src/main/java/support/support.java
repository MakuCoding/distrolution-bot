package support;

import main.main;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Role;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class support {

    public static final List<supportCase> list = new ArrayList<>();

    public static void notifySupporter(supportCase sc) {

        for (Member m:sc.getGuild().getMembers()) {
            if (m.getOnlineStatus() == OnlineStatus.ONLINE && !m.getUser().isBot()) {
                for (Role r : m.getRoles()) {
                    if (main.statics.getAllowedRolesSupport().contains(r.getName()) && !sc.needsHelp()) {
                        if (sc.getReason() == "") {
                            m.getUser().openPrivateChannel().complete().sendMessage(new EmbedBuilder()
                                    .setColor(Color.YELLOW)
                                    .setDescription("Hey " + m.getUser().getName() + "! " + sc.getUser().getAsMention() + " braucht Hilfe!")
                                    .build()).queue();
                            break;
                        } else {
                            m.getUser().openPrivateChannel().complete().sendMessage(new EmbedBuilder()
                                    .setColor(Color.YELLOW)
                                    .setDescription("Hey " + m.getUser().getName() + "! " + sc.getUser().getAsMention() + " braucht Hilfe!\nGrund: " + sc.getReason())
                                    .build()).queue();
                        }
                    } else if (main.statics.getAllowedRolesSupport().contains(r.getName()) && sc.needsHelp()) {
                        m.getUser().openPrivateChannel().complete().sendMessage(new EmbedBuilder()
                                .setColor(Color.YELLOW)
                                .setDescription("Hey " + m.getUser().getName() + "! " + sc.getSupporter().get(0).getAsMention() + " braucht Unterstützung bei einem Supportfall von " + sc.getUser().getAsMention() + "!")
                                .build()).queue();
                    }
                }
            }
        }

    }

}
