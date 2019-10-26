package io.usoamic.usoamickt.core

import io.usoamic.usoamickt.enum.NetworkType
import io.usoamic.usoamickt.enum.NodeProvider
import org.web3j.abi.TypeReference
import org.web3j.abi.datatypes.Address
import org.web3j.abi.datatypes.Utf8String
import org.web3j.abi.datatypes.generated.Uint256
import java.math.BigInteger


class Usoamic constructor(filename: String, contractAddress: String, node: String) : Swap(filename, contractAddress, node) {
    constructor(filename: String, contractAddress: String, nodeProvider: NodeProvider, networkType: NetworkType) : this(
        filename,
        contractAddress,
        nodeProvider.getUrl(networkType)
    )

    @Throws(Exception::class)
    fun getUsoBalance(): BigInteger? {
        return balanceOf(address)
    }

    @Throws(Exception::class)
    fun balanceOf(address: String): BigInteger? = executeCallUint256ValueReturn("balanceOf", listOf(Address(address)))

    @Throws(Exception::class)
    fun burn(password: String, value: BigInteger): String = executeTransaction(password, "burn", listOf(Uint256(value)))

    @Throws(Exception::class)
    fun transferUso(password: String, to: String, value: BigInteger): String = executeTransaction(
        password,
        "transfer",
        listOf(
            Address(to),
            Uint256(value)
        )
    )

    @Throws(Exception::class)
    fun getSupply(): BigInteger? = executeCallEmptyPassValueAndUint256Return("getSupply")

    @Throws(Exception::class)
    fun getVersion(): String? = executeCallEmptyPassValueAndSingleValueReturn(
        "getVersion",
        listOf(object: TypeReference<Utf8String>() { })
    )
}