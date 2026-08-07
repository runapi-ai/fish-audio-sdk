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
  /** Output audio format. Defaults to MP3. */
  output_format?: 'mp3' | 'wav';
  /** Output sample rate in hertz. */
  sample_rate_hz?: 8000 | 16000 | 24000 | 32000 | 44100;
  /** MP3 bitrate in kilobits per second. Not allowed for WAV. */
  bitrate_kbps?: 64 | 128 | 192;
  /** Optional request-scoped reference audio samples. */
  references?: ReferenceAudio[];
}

/** A RunAPI-managed audio result. */
export interface Audio {
  url: string;
  format: string;
  mime_type: string;
  size_bytes: number;
}

/** Result of a synchronous text-to-speech request. */
export interface TextToSpeechResponse extends TaskResponse {
  id: string;
  status: 'completed';
  audios: Audio[];
  error?: string;
  [key: string]: unknown;
}
import type { TaskResponse } from '@runapi.ai/core';
