package commands;

import audioCore.AudioInfo;
import audioCore.PlayerSendHandler;
import audioCore.TrackManager;
import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import main.permissions;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.awt.Color;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by zekro on 18.06.2017 / 11:47
 * supremeBot.commands
 * dev.zekro.de - github.zekro.de
 * © zekro 2017
 */

public class Music implements Command {


    private static Guild guild;
    private static final AudioPlayerManager MANAGER = new DefaultAudioPlayerManager();
    private static final Map<Guild, Map.Entry<AudioPlayer, TrackManager>> PLAYERS = new HashMap<>();


    /**
     * Audio Manager als Audio-Stream-Recource deklarieren.
     */
    public Music() {
        AudioSourceManagers.registerRemoteSources(MANAGER);
    }

    /**
     * Erstellt einen Audioplayer und fügt diesen in die PLAYERS-Map ein.
     * @param g Guild
     * @return AudioPlayer
     */
    private AudioPlayer createPlayer(Guild g) {
        AudioPlayer p = MANAGER.createPlayer();
        TrackManager m = new TrackManager(p);
        p.addListener(m);

        guild.getAudioManager().setSendingHandler(new PlayerSendHandler(p));

        PLAYERS.put(g, new AbstractMap.SimpleEntry<>(p, m));

        return p;
    }

    /**
     * Returnt, ob die Guild einen Eintrag in der PLAYERS-Map hat.
     * @param g Guild
     * @return Boolean
     */
    private boolean hasPlayer(Guild g) {
        return PLAYERS.containsKey(g);
    }

    /**
     * Returnt den momentanen Player der Guild aus der PLAYERS-Map,
     * oder erstellt einen neuen Player für die Guild.
     * @param g Guild
     * @return AudioPlayer
     */
    private AudioPlayer getPlayer(Guild g) {
        if (hasPlayer(g))
            return PLAYERS.get(g).getKey();
        else
            return createPlayer(g);
    }

    /**
     * Returnt den momentanen TrackManager der Guild aus der PLAYERS-Map.
     * @param g Guild
     * @return TrackManager
     */
    private TrackManager getManager(Guild g) {
        return PLAYERS.get(g).getValue();
    }

    /**
     * Returnt, ob die Guild einen Player hat oder ob der momentane Player
     * gerade einen Track spielt.
     * @param g Guild
     * @return Boolean
     */
    private boolean isIdle(Guild g) {
        return !hasPlayer(g) || getPlayer(g).getPlayingTrack() == null;
    }

    /**
     * Läd aus der URL oder dem Search String einen Track oder eine Playlist
     * in die Queue.
     * @param identifier URL oder Search String
     * @param author Member, der den Track / die Playlist eingereiht hat
     * @param msg Message des Contents
     */
    private void loadTrack(String identifier, Member author, Message msg, String mode) {

        Guild guild = author.getGuild();
        getPlayer(guild);

        MANAGER.setFrameBufferDuration(5000);
        MANAGER.loadItemOrdered(guild, identifier, new AudioLoadResultHandler() {

            @Override
            public void trackLoaded(AudioTrack track) {
                if (mode.equalsIgnoreCase("queueadd")) getManager(guild).queueAdd(track, author);
                if (mode.equalsIgnoreCase("queueremove")) getManager(guild).queueRemove(track, author, msg.getTextChannel());
                if (mode.equalsIgnoreCase("play")) getManager(guild).play(track, author);
            }

            @Override
            public void playlistLoaded(AudioPlaylist playlist) {
                if (mode.equalsIgnoreCase("queueadd")) getManager(guild).queueAdd(playlist.getTracks().get(0), author);
                if (mode.equalsIgnoreCase("queueremove")) getManager(guild).queueRemove(playlist.getTracks().get(0), author, msg.getTextChannel());
                if (mode.equalsIgnoreCase("play")) getManager(guild).play(playlist.getTracks().get(0), author);
                if (mode.equalsIgnoreCase("listplay")) {
                    for (int i = 0; i < (playlist.getTracks().size() > 100 ? 100 : playlist.getTracks().size()); i++) {
                        getManager(guild).playList(playlist.getTracks().get(i), author);
                    }
                }
            }

            @Override
            public void noMatches() {
                System.out.println("Kein Track gefunden");
                msg.getTextChannel().sendMessage(new EmbedBuilder()
                        .setColor(Color.RED)
                .setDescription("Kein Track mit dieser Bezeichnung gefunden!")
                        .build()).queue();
                guild.getAudioManager().closeAudioConnection();
            }

            @Override
            public void loadFailed(FriendlyException exception) {
                System.out.println("Laden fehlgeschlagen!");
                exception.printStackTrace();
            }
        });

    }

    /**
     * Stoppt den momentanen Track, worauf der nächste Track gespielt wird.
     * @param g Guild
     */
    private void skip(Guild g) {
        getPlayer(g).stopTrack();
    }

    /**
     * Erzeugt aus dem Timestamp in Millisekunden ein hh:mm:ss - Zeitformat.
     * @param milis Timestamp
     * @return Zeitformat
     */
    private String getTimestamp(long milis) {
        long seconds = milis / 1000;
        long hours = Math.floorDiv(seconds, 3600);
        seconds = seconds - (hours * 3600);
        long mins = Math.floorDiv(seconds, 60);
        seconds = seconds - (mins * 60);
        return (hours == 0 ? "" : hours + ":") + String.format("%02d", mins) + ":" + String.format("%02d", seconds);
    }

    /**
     * Returnt aus der AudioInfo eines Tracks die Informationen als String.
     * @param info AudioInfo
     * @return Informationen als String
     */
    private String buildQueueMessage(AudioInfo info) {
        AudioTrackInfo trackInfo = info.getTrack().getInfo();
        String title = trackInfo.title;
        long length = trackInfo.length;
        return "`[ " + getTimestamp(length) + " ]` " + title + "\n";
    }

    @Override
    public boolean called(String[] args, GuildMessageReceivedEvent event) {
        return false;
    }

    @Override
    public void action(String[] args, GuildMessageReceivedEvent event) {

        if (permissions.checkMusicPermission(event)) {

            MessageEmbed syntax = new EmbedBuilder()
                    .setColor(Color.RED)
                    .addField("Syntax", "Bitte benutze diese Syntax", false)
                    .addField("Musik abspielen:", "-music play [Link/YouTube-Suche]\n-music p [Link/YouTube-Suche]", false)
                    .addField("Musik pausieren", "-music pause", false)
                    .addField("Musik stoppen:", "-music stop", false)
                    .addField("Musik überspringen:", "-music skip [Anzah])\n-music s [Anzahl]", false)
                    .addField("Musik shufflen:", "-music shuffle", false)
                    .addField("Lautstärke einstellen:", "-music volume (Lautstärke: 0-150)", false)
                    .addField("Info über den aktuellen Titel:", "-music info\n-music playing", false)
                    .addField("Aktuelle Wiedergabeliste anzeigen:", "-music playlist info [Seitennummer]", false)
                    .addField("Titel zur Wiedergabeliste hinzufügen/aus der Wiedergabeliste entfernen:", "-music playlist add (Link/YouTube-Suche)\n-music playlist remove (Link/YouTube-Suche", false)
                    .addField("Syntax anzeigen/Hilfe:", "-music help\n-music syntax", false)
                    .addField("Erläuterungen:", "Alles was in eckigen klammern steht ist optional\nAlles was in runden Klammern steht ist obligatorisch", false)
                    .build();

            guild = event.getGuild();
            boolean bol = true;

            if (args.length == 0) {
                event.getChannel().sendMessage(syntax).queue();
                event.getMessage().delete().queue();
                System.err.println("[COMMANDS] Fehler in Commmand \"music\"");
                return;
            }

            switch (args[0].toLowerCase()) {

                case "play":
                case "p":


                    if (!(isIdle(guild))) {
                        if (getManager(guild).isPaused()) {
                            getManager(guild).setPause(false);
                            break;
                        }
                    } else if (args.length == 1) {
                        getPlayer(guild);
                        if (!getManager(guild).getQueue().toString().equalsIgnoreCase("[]")) {
                            getManager(guild).play();
                            break;
                        } else {
                            event.getChannel().sendMessage(new EmbedBuilder().setColor(Color.RED).setDescription("Es ist kein Titel in der Wiedergabeliste").build()).queue();
                            bol = false;
                            break;
                        }
                    } else if (args.length > 1 && args[1].equalsIgnoreCase("list")) {
                        String input = Arrays.stream(args).skip(2).map(s -> " " + s).collect(Collectors.joining()).substring(1);
                        if (!(input.startsWith("http://") || input.startsWith("https://")))
                            input = "ytsearch: " + input;

                        loadTrack(input, event.getMember(), event.getMessage(), "listplay");
                        break;
                    } else {
                        String input = Arrays.stream(args).skip(1).map(s -> " " + s).collect(Collectors.joining()).substring(1);
                        input = "ytsearch: " + input;
                        loadTrack(input, event.getMember(), event.getMessage(), "play");
                        break;
                    }

                case "skip":
                case "s":

                    if (isIdle(guild)) {bol = false; break;}
                    try {
                    for (int i = (args.length > 1 ? Integer.parseInt(args[1]) : 1); i == 1; i--) {
                        skip(guild);
                    }} catch (Exception e) {
                        event.getChannel().sendMessage(syntax);
                        bol = false;
                    }

                    break;

                case "stop":

                    if (isIdle(guild)) {bol = false; break;}

                    skip(guild);
                    getManager(guild).purgeQueue();
                    guild.getAudioManager().closeAudioConnection();

                    break;


                case "shuffle":

                    if (isIdle(guild)) {bol = false; break;}
                    getManager(guild).shuffleQueue();

                    break;


                case "playing":
                case "info":

                    if (isIdle(guild)) {bol = false; break;}

                    AudioTrack track = getPlayer(guild).getPlayingTrack();
                    AudioTrackInfo info = track.getInfo();

                    event.getChannel().sendMessage(
                            new EmbedBuilder()
                                    .setDescription("**Aktuell läuft:**")
                                    .addField("Titel", info.title, false)
                                    .addField("Dauer", getTimestamp(track.getPosition()) + "/" + getTimestamp(track.getDuration()), false)
                                    .addField("Autor", info.author, false)
                                    .build()
                    ).queue();

                    break;

                case "playlist":

                    if (args.length >= 2 && args[1].equalsIgnoreCase("info")) {

                        int sideNumb = 1;
                        try {sideNumb = args.length > 2 ? Integer.parseInt(args[2]) : 1;} catch (Exception e) {}

                        List<String> tracks = new ArrayList<>();
                        List<String> trackSublist;

                        try {
                            getManager(guild).getQueue().forEach(audioInfo -> tracks.add(buildQueueMessage(audioInfo)));
                        } catch (
                            Exception e) {event.getChannel().sendMessage(syntax);
                            bol = false;
                        }
                        if (tracks.size() > 20)
                            trackSublist = tracks.subList((sideNumb - 1) * 20, (sideNumb - 1) * 20 + 20);
                        else
                            trackSublist = tracks;

                        String out = trackSublist.stream().collect(Collectors.joining("\n"));
                        int sideNumbAll = tracks.size() >= 20 ? tracks.size() / 20 : 1;

                        event.getChannel().sendMessage(
                                new EmbedBuilder()
                                        .setDescription(
                                                "**Aktuelle Wiedergabeliste:**\n" + out + "\n" + trackSublist.size() + " Lieder | Seite " + sideNumb + " / " + sideNumbAll)
                                        .build()
                        ).queue();
                        break;
                    } if (args.length > 2 && args[1].equalsIgnoreCase("add")) {
                    String in = Arrays.stream(args).skip(2).map(s -> " " + s).collect(Collectors.joining()).substring(1);
                    in = "ytsearch: " + in;
                    loadTrack(in, event.getMember(), event.getMessage(), "queueadd");
                    event.getChannel().sendMessage("Titel wurde zur Wiedergabeliste hinzugefügt").queue();
                    break;
                    } if (args.length > 2 && args[1].equalsIgnoreCase("remove")) {
                    String in = Arrays.stream(args).skip(2).map(s -> " " + s).collect(Collectors.joining()).substring(1);
                    in = "ytsearch: " + in;
                    loadTrack(in, event.getMember(), event.getMessage(), "queueremove");
                    break;
                    }
                    bol = false;

                case "add":

                    if (args.length > 1) {
                        String in = Arrays.stream(args).skip(1).map(s -> " " + s).collect(Collectors.joining()).substring(1);
                        in = "ytsearch: " + in;
                        loadTrack(in, event.getMember(), event.getMessage(), "queueadd");
                        event.getChannel().sendMessage("Titel wurde zur Wiedergabeliste hinzugefügt").queue();
                        break;
                    } bol = false;

                case "remove":

                    if (args.length > 1) {
                        String in = Arrays.stream(args).skip(1).map(s -> " " + s).collect(Collectors.joining()).substring(1);
                        in = "ytsearch: " + in;
                        loadTrack(in, event.getMember(), event.getMessage(), "queueremove");
                        break;
                    } bol = false;

                case "pause":

                    if (getManager(guild).isPaused()) {
                        getManager(guild).setPause(false);
                        break;
                    } else {
                        getManager(guild).setPause(true);
                        break;
                    }

                case "volume":

                    if (!(isIdle(guild)) && args.length == 2) {
                        try {getManager(guild).setVolume(Integer.parseInt(args[1]));}
                        catch (Exception e) {
                            event.getChannel().sendMessage(syntax);
                            bol = false;
                        }
                        break;
                    }
                    bol = false;

                case "help":
                case "syntax":

                    event.getChannel().sendMessage(syntax).queue();
                    break;

            }

            event.getMessage().delete().queue();

            Calendar cal = Calendar.getInstance();

            if (bol) System.out.println("[" + String.format("%02d:%02d:%02d", cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), cal.get(Calendar.SECOND)) + "] [COMMANDS] Command \"music\" wurde erfolgreich ausgeführt");
            else System.err.println("[" + String.format("%02d:%02d:%02d", cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), cal.get(Calendar.SECOND)) + "] [COMMANDS] Fehler in Command \"music\"");

        }
    }

    @Override
    public void executed(boolean sucess, GuildMessageReceivedEvent event) {

    }

    @Override
    public String help() {
        return null;
    }
}