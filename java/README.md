# Fish Audio Java SDK for RunAPI

Install the SDK:

```kotlin
implementation("ai.runapi:runapi-fish-audio:0.5.0")
```

Create a `FishAudioClient`.

```java
VoiceResponse created = client.createVoice().run(CreateVoiceParams.builder().name("Narrator").sourceAudioUrl("https://cdn.runapi.ai/public/samples/voice.mp3").build());
VoiceResponse voice = client.getVoice().run(GetVoiceParams.builder().voiceId(created.getVoice().getVoiceId()).build());
if (!"trained".equals(voice.getVoice().getState())) throw new IllegalStateException("voice is not trained");
TextToSpeechResponse result = client.textToSpeech().run(TextToSpeechParams.builder().model(TextToSpeechModel.S1).text("Hello").voiceId(created.getVoice().getVoiceId()).build());
```

Use `listVoices()` and `getVoice()` to inspect account-owned voice resources. Only `trained` voices can be submitted for speech generation; a returned `voice_id` is a best-effort reference and may stop working later. Update, delete, revoke, or voice-library management methods are not provided.

For request-scoped matching, set optional `ReferenceAudio` entries with a publicly accessible HTTPS audio URL and exact transcripts instead of `voiceId`.

Use `TextToSpeechModel.S2_1_PRO` for recommended production TTS. The output defaults to MP3; use `outputFormat("wav")` and `sampleRateHz(...)` when WAV is required. `bitrateKbps(...)` applies only to MP3.

Model details and pricing: https://runapi.ai/models/fish-audio/s2.1-pro

Licensed under the Apache License, Version 2.0.

## Temporary voice matching

For `s1`, `s2-pro`, and `s2.1-pro`, supply at most one `references` item containing a publicly accessible HTTPS audio URL in `audio` and a nonempty, verbatim transcript in `text`. Do not send `voice_id` with a reference. Omit `references` when selecting an existing voice. The transcript is forwarded unchanged; callers must ensure it matches the audio.

For local audio or Base64, first upload through `POST /api/v1/files`, then use the returned `url` as `references[0].audio`. Keep only one sample and remove `voice_id`. The URL must remain valid while the request waits and the audio is fetched. Files URLs expire by default after one hour; storing a reference does not extend that lifetime. For longer waits, use an external HTTPS URL with enough validity. If a URL expires, upload again and submit a new request.
