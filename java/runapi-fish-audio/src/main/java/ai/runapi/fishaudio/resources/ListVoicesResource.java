package ai.runapi.fishaudio.resources;

import ai.runapi.core.ClientOptions;
import ai.runapi.core.RequestOptions;
import ai.runapi.core.http.HttpTransport;
import ai.runapi.fishaudio.types.ListVoicesParams;
import ai.runapi.fishaudio.types.VoicesResponse;

/** Account-owned reusable voice listing operations. */
public final class ListVoicesResource extends FishaudioResource {
  /** API endpoint path for reusable voices. */
  public static final String ENDPOINT = "/api/v1/fish_audio/voices";

  /** Creates a resource bound to the supplied transport and client options. */
  public ListVoicesResource(HttpTransport transport, ClientOptions options) {
    super(transport, options, ENDPOINT);
  }

  /** Lists account-owned reusable voices. */
  public VoicesResponse run(ListVoicesParams params) {
    return run(params, RequestOptions.none());
  }

  /** Lists account-owned reusable voices with per-request options. */
  public VoicesResponse run(ListVoicesParams params, RequestOptions options) {
    return runSyncGet(params.action(), params.toMap(), ENDPOINT, params.toQuery(), options, VoicesResponse.class);
  }
}
