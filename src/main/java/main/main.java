package main;

import commands.*;
import listeners.GameUpdateListener;
import listeners.ReadyListener;
import listeners.messageListener;
import listeners.messageUpdateListener;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.exceptions.RateLimitedException;
import util.settings;
import util.STATIC;

import javax.security.auth.login.LoginException;
import java.nio.file.Paths;
import java.util.Calendar;

public class main {

    static JDABuilder builder;
    static JDA jda;
    public static STATIC statics;

    public static void main(String[] args) {

        builder = new JDABuilder(AccountType.BOT);
        statics = new STATIC();

        settings.loadSettings(false);
        if (statics.getToken() == "") {
            firstStart fs = new firstStart();
            fs.start();
            try {fs.join();} catch (Exception e) {System.exit(0);}
        }

        builder.setToken(statics.getToken());
        builder.setGame(Game.of("Beta"));
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

        builder.addListener(new ReadyListener());
        builder.addListener(new GameUpdateListener());
        builder.addListener(new messageListener());
        builder.addListener(new messageUpdateListener());

        System.out.println("[" + String.format("%02d:%02d:%02d", cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), cal.get(Calendar.SECOND)) + "] [BOT] Listener hinzugefügt");

    }

}
