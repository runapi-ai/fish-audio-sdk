package ai.runapi.fishaudio.types;

import java.util.LinkedHashMap;
import java.util.Map;

/** Parameters for paginating account-owned reusable voices. */
public final class ListVoicesParams {
  private final Integer pageNumber;
  private final Integer pageSize;

  private ListVoicesParams(Builder builder) {
    this.pageNumber = builder.pageNumber;
    this.pageSize = builder.pageSize;
  }

  /** Creates a new builder. */
  public static Builder builder() {
    return new Builder();
  }

  /** Returns the RunAPI action key. */
  public String action() {
    return "fish-audio/list-voices";
  }

  /** Converts these parameters to the contract input shape. */
  public Map<String, Object> toMap() {
    Map<String, Object> raw = new LinkedHashMap<String, Object>();
    raw.put("page_number", pageNumber);
    raw.put("page_size", pageSize);
    return FishaudioParamUtils.compact(raw);
  }

  /** Converts these parameters to URL query values. */
  public Map<String, String> toQuery() {
    Map<String, String> query = new LinkedHashMap<String, String>();
    if (pageNumber != null) {
      query.put("page_number", pageNumber.toString());
    }
    if (pageSize != null) {
      query.put("page_size", pageSize.toString());
    }
    return query;
  }

  /** Builder for {@link ListVoicesParams}. */
  public static final class Builder {
    private Integer pageNumber;
    private Integer pageSize;

    private Builder() {}

    /** Sets the page number. */
    public Builder pageNumber(int value) {
      this.pageNumber = value;
      return this;
    }

    /** Sets the page size. */
    public Builder pageSize(int value) {
      this.pageSize = value;
      return this;
    }

    /** Builds immutable list voices parameters. */
    public ListVoicesParams build() {
      return new ListVoicesParams(this);
    }
  }
}
