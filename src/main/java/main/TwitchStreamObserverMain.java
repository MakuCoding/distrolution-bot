
package main;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Guild;

import streamObserver.TwitchStreamObserver;
import streamObserver.event.StreamStatusEvent;
import streamObserver.event.StreamUpdateEvent;
import streamObserver.listener.StreamListener;

import java.awt.*;

import static main.main.jda;

public class TwitchStreamObserverMain {

    public static void main() {

        TwitchStreamObserver observer = new TwitchStreamObserver("xkest4crsrkpwfbv7psh7t03xfwwfk");

        observer.addChannel("gronkh");
        observer.addListener(new StreamListener() {
            @Override
            public void streamUpdate(StreamUpdateEvent event) {
                super.streamUpdate(event);
                }

            @Override
            public void streamIsOffline(StreamStatusEvent event) {
                super.streamIsOffline(event);
                System.out.println("[STREAM OFFLINE] Streamer " + event.getChannelName() + " ist offline gegangen");
            }

            @Override
            public void streamIsOnline(StreamStatusEvent event) {
                super.streamIsOnline(event);
                String name = event.getChannelName();
                System.out.println("[STREAM ONLINE] Streamer " + name + " ist online gegangen");
                for (Guild g:jda.getGuilds()) {
                    EmbedBuilder build = new EmbedBuilder()
                            .setColor(Color.MAGENTA)
                            .setDescription("Zuschauer: " + event.getStreamData().getViewers() + "          Spiel: " + event.getStreamData().getGame())
                            .setTitle(event.getChannelName().toUpperCase() + " ist jetzt " + event.getStreamData().getStreamType() + " auf Twitch",event.getStreamData().getChannel().getUrl())
                            .setThumbnail(event.getStreamData().getChannel().getLogo())
                            .setImage(event.getStreamData().getChannel().getVideoBanner());
                    g.getTextChannelsByName("lobby",true).get(0).sendMessage(build.build()).queue();
                }
            }
        });
        observer.start();
    }
}