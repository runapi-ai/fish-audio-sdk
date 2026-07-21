# frozen_string_literal: true

Dir.chdir(__dir__) do

  Gem::Specification.new do |spec|
    spec.name = "runapi-fish-audio"
    spec.version = "0.1.1"
    spec.metadata["runapi_slug"] = "fish-audio"
    spec.authors = ["RunAPI"]
    spec.email = ["contact@runapi.ai"]
    spec.summary = "Fish Audio Ruby SDK for RunAPI"
    spec.description = "Install `runapi-fish-audio`, create `RunApi::FishAudio::Client`, and call `client.text_to_speech.run(model: \"s1\", text: \"Hello\")`."
    spec.homepage = "https://runapi.ai/models/fish-audio"
    spec.license = "Apache-2.0"
    spec.required_ruby_version = ">= 3.1.0"
    spec.metadata["homepage_uri"] = "https://runapi.ai/models/fish-audio"
    spec.metadata["documentation_uri"] = "https://github.com/runapi-ai/fish-audio-sdk/blob/main/ruby/README.md"
    spec.metadata["source_code_uri"] = "https://github.com/runapi-ai/fish-audio-sdk"
    spec.metadata["bug_tracker_uri"] = "https://github.com/runapi-ai/fish-audio-sdk/issues"
    spec.metadata["changelog_uri"] = "https://github.com/runapi-ai/fish-audio-sdk/blob/main/CHANGELOG.md"

    spec.files = Dir.glob("lib/**/*") + %w[LICENSE README.md]
    spec.extra_rdoc_files = ["README.md"]
        spec.require_paths = ["lib"]
    spec.add_dependency "runapi-core", "~> 0.2.14"
  end
end
