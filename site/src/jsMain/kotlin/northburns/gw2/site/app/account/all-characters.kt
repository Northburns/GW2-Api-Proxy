package northburns.gw2.site.app.account

import io.kvision.core.Container
import io.kvision.html.div
import io.kvision.html.li
import io.kvision.html.p
import northburns.gw2.site.app.GoonPath
import northburns.gw2.site.layout.components.CommonComponents.goonUl
import northburns.gw2.site.layout.components.TextWithIcon
import northburns.gw2.site.util.bind.goonBind
import northburns.gw2.site.util.reselect.reselect

fun Container.allCharacters() {

    div().goonBind {
        reselect(
            { it.account?.party },
            { it.snapshots.snapshots },
        )
    }.render { (party, snapshots) ->

        if (party == null) {
            +"Log in first"
            return@render
        }

        party.forEach { member ->
            p(member.name)

            goonUl {
                snapshots[member.id]?.characters?.entries?.sortedBy { it.key }?.forEach { (name, characer) ->

                    li {
                        TextWithIcon(
                            text = name,
                            image = null,
                            href = GoonPath.ACCOUNTS_CHARACTERS_VIEW.buildPath(
                                "account" to member.id.id,
                                "character" to name,
                            ),
                            external = false,
                        ).also { add(it) }
                    }

                }
            }
        }

    }

}