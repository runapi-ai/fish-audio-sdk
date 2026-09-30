# Changelog

## [js/v0.4.0](https://github.com/runapi-ai/fish-audio-sdk/releases/tag/js%2Fv0.4.0), [ruby/v0.5.0](https://github.com/runapi-ai/fish-audio-sdk/releases/tag/ruby%2Fv0.5.0), [go/v0.5.0](https://github.com/runapi-ai/fish-audio-sdk/releases/tag/go%2Fv0.5.0), [python/v0.6.0](https://github.com/runapi-ai/fish-audio-sdk/releases/tag/python%2Fv0.6.0), [java/v0.4.0](https://github.com/runapi-ai/fish-audio-sdk/releases/tag/java%2Fv0.4.0) - 2026-09-30

### Changed
- Send request parameters to the service without local validation. Model ids and parameter values the service supports work without an SDK upgrade; static types and enum constants remain for completion.
  Migration: Invalid parameters now fail with the validation error built from the service's 400 response, including its status and message, instead of a validation error raised locally before the request. The error type is unchanged: `ValidationError` in JavaScript, Python, and Ruby, `ValidationException` in Java and PHP, and `ErrValidation` in Go.


## [js/v0.3.1](https://github.com/runapi-ai/fish-audio-sdk/releases/tag/js%2Fv0.3.1), [ruby/v0.4.1](https://github.com/runapi-ai/fish-audio-sdk/releases/tag/ruby%2Fv0.4.1), [go/v0.4.1](https://github.com/runapi-ai/fish-audio-sdk/releases/tag/go%2Fv0.4.1), [python/v0.5.1](https://github.com/runapi-ai/fish-audio-sdk/releases/tag/python%2Fv0.5.1), [java/v0.3.1](https://github.com/runapi-ai/fish-audio-sdk/releases/tag/java%2Fv0.3.1) - 2026-09-28

### Added
- Return usage.cost as a float USD amount on completed async Task query and webhook envelopes.

### Removed
- Remove the public Task billing object from Task envelopes.
  Migration: Read usage.cost on completed Task envelopes. Create, processing, and failed envelopes omit usage.


## [python/v0.5.0](https://github.com/runapi-ai/fish-audio-sdk/releases/tag/python%2Fv0.5.0) - 2026-09-04

### Added
- Automatically follow accepted Task results for speech generation and voice creation.

## [go/v0.4.0](https://github.com/runapi-ai/fish-audio-sdk/releases/tag/go%2Fv0.4.0) - 2026-09-04

### Added
- Add Create, Subscribe, and automatic Run support for speech generation and voice creation when a Task is accepted.

## [ruby/v0.4.0](https://github.com/runapi-ai/fish-audio-sdk/releases/tag/ruby%2Fv0.4.0) - 2026-09-04

### Added
- Add run and subscribe support for hybrid Task responses.


## [js/v0.3.0](https://github.com/runapi-ai/fish-audio-sdk/releases/tag/js%2Fv0.3.0), [go/v0.3.0](https://github.com/runapi-ai/fish-audio-sdk/releases/tag/go%2Fv0.3.0), [python/v0.4.0](https://github.com/runapi-ai/fish-audio-sdk/releases/tag/python%2Fv0.4.0), [java/v0.3.0](https://github.com/runapi-ai/fish-audio-sdk/releases/tag/java%2Fv0.3.0) - 2026-08-18

### Added
- Add typed create, list, and get resources for account-owned reusable voices.
- Accept trained account-owned voice IDs in s1, s2-pro, and s2.1-pro text-to-speech requests.

## [ruby/v0.3.0](https://github.com/runapi-ai/fish-audio-sdk/releases/tag/ruby%2Fv0.3.0) - 2026-08-18

### Added
- Add typed create, list, and get resources for account-owned reusable voices.
- Accept trained account-owned voice IDs in s1, s2-pro, and s2.1-pro text-to-speech requests.

### Changed
- Allow Ruby clients to install the core SDK release that adds persistent Files and multipart Uploads alongside this model SDK.


## [js/v0.2.0](https://github.com/runapi-ai/fish-audio-sdk/releases/tag/js%2Fv0.2.0), [ruby/v0.2.0](https://github.com/runapi-ai/fish-audio-sdk/releases/tag/ruby%2Fv0.2.0), [go/v0.2.0](https://github.com/runapi-ai/fish-audio-sdk/releases/tag/go%2Fv0.2.0), [python/v0.3.0](https://github.com/runapi-ai/fish-audio-sdk/releases/tag/python%2Fv0.3.0), [java/v0.2.0](https://github.com/runapi-ai/fish-audio-sdk/releases/tag/java%2Fv0.2.0) - 2026-08-07

### Added
- Add s2.1-pro with managed MP3 and WAV output controls.


## [js/v0.1.3](https://github.com/runapi-ai/fish-audio-sdk/releases/tag/js%2Fv0.1.3), [ruby/v0.1.3](https://github.com/runapi-ai/fish-audio-sdk/releases/tag/ruby%2Fv0.1.3), [go/v0.1.3](https://github.com/runapi-ai/fish-audio-sdk/releases/tag/go%2Fv0.1.3), [python/v0.2.1](https://github.com/runapi-ai/fish-audio-sdk/releases/tag/python%2Fv0.2.1) - 2026-07-28

### Fixed
- Validate the required model before sending text-to-speech requests.


## [java/v0.1.2](https://github.com/runapi-ai/fish-audio-sdk/releases/tag/java%2Fv0.1.2) - 2026-07-28

### Added
- Decode typed Task Billing Facts on synchronous text-to-speech responses.

## [go/v0.1.2](https://github.com/runapi-ai/fish-audio-sdk/releases/tag/go%2Fv0.1.2) - 2026-07-28

### Added
- Expose persisted billing facts on task responses.

## [js/v0.1.2](https://github.com/runapi-ai/fish-audio-sdk/releases/tag/js%2Fv0.1.2), [ruby/v0.1.2](https://github.com/runapi-ai/fish-audio-sdk/releases/tag/ruby%2Fv0.1.2) - 2026-07-28

### Added
- Type task billing facts on text-to-speech responses.


## [python/v0.2.0](https://github.com/runapi-ai/fish-audio-sdk/releases/tag/python%2Fv0.2.0) - 2026-07-24

### Added
- Expose shared Files, Account, and Pricing resources plus typed Task Billing Facts through the Provider Client.


## [js/v0.1.1](https://github.com/runapi-ai/fish-audio-sdk/releases/tag/js%2Fv0.1.1), [ruby/v0.1.1](https://github.com/runapi-ai/fish-audio-sdk/releases/tag/ruby%2Fv0.1.1), [go/v0.1.1](https://github.com/runapi-ai/fish-audio-sdk/releases/tag/go%2Fv0.1.1), [python/v0.1.1](https://github.com/runapi-ai/fish-audio-sdk/releases/tag/python%2Fv0.1.1), [java/v0.1.1](https://github.com/runapi-ai/fish-audio-sdk/releases/tag/java%2Fv0.1.1) - 2026-07-21

### Added
- Accept optional inline reference audio samples with exact transcripts for text-to-speech requests.


## [js/v0.1.0](https://github.com/runapi-ai/fish-audio-sdk/releases/tag/js%2Fv0.1.0), [ruby/v0.1.0](https://github.com/runapi-ai/fish-audio-sdk/releases/tag/ruby%2Fv0.1.0), [go/v0.1.0](https://github.com/runapi-ai/fish-audio-sdk/releases/tag/go%2Fv0.1.0), [python/v0.1.0](https://github.com/runapi-ai/fish-audio-sdk/releases/tag/python%2Fv0.1.0), [java/v0.1.0](https://github.com/runapi-ai/fish-audio-sdk/releases/tag/java%2Fv0.1.0) - 2026-07-20

### Added
- Add synchronous text-to-speech clients with typed managed MP3 responses.
- Support s1 and s2-pro through the public text-only contract.
