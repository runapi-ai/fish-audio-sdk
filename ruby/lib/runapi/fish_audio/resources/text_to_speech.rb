# frozen_string_literal: true

module RunApi
  module FishAudio
    module Resources
      # Generates RunAPI-managed MP3 speech from text.
      class TextToSpeech
        include RunApi::Core::ResourceHelpers

        ENDPOINT = "/api/v1/fish_audio/text_to_speech"
        RESPONSE_CLASS = Types::TextToSpeechResponse

        def initialize(http)
          @http = http
        end

        def run(options: nil, **params)
          params = compact_params(params)
          validate_contract!(CONTRACT["text-to-speech"], params)
          validate_references!(params[:references])
          request(:post, ENDPOINT, body: params, options: options)
        end

        private

        def validate_references!(references)
          return if references.nil?
          raise Core::ValidationError, "references must be an array" unless references.is_a?(Array)

          references.each_with_index do |reference, index|
            raise Core::ValidationError, "references[#{index}] must be an object" unless reference.respond_to?(:key?)

            %i[audio text].each do |field|
              value = reference[field] || reference[field.to_s]
              if value.nil? || (value.is_a?(String) && value.strip.empty?)
                raise Core::ValidationError, "references[#{index}].#{field} is required"
              end
              raise Core::ValidationError, "references[#{index}].#{field} must be a string" unless value.is_a?(String)
            end
          end
        end
      end
    end
  end
end
