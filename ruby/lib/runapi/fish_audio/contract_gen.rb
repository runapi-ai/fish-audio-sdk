# frozen_string_literal: true

module RunApi
  module FishAudio
    CONTRACT = {
      "text-to-speech" => {
        "models" => ["s1", "s2-pro", "s2.1-pro"],
        "fields_by_model" => {
          "s1" => {
            "bitrate_kbps" => {
              "enum" => [64, 128, 192],
              "type" => "integer"
            },
            "model" => {
              "required" => true
            },
            "output_format" => {
              "enum" => ["mp3", "wav"]
            },
            "sample_rate_hz" => {
              "enum" => [8000, 16000, 24000, 32000, 44100],
              "type" => "integer"
            },
            "text" => {
              "required" => true
            }
          },
          "s2-pro" => {
            "bitrate_kbps" => {
              "enum" => [64, 128, 192],
              "type" => "integer"
            },
            "model" => {
              "required" => true
            },
            "output_format" => {
              "enum" => ["mp3", "wav"]
            },
            "sample_rate_hz" => {
              "enum" => [8000, 16000, 24000, 32000, 44100],
              "type" => "integer"
            },
            "text" => {
              "required" => true
            }
          },
          "s2.1-pro" => {
            "bitrate_kbps" => {
              "enum" => [64, 128, 192],
              "type" => "integer"
            },
            "model" => {
              "required" => true
            },
            "output_format" => {
              "enum" => ["mp3", "wav"]
            },
            "sample_rate_hz" => {
              "enum" => [8000, 16000, 24000, 32000, 44100],
              "type" => "integer"
            },
            "text" => {
              "required" => true
            }
          }
        },
        "rules" => [{
          "when" => {
            "output_format" => "wav"
          },
          "forbidden" => ["bitrate_kbps"]
        }, {
          "enum" => {
            "sample_rate_hz" => [32000, 44100]
          },
          "when" => {
            "output_format" => "mp3"
          }
        }, {
          "enum" => {
            "sample_rate_hz" => [32000, 44100]
          },
          "when" => {
            "output_format" => {
              "present" => false
            }
          }
        }]
      }
    }.freeze
  end
end
