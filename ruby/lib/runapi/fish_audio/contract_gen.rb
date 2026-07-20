# frozen_string_literal: true

module RunApi
  module FishAudio
    CONTRACT = {
      "text-to-speech" => {
        "models" => ["s1", "s2-pro"],
        "fields_by_model" => {
          "s1" => {
            "text" => {
              "required" => true
            }
          },
          "s2-pro" => {
            "text" => {
              "required" => true
            }
          }
        }
      }
    }.freeze
  end
end
