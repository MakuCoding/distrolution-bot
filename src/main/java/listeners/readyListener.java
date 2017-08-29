package listeners;

import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.util.Calendar;

public class readyListener extends ListenerAdapter {


    public void onReady(ReadyEvent event) {

        util.settings.loadSettings();
        Calendar cal = Calendar.getInstance();
        System.out.println("[" + String.format("%02d:%02d:%02d", cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), cal.get(Calendar.SECOND)) + "] [BOT] Bot ist bereit");

    }
}
