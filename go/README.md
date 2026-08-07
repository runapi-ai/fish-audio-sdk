# Fish Audio Go SDK for RunAPI

Install `github.com/runapi-ai/fish-audio-sdk/go`, create a `fishaudio.Client`, and call `client.TextToSpeech.Run`.

Set optional `References` entries with base64-encoded raw audio bytes and exact transcripts for request-scoped voice matching.

Use `s2.1-pro` for recommended production TTS. The output defaults to MP3; set `OutputFormat` to `wav` and select `SampleRateHz` when WAV is required. `BitrateKbps` applies only to MP3.

Model details and pricing: https://runapi.ai/models/fish-audio/s2.1-pro

Licensed under the Apache License, Version 2.0.
