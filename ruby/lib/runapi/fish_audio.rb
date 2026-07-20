# frozen_string_literal: true

require "runapi/core"
require_relative "fish_audio/types"
require_relative "fish_audio/contract_gen"
require_relative "fish_audio/resources/text_to_speech"
require_relative "fish_audio/client"

module RunApi
  module FishAudio
    AuthenticationError = RunApi::Core::AuthenticationError
    RateLimitError = RunApi::Core::RateLimitError
    InsufficientCreditsError = RunApi::Core::InsufficientCreditsError
    NotFoundError = RunApi::Core::NotFoundError
    ValidationError = RunApi::Core::ValidationError
  end
end
