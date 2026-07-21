import { beforeEach, describe, expect, it, vi } from 'vitest';
import type { HttpClient } from '@runapi.ai/core';
import { TextToSpeech } from '../../src/resources/text-to-speech';

describe('Fish Audio resources', () => {
  const mockHttp: HttpClient = { request: vi.fn() };

  beforeEach(() => vi.clearAllMocks());

  it('posts text-to-speech params and decodes managed audio', async () => {
    vi.mocked(mockHttp.request).mockResolvedValueOnce({
      id: 'task_1',
      status: 'completed',
      audios: [{ url: 'https://runapi.ai/rails/active_storage/audio.mp3', format: 'mp3', mime_type: 'audio/mpeg', size_bytes: 128 }],
    });
    const resource = new TextToSpeech(mockHttp);

    const result = await resource.run({
      model: 's1',
      text: 'Hello from RunAPI',
      references: [{ audio: 'UklGRg==', text: 'Reference transcript' }],
    });

    expect(mockHttp.request).toHaveBeenCalledWith('POST', '/api/v1/fish_audio/text_to_speech', {
      body: {
        model: 's1',
        text: 'Hello from RunAPI',
        references: [{ audio: 'UklGRg==', text: 'Reference transcript' }],
      },
    });
    expect(result.audios[0]?.format).toBe('mp3');
  });
});
