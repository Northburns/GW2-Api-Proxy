package northburns.gw2.site.layout.components

import io.kvision.core.Component
import io.kvision.core.Container
import io.kvision.html.span
import io.kvision.panel.SimplePanel
import northburns.gw2.client.myclient.data.Coins
import northburns.gw2.client.myclient.data.Coins.Companion.coins


class CoinComponent(
    private val coins: Coins,
) : SimplePanel("coin") {

    constructor(coins: Long) : this(coins(coins))

    init {
        span(coins.toPadStartString())
    }

    companion object {
        fun Container.coin(coins: Long): Component = coin(coins(coins))

        fun Container.coin(coins: Coins): Component {
            return CoinComponent(coins).also { add(it) }
        }
    }
}

// TODO binded version too! (if it's extractor is null = loading; else render coins) Should be straightforward :thinking: