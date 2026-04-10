package northburns.gw2.site.app.content.profession

import com.gw2tb.gw2api.types.v2.GW2v2Trait
import io.kvision.core.Container
import io.kvision.html.div
import io.kvision.html.span
import northburns.gw2.client.myclient.data.Gw2Image.Companion.gw2Image
import northburns.gw2.site.layout.components.TextWithIcon
import northburns.gw2.site.util.tailwind.tw

fun Container.trait(trait: GW2v2Trait) {

    val slot = trait.slot // major or minor
    val name = trait.name
    val description = trait.description
    val facts = trait.facts
    val traitedFacts = trait.traitedFacts

    trait.gw2Image()


    div(className = tw {
        +""
    }) {
        TextWithIcon(name, trait.gw2Image()).also { add(it) }
        span(": ")
        span(description)
    }

}