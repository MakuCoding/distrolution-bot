package main;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;

public class permissions {

    public static boolean checkMusicPermission(GuildMessageReceivedEvent event) {

        for (Role r:event.getGuild().getMember(event.getAuthor()).getRoles()) {
            if (main.statics.getAllowedRolesMusic().contains(r.getName()))
                return true;
        }
        event.getChannel().sendMessage(new EmbedBuilder()
                .setColor(Color.RED)
                .setDescription(":warning: Du hast nicht die Berechtigung dazu " + event.getMember().getAsMention() + ":exclamation:")
                .build()).queue();
        event.getMessage().delete().queue();
        return false;
    }

    public static boolean checkSupportPermission(GuildMessageReceivedEvent event) {

        for (Role r:event.getGuild().getMember(event.getAuthor()).getRoles()) {
            if (main.statics.getAllowedRolesSupport().contains(r.getName()))
                return true;
        }
        event.getChannel().sendMessage(new EmbedBuilder()
                .setColor(Color.RED)
                .setDescription(":warning: Du hast nicht die Berechtigung dazu " + event.getMember().getAsMention() + ":exclamation:")
                .build()).queue();
        event.getMessage().delete().queue();
        return false;
    }

}
