import type { ActionSchema, HttpClient, RequestOptions } from '@runapi.ai/core';
import { compactParams, validateParams } from '@runapi.ai/core';
import { contract } from '../contract_gen';
import type { CreateVoiceParams, VoiceResponse } from '../types';

const ENDPOINT = '/api/v1/fish_audio/voices';

/** Create an account-owned reusable voice from source audio. */
export class CreateVoice {
  constructor(private readonly http: HttpClient) {}

  /** Create a reusable voice synchronously. */
  async run(params: CreateVoiceParams, options?: RequestOptions): Promise<VoiceResponse> {
    const body = compactParams(params);
    validateParams(contract['create-voice'] as ActionSchema, body as Record<string, unknown>);
    return this.http.request<VoiceResponse>('POST', ENDPOINT, { body, ...options });
  }
}
