package util;

import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

public class timeThread extends Thread {

    int time;
    GuildMessageReceivedEvent event;

    public timeThread(int time, GuildMessageReceivedEvent event) {
        this.time = time;
        this.event = event;
    }

    public void run() {

         try {Thread.sleep(this.time);} catch (Exception e) {}
         this.event.getMessage().delete().queue();

    }

}
