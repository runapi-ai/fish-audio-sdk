# Fish Audio Ruby SDK for RunAPI

Install `runapi-fish-audio`, create `RunApi::FishAudio::Client`, and call `client.text_to_speech.run(model: "s2.1-pro", text: "Hello [excited]", output_format: "wav", sample_rate_hz: 44_100)`.

Pass optional `references` entries with base64-encoded raw audio bytes and exact transcripts for request-scoped voice matching.

The output defaults to MP3. Select WAV with `output_format`; `bitrate_kbps` applies only to MP3.

Model details and pricing: https://runapi.ai/models/fish-audio/s2.1-pro

Licensed under the Apache License, Version 2.0.
