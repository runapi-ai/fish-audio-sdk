/** A request-scoped reference audio sample. */
export interface ReferenceAudio {
  /** Base64-encoded raw audio bytes. */
  audio: string;
  /** Exact transcript of the reference audio. */
  text: string;
}

/** Parameters for Fish Audio text-to-speech generation. */
export interface TextToSpeechParams {
  /** Model slug accepted by the Fish Audio catalog. */
  model: string;
  /** Text to synthesize. */
  text: string;
  /** Optional request-scoped reference audio samples. */
  references?: ReferenceAudio[];
}

/** A RunAPI-managed MP3 audio result. */
export interface Audio {
  url: string;
  format: string;
  mime_type: string;
  size_bytes: number;
}

/** Result of a synchronous text-to-speech request. */
export interface TextToSpeechResponse {
  id: string;
  status: 'completed';
  audios: Audio[];
  error?: string;
  [key: string]: unknown;
}
