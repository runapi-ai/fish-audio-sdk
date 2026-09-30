<p align="center">
  <a href="https://runapi.ai"><img src="https://runapi.ai/icon.svg" height="56" alt="RunAPI"></a>
</p>

<h3 align="center"><a href="https://github.com/runapi-ai/fish-audio-sdk">Fish Audio API SDK for RunAPI</a></h3>

<p align="center">Fish Audio API SDKs for account-owned voice resources and speech generation in JavaScript, Python, Ruby, Go, Java, and PHP on RunAPI.</p>

<div align="center">

[![npm](https://img.shields.io/npm/v/@runapi.ai/fish-audio)](https://www.npmjs.com/package/@runapi.ai/fish-audio)
[![PyPI](https://img.shields.io/pypi/v/runapi-fish-audio)](https://pypi.org/project/runapi-fish-audio/)
[![RubyGems](https://img.shields.io/gem/v/runapi-fish-audio)](https://rubygems.org/gems/runapi-fish-audio)
[![Go Reference](https://pkg.go.dev/badge/github.com/runapi-ai/fish-audio-sdk/go.svg)](https://pkg.go.dev/github.com/runapi-ai/fish-audio-sdk/go)
[![Maven Central](https://img.shields.io/maven-central/v/ai.runapi/runapi-fish-audio)](https://central.sonatype.com/artifact/ai.runapi/runapi-fish-audio)
[![License](https://img.shields.io/github/license/runapi-ai/fish-audio-sdk)](https://github.com/runapi-ai/fish-audio-sdk/blob/main/LICENSE)

</div>

Create account-owned voice resources and generate RunAPI-managed MP3 or WAV speech. Use `s2.1-pro` for recommended production TTS with 83-language support and natural-language expression control. `s2-pro` remains available as a previous-generation option.

## Install

```bash
npm install @runapi.ai/fish-audio
pip install runapi-fish-audio
gem install runapi-fish-audio
go get github.com/runapi-ai/fish-audio-sdk/go@latest
```

For Java, install `ai.runapi:runapi-fish-audio:0.4.0`. The PHP package is released from the split repository at https://github.com/runapi-ai/fish-audio-php.

## Quick start

```typescript
import { FishAudioClient } from '@runapi.ai/fish-audio';

const client = new FishAudioClient();
const created = await client.createVoice.run({
  name: 'Narrator',
  source_audio_url: 'https://cdn.runapi.ai/public/samples/voice.mp3',
});
const voice = await client.getVoice.run({ voice_id: created.voice.voice_id });
if (voice.voice.state !== 'trained') throw new Error(`Voice is ${voice.voice.state}`);

const result = await client.textToSpeech.run({
  model: 's2.1-pro',
  text: 'Hello from RunAPI [excited]',
  voice_id: created.voice.voice_id,
  output_format: 'wav',
  sample_rate_hz: 44100,
});
console.log(result.audios[0].url);
```

Use `client.listVoices.run({ page_number: 1, page_size: 10 })` to list voice resources created by the current account. Voice state is `created`, `training`, `trained`, or `failed`; only `trained` voices can be submitted for speech generation. A returned `voice_id` is a best-effort reference and may stop working later.

Alternatively, pass request-scoped `references`, where each item contains base64-encoded raw audio bytes and its exact transcript. Use `references` when the request must not depend on a previously returned `voice_id`.

`output_format` defaults to `mp3`. MP3 supports `32000` or `44100` Hz and optional `bitrate_kbps` values of `64`, `128`, or `192`. WAV supports `8000`, `16000`, `24000`, `32000`, or `44100` Hz and does not accept `bitrate_kbps`.

This version does not provide update, delete, revoke, or voice-library management methods. It does not make promises about voice retention or how source audio is used for training.

## Variants

- [s1](https://runapi.ai/models/fish-audio/s1)
- [s2-pro](https://runapi.ai/models/fish-audio/s2-pro)
- [s2.1-pro](https://runapi.ai/models/fish-audio/s2.1-pro)

The returned URL points to RunAPI-managed storage. See the [Fish Audio model page](https://runapi.ai/models/fish-audio) for current pricing, limits, and usage details.

## License

Licensed under the Apache License, Version 2.0.
