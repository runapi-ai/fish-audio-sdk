"""Fish Audio client."""

from __future__ import annotations

from typing import Any, Optional

from runapi.core import ProviderClient

from .resources.create_voice import CreateVoice
from .resources.get_voice import GetVoice
from .resources.list_voices import ListVoices
from .resources.text_to_speech import TextToSpeech


class FishAudioClient(ProviderClient):
    """Fish Audio reusable voice and speech generation client."""

    def __init__(self, api_key: Optional[str] = None, **options: Any) -> None:
        super().__init__(api_key, **options)
        http = self._http
        self.text_to_speech = TextToSpeech(http)
        self.create_voice = CreateVoice(http)
        self.list_voices = ListVoices(http)
        self.get_voice = GetVoice(http)
