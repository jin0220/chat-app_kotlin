package com.example.chat.model.data

import java.io.Serializable

data class Users(
    var phone: String = "",
    var password: String = "",
    var name: String = "",
    var email: String? = null,
    var token: String = ""
): Serializable //객체를 intent에 담아 넘기기 위해 사용
