package ai.runapi.fishaudio.types;

import java.util.Collections;
import java.util.Map;

/** Parameters for getting one account-owned reusable voice. */
public final class GetVoiceParams {
  private final String voiceId;

  private GetVoiceParams(Builder builder) {
    this.voiceId = builder.voiceId;
  }

  /** Creates a new builder. */
  public static Builder builder() {
    return new Builder();
  }

  /** Returns the reusable voice ID. */
  public String voiceId() {
    return voiceId;
  }

  /** Returns the RunAPI action key. */
  public String action() {
    return "fish-audio/get-voice";
  }

  /** Converts these parameters to the contract input shape. */
  public Map<String, Object> toMap() {
    return Collections.<String, Object>singletonMap("voice_id", voiceId);
  }

  /** Builder for {@link GetVoiceParams}. */
  public static final class Builder {
    private String voiceId;

    private Builder() {}

    /** Sets the reusable voice ID. */
    public Builder voiceId(String value) {
      this.voiceId = value;
      return this;
    }

    /** Builds immutable get voice parameters. */
    public GetVoiceParams build() {
      return new GetVoiceParams(this);
    }
  }
}
