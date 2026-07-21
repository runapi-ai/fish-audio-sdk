package ai.runapi.fishaudio.types;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/** Parameters for text to speech operations. */
public final class TextToSpeechParams {
  private final String model;
  private final String text;
  private final List<ReferenceAudio> references;

  private TextToSpeechParams(Builder builder) {
    this.model = builder.model;
    this.text = FishaudioParamUtils.requireNonBlank(builder.text, "text");
    this.references = FishaudioParamUtils.list(builder.references, "references");
  }

  /** Creates a new TextToSpeechParams builder. */
  public static Builder builder() {
    return new Builder();
  }

  /** Returns the RunAPI action key for this request. */
  public String action() {
    return "fish-audio/text-to-speech";
  }

  /** Converts these parameters to the JSON request body shape. */
  public Map<String, Object> toMap() {
    Map<String, Object> raw = new LinkedHashMap<String, Object>();
    raw.put("model", FishaudioParamUtils.wireValue(model));
    raw.put("text", FishaudioParamUtils.wireValue(text));
    raw.put("references", referencesToMaps(references));
    return FishaudioParamUtils.compact(raw);
  }

  private static List<Map<String, Object>> referencesToMaps(List<ReferenceAudio> values) {
    if (values == null) {
      return null;
    }
    List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
    for (ReferenceAudio item : values) {
      result.add(item.toMap());
    }
    return java.util.Collections.unmodifiableList(result);
  }

  /** Builder for {@link TextToSpeechParams}. */
  public static final class Builder {
    private String model;
    private String text;
    private List<ReferenceAudio> references;

    private Builder() {}

    /** Sets the model slug using a typed model value. */
    public Builder model(TextToSpeechModel value) {
      this.model = java.util.Objects.requireNonNull(value, "model").value();
      return this;
    }

    /** Sets the model slug using a string value. */
    public Builder model(String value) {
      this.model = FishaudioParamUtils.requireNonBlankTrim(value, "model");
      return this;
    }


    /** Sets the line text. */
    public Builder text(String value) {
      this.text = FishaudioParamUtils.requireNonBlank(value, "text");
      return this;
    }

    /** Sets the request-scoped reference audio samples. */
    public Builder references(List<ReferenceAudio> value) {
      this.references = value;
      return this;
    }

    /** Builds immutable text to speech parameters. */
    public TextToSpeechParams build() {
      return new TextToSpeechParams(this);
    }
  }
}
