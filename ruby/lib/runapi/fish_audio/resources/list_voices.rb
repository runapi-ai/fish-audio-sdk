# frozen_string_literal: true

module RunApi
  module FishAudio
    module Resources
      # Lists reusable voices owned by the current account.
      class ListVoices
        include RunApi::Core::ResourceHelpers

        ENDPOINT = "/api/v1/fish_audio/voices"
        RESPONSE_CLASS = Types::VoicesResponse

        def initialize(http)
          @http = http
        end

        def run(options: nil, **params)
          params = compact_params(params)
          validate_contract!(CONTRACT["list-voices"], params)
          query = URI.encode_www_form(params)
          path = query.empty? ? ENDPOINT : "#{ENDPOINT}?#{query}"
          request(:get, path, options: options)
        end
      end
    end
  end
end
