# frozen_string_literal: true

module RunApi
  module FishAudio
    # Fish Audio reusable voice and speech generation client.
    class Client < RunApi::Core::Client
      attr_reader :text_to_speech, :create_voice, :list_voices, :get_voice

      def initialize(api_key: nil, **options)
        super
        @text_to_speech = Resources::TextToSpeech.new(http)
        @create_voice = Resources::CreateVoice.new(http)
        @list_voices = Resources::ListVoices.new(http)
        @get_voice = Resources::GetVoice.new(http)
      end
    end
  end
end
