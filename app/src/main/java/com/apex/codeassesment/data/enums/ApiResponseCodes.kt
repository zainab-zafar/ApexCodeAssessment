package com.apex.codeassesment.data.enums

enum class ApiResponseCodes(codes: APICodes) {

    CODE_200_SUCCESS(APICodes(200, "Success"));

    var tag: APICodes = codes

}

class APICodes(val code: Int, val desc: String) {
    override fun toString(): String {
        return desc
    }
}