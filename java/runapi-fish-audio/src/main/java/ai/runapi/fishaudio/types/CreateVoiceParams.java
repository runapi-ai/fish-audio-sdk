package ai.runapi.fishaudio.types;

import java.util.LinkedHashMap;
import java.util.Map;

/** Parameters for creating an account-owned reusable voice. */
public final class CreateVoiceParams {
  private final String name;
  private final String sourceAudioUrl;

  private CreateVoiceParams(Builder builder) {
    this.name = FishaudioParamUtils.requireNonBlank(builder.name, "name");
    this.sourceAudioUrl = FishaudioParamUtils.requireNonBlank(builder.sourceAudioUrl, "sourceAudioUrl");
  }

  /** Creates a new builder. */
  public static Builder builder() {
    return new Builder();
  }

  /** Returns the RunAPI action key. */
  public String action() {
    return "fish-audio/create-voice";
  }

  /** Converts these parameters to the JSON request body. */
  public Map<String, Object> toMap() {
    Map<String, Object> raw = new LinkedHashMap<String, Object>();
    raw.put("name", FishaudioParamUtils.wireValue(name));
    raw.put("source_audio_url", FishaudioParamUtils.wireValue(sourceAudioUrl));
    return FishaudioParamUtils.compact(raw);
  }

  /** Builder for {@link CreateVoiceParams}. */
  public static final class Builder {
    private String name;
    private String sourceAudioUrl;

    private Builder() {}

    /** Sets the voice name. */
    public Builder name(String value) {
      this.name = FishaudioParamUtils.requireNonBlank(value, "name");
      return this;
    }

    /** Sets the source audio URL. */
    public Builder sourceAudioUrl(String value) {
      this.sourceAudioUrl = FishaudioParamUtils.requireNonBlank(value, "sourceAudioUrl");
      return this;
    }

    /** Builds immutable create voice parameters. */
    public CreateVoiceParams build() {
      return new CreateVoiceParams(this);
    }
  }
}
