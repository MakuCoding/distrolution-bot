package streamObserver.result;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(value = { "_links" })
public class Stream {
	@JsonProperty("game")
	private String game;
	@JsonProperty("viewers")
	private Long viewers;
	@JsonProperty("average_fps")
	private Double averageFps;
	@JsonProperty("video_height")
	private Integer videoHeight;
	@JsonProperty("created_at")
	private String createdAt;
	@JsonProperty("_id")
	private Long id;
	@JsonProperty("channel")
	private Channel channel;
	@JsonProperty("preview")
	private Preview preview;
	@JsonProperty("delay")
	private Long delay;
	@JsonProperty("is_playlist")
	private boolean is_playlist;
	@JsonProperty("stream_type")
	private String stream_type;

	@JsonProperty("game")
	public String getGame() {
		return game;
	}

	@JsonProperty("game")
	public void setGame(String game) {
		this.game = game;
	}

	@JsonProperty("viewers")
	public Long getViewers() {
		return viewers;
	}

	@JsonProperty("viewers")
	public void setViewers(Long viewers) {
		this.viewers = viewers;
	}

	@JsonProperty("average_fps")
	public Double getAverageFps() {
		return averageFps;
	}

	@JsonProperty("average_fps")
	public void setAverageFps(Double averageFps) {
		this.averageFps = averageFps;
	}

	@JsonProperty("video_height")
	public Integer getVideoHeight() {
		return videoHeight;
	}

	@JsonProperty("video_height")
	public void setVideoHeight(Integer videoHeight) {
		this.videoHeight = videoHeight;
	}

	@JsonProperty("created_at")
	public String getCreatedAt() {
		return createdAt;
	}

	@JsonProperty("created_at")
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	@JsonProperty("_id")
	public Long getId() {
		return id;
	}

	@JsonProperty("_id")
	public void setId(Long id) {
		this.id = id;
	}

	@JsonProperty("channel")
	public Channel getChannel() {
		return channel;
	}

	@JsonProperty("channel")
	public void setChannel(Channel channel) {
		this.channel = channel;
	}

	@JsonProperty("preview")
	public Preview getPreview() {
		return preview;
	}

	@JsonProperty("preview")
	public void setPreview(Preview preview) {
		this.preview = preview;
	}

	@JsonProperty("delay")
	public Long getDelay() {return delay; }

	@JsonProperty("delay")
	public void setDelay(Long delay) {this.delay = delay; }

	@JsonProperty("is_playlist")
	public boolean getIsPlaylist() {return is_playlist; }

	@JsonProperty("is_playlist")
	public void setIsPlaylist(boolean is_playlist) {this.is_playlist = is_playlist; }

	@JsonProperty("stream_type")
	public String getStreamType() {return stream_type; }

	@JsonProperty("stream_type")
	public void setStreamType(String stream_type) {this.stream_type = stream_type; }
}