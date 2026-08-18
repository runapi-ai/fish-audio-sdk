# Fish Audio Ruby SDK for RunAPI

Install `runapi-fish-audio` and create a `RunApi::FishAudio::Client`.

```ruby
created = client.create_voice.run(name: "Narrator", source_audio_url: "https://cdn.runapi.ai/public/samples/voice.mp3")
voice = client.get_voice.run(voice_id: created.voice.voice_id)
raise "Voice is #{voice.voice.state}" unless voice.voice.state == "trained"
result = client.text_to_speech.run(model: "s2.1-pro", text: "Hello [excited]", voice_id: created.voice.voice_id, output_format: "wav", sample_rate_hz: 44_100)
```

Pass optional `references` entries with base64-encoded raw audio bytes and exact transcripts for request-scoped voice matching.

Use `list_voices` and `get_voice` to inspect account-owned voice resources. Only `trained` voices can be submitted for speech generation; a returned `voice_id` is a best-effort reference and may stop working later. Update, delete, revoke, or voice-library management methods are not provided; voice retention is not promised.

The output defaults to MP3. Select WAV with `output_format`; `bitrate_kbps` applies only to MP3.

Model details and pricing: https://runapi.ai/models/fish-audio/s2.1-pro

Licensed under the Apache License, Version 2.0.
