<p align="center"><a href="https://github.com/runapi-ai/fish-audio"><h3 align="center">Fish Audio API Skill for RunAPI</h3></a></p>

<p align="center">Create account-owned voice resources, attempt to reuse their IDs, or generate MP3/WAV speech through RunAPI from Claude Code, Codex, Gemini CLI, Cursor, and other agents.</p>

Requests may include a single HTTPS reference audio URL and exact transcripts for request-scoped voice matching.

<div align="center">

[![skills.sh](https://www.skills.sh/b/runapi-ai/fish-audio)](https://www.skills.sh/runapi-ai/fish-audio/fish-audio)
[![ClawHub](https://img.shields.io/badge/ClawHub-runapi--fish--audio-111827)](https://clawhub.ai/runapi-ai/runapi-fish-audio)
[![License](https://img.shields.io/github/license/runapi-ai/fish-audio)](https://github.com/runapi-ai/fish-audio/blob/main/LICENSE)

</div>

## Install

```bash
npx skills add runapi-ai/fish-audio -g
```

Or paste this prompt to your AI agent:

```text
Install the fish-audio skill for me:

1. Clone https://github.com/runapi-ai/fish-audio
2. Copy the skills/fish-audio/ directory into your user-level skills directory for Claude Code, Codex, or Gemini CLI.
3. Verify that skills/fish-audio/SKILL.md is present.
4. Confirm the install path when done.
```

## Variants

- [s1](https://runapi.ai/models/fish-audio/s1)
- [s2-pro](https://runapi.ai/models/fish-audio/s2-pro)
- [s2.1-pro](https://runapi.ai/models/fish-audio/s2.1-pro)

The API and SDKs support creating, listing, and getting account-owned voice resources. Only `trained` voices can be submitted for speech generation; a returned `voice_id` is a best-effort reference and may stop working later. Request-scoped `references` remain available when a request must not depend on prior reuse. This version does not provide update, delete, revoke, or voice-library management methods, and it does not promise voice retention.

## License

Licensed under the Apache License, Version 2.0.

## Temporary voice matching

For `s1`, `s2-pro`, and `s2.1-pro`, supply at most one `references` item containing a publicly accessible HTTPS audio URL in `audio` and a nonempty, verbatim transcript in `text`. Do not send `voice_id` with a reference. Omit `references` when selecting an existing voice. The transcript is forwarded unchanged; callers must ensure it matches the audio.

For local audio or Base64, first upload through `POST /api/v1/files`, then use the returned `url` as `references[0].audio`. Keep only one sample and remove `voice_id`. The URL must remain valid while the request waits and the audio is fetched. Files URLs expire by default after one hour; storing a reference does not extend that lifetime. For longer waits, use an external HTTPS URL with enough validity. If a URL expires, upload again and submit a new request.
