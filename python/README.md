# Fish Audio Python SDK for RunAPI

Install `runapi-fish-audio` and create a `FishAudioClient`.

```python
created = client.create_voice.run(name="Narrator", source_audio_url="https://cdn.runapi.ai/public/samples/voice.mp3")
voice = client.get_voice.run(voice_id=created.voice.voice_id)
if voice.voice.state != "trained":
    raise RuntimeError(f"Voice is {voice.voice.state}")
result = client.text_to_speech.run(model="s2.1-pro", text="Hello [excited]", voice_id=created.voice.voice_id, output_format="wav", sample_rate_hz=44100)
```

Pass optional `references` entries with a publicly accessible HTTPS audio URL and exact transcripts for request-scoped voice matching.

`create_voice.run()` and `text_to_speech.run()` return the same typed terminal result whether the request completes directly or is accepted for background processing. To resume an accepted request from its opaque Task Result URL, call `subscribe(location)` on the same resource.

Use `list_voices` and `get_voice` to inspect account-owned voice resources. Only `trained` voices can be submitted for speech generation; a returned `voice_id` is a best-effort reference and may stop working later. Update, delete, revoke, or voice-library management methods are not provided; voice retention is not promised.

The output defaults to MP3. Select WAV with `output_format`; `bitrate_kbps` applies only to MP3.

Model details and pricing: https://runapi.ai/models/fish-audio/s2.1-pro

Licensed under the Apache License, Version 2.0.

## Temporary voice matching

For `s1`, `s2-pro`, and `s2.1-pro`, supply at most one `references` item containing a publicly accessible HTTPS audio URL in `audio` and a nonempty, verbatim transcript in `text`. Do not send `voice_id` with a reference. Omit `references` when selecting an existing voice. The transcript is forwarded unchanged; callers must ensure it matches the audio.

For local audio or Base64, first upload through `POST /api/v1/files`, then use the returned `url` as `references[0].audio`. Keep only one sample and remove `voice_id`. The URL must remain valid while the request waits and the audio is fetched. Files URLs expire by default after one hour; storing a reference does not extend that lifetime. For longer waits, use an external HTTPS URL with enough validity. If a URL expires, upload again and submit a new request.
