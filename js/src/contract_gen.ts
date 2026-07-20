export const contract = {
  "text-to-speech": {
    "models": [
      "s1",
      "s2-pro"
    ],
    "fields_by_model": {
      "s1": {
        "text": {
          "required": true
        }
      },
      "s2-pro": {
        "text": {
          "required": true
        }
      }
    }
  }
} as const;
