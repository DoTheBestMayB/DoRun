package com.dothebestmayb.auth.domain

interface PatternValidator {

    fun matches(value: String): Boolean
}
