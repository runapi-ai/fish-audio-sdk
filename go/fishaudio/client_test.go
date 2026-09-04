package fishaudio

import (
	"context"
	"encoding/json"
	"net/http"
	"net/http/httptest"
	"testing"
	"time"

	"github.com/runapi-ai/core-sdk/go/core"
	"github.com/runapi-ai/core-sdk/go/option"
)

type stubHTTPClient struct {
	method   string
	path     string
	body     any
	query    map[string]string
	response json.RawMessage
}

func (s *stubHTTPClient) Request(_ context.Context, method, path string, opts *core.HTTPRequestOptions) (json.RawMessage, error) {
	s.method = method
	s.path = path
	if opts != nil {
		s.body = opts.Body
		s.query = opts.Query
	}
	return s.response, nil
}

func (s *stubHTTPClient) RequestWithResponse(ctx context.Context, method, path string, opts *core.HTTPRequestOptions) (*core.HTTPResponse, error) {
	payload, err := s.Request(ctx, method, path, opts)
	if err != nil {
		return nil, err
	}
	return &core.HTTPResponse{Body: payload, StatusCode: http.StatusOK, Header: make(http.Header)}, nil
}

func TestCreateVoiceRunFollowsAcceptedTaskLocation(t *testing.T) {
	requests := 0
	server := httptest.NewServer(http.HandlerFunc(func(w http.ResponseWriter, r *http.Request) {
		requests++
		switch requests {
		case 1:
			if r.Method != http.MethodPost || r.URL.Path != voicesPath || r.Header.Get("Idempotency-Key") == "" {
				t.Fatalf("unexpected create request: %s %s", r.Method, r.URL.Path)
			}
			w.Header().Set("Location", "/api/v1/tasks/voice_task")
			w.Header().Set("Retry-After", "0")
			w.WriteHeader(http.StatusAccepted)
			_, _ = w.Write([]byte(`{"id":"voice_task","status":"processing"}`))
		case 2:
			_, _ = w.Write([]byte(`{"id":"voice_task","status":"completed","response":{"status":200,"content_type":"application/json; charset=utf-8","headers":{},"body":{"voice":{"voice_id":"voice_1","name":"Narrator","state":"training"}}}}`))
		default:
			t.Fatalf("unexpected request %d", requests)
		}
	}))
	defer server.Close()

	client, err := NewClient(option.WithAPIKey("test-key"), option.WithBaseURL(server.URL))
	if err != nil {
		t.Fatal(err)
	}
	result, err := client.CreateVoice.Run(context.Background(), CreateVoiceParams{Name: "Narrator", SourceAudioURL: "https://cdn.runapi.ai/narrator.mp3"}, option.WithPollInterval(time.Millisecond))
	if err != nil {
		t.Fatal(err)
	}
	if result.Voice.VoiceID != "voice_1" {
		t.Fatalf("unexpected terminal result: %#v", result)
	}
}

func TestCreateVoiceRun(t *testing.T) {
	stub := &stubHTTPClient{response: json.RawMessage(`{"voice":{"voice_id":"voice_1","name":"Narrator","state":"training"},"billing":{"reservation":null,"settlement":{"charged_amount_cents":0,"amount_micro_cents":0},"refund":null}}`)}
	client := NewClientWithHTTP(stub)
	response, err := client.CreateVoice.Run(context.Background(), CreateVoiceParams{Name: "Narrator", SourceAudioURL: "https://cdn.runapi.ai/narrator.mp3"})
	if err != nil {
		t.Fatal(err)
	}
	if stub.method != "POST" || stub.path != voicesPath {
		t.Fatalf("unexpected request: %s %s", stub.method, stub.path)
	}
	body := stub.body.(map[string]any)
	if body["name"] != "Narrator" || body["source_audio_url"] != "https://cdn.runapi.ai/narrator.mp3" {
		t.Fatalf("unexpected body: %v", body)
	}
	if response.Voice.VoiceID != "voice_1" || response.Voice.State != "training" {
		t.Fatalf("unexpected response: %+v", response)
	}
	if response.Billing == nil || response.Billing.Settlement == nil {
		t.Fatalf("expected billing facts: %#v", response.Billing)
	}
}

func TestListVoicesRun(t *testing.T) {
	stub := &stubHTTPClient{response: json.RawMessage(`{"voices":[{"voice_id":"voice_1","name":"Narrator","state":"trained"}],"total":1,"page_number":2,"page_size":25,"billing":{"reservation":null,"settlement":{"charged_amount_cents":0,"amount_micro_cents":0},"refund":null}}`)}
	client := NewClientWithHTTP(stub)
	response, err := client.ListVoices.Run(context.Background(), ListVoicesParams{PageNumber: 2, PageSize: 25})
	if err != nil {
		t.Fatal(err)
	}
	if stub.method != "GET" || stub.path != voicesPath {
		t.Fatalf("unexpected request: %s %s", stub.method, stub.path)
	}
	if stub.body != nil || stub.query["page_number"] != "2" || stub.query["page_size"] != "25" {
		t.Fatalf("unexpected request params: body=%v query=%v", stub.body, stub.query)
	}
	if response.Total != 1 || len(response.Voices) != 1 || response.Voices[0].VoiceID != "voice_1" {
		t.Fatalf("unexpected response: %+v", response)
	}
	if response.Billing == nil || response.Billing.Settlement == nil {
		t.Fatalf("expected billing facts: %#v", response.Billing)
	}
}

func TestGetVoiceRun(t *testing.T) {
	stub := &stubHTTPClient{response: json.RawMessage(`{"voice":{"voice_id":"voice/1","name":"Narrator","state":"trained"},"billing":{"reservation":null,"settlement":{"charged_amount_cents":0,"amount_micro_cents":0},"refund":null}}`)}
	client := NewClientWithHTTP(stub)
	response, err := client.GetVoice.Run(context.Background(), GetVoiceParams{VoiceID: "voice/1"})
	if err != nil {
		t.Fatal(err)
	}
	if stub.method != "GET" || stub.path != voicesPath+"/voice%2F1" {
		t.Fatalf("unexpected request: %s %s", stub.method, stub.path)
	}
	if stub.body != nil || response.Voice.State != "trained" {
		t.Fatalf("unexpected response: %+v", response)
	}
	if response.Billing == nil || response.Billing.Settlement == nil {
		t.Fatalf("expected billing facts: %#v", response.Billing)
	}
}

func TestTextToSpeechRun(t *testing.T) {
	stub := &stubHTTPClient{response: json.RawMessage(`{"id":"task_1","status":"completed","audios":[{"url":"https://runapi.ai/audio.mp3","format":"mp3","mime_type":"audio/mpeg","size_bytes":128}],"billing":{"reservation":{"amount_cents":10},"settlement":{"charged_amount_cents":9,"amount_micro_cents":950000},"refund":{"refunded_at":"2026-07-23T00:00:00.000000Z"}}}`)}
	client := NewClientWithHTTP(stub)
	response, err := client.TextToSpeech.Run(context.Background(), TextToSpeechParams{
		Model:        "s2.1-pro",
		Text:         "Hello",
		OutputFormat: "wav",
		SampleRateHz: 24_000,
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
	if body["model"] != "s2.1-pro" || body["text"] != "Hello" || body["output_format"] != "wav" || body["sample_rate_hz"] != float64(24_000) {
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
	if response.Billing == nil || response.Billing.Reservation == nil || response.Billing.Settlement == nil || response.Billing.Refund == nil {
		t.Fatalf("expected complete billing facts: %#v", response.Billing)
	}
}

func TestTextToSpeechResponseAcceptsLegacyNullBilling(t *testing.T) {
	var response TextToSpeechResponse
	if err := json.Unmarshal([]byte(`{"id":"task_1","status":"completed","billing":null}`), &response); err != nil {
		t.Fatal(err)
	}
	if response.Billing != nil {
		t.Fatalf("expected nil billing when no facts were recorded: %#v", response.Billing)
	}
}

func TestTextToSpeechVoiceRun(t *testing.T) {
	stub := &stubHTTPClient{response: json.RawMessage(`{"id":"task_1","status":"completed","audios":[{"url":"https://runapi.ai/audio.mp3","format":"mp3","mime_type":"audio/mpeg","size_bytes":128}]}`)}
	client := NewClientWithHTTP(stub)
	_, err := client.TextToSpeech.Run(context.Background(), TextToSpeechParams{Model: "s1", Text: "Hello", VoiceID: "voice_1"})
	if err != nil {
		t.Fatal(err)
	}
	body := stub.body.(map[string]any)
	if body["voice_id"] != "voice_1" {
		t.Fatalf("unexpected body: %v", body)
	}
}
