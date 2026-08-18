// Package fishaudio provides Fish Audio reusable voices and speech generation through RunAPI.
package fishaudio

import "github.com/runapi-ai/core-sdk/go/core"

// ReferenceAudio is a request-scoped reference audio sample.
type ReferenceAudio struct {
	Audio string `json:"audio"`
	Text  string `json:"text"`
}

// TextToSpeechParams configures synchronous speech generation.
type TextToSpeechParams struct {
	Model        string           `json:"model" help:"required; model slug"`
	Text         string           `json:"text" help:"required; text to synthesize"`
	OutputFormat string           `json:"output_format,omitempty" help:"optional; output audio format (mp3 or wav); defaults to mp3"`
	SampleRateHz int              `json:"sample_rate_hz,omitempty" help:"optional; output sample rate in hertz"`
	BitrateKbps  int              `json:"bitrate_kbps,omitempty" help:"optional; MP3 bitrate in kilobits per second; not allowed for WAV"`
	References   []ReferenceAudio `json:"references,omitempty" help:"optional; request-scoped reference audio samples with base64 audio and exact transcripts"`
	VoiceID      string           `json:"voice_id,omitempty" help:"optional; reusable voice ID returned by create-voice"`
}

// CreateVoiceParams configures account-owned reusable voice creation.
type CreateVoiceParams struct {
	Name           string `json:"name" help:"required; voice name"`
	SourceAudioURL string `json:"source_audio_url" help:"required; source audio URL"`
}

// ListVoicesParams configures account-owned voice pagination.
type ListVoicesParams struct {
	PageNumber int `json:"page_number,omitempty" help:"optional; page number; minimum 1; default 1"`
	PageSize   int `json:"page_size,omitempty" help:"optional; page size; range 1..100; default 10"`
}

// GetVoiceParams identifies an account-owned reusable voice.
type GetVoiceParams struct {
	VoiceID string `json:"voice_id" help:"required; reusable voice ID"`
}

// Voice describes an account-owned reusable Fish Audio voice.
type Voice struct {
	VoiceID string `json:"voice_id"`
	Name    string `json:"name,omitempty"`
	State   string `json:"state"`
}

// VoiceResponse wraps one reusable voice.
type VoiceResponse struct {
	core.TaskBillingFacts
	Voice Voice `json:"voice"`
}

// VoicesResponse lists account-owned reusable voices.
type VoicesResponse struct {
	core.TaskBillingFacts
	Voices     []Voice `json:"voices"`
	Total      int     `json:"total"`
	PageNumber int     `json:"page_number"`
	PageSize   int     `json:"page_size"`
}

// Audio describes a RunAPI-managed audio result.
type Audio struct {
	URL       string `json:"url"`
	Format    string `json:"format"`
	MIMEType  string `json:"mime_type"`
	SizeBytes int64  `json:"size_bytes"`
}

// TextToSpeechResponse is the completed synchronous speech result.
type TextToSpeechResponse struct {
	core.TaskBillingFacts
	ID     string  `json:"id"`
	Status string  `json:"status"`
	Audios []Audio `json:"audios"`
	Error  string  `json:"error,omitempty"`
}
