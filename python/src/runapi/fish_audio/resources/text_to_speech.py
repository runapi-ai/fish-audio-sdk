"""Fish Audio text-to-speech resource."""

from __future__ import annotations

from typing import Any, Optional

from runapi.core import RequestOptions, Resource
from runapi.core.errors import ValidationError

from ..contract_gen import CONTRACT
from ..types import TextToSpeechResponse


class TextToSpeech(Resource):
    """Generate a RunAPI-managed MP3 from text."""

    ENDPOINT = "/api/v1/fish_audio/text_to_speech"
    RESPONSE_CLASS = TextToSpeechResponse

    def run(self, options: Optional[RequestOptions] = None, **params: Any) -> Any:
        compacted = self._compact_params(params)
        self._validate_contract(CONTRACT["text-to-speech"], compacted)
        self._validate_references(compacted.get("references"))
        return self._request("post", self.ENDPOINT, body=compacted, options=options)

    @staticmethod
    def _validate_references(references: Any) -> None:
        if references is None:
            return
        if not isinstance(references, list):
            raise ValidationError("references must be an array")

        for index, reference in enumerate(references):
            if not isinstance(reference, dict):
                raise ValidationError(f"references[{index}] must be an object")
            for field in ("audio", "text"):
                value = reference.get(field)
                if value is None or (isinstance(value, str) and not value.strip()):
                    raise ValidationError(f"references[{index}].{field} is required")
                if not isinstance(value, str):
                    raise ValidationError(f"references[{index}].{field} must be a string")
