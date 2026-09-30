"""Fish Audio text-to-speech resource."""

from __future__ import annotations

from typing import Any, Optional

from runapi.core import RequestOptions, Resource

from ..types import TextToSpeechResponse


class TextToSpeech(Resource):
    """Generate RunAPI-managed MP3 or WAV audio from text."""

    ENDPOINT = "/api/v1/fish_audio/text_to_speech"
    RESPONSE_CLASS = TextToSpeechResponse

    def run(self, options: Optional[RequestOptions] = None, **params: Any) -> Any:
        compacted = self._compact_params(params)
        return self._run_hybrid("post", self.ENDPOINT, body=compacted, options=options)
