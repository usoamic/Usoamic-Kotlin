package io.usoamic.usoamickt.enumcls

sealed class TxSpeed {
    object Auto : TxSpeed()
    object GP20 : TxSpeed()
    object GP40 : TxSpeed()
    object GP60 : TxSpeed()
    object GP80 : TxSpeed()
    object GP100 : TxSpeed()
    object GP120 : TxSpeed()
}