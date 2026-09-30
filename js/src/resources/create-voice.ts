import type { HttpClient, RequestOptions } from '@runapi.ai/core';
import { compactParams } from '@runapi.ai/core';
import type { CreateVoiceParams, VoiceResponse } from '../types';

const ENDPOINT = '/api/v1/fish_audio/voices';

/** Create an account-owned reusable voice from source audio. */
export class CreateVoice {
  constructor(private readonly http: HttpClient) {}

  /** Create a reusable voice synchronously. */
  async run(params: CreateVoiceParams, options?: RequestOptions): Promise<VoiceResponse> {
    const body = compactParams(params);
    return this.http.request<VoiceResponse>('POST', ENDPOINT, { body, ...options });
  }
}
