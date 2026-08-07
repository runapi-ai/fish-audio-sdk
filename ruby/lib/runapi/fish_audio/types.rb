# frozen_string_literal: true

module RunApi
  module FishAudio
    module Types
      # A RunAPI-managed audio result.
      class Audio < RunApi::Core::BaseModel
        required :url, String
        required :format, String
        required :mime_type, String
        required :size_bytes, Integer
      end

      # Completed synchronous text-to-speech response.
      class TextToSpeechResponse < RunApi::Core::TaskResponse
        required :id, String
        required :status, String
        required :audios, [-> { Audio }]
        optional :error, String
      end
    end
  end
end
