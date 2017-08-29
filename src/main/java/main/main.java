package main;

import commands.*;
import listeners.GameUpdateListener;
import listeners.messageListener;
import listeners.messageUpdateListener;
import listeners.readyListener;
import net.dv8tion.jda.core.*;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.exceptions.RateLimitedException;
import support.supportCase;
import util.SECRETS;
import util.STATIC;

import javax.security.auth.login.LoginException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class main {

    static JDABuilder builder;
    static JDA jda;
    public static STATIC statics;

    public static void main(String[] args) {

        builder = new JDABuilder(AccountType.BOT);
        statics = new STATIC();

        builder.setToken(SECRETS.TOKEN);

        builder.setGame(new Game() {
            @Override
            public String getName() {
                return "mit Titten";
            }

            @Override
            public String getUrl() {
                return null;
            }

            @Override
            public GameType getType() {
                return GameType.DEFAULT;
            }
        });
        builder.setAutoReconnect(true);
        builder.setStatus(OnlineStatus.ONLINE);

        addListener();
        addCommands();

        try {
            jda = builder.buildBlocking();
        } catch (LoginException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (RateLimitedException e) {
            e.printStackTrace();
        }

        TwitchStreamObserverMain.main();
        Calendar cal = Calendar.getInstance();
        System.out.println("[" + String.format("%02d:%02d:%02d", cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), cal.get(Calendar.SECOND)) + "] [BOT] Bot erfolgreich gestartet");

    }

    public static void addCommands() {

        Calendar cal = Calendar.getInstance();

        commandHandler.commands.put("clear", new cmdClear());
        commandHandler.commands.put("music", new Music());
        commandHandler.commands.put("time", new cmdTime());
        commandHandler.commands.put("reload", new cmdReload());
        commandHandler.commands.put("support", new cmdSupport());

        System.out.println("[" + String.format("%02d:%02d:%02d", cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), cal.get(Calendar.SECOND)) + "] [BOT] Commands registriert");

    }

    static void addListener() {

        Calendar cal = Calendar.getInstance();

        builder.addListener(new readyListener());
        builder.addListener(new GameUpdateListener());
        builder.addListener(new messageListener());
        builder.addListener(new messageUpdateListener());

        System.out.println("[" + String.format("%02d:%02d:%02d", cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), cal.get(Calendar.SECOND)) + "] [BOT] Listener hinzugefügt");

    }

}
