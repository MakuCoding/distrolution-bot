package commands;

import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

public interface Command {

    boolean called(String[] args, GuildMessageReceivedEvent event);
    void action(String[] args, GuildMessageReceivedEvent event);
    void executed(boolean success, GuildMessageReceivedEvent event);
    String help();

}