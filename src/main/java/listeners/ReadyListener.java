package listeners;

import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import static main.main.statics;

public class ReadyListener extends ListenerAdapter {

    public void onReady(ReadyEvent event) {
        if (!statics.getOnceStarted()) {
            event.getJDA().getUserById(statics.getOwner()).openPrivateChannel().complete().sendMessage("Hallo Owner. Ich bin nun dein Bot. Einem Server kannst du mich hinzufügen indem du folgende Website: https://discordapp.com/oauth2/authorize?client_id=" + event.getJDA().getSelfUser().getId() + "&scope=bot aufrust, dich ggf. anmeldest und den Server auswählst.").queue();
            statics.setOnceStarted(true);
            util.settings.setSettings();
        }
    }

}
