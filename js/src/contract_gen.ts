export const contract = {
  "text-to-speech": {
    "models": [
      "s1",
      "s2-pro"
    ],
    "fields_by_model": {
      "s1": {
        "model": {
          "required": true
        },
        "text": {
          "required": true
        }
      },
      "s2-pro": {
        "model": {
          "required": true
        },
        "text": {
          "required": true
        }
      }
    }
  }
} as const;
