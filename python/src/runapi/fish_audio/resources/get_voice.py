"""Fish Audio reusable voice lookup resource."""

from __future__ import annotations

from typing import Any, Optional
from urllib.parse import quote

from runapi.core import RequestOptions, Resource

from ..types import VoiceResponse


class GetVoice(Resource):
    """Get one reusable voice owned by the current account."""

    ENDPOINT = "/api/v1/fish_audio/voices"
    RESPONSE_CLASS = VoiceResponse

    def run(self, voice_id: str, options: Optional[RequestOptions] = None) -> Any:
        path = f"{self.ENDPOINT}/{quote(voice_id, safe='')}"
        return self._request("get", path, options=options)
