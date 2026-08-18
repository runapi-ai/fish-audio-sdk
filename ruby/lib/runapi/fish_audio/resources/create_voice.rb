# frozen_string_literal: true

module RunApi
  module FishAudio
    module Resources
      # Creates an account-owned reusable voice from source audio.
      class CreateVoice
        include RunApi::Core::ResourceHelpers

        ENDPOINT = "/api/v1/fish_audio/voices"
        RESPONSE_CLASS = Types::VoiceResponse

        def initialize(http)
          @http = http
        end

        def run(options: nil, **params)
          params = compact_params(params)
          validate_contract!(CONTRACT["create-voice"], params)
          request(:post, ENDPOINT, body: params, options: options)
        end
      end
    end
  end
end
