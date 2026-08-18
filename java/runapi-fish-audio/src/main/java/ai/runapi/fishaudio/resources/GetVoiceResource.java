package ai.runapi.fishaudio.resources;

import ai.runapi.core.ClientOptions;
import ai.runapi.core.RequestOptions;
import ai.runapi.core.http.HttpTransport;
import ai.runapi.fishaudio.types.GetVoiceParams;
import ai.runapi.fishaudio.types.VoiceResponse;
import java.net.URLEncoder;
import java.util.Collections;

/** Account-owned reusable voice lookup operations. */
public final class GetVoiceResource extends FishaudioResource {
  /** API endpoint path for reusable voices. */
  public static final String ENDPOINT = "/api/v1/fish_audio/voices";

  /** Creates a resource bound to the supplied transport and client options. */
  public GetVoiceResource(HttpTransport transport, ClientOptions options) {
    super(transport, options, ENDPOINT);
  }

  /** Gets one account-owned reusable voice. */
  public VoiceResponse run(GetVoiceParams params) {
    return run(params, RequestOptions.none());
  }

  /** Gets one account-owned reusable voice with per-request options. */
  public VoiceResponse run(GetVoiceParams params, RequestOptions options) {
    String encoded = encodePathSegment(params.voiceId());
    return runSyncGet(
        params.action(),
        params.toMap(),
        ENDPOINT + "/" + encoded,
        Collections.<String, String>emptyMap(),
        options,
        VoiceResponse.class);
  }

  private static String encodePathSegment(String value) {
    try {
      return URLEncoder.encode(value, "UTF-8").replace("+", "%20");
    } catch (java.io.UnsupportedEncodingException e) {
      throw new IllegalStateException(e);
    }
  }
}
