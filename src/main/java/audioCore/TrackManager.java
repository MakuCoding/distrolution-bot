package audioCore;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.VoiceChannel;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;


public class TrackManager extends AudioEventAdapter {

    private final AudioPlayer PLAYER;
    private final Queue<AudioInfo> queue;


    public TrackManager(AudioPlayer player) {
        this.PLAYER = player;
        this.queue = new LinkedBlockingQueue<>();
    }

    public void queueAdd(AudioTrack track, Member author) {
        AudioInfo info = new AudioInfo(track, author);
        queue.add(info);
    }

    public void queueRemove(AudioTrack track, Member author, TextChannel txtchan) {
            AudioInfo info = new AudioInfo(track, author);
            AudioInfo ai = queue.poll();
            if (ai != null) txtchan.sendMessage("Titel aus der Wiedergabeliste entfernt").queue();
            else txtchan.sendMessage(new EmbedBuilder().setColor(Color.RED).setDescription("Dieser Titel ist nicht in der Wiedergabeliste!").build()).queue();
    }

    public void play() {
        if (PLAYER.getPlayingTrack() == null && !queue.isEmpty())
            PLAYER.playTrack(queue.element().getTrack());
    }

    public void play(AudioTrack track, Member author) {
        if (PLAYER.getPlayingTrack() != null) PLAYER.stopTrack();
        Queue<AudioInfo> subqueue = new LinkedBlockingQueue<>();
        subqueue.add(new AudioInfo(track, author));
        subqueue.addAll(queue);
        for (int i = queue.size();i > 0;i--) {
            queue.poll();
        }
        queue.addAll(subqueue);
        PLAYER.playTrack(track);
    }

    public void playList(AudioTrack track, Member author) {
        AudioInfo info = new AudioInfo(track, author);
        queue.add(info);

        if (PLAYER.getPlayingTrack() == null) {
            PLAYER.playTrack(track);
        }
    }

    public boolean isPaused() {
        return PLAYER.isPaused();
    }

    public void setPause(boolean pause) {
        PLAYER.setPaused(pause);
    }

    public void setVolume(int volume) {
        PLAYER.setVolume(volume);
    }

    public int getVolume() {
        return PLAYER.getVolume();
    }

    public Set<AudioInfo> getQueue() {
        return new LinkedHashSet<>(queue);
    }

    public AudioInfo getInfo(AudioTrack track) {
        return queue.stream()
                .filter(info -> info.getTrack().equals(track))
                .findFirst().orElse(null);
    }

    public void purgeQueue() {
        queue.clear();
    }

    public void shuffleQueue() {
        List<AudioInfo> cQueue = new ArrayList<>(getQueue());
        AudioInfo current = cQueue.get(0);
        cQueue.remove(0);
        Collections.shuffle(cQueue);
        cQueue.add(0, current);
        purgeQueue();
        queue.addAll(cQueue);

    }


    @Override
    public void onTrackStart(AudioPlayer player, AudioTrack track) {
        AudioInfo info = queue.element();
        VoiceChannel vChan = info.getAuthor().getVoiceState().getChannel();

        if (vChan == null) {
            player.stopTrack();
            System.out.println("Kein Channel gefunden");
        } else
            info.getAuthor().getGuild().getAudioManager().openAudioConnection(vChan);
    }

    @Override
    public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
        Guild g = queue.remove().getAuthor().getGuild();

        if (queue.isEmpty()) {
            g.getAudioManager().closeAudioConnection();
        } else
            player.playTrack(queue.element().getTrack());
    }

}