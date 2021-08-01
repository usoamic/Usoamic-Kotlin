package io.usoamic.usoamickt.corex

import io.usoamic.usoamickt.enumcls.NetworkType
import io.usoamic.usoamickt.enumcls.NodeProvider
import io.usoamic.usoamickt.enumcls.TxSpeed
import io.usoamic.usoamickt.other.Contract
import io.usoamic.usoamickt.other.Node
import org.web3j.abi.TypeReference
import org.web3j.abi.datatypes.Address
import org.web3j.abi.datatypes.Utf8String
import org.web3j.abi.datatypes.generated.Uint256
import org.web3j.crypto.Credentials
import java.math.BigInteger

class Usoamic(
    contractAddress: String,
    node: String
) : Swap(
    contractAddress = contractAddress,
    node = node
) {
    constructor(networkType: NetworkType, nodeProvider: NodeProvider) : this(
        Contract.forNetwork(networkType),
        Node.by(networkType, nodeProvider)
    )

    fun balanceOf(address: String): BigInteger? = executeCallUint256ValueReturn(
        name = "balanceOf",
        addressOfRequester = address,
        inputParameters = listOf(
            Address(address)
        )
    )

    fun burn(
        credentials: Credentials,
        value: BigInteger,
        txSpeed: TxSpeed = TxSpeed.Auto
    ): String = executeTransaction(
        credentials = credentials,
        name = "burn",
        inputParameters = listOf(Uint256(value)),
        txSpeed = txSpeed
    )

    fun transferUso(
        credentials: Credentials,
        to: String,
        value: BigInteger,
        txSpeed: TxSpeed = TxSpeed.Auto
    ): String = executeTransaction(
        credentials = credentials,
        name = "transfer",
        inputParameters = listOf(
            Address(to),
            Uint256(value)
        ),
        txSpeed = txSpeed
    )

    fun getSupply(addressOfRequester: String): BigInteger? = executeCallEmptyPassValueAndUint256Return(
        name = "getSupply",
        addressOfRequester = addressOfRequester
    )

    fun getVersion(addressOfRequester: String): String? = executeCallEmptyPassValueAndSingleValueReturn(
        name = "getVersion",
        addressOfRequester = addressOfRequester,
        outputParameters = listOf(object : TypeReference<Utf8String>() {})
    )
}