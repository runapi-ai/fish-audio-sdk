package fishaudio

import (
	"context"
	"encoding/json"
	"testing"

	"github.com/runapi-ai/core-sdk/go/core"
)

type stubHTTPClient struct {
	method   string
	path     string
	body     any
	response json.RawMessage
}

func (s *stubHTTPClient) Request(_ context.Context, method, path string, opts *core.HTTPRequestOptions) (json.RawMessage, error) {
	s.method = method
	s.path = path
	if opts != nil {
		s.body = opts.Body
	}
	return s.response, nil
}

func TestTextToSpeechRun(t *testing.T) {
	stub := &stubHTTPClient{response: json.RawMessage(`{"id":"task_1","status":"completed","audios":[{"url":"https://runapi.ai/audio.mp3","format":"mp3","mime_type":"audio/mpeg","size_bytes":128}]}`)}
	client := NewClientWithHTTP(stub)
	response, err := client.TextToSpeech.Run(context.Background(), TextToSpeechParams{
		Model: "s1",
		Text:  "Hello",
		References: []ReferenceAudio{{
			Audio: "UklGRg==",
			Text:  "Reference transcript",
		}},
	})
	if err != nil {
		t.Fatal(err)
	}
	if stub.method != "POST" || stub.path != textToSpeechPath {
		t.Fatalf("unexpected request: %s %s", stub.method, stub.path)
	}
	body := stub.body.(map[string]any)
	if body["model"] != "s1" || body["text"] != "Hello" {
		t.Fatalf("unexpected body: %v", body)
	}
	references := body["references"].([]any)
	reference := references[0].(map[string]any)
	if reference["audio"] != "UklGRg==" || reference["text"] != "Reference transcript" {
		t.Fatalf("unexpected references: %v", references)
	}
	if len(response.Audios) != 1 || response.Audios[0].Format != "mp3" {
		t.Fatalf("unexpected response: %+v", response)
	}
}
