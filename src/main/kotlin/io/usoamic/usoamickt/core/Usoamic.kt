package io.usoamic.usoamickt.core

import io.usoamic.usoamickt.enumcls.NetworkType
import io.usoamic.usoamickt.enumcls.NodeProvider
import io.usoamic.usoamickt.enumcls.TxSpeed
import io.usoamic.usoamickt.other.Contract
import io.usoamic.usoamickt.other.Node
import org.web3j.abi.TypeReference
import org.web3j.abi.datatypes.Address
import org.web3j.abi.datatypes.Utf8String
import org.web3j.abi.datatypes.generated.Uint256
import java.math.BigInteger

class Usoamic constructor(fileName: String, filePath: String, contractAddress: String, node: String) :
    Swap(fileName, filePath, contractAddress, node) {
    constructor(fileName: String, networkType: NetworkType, nodeProvider: NodeProvider) : this(
        fileName,
        "",
        Contract.forNetwork(networkType),
        Node.by(networkType, nodeProvider)
    )

    constructor(fileName: String, filePath: String, networkType: NetworkType, nodeProvider: NodeProvider) : this(
        fileName,
        filePath,
        Contract.forNetwork(networkType),
        Node.by(networkType, nodeProvider)
    )


    fun getUsoBalance(): BigInteger? {
        return balanceOf(address)
    }


    fun balanceOf(address: String): BigInteger? = executeCallUint256ValueReturn("balanceOf", listOf(Address(address)))


    fun burn(password: String, value: BigInteger, txSpeed: TxSpeed = TxSpeed.Auto): String =
        executeTransaction(
            password,
            "burn",
            listOf(Uint256(value)),
            txSpeed
        )


    fun transferUso(password: String, to: String, value: BigInteger, txSpeed: TxSpeed = TxSpeed.Auto): String = executeTransaction(
        password,
        "transfer",
        listOf(
            Address(to),
            Uint256(value)
        ),
        txSpeed
    )


    fun getSupply(): BigInteger? = executeCallEmptyPassValueAndUint256Return("getSupply")


    fun getVersion(): String? = executeCallEmptyPassValueAndSingleValueReturn(
        "getVersion",
        listOf(object : TypeReference<Utf8String>() {})
    )
}