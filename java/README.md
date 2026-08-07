# Fish Audio Java SDK for RunAPI

Install the SDK:

```kotlin
implementation("ai.runapi:runapi-fish-audio:0.2.0")
```

Create `FishAudioClient` and call `client.textToSpeech().run(...)`.

Set optional `ReferenceAudio` entries with base64-encoded raw audio bytes and exact transcripts for request-scoped voice matching.

Use `TextToSpeechModel.S2_1_PRO` for recommended production TTS. The output defaults to MP3; use `outputFormat("wav")` and `sampleRateHz(...)` when WAV is required. `bitrateKbps(...)` applies only to MP3.

Model details and pricing: https://runapi.ai/models/fish-audio/s2.1-pro

Licensed under the Apache License, Version 2.0.
