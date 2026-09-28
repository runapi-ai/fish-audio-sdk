import { beforeEach, describe, expect, it, vi } from 'vitest';
import type { HttpClient } from '@runapi.ai/core';
import { CreateVoice } from '../../src/resources/create-voice';
import { GetVoice } from '../../src/resources/get-voice';
import { ListVoices } from '../../src/resources/list-voices';
import { TextToSpeech } from '../../src/resources/text-to-speech';

describe('Fish Audio resources', () => {
  const mockHttp: HttpClient = { request: vi.fn() };

  beforeEach(() => vi.clearAllMocks());

  it('creates an account-owned reusable voice', async () => {
    vi.mocked(mockHttp.request).mockResolvedValueOnce({
      voice: { voice_id: 'voice_1', name: 'Narrator', state: 'training' },
    });
    const resource = new CreateVoice(mockHttp);

    const result = await resource.run({
      name: 'Narrator',
      source_audio_url: 'https://cdn.runapi.ai/narrator.mp3',
    });

    expect(mockHttp.request).toHaveBeenCalledWith('POST', '/api/v1/fish_audio/voices', {
      body: { name: 'Narrator', source_audio_url: 'https://cdn.runapi.ai/narrator.mp3' },
    });
    expect(result.voice.state).toBe('training');
    expect(result).not.toHaveProperty('billing');
    expect(result).not.toHaveProperty('usage');
  });

  it('lists account-owned reusable voices with pagination', async () => {
    vi.mocked(mockHttp.request).mockResolvedValueOnce({
      voices: [{ voice_id: 'voice_1', name: 'Narrator', state: 'trained' }],
      total: 1,
      page_number: 2,
      page_size: 25,
    });
    const resource = new ListVoices(mockHttp);

    const result = await resource.run({ page_number: 2, page_size: 25 });

    expect(mockHttp.request).toHaveBeenCalledWith('GET', '/api/v1/fish_audio/voices', {
      query: { page_number: 2, page_size: 25 },
    });
    expect(result.voices[0]?.voice_id).toBe('voice_1');
    expect(result).not.toHaveProperty('billing');
    expect(result).not.toHaveProperty('usage');
  });

  it('gets one account-owned reusable voice', async () => {
    vi.mocked(mockHttp.request).mockResolvedValueOnce({
      voice: { voice_id: 'voice/1', name: 'Narrator', state: 'trained' },
    });
    const resource = new GetVoice(mockHttp);

    const result = await resource.run({ voice_id: 'voice/1' });

    expect(mockHttp.request).toHaveBeenCalledWith('GET', '/api/v1/fish_audio/voices/voice%2F1', {});
    expect(result.voice.state).toBe('trained');
    expect(result).not.toHaveProperty('billing');
    expect(result).not.toHaveProperty('usage');
  });

  it('posts text-to-speech params and decodes managed audio', async () => {
    vi.mocked(mockHttp.request).mockResolvedValueOnce({
      id: 'task_1',
      status: 'completed',
      audios: [{ url: 'https://runapi.ai/rails/active_storage/audio.mp3', format: 'mp3', mime_type: 'audio/mpeg', size_bytes: 128 }],
      usage: { cost: 0.02 },
    });
    const resource = new TextToSpeech(mockHttp);

    const result = await resource.run({
      model: 's2.1-pro',
      text: 'Hello from RunAPI',
      output_format: 'wav',
      sample_rate_hz: 24000,
      references: [{ audio: 'UklGRg==', text: 'Reference transcript' }],
    });

    expect(mockHttp.request).toHaveBeenCalledWith('POST', '/api/v1/fish_audio/text_to_speech', {
      body: {
        model: 's2.1-pro',
        text: 'Hello from RunAPI',
        output_format: 'wav',
        sample_rate_hz: 24000,
        references: [{ audio: 'UklGRg==', text: 'Reference transcript' }],
      },
    });
    expect(result.audios[0]?.format).toBe('mp3');
    expect(result.usage?.cost).toBe(0.02);
  });

  it('posts a reusable voice id for text-to-speech', async () => {
    vi.mocked(mockHttp.request).mockResolvedValueOnce({ id: 'task_1', status: 'completed', audios: [] });
    const resource = new TextToSpeech(mockHttp);

    await resource.run({ model: 's1', text: 'Hello from RunAPI', voice_id: 'voice_1' });

    expect(mockHttp.request).toHaveBeenCalledWith('POST', '/api/v1/fish_audio/text_to_speech', {
      body: { model: 's1', text: 'Hello from RunAPI', voice_id: 'voice_1' },
    });
  });
});
