# frozen_string_literal: true

module RunApi
  module FishAudio
    module Resources
      # Gets one reusable voice owned by the current account.
      class GetVoice
        include RunApi::Core::ResourceHelpers

        ENDPOINT = "/api/v1/fish_audio/voices"
        RESPONSE_CLASS = Types::VoiceResponse

        def initialize(http)
          @http = http
        end

        def run(voice_id:, options: nil)
          params = compact_params(voice_id: voice_id)
          validate_contract!(CONTRACT["get-voice"], params)
          path = "#{ENDPOINT}/#{URI.encode_uri_component(voice_id)}"
          request(:get, path, options: options)
        end
      end
    end
  end
end
