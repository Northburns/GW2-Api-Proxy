package northburns.gw2.site

import arrow.core.Nel
import arrow.core.toNonEmptyListOrNull
import kotlinx.browser.localStorage
import northburns.gw2.client.myclient.PlayerId
import org.w3c.dom.get
import org.w3c.dom.set

class LocalStorageService {

    var currentPlayersX: Nel<PlayerId>?
        get() {
            return localStorage["currentPlayer"]?.split("|")?.map(::PlayerId)?.toNonEmptyListOrNull()
        }
        set(value) {
            if (value == null) localStorage.removeItem("currentPlayer")
            else localStorage.set("currentPlayer", value.joinToString("|"))
        }

}
