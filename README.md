<p align="center">
  <a href="https://runapi.ai"><img src="https://runapi.ai/icon.svg" height="56" alt="RunAPI"></a>
</p>

<h3 align="center"><a href="https://github.com/runapi-ai/fish-audio-sdk">Fish Audio API SDK for RunAPI</a></h3>

<p align="center">Fish Audio API SDKs for JavaScript, Python, Ruby, Go, Java, and PHP on RunAPI.</p>

<div align="center">

[![npm](https://img.shields.io/npm/v/@runapi.ai/fish-audio)](https://www.npmjs.com/package/@runapi.ai/fish-audio)
[![PyPI](https://img.shields.io/pypi/v/runapi-fish-audio)](https://pypi.org/project/runapi-fish-audio/)
[![RubyGems](https://img.shields.io/gem/v/runapi-fish-audio)](https://rubygems.org/gems/runapi-fish-audio)
[![Go Reference](https://pkg.go.dev/badge/github.com/runapi-ai/fish-audio-sdk/go.svg)](https://pkg.go.dev/github.com/runapi-ai/fish-audio-sdk/go)
[![Maven Central](https://img.shields.io/maven-central/v/ai.runapi/runapi-fish-audio)](https://central.sonatype.com/artifact/ai.runapi/runapi-fish-audio)
[![License](https://img.shields.io/github/license/runapi-ai/fish-audio-sdk)](https://github.com/runapi-ai/fish-audio-sdk/blob/main/LICENSE)

</div>

Generate MP3 speech from text with `s1` or `s2-pro`. RunAPI validates and stores each audio result before returning it.

## Install

```bash
npm install @runapi.ai/fish-audio
pip install runapi-fish-audio
gem install runapi-fish-audio
go get github.com/runapi-ai/fish-audio-sdk/go@latest
```

For Java, install `ai.runapi:runapi-fish-audio:0.1.0`. The PHP package is released from the split repository at https://github.com/runapi-ai/fish-audio-php.

## Quick start

```typescript
import { FishAudioClient } from '@runapi.ai/fish-audio';

const client = new FishAudioClient();
const result = await client.textToSpeech.run({ model: 's1', text: 'Hello from RunAPI' });
console.log(result.audios[0].url);
```

## Variants

- [s1](https://runapi.ai/models/fish-audio/s1)
- [s2-pro](https://runapi.ai/models/fish-audio/s2-pro)

The returned URL points to RunAPI-managed storage. See the [Fish Audio model page](https://runapi.ai/models/fish-audio) for current pricing, limits, and usage details.

## License

Licensed under the Apache License, Version 2.0.
