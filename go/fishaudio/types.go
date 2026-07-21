// Package fishaudio provides Fish Audio speech generation through RunAPI.
package fishaudio

// ReferenceAudio is a request-scoped reference audio sample.
type ReferenceAudio struct {
	Audio string `json:"audio"`
	Text  string `json:"text"`
}

// TextToSpeechParams configures synchronous speech generation.
type TextToSpeechParams struct {
	Model      string           `json:"model" help:"required; model slug"`
	Text       string           `json:"text" help:"required; text to synthesize"`
	References []ReferenceAudio `json:"references,omitempty" help:"optional; request-scoped reference audio samples with base64 audio and exact transcripts"`
}

// Audio describes a RunAPI-managed MP3 result.
type Audio struct {
	URL       string `json:"url"`
	Format    string `json:"format"`
	MIMEType  string `json:"mime_type"`
	SizeBytes int64  `json:"size_bytes"`
}

// TextToSpeechResponse is the completed synchronous speech result.
type TextToSpeechResponse struct {
	ID     string  `json:"id"`
	Status string  `json:"status"`
	Audios []Audio `json:"audios"`
	Error  string  `json:"error,omitempty"`
}
