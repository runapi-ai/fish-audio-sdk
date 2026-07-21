package ai.runapi.fishaudio.types;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/** A request-scoped reference audio sample. */
public final class ReferenceAudio {
  private final String audio;
  private final String text;

  private ReferenceAudio(Builder builder) {
    this.audio = FishaudioParamUtils.requireNonBlank(builder.audio, "audio");
    this.text = FishaudioParamUtils.requireNonBlank(builder.text, "text");
  }

  /** Creates a new ReferenceAudio builder. */
  public static Builder builder() {
    return new Builder();
  }

  /** Returns the base64-encoded raw audio bytes. */
  public String getAudio() {
    return audio;
  }

  /** Returns the exact transcript of the reference audio. */
  public String getText() {
    return text;
  }

  Map<String, Object> toMap() {
    Map<String, Object> raw = new LinkedHashMap<String, Object>();
    raw.put("audio", FishaudioParamUtils.wireValue(audio));
    raw.put("text", FishaudioParamUtils.wireValue(text));
    return FishaudioParamUtils.compact(raw);
  }

  /** Builder for {@link ReferenceAudio}. */
  public static final class Builder {
    private String audio;
    private String text;

    private Builder() {}

    /** Sets the base64-encoded raw audio bytes. */
    public Builder audio(String value) {
      this.audio = FishaudioParamUtils.requireNonBlank(value, "audio");
      return this;
    }

    /** Sets the exact transcript of the reference audio. */
    public Builder text(String value) {
      this.text = FishaudioParamUtils.requireNonBlank(value, "text");
      return this;
    }

    /** Builds an immutable ReferenceAudio. */
    public ReferenceAudio build() {
      return new ReferenceAudio(this);
    }
  }
}
