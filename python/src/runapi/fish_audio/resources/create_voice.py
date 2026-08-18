"""Fish Audio reusable voice creation resource."""

from __future__ import annotations

from typing import Any, Optional

from runapi.core import RequestOptions, Resource

from ..contract_gen import CONTRACT
from ..types import VoiceResponse


class CreateVoice(Resource):
    """Create an account-owned reusable voice from source audio."""

    ENDPOINT = "/api/v1/fish_audio/voices"
    RESPONSE_CLASS = VoiceResponse

    def run(self, options: Optional[RequestOptions] = None, **params: Any) -> Any:
        compacted = self._compact_params(params)
        self._validate_contract(CONTRACT["create-voice"], compacted)
        return self._request("post", self.ENDPOINT, body=compacted, options=options)
