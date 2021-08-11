package testpassword.models

import org.json.JSONObject

sealed interface JSONRecoverable<T> { infix fun recoverFromJSON(changes: JSONObject): T }