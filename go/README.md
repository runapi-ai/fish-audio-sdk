# Fish Audio Go SDK for RunAPI

Install `github.com/runapi-ai/fish-audio-sdk/go` and create a `fishaudio.Client`.

```go
created, err := client.CreateVoice.Run(ctx, fishaudio.CreateVoiceParams{Name: "Narrator", SourceAudioURL: "https://cdn.runapi.ai/public/samples/voice.mp3"})
voice, err := client.GetVoice.Run(ctx, fishaudio.GetVoiceParams{VoiceID: created.Voice.VoiceID})
if voice.Voice.State != "trained" { panic("voice is not trained") }
result, err := client.TextToSpeech.Run(ctx, fishaudio.TextToSpeechParams{Model: "s1", Text: "Hello", VoiceID: created.Voice.VoiceID})
```

Use `ListVoices` and `GetVoice` to inspect account-owned voice resources. Only `trained` voices can be submitted for speech generation; a returned `voice_id` is a best-effort reference and may stop working later. Update, delete, revoke, or voice-library management methods are not provided.

Set optional `References` entries with a publicly accessible HTTPS audio URL and exact transcripts for request-scoped voice matching.

Use `s2.1-pro` for recommended production TTS. The output defaults to MP3; set `OutputFormat` to `wav` and select `SampleRateHz` when WAV is required. `BitrateKbps` applies only to MP3.

Model details and pricing: https://runapi.ai/models/fish-audio/s2.1-pro

Licensed under the Apache License, Version 2.0.

## Temporary voice matching

For `s1`, `s2-pro`, and `s2.1-pro`, supply at most one `references` item containing a publicly accessible HTTPS audio URL in `audio` and a nonempty, verbatim transcript in `text`. Do not send `voice_id` with a reference. Omit `references` when selecting an existing voice. The transcript is forwarded unchanged; callers must ensure it matches the audio.

For local audio or Base64, first upload through `POST /api/v1/files`, then use the returned `url` as `references[0].audio`. Keep only one sample and remove `voice_id`. The URL must remain valid while the request waits and the audio is fetched. Files URLs expire by default after one hour; storing a reference does not extend that lifetime. For longer waits, use an external HTTPS URL with enough validity. If a URL expires, upload again and submit a new request.
