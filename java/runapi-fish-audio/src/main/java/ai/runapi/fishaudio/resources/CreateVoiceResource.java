package ai.runapi.fishaudio.resources;

import ai.runapi.core.ClientOptions;
import ai.runapi.core.RequestOptions;
import ai.runapi.core.http.HttpTransport;
import ai.runapi.fishaudio.types.CreateVoiceParams;
import ai.runapi.fishaudio.types.VoiceResponse;

/** Account-owned reusable voice creation operations. */
public final class CreateVoiceResource extends FishaudioResource {
  /** API endpoint path for reusable voices. */
  public static final String ENDPOINT = "/api/v1/fish_audio/voices";

  /** Creates a resource bound to the supplied transport and client options. */
  public CreateVoiceResource(HttpTransport transport, ClientOptions options) {
    super(transport, options, ENDPOINT);
  }

  /** Creates an account-owned reusable voice. */
  public VoiceResponse run(CreateVoiceParams params) {
    return run(params, RequestOptions.none());
  }

  /** Creates an account-owned reusable voice with per-request options. */
  public VoiceResponse run(CreateVoiceParams params, RequestOptions options) {
    return runSync(params.action(), params.toMap(), options, VoiceResponse.class);
  }
}
