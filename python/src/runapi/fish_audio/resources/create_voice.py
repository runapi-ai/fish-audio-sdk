"""Fish Audio reusable voice creation resource."""

from __future__ import annotations

from typing import Any, Optional

from runapi.core import RequestOptions, Resource

from ..types import VoiceResponse


class CreateVoice(Resource):
    """Create an account-owned reusable voice from source audio."""

    ENDPOINT = "/api/v1/fish_audio/voices"
    RESPONSE_CLASS = VoiceResponse

    def run(self, options: Optional[RequestOptions] = None, **params: Any) -> Any:
        compacted = self._compact_params(params)
        return self._run_hybrid("post", self.ENDPOINT, body=compacted, options=options)
