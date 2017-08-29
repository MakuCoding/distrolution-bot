package commands;

import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

public class cmdReload implements Command {
    @Override
    public boolean called(String[] args, GuildMessageReceivedEvent event) {
        return false;
    }

    @Override
    public void action(String[] args, GuildMessageReceivedEvent event) {

        if (args.length == 0) {
            util.settings.loadSettings();
            event.getChannel().sendMessage("Reload erfolgreich!").queue();
            event.getMessage().delete().queue();
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
