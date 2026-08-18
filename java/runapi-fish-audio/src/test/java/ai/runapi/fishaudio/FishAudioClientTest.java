package ai.runapi.fishaudio;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import ai.runapi.core.RequestOptions;
import ai.runapi.core.errors.ValidationException;
import ai.runapi.core.http.HttpRequest;
import ai.runapi.core.http.HttpResponse;
import ai.runapi.core.http.HttpTransport;
import ai.runapi.core.http.JsonRequestBody;
import ai.runapi.core.json.Json;
import ai.runapi.fishaudio.types.TextToSpeechResponse;
import ai.runapi.fishaudio.types.ReferenceAudio;
import ai.runapi.fishaudio.types.TextToSpeechModel;
import ai.runapi.fishaudio.types.TextToSpeechParams;
import ai.runapi.fishaudio.types.CreateVoiceParams;
import ai.runapi.fishaudio.types.GetVoiceParams;
import ai.runapi.fishaudio.types.ListVoicesParams;
import ai.runapi.fishaudio.types.VoiceResponse;
import ai.runapi.fishaudio.types.VoicesResponse;
import com.fasterxml.jackson.databind.JsonNode;
import java.io.ByteArrayOutputStream;
import java.time.Duration;
import java.util.Collections;
import org.junit.jupiter.api.Test;

class FishAudioClientTest {
  @Test
  void builderCreatesClientAndUniversalResources() {
    FishAudioClient client = FishAudioClient.builder().apiKey("sk-test").build();

    assertNotNull(client.textToSpeech());
    assertNotNull(client.createVoice());
    assertNotNull(client.listVoices());
    assertNotNull(client.getVoice());
    assertNotNull(client.files());
    assertNotNull(client.account());
  }

  @Test
  void openValueClassesSerializeAsScalarStrings() throws Exception {
    String json = Json.mapper().writeValueAsString(new TextToSpeechModel("s1"));

    assertEquals("\"s1\"", json);
    assertEquals(new TextToSpeechModel("s1"), Json.mapper().readValue(json, TextToSpeechModel.class));
  }

  @Test
  void runSendsExpectedRequestShape() throws Exception {
    CapturingTransport transport = new CapturingTransport("{\"id\":\"sync_123\",\"status\":\"completed\",\"audios\":[{\"url\":\"https://file.runapi.ai/generated.mp3\",\"format\":\"mp3\",\"mime_type\":\"audio/mpeg\",\"size_bytes\":128}],\"billing\":{\"settlement\":{\"charged_amount_cents\":11,\"amount_micro_cents\":1050000}},\"custom\":\"kept\"}");
    FishAudioClient client = FishAudioClient.builder().apiKey("sk-test").transport(transport).build();

    client.textToSpeech().run(
        TextToSpeechParams.builder()
            .model(TextToSpeechModel.S2_1_PRO)
            .text("sample")
            .outputFormat("wav")
            .sampleRateHz(24000)
            .references(java.util.Collections.singletonList(ReferenceAudio.builder().audio("UklGRg==").text("Reference transcript").build()))
            .build()
    );

    assertEquals("POST", transport.request.getMethod().name());
    assertEquals("/api/v1/fish_audio/text_to_speech", transport.request.getPath());
    JsonNode body = bodyJson(transport.request);
    assertNotNull(body);
    assertEquals("s2.1-pro", body.get("model").asText());
    assertEquals("wav", body.get("output_format").asText());
    assertEquals(24000, body.get("sample_rate_hz").asInt());
    assertEquals("UklGRg==", body.get("references").get(0).get("audio").asText());
    assertEquals("Reference transcript", body.get("references").get(0).get("text").asText());
  }

  @Test
  void sendsReusableVoiceId() throws Exception {
    CapturingTransport transport = new CapturingTransport("{\"id\":\"sync_123\",\"status\":\"completed\",\"audios\":[]}");
    FishAudioClient client = FishAudioClient.builder().apiKey("sk-test").transport(transport).build();

    client.textToSpeech().run(
        TextToSpeechParams.builder()
            .model(TextToSpeechModel.S1)
            .text("sample")
            .voiceId("voice_1")
            .build());

    JsonNode body = bodyJson(transport.request);
    assertEquals("voice_1", body.get("voice_id").asText());
  }

  @Test
  void createsPrivateReusableVoice() throws Exception {
    CapturingTransport transport = new CapturingTransport("{\"voice\":{\"voice_id\":\"voice_1\",\"name\":\"Narrator\",\"state\":\"training\"},\"billing\":{\"reservation\":null,\"settlement\":{\"charged_amount_cents\":0,\"amount_micro_cents\":0},\"refund\":null}}");
    FishAudioClient client = FishAudioClient.builder().apiKey("sk-test").transport(transport).build();

    VoiceResponse response = client.createVoice().run(
        CreateVoiceParams.builder()
            .name("Narrator")
            .sourceAudioUrl("https://cdn.runapi.ai/narrator.mp3")
            .build());

    assertEquals("POST", transport.request.getMethod().name());
    assertEquals("/api/v1/fish_audio/voices", transport.request.getPath());
    assertEquals("https://cdn.runapi.ai/narrator.mp3", bodyJson(transport.request).get("source_audio_url").asText());
    assertEquals("training", response.getVoice().getState());
    assertEquals(Long.valueOf(0), response.getBilling().getSettlement().getChargedAmountCents());
  }

  @Test
  void listsAccountOwnedReusableVoices() {
    CapturingTransport transport = new CapturingTransport("{\"voices\":[{\"voice_id\":\"voice_1\",\"name\":\"Narrator\",\"state\":\"trained\"}],\"total\":1,\"page_number\":2,\"page_size\":25,\"billing\":{\"reservation\":null,\"settlement\":{\"charged_amount_cents\":0,\"amount_micro_cents\":0},\"refund\":null}}");
    FishAudioClient client = FishAudioClient.builder().apiKey("sk-test").transport(transport).build();

    VoicesResponse response = client.listVoices().run(
        ListVoicesParams.builder().pageNumber(2).pageSize(25).build());

    assertEquals("GET", transport.request.getMethod().name());
    assertEquals("/api/v1/fish_audio/voices", transport.request.getPath());
    assertEquals("2", transport.request.getQuery().get("page_number"));
    assertEquals("25", transport.request.getQuery().get("page_size"));
    assertEquals("voice_1", response.getVoices().get(0).getVoiceId());
    assertEquals(Long.valueOf(0), response.getBilling().getSettlement().getChargedAmountCents());
  }

  @Test
  void getsEncodedAccountOwnedReusableVoiceId() {
    CapturingTransport transport = new CapturingTransport("{\"voice\":{\"voice_id\":\"voice/1\",\"name\":\"Narrator\",\"state\":\"trained\"},\"billing\":{\"reservation\":null,\"settlement\":{\"charged_amount_cents\":0,\"amount_micro_cents\":0},\"refund\":null}}");
    FishAudioClient client = FishAudioClient.builder().apiKey("sk-test").transport(transport).build();

    VoiceResponse response = client.getVoice().run(GetVoiceParams.builder().voiceId("voice/1").build());

    assertEquals("GET", transport.request.getMethod().name());
    assertEquals("/api/v1/fish_audio/voices/voice%2F1", transport.request.getPath());
    assertEquals("trained", response.getVoice().getState());
    assertEquals(Long.valueOf(0), response.getBilling().getSettlement().getChargedAmountCents());
  }

  @Test
  void runDecodesResponseAndExtraFields() {
    CapturingTransport transport = new CapturingTransport("{\"id\":\"sync_123\",\"status\":\"completed\",\"audios\":[{\"url\":\"https://file.runapi.ai/generated.mp3\",\"format\":\"mp3\",\"mime_type\":\"audio/mpeg\",\"size_bytes\":128}],\"billing\":{\"settlement\":{\"charged_amount_cents\":11,\"amount_micro_cents\":1050000}},\"custom\":\"kept\"}");
    FishAudioClient client = FishAudioClient.builder().apiKey("sk-test").transport(transport).build();

    TextToSpeechResponse response = client.textToSpeech().run(
        TextToSpeechParams.builder()
            .model(TextToSpeechModel.S1)
            .text("sample")
            .build()
    );

    assertEquals("POST", transport.request.getMethod().name());
    assertEquals("/api/v1/fish_audio/text_to_speech", transport.request.getPath());
    assertNotNull(response.getAudios());
    assertEquals("completed", response.getStatus().value());
    assertEquals("audio/mpeg", response.getAudios().get(0).getMimeType());
    assertEquals(Long.valueOf(128), response.getAudios().get(0).getSizeBytes());
    assertEquals(Long.valueOf(11), response.getBilling().getSettlement().getChargedAmountCents());
    assertEquals("kept", response.extraFields().get("custom").asText());
  }

    @Test
    void coversTexttospeechResourceMethods() {
      CapturingTransport transport = new CapturingTransport("{\"id\":\"sync_text_to_speech\",\"status\":\"completed\",\"audios\":[{\"url\":\"https://file.runapi.ai/generated.mp3\",\"format\":\"mp3\",\"mime_type\":\"audio/mpeg\",\"size_bytes\":128}],\"billing\":{\"settlement\":{\"charged_amount_cents\":11,\"amount_micro_cents\":1050000}}}");
      FishAudioClient client = FishAudioClient.builder().apiKey("sk-test").transport(transport).build();

      TextToSpeechResponse response = client.textToSpeech().run(
              TextToSpeechParams.builder()
                  .model(TextToSpeechModel.S1)
                  .text("sample")
                  .build()
      );
      assertNotNull(response);
      assertEquals(Long.valueOf(11), response.getBilling().getSettlement().getChargedAmountCents());

      CapturingTransport transportWithOptions = new CapturingTransport("{\"id\":\"sync_text_to_speech_options\",\"status\":\"completed\",\"audios\":[{\"url\":\"https://file.runapi.ai/generated.mp3\",\"format\":\"mp3\",\"mime_type\":\"audio/mpeg\",\"size_bytes\":128}],\"billing\":{\"settlement\":{\"charged_amount_cents\":11,\"amount_micro_cents\":1050000}}}");
      FishAudioClient clientWithOptions = FishAudioClient.builder().apiKey("sk-test").transport(transportWithOptions).build();
      assertNotNull(clientWithOptions.textToSpeech().run(
              TextToSpeechParams.builder()
                  .model(TextToSpeechModel.S1)
                  .text("sample")
                  .build(),
          RequestOptions.none()));
    }

  private static JsonNode bodyJson(HttpRequest request) throws Exception {
    JsonRequestBody body = (JsonRequestBody) request.getBody();
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    body.writeTo(out);
    return Json.mapper().readTree(out.toByteArray());
  }

  private static final class CapturingTransport implements HttpTransport {
    private final String body;
    private HttpRequest request;

    private CapturingTransport(String body) {
      this.body = body;
    }

    public HttpResponse send(HttpRequest request) {
      this.request = request;
      return new HttpResponse(200, body, Collections.<String, java.util.List<String>>emptyMap());
    }

    public void close() {}
  }

  private static final class SequenceTransport implements HttpTransport {
    private final String[] responses;
    private int calls;

    private SequenceTransport(String... responses) {
      this.responses = responses;
    }

    public HttpResponse send(HttpRequest request) {
      String response = responses[Math.min(calls, responses.length - 1)];
      calls++;
      return new HttpResponse(200, response, Collections.<String, java.util.List<String>>emptyMap());
    }

    public void close() {}
  }
}
