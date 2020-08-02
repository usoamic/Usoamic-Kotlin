package io.usoamic.usoamickt.enumcls

sealed class TxSpeed {
    object Auto : TxSpeed()
    object GP20 : TxSpeed()
    object GP40 : TxSpeed()
    object GP60 : TxSpeed()
    object GP80 : TxSpeed()
    object GP100 : TxSpeed()
    object GP120 : TxSpeed()

    companion object {
        fun parseString(str: String): TxSpeed {
            return when (str) {
                "20", "20 Gwei" -> GP20
                "40", "40 Gwei" -> GP40
                "60", "60 Gwei" -> GP60
                "80", "80 Gwei" -> GP80
                "100", "100 Gwei" -> GP100
                "120", "120 Gwei" -> GP120
                else -> Auto
            }
        }
    }
}