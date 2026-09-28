"""Fish Audio response models."""

from runapi.core import BaseModel, TaskResponse, optional, required


class Voice(BaseModel):
    """An account-owned reusable voice."""

    voice_id = required(str)
    name = optional(str)
    state = required(str)


class VoiceResponse(BaseModel):
    """Response containing one reusable voice."""

    voice = required(lambda: Voice)


class VoicesResponse(BaseModel):
    """Paginated response containing account-owned reusable voices."""

    voices = required([lambda: Voice])
    total = required(int)
    page_number = required(int)
    page_size = required(int)


class Audio(BaseModel):
    """A RunAPI-managed audio result."""

    url = required(str)
    format = required(str)
    mime_type = required(str)
    size_bytes = required(int)


class TextToSpeechResponse(TaskResponse):
    """Completed text-to-speech response."""

    id = required(str)
    status = required(str)
    audios = required([lambda: Audio])
    error = optional(str)
