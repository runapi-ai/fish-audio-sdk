# Fish Audio Java SDK for RunAPI

Install the SDK:

```kotlin
implementation("ai.runapi:runapi-fish-audio:0.1.1")
```

Create `FishAudioClient` and call `client.textToSpeech().run(...)`.

Set optional `ReferenceAudio` entries with base64-encoded raw audio bytes and exact transcripts for request-scoped voice matching.

Model details and pricing: https://runapi.ai/models/fish-audio

Licensed under the Apache License, Version 2.0.
