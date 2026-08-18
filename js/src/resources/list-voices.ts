import type { ActionSchema, HttpClient, QueryParams, RequestOptions } from '@runapi.ai/core';
import { compactParams, validateParams } from '@runapi.ai/core';
import { contract } from '../contract_gen';
import type { ListVoicesParams, VoicesResponse } from '../types';

const ENDPOINT = '/api/v1/fish_audio/voices';

/** List reusable voices owned by the current account. */
export class ListVoices {
  constructor(private readonly http: HttpClient) {}

  /** List account-owned reusable voices synchronously. */
  async run(params: ListVoicesParams = {}, options?: RequestOptions): Promise<VoicesResponse> {
    const query = compactParams(params);
    validateParams(contract['list-voices'] as ActionSchema, query as Record<string, unknown>);
    return this.http.request<VoicesResponse>('GET', ENDPOINT, {
      query: query as QueryParams,
      ...options,
    });
  }
}
