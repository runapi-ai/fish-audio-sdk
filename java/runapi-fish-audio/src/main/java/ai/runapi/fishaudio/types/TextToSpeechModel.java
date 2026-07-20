package ai.runapi.fishaudio.types;

import com.fasterxml.jackson.annotation.JsonCreator;

/** Model slug for text to speech operations. */
public final class TextToSpeechModel extends FishaudioValue {
  /** s1 model slug. */
  public static final TextToSpeechModel S1 = new TextToSpeechModel("s1");
  /** s2-pro model slug. */
  public static final TextToSpeechModel S2_PRO = new TextToSpeechModel("s2-pro");

  /** Creates a model value from a literal model slug. */
  @JsonCreator
  public TextToSpeechModel(String value) {
    super(value);
  }
}
