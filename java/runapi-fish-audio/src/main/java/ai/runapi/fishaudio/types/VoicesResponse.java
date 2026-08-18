package ai.runapi.fishaudio.types;

import ai.runapi.core.billing.TaskBillingFacts;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Collections;
import java.util.List;

/** Paginated response containing account-owned reusable voices. */
public class VoicesResponse {
  @JsonProperty("voices")
  private List<Voice> voices;

  @JsonProperty("total")
  private Integer total;

  @JsonProperty("page_number")
  private Integer pageNumber;

  @JsonProperty("page_size")
  private Integer pageSize;

  @JsonProperty("billing")
  private TaskBillingFacts billing;

  /** Returns account-owned reusable voices. */
  public List<Voice> getVoices() {
    return voices == null ? Collections.<Voice>emptyList() : Collections.unmodifiableList(voices);
  }

  /** Returns the total number of voices in the filtered page. */
  public Integer getTotal() {
    return total;
  }

  /** Returns the current page number. */
  public Integer getPageNumber() {
    return pageNumber;
  }

  /** Returns the current page size. */
  public Integer getPageSize() {
    return pageSize;
  }

  /** Returns persisted billing facts for this request. */
  public TaskBillingFacts getBilling() {
    return billing;
  }
}
