"""Fish Audio reusable voice listing resource."""

from __future__ import annotations

from typing import Any, Optional
from urllib.parse import urlencode

from runapi.core import RequestOptions, Resource

from ..types import VoicesResponse


class ListVoices(Resource):
    """List reusable voices owned by the current account."""

    ENDPOINT = "/api/v1/fish_audio/voices"
    RESPONSE_CLASS = VoicesResponse

    def run(self, options: Optional[RequestOptions] = None, **params: Any) -> Any:
        compacted = self._compact_params(params)
        query = urlencode(compacted)
        path = f"{self.ENDPOINT}?{query}" if query else self.ENDPOINT
        return self._request("get", path, options=options)
