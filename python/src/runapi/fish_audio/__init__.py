"""Fish Audio client for RunAPI."""

from runapi.core import (
    AuthenticationError,
    InsufficientCreditsError,
    NotFoundError,
    RateLimitError,
    ValidationError,
)

from .client import FishAudioClient

__all__ = [
    "FishAudioClient",
    "AuthenticationError",
    "RateLimitError",
    "InsufficientCreditsError",
    "NotFoundError",
    "ValidationError",
]
