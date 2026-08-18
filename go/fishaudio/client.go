package fishaudio

import (
	"context"
	"net/url"
	"strconv"

	"github.com/runapi-ai/core-sdk/go/base"
	"github.com/runapi-ai/core-sdk/go/core"
	"github.com/runapi-ai/core-sdk/go/option"
)

const textToSpeechPath = "/api/v1/fish_audio/text_to_speech"
const voicesPath = "/api/v1/fish_audio/voices"

// Client provides Fish Audio reusable voices and speech generation.
type Client struct {
	base.Base
	TextToSpeech *TextToSpeech
	CreateVoice  *CreateVoice
	ListVoices   *ListVoices
	GetVoice     *GetVoice
}

// NewClient creates a Fish Audio client with the given options.
func NewClient(opts ...option.ClientOption) (*Client, error) {
	resolved, err := option.ResolveClientOptions(opts...)
	if err != nil {
		return nil, err
	}
	httpClient, err := core.NewHTTPClient(resolved)
	if err != nil {
		return nil, err
	}
	return NewClientWithHTTP(httpClient), nil
}

// NewClientWithHTTP creates a Fish Audio client with a pre-configured transport.
func NewClientWithHTTP(httpClient core.HTTPClient) *Client {
	return &Client{
		Base:         base.New(httpClient),
		TextToSpeech: &TextToSpeech{http: httpClient},
		CreateVoice:  &CreateVoice{http: httpClient},
		ListVoices:   &ListVoices{http: httpClient},
		GetVoice:     &GetVoice{http: httpClient},
	}
}

// CreateVoice creates an account-owned reusable voice.
type CreateVoice struct{ http core.HTTPClient }

// Run creates an account-owned reusable voice synchronously.
func (r *CreateVoice) Run(ctx context.Context, params CreateVoiceParams, opts ...option.RequestOption) (*VoiceResponse, error) {
	requestOptions, _ := option.ResolveRequestOptions(opts...)
	body := core.CompactParams(params)
	if err := core.ValidateParams(contractSchema["create-voice"], body); err != nil {
		return nil, err
	}
	return core.PostJSON[VoiceResponse](ctx, r.http, voicesPath, body, requestOptions)
}

// ListVoices lists reusable voices owned by the current account.
type ListVoices struct{ http core.HTTPClient }

// Run lists account-owned reusable voices synchronously.
func (r *ListVoices) Run(ctx context.Context, params ListVoicesParams, opts ...option.RequestOption) (*VoicesResponse, error) {
	requestOptions, _ := option.ResolveRequestOptions(opts...)
	body := core.CompactParams(params)
	if err := core.ValidateParams(contractSchema["list-voices"], body); err != nil {
		return nil, err
	}
	query := make(map[string]string, len(body))
	if params.PageNumber != 0 {
		query["page_number"] = strconv.Itoa(params.PageNumber)
	}
	if params.PageSize != 0 {
		query["page_size"] = strconv.Itoa(params.PageSize)
	}
	payload, err := r.http.Request(ctx, "GET", voicesPath, &core.HTTPRequestOptions{
		Query: query, Headers: requestOptions.Headers, Request: requestOptions,
	})
	if err != nil {
		return nil, err
	}
	return core.DecodeResponse[VoicesResponse](payload)
}

// GetVoice gets one reusable voice owned by the current account.
type GetVoice struct{ http core.HTTPClient }

// Run gets one account-owned reusable voice synchronously.
func (r *GetVoice) Run(ctx context.Context, params GetVoiceParams, opts ...option.RequestOption) (*VoiceResponse, error) {
	requestOptions, _ := option.ResolveRequestOptions(opts...)
	body := core.CompactParams(params)
	if err := core.ValidateParams(contractSchema["get-voice"], body); err != nil {
		return nil, err
	}
	path := voicesPath + "/" + url.PathEscape(params.VoiceID)
	return core.GetJSON[VoiceResponse](ctx, r.http, path, requestOptions)
}

// TextToSpeech generates RunAPI-managed audio from text.
type TextToSpeech struct{ http core.HTTPClient }

// Run generates speech synchronously.
func (r *TextToSpeech) Run(ctx context.Context, params TextToSpeechParams, opts ...option.RequestOption) (*TextToSpeechResponse, error) {
	requestOptions, _ := option.ResolveRequestOptions(opts...)
	body := core.CompactParams(params)
	if err := core.ValidateParams(contractSchema["text-to-speech"], body); err != nil {
		return nil, err
	}
	return core.PostJSON[TextToSpeechResponse](ctx, r.http, textToSpeechPath, body, requestOptions)
}
