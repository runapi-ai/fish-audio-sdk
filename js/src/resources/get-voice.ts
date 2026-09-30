import type { HttpClient, RequestOptions } from '@runapi.ai/core';
import type { GetVoiceParams, VoiceResponse } from '../types';

const ENDPOINT = '/api/v1/fish_audio/voices';

/** Get one reusable voice owned by the current account. */
export class GetVoice {
  constructor(private readonly http: HttpClient) {}

  /** Get one account-owned reusable voice synchronously. */
  async run(params: GetVoiceParams, options?: RequestOptions): Promise<VoiceResponse> {
    const path = `${ENDPOINT}/${encodeURIComponent(params.voice_id)}`;
    return this.http.request<VoiceResponse>('GET', path, options ?? {});
  }
}
