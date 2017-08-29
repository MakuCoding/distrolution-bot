package listeners;

import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class messageUpdateListener extends ListenerAdapter {

    @Override
    public void onMessageReactionAdd(MessageReactionAddEvent event) {
        Message msg = event.getChannel().getMessageById(event.getMessageId()).complete();
            try {msg.addReaction("\uD83D\uDC4D").queue();} catch (Exception x) {x.printStackTrace();}
    }

}

