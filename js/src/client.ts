import { BaseClient, type ClientOptions } from '@runapi.ai/core';
import { CreateVoice } from './resources/create-voice';
import { GetVoice } from './resources/get-voice';
import { ListVoices } from './resources/list-voices';
import { TextToSpeech } from './resources/text-to-speech';

/** Fish Audio reusable voice and speech generation client. */
export class FishAudioClient extends BaseClient {
  /** Generate speech from text. */
  public readonly textToSpeech: TextToSpeech;
  /** Create an account-owned reusable voice. */
  public readonly createVoice: CreateVoice;
  /** List reusable voices owned by the current account. */
  public readonly listVoices: ListVoices;
  /** Get one reusable voice owned by the current account. */
  public readonly getVoice: GetVoice;

  constructor(options: ClientOptions = {}) {
    super(options);
    this.textToSpeech = new TextToSpeech(this.http);
    this.createVoice = new CreateVoice(this.http);
    this.listVoices = new ListVoices(this.http);
    this.getVoice = new GetVoice(this.http);
  }
}
