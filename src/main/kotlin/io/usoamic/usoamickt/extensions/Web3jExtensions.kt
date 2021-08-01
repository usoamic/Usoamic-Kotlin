package io.usoamic.usoamickt.extensions

import org.web3j.abi.FunctionEncoder
import org.web3j.abi.datatypes.Function

fun Function.encode(): String {
    return FunctionEncoder.encode(this)
}