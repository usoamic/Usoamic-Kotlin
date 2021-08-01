package io.usoamic.usoamickt.corex

import io.usoamic.usoamickt.enumcls.TxSpeed
import org.web3j.abi.TypeReference
import org.web3j.abi.datatypes.Bool
import org.web3j.abi.datatypes.generated.Uint256
import org.web3j.crypto.Credentials
import java.math.BigInteger

open class Swap(
    contractAddress: String,
    node: String
) : TransactionExplorer(
    contractAddress = contractAddress,
    node = node
) {
    fun withdrawEth(credentials: Credentials, value: BigInteger, txSpeed: TxSpeed = TxSpeed.Auto): String = executeTransaction(
        credentials = credentials,
        name = "withdrawEth",
        inputParameters = listOf(Uint256(value)),
        txSpeed = txSpeed
    )

    fun burnSwap(credentials: Credentials, value: BigInteger, txSpeed: TxSpeed = TxSpeed.Auto): String = executeTransaction(
        credentials = credentials,
        name = "burnSwap",
        inputParameters = listOf(Uint256(value)),
        txSpeed = txSpeed
    )

    fun setSwapRate(credentials: Credentials, swapRate: BigInteger, txSpeed: TxSpeed = TxSpeed.Auto): String = executeTransaction(
        credentials = credentials,
        name = "setSwapRate",
        inputParameters = listOf(Uint256(swapRate)),
        txSpeed = txSpeed
    )

    fun setSwappable(credentials: Credentials, swappable: Boolean, txSpeed: TxSpeed = TxSpeed.Auto): String = executeTransaction(
        credentials = credentials,
        name = "setSwappable",
        inputParameters = listOf(Bool(swappable)),
        txSpeed = txSpeed
    )

    fun getSwapBalance(addressOfRequester: String): BigInteger? = executeCallEmptyPassValueAndUint256Return(
        name = "getSwapBalance",
        addressOfRequester = addressOfRequester
    )

    fun getSwapRate(addressOfRequester: String): BigInteger? = executeCallEmptyPassValueAndUint256Return(
        name = "getSwapRate",
        addressOfRequester = addressOfRequester
    )

    fun getSwappable(addressOfRequester: String): Boolean? = executeCallEmptyPassValueAndSingleValueReturn(
        name = "getSwappable",
        addressOfRequester = addressOfRequester,
        outputParameters = listOf(object : TypeReference<Bool>() {})
    )
}