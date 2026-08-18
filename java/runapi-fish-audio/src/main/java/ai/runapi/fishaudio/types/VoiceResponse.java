package ai.runapi.fishaudio.types;

import ai.runapi.core.billing.TaskBillingFacts;
import com.fasterxml.jackson.annotation.JsonProperty;

/** Response containing one reusable voice. */
public class VoiceResponse {
  @JsonProperty("voice")
  private Voice voice;

  @JsonProperty("billing")
  private TaskBillingFacts billing;

  /** Returns the reusable voice. */
  public Voice getVoice() {
    return voice;
  }

  /** Returns persisted billing facts for this request. */
  public TaskBillingFacts getBilling() {
    return billing;
  }
}
