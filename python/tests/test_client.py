import pytest

from runapi.core import ApiResponse, config
from runapi.fish_audio import FishAudioClient
from runapi.fish_audio.types import TextToSpeechResponse, VoiceResponse, VoicesResponse


class FakeHttp:
    def __init__(self, *responses):
        self._responses = list(responses)
        self.calls = []
        self.options = []

    def request(self, method, path, body=None, options=None):
        self.calls.append((method, path, body))
        self.options.append(options)
        return self._responses.pop(0)


@pytest.fixture(autouse=True)
def reset_config(monkeypatch):
    monkeypatch.delenv("RUNAPI_API_KEY", raising=False)
    monkeypatch.setattr(config, "api_key", None)


def test_run_posts_params_and_decodes_managed_audio():
    fake = FakeHttp({"id": "task_1", "status": "completed", "usage": {"cost": 0.05}, "audios": [{"url": "https://runapi.ai/audio.mp3", "format": "mp3", "mime_type": "audio/mpeg", "size_bytes": 128}]})
    client = FishAudioClient(api_key="k", http_client=fake)

    references = [{"audio": "UklGRg==", "text": "Reference transcript"}]
    result = client.text_to_speech.run(
        model="s2.1-pro", text="Hello", output_format="wav", sample_rate_hz=24000, references=references
    )

    assert fake.calls == [(
        "post",
        "/api/v1/fish_audio/text_to_speech",
        {
            "model": "s2.1-pro",
            "text": "Hello",
            "output_format": "wav",
            "sample_rate_hz": 24000,
            "references": references},
    )]
    assert isinstance(result, TextToSpeechResponse)
    assert result.audios[0].format == "mp3"


def test_run_posts_reusable_voice_id():
    fake = FakeHttp({"id": "task_1", "status": "completed", "usage": {"cost": 0.05}, "audios": []})
    client = FishAudioClient(api_key="k", http_client=fake)

    client.text_to_speech.run(model="s1", text="Hello", voice_id="voice_1")

    assert fake.calls == [("post", "/api/v1/fish_audio/text_to_speech", {"model": "s1", "text": "Hello", "voice_id": "voice_1"})]


def test_create_voice_posts_public_params_and_decodes_voice():
    fake = FakeHttp({"voice": {"voice_id": "voice_1", "name": "Narrator", "state": "training"}})
    client = FishAudioClient(api_key="k", http_client=fake)

    result = client.create_voice.run(name="Narrator", source_audio_url="https://cdn.runapi.ai/narrator.mp3")

    assert fake.calls == [("post", "/api/v1/fish_audio/voices", {"name": "Narrator", "source_audio_url": "https://cdn.runapi.ai/narrator.mp3"})]
    assert isinstance(result, VoiceResponse)
    assert result.voice.state == "training"


@pytest.mark.parametrize(
    ("resource_name", "params", "endpoint", "terminal_body", "response_class"),
    [
        (
            "text_to_speech",
            {"model": "s1", "text": "Hello"},
            "/api/v1/fish_audio/text_to_speech",
            {"id": "task_1", "status": "completed", "usage": {"cost": 0.05}, "audios": []},
            TextToSpeechResponse,
        ),
        (
            "create_voice",
            {"name": "Narrator", "source_audio_url": "https://cdn.runapi.ai/narrator.mp3"},
            "/api/v1/fish_audio/voices",
            {
                "voice": {"voice_id": "voice_1", "name": "Narrator", "state": "training"}},
            VoiceResponse,
        )],
)
def test_hybrid_resources_follow_accepted_task_result(
    resource_name, params, endpoint, terminal_body, response_class
):
    location = "https://runapi.ai/api/v1/tasks/task_1"
    fake = FakeHttp(
        ApiResponse({"id": "task_1", "status": "processing"}, {"Location": location}, status_code=202),
        ApiResponse(
            {
                "id": "task_1",
                "status": "completed", "usage": {"cost": 0.05},
                "response": {
                    "status": 200,
                    "content_type": "application/json",
                    "headers": {},
                    "body": terminal_body}}
        ),
    )
    client = FishAudioClient(api_key="k", http_client=fake)

    result = getattr(client, resource_name).run(**params)

    assert isinstance(result, response_class)
    assert [call[:2] for call in fake.calls] == [("post", endpoint), ("get", location)]
    assert fake.options[0].headers["Idempotency-Key"]
    assert fake.options[1].headers == fake.options[0].headers


def test_list_voices_gets_account_owned_page():
    fake = FakeHttp({"voices": [{"voice_id": "voice_1", "name": "Narrator", "state": "trained"}], "total": 1, "page_number": 2, "page_size": 25})
    client = FishAudioClient(api_key="k", http_client=fake)

    result = client.list_voices.run(page_number=2, page_size=25)

    assert fake.calls == [("get", "/api/v1/fish_audio/voices?page_number=2&page_size=25", None)]
    assert isinstance(result, VoicesResponse)
    assert result.voices[0].voice_id == "voice_1"


def test_get_voice_gets_encoded_account_owned_voice_id():
    fake = FakeHttp({"voice": {"voice_id": "voice/1", "name": "Narrator", "state": "trained"}})
    client = FishAudioClient(api_key="k", http_client=fake)

    result = client.get_voice.run(voice_id="voice/1")

    assert fake.calls == [("get", "/api/v1/fish_audio/voices/voice%2F1", None)]
    assert isinstance(result, VoiceResponse)
    assert result.voice.state == "trained"
