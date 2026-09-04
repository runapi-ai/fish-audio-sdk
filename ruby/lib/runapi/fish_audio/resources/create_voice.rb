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
          run_hybrid(ENDPOINT, body: params, options: options, response_class: RESPONSE_CLASS)
        end

        def subscribe(options: nil, **params)
          params = compact_params(params)
          validate_contract!(CONTRACT["create-voice"], params)
          subscribe_hybrid(ENDPOINT, body: params, options: options, response_class: RESPONSE_CLASS)
        end
      end
    end
  end
end
