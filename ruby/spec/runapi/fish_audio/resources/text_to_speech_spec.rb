# frozen_string_literal: true

require "spec_helper"

RSpec.describe RunApi::FishAudio::Resources::TextToSpeech do
  let(:http) { instance_double(RunApi::Core::HttpClient) }
  let(:resource) { described_class.new(http) }
  let(:endpoint) { "/api/v1/fish_audio/text_to_speech" }

  it "POSTs params and decodes managed audio" do
    params = {
      model: "s1",
      text: "Hello",
      references: [{audio: "UklGRg==", text: "Reference transcript"}]
    }
    expect(http).to receive(:request).with(:post, endpoint, body: params)
      .and_return("id" => "task_1", "status" => "completed", "audios" => [{"url" => "https://runapi.ai/audio.mp3", "format" => "mp3", "mime_type" => "audio/mpeg", "size_bytes" => 128}])

    result = resource.run(**params)
    expect(result).to be_a(RunApi::FishAudio::Types::TextToSpeechResponse)
    expect(result.audios.first.format).to eq("mp3")
  end

  it "requires a transcript for every reference" do
    expect { resource.run(model: "s1", text: "Hello", references: [{audio: "UklGRg=="}]) }
      .to raise_error(RunApi::Core::ValidationError, /references\[0\]\.text is required/)
  end
end
