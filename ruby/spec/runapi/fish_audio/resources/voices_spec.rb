# frozen_string_literal: true

require "spec_helper"

RSpec.describe "Fish Audio reusable voice resources" do
  let(:http) { instance_double(RunApi::Core::HttpClient) }

  it "creates an account-owned reusable voice" do
    params = {name: "Narrator", source_audio_url: "https://cdn.runapi.ai/narrator.mp3"}
    expect(http).to receive(:request).with(:post, "/api/v1/fish_audio/voices", body: params, options: anything)
      .and_return(
        "voice" => {"voice_id" => "voice_1", "name" => "Narrator", "state" => "training"}
      )

    result = RunApi::FishAudio::Resources::CreateVoice.new(http).run(**params)

    expect(result).to be_a(RunApi::FishAudio::Types::VoiceResponse)
    expect(result.voice.state).to eq("training")
  end

  it "lists account-owned reusable voices with pagination" do
    expect(http).to receive(:request).with(:get, "/api/v1/fish_audio/voices?page_number=2&page_size=25")
      .and_return(
        "voices" => [{"voice_id" => "voice_1", "name" => "Narrator", "state" => "trained"}],
        "total" => 1,
        "page_number" => 2,
        "page_size" => 25
      )

    result = RunApi::FishAudio::Resources::ListVoices.new(http).run(page_number: 2, page_size: 25)

    expect(result).to be_a(RunApi::FishAudio::Types::VoicesResponse)
    expect(result.voices.first.voice_id).to eq("voice_1")
  end

  it "gets one account-owned reusable voice" do
    expect(http).to receive(:request).with(:get, "/api/v1/fish_audio/voices/voice%2F1")
      .and_return(
        "voice" => {"voice_id" => "voice/1", "name" => "Narrator", "state" => "trained"}
      )

    result = RunApi::FishAudio::Resources::GetVoice.new(http).run(voice_id: "voice/1")

    expect(result).to be_a(RunApi::FishAudio::Types::VoiceResponse)
    expect(result.voice.state).to eq("trained")
  end
end
