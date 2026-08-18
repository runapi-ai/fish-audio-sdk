import pytest

from runapi.core import config
from runapi.core.errors import ValidationError
from runapi.fish_audio import FishAudioClient
from runapi.fish_audio.types import TextToSpeechResponse, VoiceResponse, VoicesResponse


class FakeHttp:
    def __init__(self, *responses):
        self._responses = list(responses)
        self.calls = []

    def request(self, method, path, body=None, options=None):
        self.calls.append((method, path, body))
        return self._responses.pop(0)


@pytest.fixture(autouse=True)
def reset_config(monkeypatch):
    monkeypatch.delenv("RUNAPI_API_KEY", raising=False)
    monkeypatch.setattr(config, "api_key", None)


def test_run_posts_params_and_decodes_managed_audio():
    fake = FakeHttp({"id": "task_1", "status": "completed", "audios": [{"url": "https://runapi.ai/audio.mp3", "format": "mp3", "mime_type": "audio/mpeg", "size_bytes": 128}]})
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
            "references": references,
        },
    )]
    assert isinstance(result, TextToSpeechResponse)
    assert result.audios[0].format == "mp3"


def test_run_requires_text():
    client = FishAudioClient(api_key="k", http_client=FakeHttp())
    with pytest.raises(ValidationError, match="text is required"):
        client.text_to_speech.run(model="s1")


def test_run_posts_reusable_voice_id():
    fake = FakeHttp({"id": "task_1", "status": "completed", "audios": []})
    client = FishAudioClient(api_key="k", http_client=fake)

    client.text_to_speech.run(model="s1", text="Hello", voice_id="voice_1")

    assert fake.calls == [("post", "/api/v1/fish_audio/text_to_speech", {"model": "s1", "text": "Hello", "voice_id": "voice_1"})]


def test_run_requires_reference_transcript():
    client = FishAudioClient(api_key="k", http_client=FakeHttp())
    with pytest.raises(ValidationError, match=r"references\[0\]\.text is required"):
        client.text_to_speech.run(model="s1", text="Hello", references=[{"audio": "UklGRg=="}])


def test_create_voice_posts_public_params_and_decodes_voice():
    fake = FakeHttp({"voice": {"voice_id": "voice_1", "name": "Narrator", "state": "training"}, "billing": {"reservation": None, "settlement": {"charged_amount_cents": 0, "amount_micro_cents": 0}, "refund": None}})
    client = FishAudioClient(api_key="k", http_client=fake)

    result = client.create_voice.run(name="Narrator", source_audio_url="https://cdn.runapi.ai/narrator.mp3")

    assert fake.calls == [("post", "/api/v1/fish_audio/voices", {"name": "Narrator", "source_audio_url": "https://cdn.runapi.ai/narrator.mp3"})]
    assert isinstance(result, VoiceResponse)
    assert result.voice.state == "training"
    assert result.billing.settlement.amount_micro_cents == 0


def test_list_voices_gets_account_owned_page():
    fake = FakeHttp({"voices": [{"voice_id": "voice_1", "name": "Narrator", "state": "trained"}], "total": 1, "page_number": 2, "page_size": 25, "billing": {"reservation": None, "settlement": {"charged_amount_cents": 0, "amount_micro_cents": 0}, "refund": None}})
    client = FishAudioClient(api_key="k", http_client=fake)

    result = client.list_voices.run(page_number=2, page_size=25)

    assert fake.calls == [("get", "/api/v1/fish_audio/voices?page_number=2&page_size=25", None)]
    assert isinstance(result, VoicesResponse)
    assert result.voices[0].voice_id == "voice_1"
    assert result.billing.settlement.amount_micro_cents == 0


def test_get_voice_gets_encoded_account_owned_voice_id():
    fake = FakeHttp({"voice": {"voice_id": "voice/1", "name": "Narrator", "state": "trained"}, "billing": {"reservation": None, "settlement": {"charged_amount_cents": 0, "amount_micro_cents": 0}, "refund": None}})
    client = FishAudioClient(api_key="k", http_client=fake)

    result = client.get_voice.run(voice_id="voice/1")

    assert fake.calls == [("get", "/api/v1/fish_audio/voices/voice%2F1", None)]
    assert isinstance(result, VoiceResponse)
    assert result.voice.state == "trained"
    assert result.billing.settlement.amount_micro_cents == 0
