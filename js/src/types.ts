import type { TaskBillingFacts, TaskResponse } from '@runapi.ai/core';

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
  /** Reusable voice ID returned by `createVoice`. */
  voice_id?: string;
}

/** Parameters for creating an account-owned reusable voice. */
export interface CreateVoiceParams {
  /** Display name for the voice. */
  name: string;
  /** Publicly fetchable source audio URL. */
  source_audio_url: string;
}

/** Parameters for paginating account-owned reusable voices. */
export interface ListVoicesParams {
  /** Page number, starting at 1. */
  page_number?: number;
  /** Number of voices per page, from 1 through 100. */
  page_size?: number;
}

/** Parameters for getting one account-owned reusable voice. */
export interface GetVoiceParams {
  /** Reusable voice ID returned by `createVoice`. */
  voice_id: string;
}

/** An account-owned reusable voice. */
export interface Voice {
  voice_id: string;
  name?: string;
  state: 'created' | 'training' | 'trained' | 'failed';
}

/** Response containing one reusable voice. */
export interface VoiceResponse {
  voice: Voice;
  billing: TaskBillingFacts;
}

/** Paginated response containing account-owned reusable voices. */
export interface VoicesResponse {
  voices: Voice[];
  total: number;
  page_number: number;
  page_size: number;
  billing: TaskBillingFacts;
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
