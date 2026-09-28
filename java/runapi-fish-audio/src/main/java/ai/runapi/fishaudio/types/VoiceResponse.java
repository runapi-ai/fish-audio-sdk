package ai.runapi.fishaudio.types;

import com.fasterxml.jackson.annotation.JsonProperty;

/** Response containing one reusable voice. */
public class VoiceResponse {
  @JsonProperty("voice")
  private Voice voice;

  /** Returns the reusable voice. */
  public Voice getVoice() {
    return voice;
  }
}
