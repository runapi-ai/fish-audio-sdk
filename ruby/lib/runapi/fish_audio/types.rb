# frozen_string_literal: true

module RunApi
  module FishAudio
    module Types
      # An account-owned reusable voice.
      class Voice < RunApi::Core::BaseModel
        required :voice_id, String
        optional :name, String
        required :state, String
      end

      # Response containing one reusable voice.
      class VoiceResponse < RunApi::Core::BaseModel
        required :voice, -> { Voice }
        required :billing, RunApi::Core::TaskBillingFacts
      end

      # Paginated response containing account-owned reusable voices.
      class VoicesResponse < RunApi::Core::BaseModel
        required :voices, [-> { Voice }]
        required :total, Integer
        required :page_number, Integer
        required :page_size, Integer
        required :billing, RunApi::Core::TaskBillingFacts
      end

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
