package com.dothebestmayb.auth.data

import android.util.Patterns
import com.dothebestmayb.auth.domain.PatternValidator

// constructor가 필요없기 때문에 object로 변경함
object EmailPatternValidator : PatternValidator {

    override fun matches(value: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(value).matches()
    }
}
