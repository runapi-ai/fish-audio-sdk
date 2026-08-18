package ai.runapi.fishaudio.types;

import com.fasterxml.jackson.annotation.JsonProperty;

/** Account-owned reusable voice. */
public class Voice {
  @JsonProperty("voice_id")
  private String voiceId;

  @JsonProperty("name")
  private String name;

  @JsonProperty("state")
  private String state;

  /** Returns the reusable voice ID. */
  public String getVoiceId() {
    return voiceId;
  }

  /** Returns the voice name, when present. */
  public String getName() {
    return name;
  }

  /** Returns the normalized voice state. */
  public String getState() {
    return state;
  }
}
