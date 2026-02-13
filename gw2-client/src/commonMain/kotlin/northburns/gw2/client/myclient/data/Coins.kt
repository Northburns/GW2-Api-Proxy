package northburns.gw2.client.myclient.data

import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline
import kotlin.math.absoluteValue
import kotlin.math.sign

@JvmInline
@Serializable
value class Coins(val coins: Long) : Comparable<Coins> {
    val sign: Int get() = coins.sign
    val gold: Long get() = coins.absoluteValue / R2
    val silver: Int get() = ((coins.absoluteValue % R2) / R).toInt()
    val copper: Int get() = (coins.absoluteValue % R).toInt()

    override fun toString(): String = toPadStartString(14)

    fun toPadStartString(length: Int = 14): String = StringBuilder().apply {
        require(length >= 14) { "Length must be at least 14 but was $length" }
        if (sign < 0) append("− ")
        if (gold > 0) append("$gold🟡 ")
        if (gold > 0 || silver > 0) append("${silver.toString().padStart(2)}⚪ ")
        append("${copper.toString().padStart(2)}🟠")
    }.toString().padStart(length)

    operator fun compareTo(otherCoins: Int): Int = coins.compareTo(otherCoins)
    operator fun compareTo(otherCoins: Long): Int = coins.compareTo(otherCoins)
    override fun compareTo(other: Coins): Int = coins.compareTo(other.coins)

    operator fun plus(other: Coins): Coins = Coins(this.coins + other.coins)
    operator fun minus(other: Coins): Coins = Coins(this.coins - other.coins)

    companion object {
        /**
         * Copper in silver, or silver in gold
         */
        private const val R = 100

        /**
         * copper in gold (R*R)
         */
        private const val R2 = 100_00

        fun coins(coins: Long) = Coins(coins)
        fun coins(coins: Long?) = coins?.let { coins(it) }

        fun coins(coins: Int) = coins(coins.toLong())
        fun coins(coins: Int?) = coins(coins?.toLong())
    }
}
