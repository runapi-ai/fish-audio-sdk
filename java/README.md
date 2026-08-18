# Fish Audio Java SDK for RunAPI

Install the SDK:

```kotlin
implementation("ai.runapi:runapi-fish-audio:0.3.0")
```

Create a `FishAudioClient`.

```java
VoiceResponse created = client.createVoice().run(CreateVoiceParams.builder().name("Narrator").sourceAudioUrl("https://cdn.runapi.ai/public/samples/voice.mp3").build());
VoiceResponse voice = client.getVoice().run(GetVoiceParams.builder().voiceId(created.getVoice().getVoiceId()).build());
if (!"trained".equals(voice.getVoice().getState())) throw new IllegalStateException("voice is not trained");
TextToSpeechResponse result = client.textToSpeech().run(TextToSpeechParams.builder().model(TextToSpeechModel.S1).text("Hello").voiceId(created.getVoice().getVoiceId()).build());
```

Use `listVoices()` and `getVoice()` to inspect account-owned voice resources. Only `trained` voices can be submitted for speech generation; a returned `voice_id` is a best-effort reference and may stop working later. Update, delete, revoke, or voice-library management methods are not provided.

For request-scoped matching, set optional `ReferenceAudio` entries with base64-encoded raw audio bytes and exact transcripts instead of `voiceId`.

Use `TextToSpeechModel.S2_1_PRO` for recommended production TTS. The output defaults to MP3; use `outputFormat("wav")` and `sampleRateHz(...)` when WAV is required. `bitrateKbps(...)` applies only to MP3.

Model details and pricing: https://runapi.ai/models/fish-audio/s2.1-pro

Licensed under the Apache License, Version 2.0.
