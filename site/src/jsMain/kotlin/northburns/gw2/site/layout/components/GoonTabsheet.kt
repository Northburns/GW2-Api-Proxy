package northburns.gw2.site.layout.components

import io.kvision.core.Container
import io.kvision.core.onClick
import io.kvision.html.Div
import io.kvision.html.TAG
import io.kvision.html.Tag
import io.kvision.html.div
import northburns.gw2.client.myclient.log.GoonLog
import northburns.gw2.site.layout.style.PanelBackgrounds.PanelBackground
import northburns.gw2.site.layout.style.PanelBackgrounds.setPanelBackground
import northburns.gw2.site.util.tailwind.tw
import kotlin.uuid.Uuid

class GoonTabsheet(
    tabs: List<GoonTab>,
) : Tag(
    type = TAG.DIV,
    className = tw {
        +"block"
    }
) {

    class GoonTab(
        val title: String,
        val selectedByDefault: Boolean,
        val build: Container.() -> Unit,
    )

    internal class GoonTabInternal(
        val id: String,
        val goonTab: GoonTab,
        val tabDiv: Div,
        val contentDiv: Div,
    )

    private val tabs = tabs.map { tab ->
        val tabId = Uuid.random().toString()
        GoonTabInternal(
            id = tabId,
            goonTab = tab,
            tabDiv = Div(className = "cursor-default") {
                setPanelBackground(PanelBackground.BROWN_W_CORNERNS, borderWidth = TAB_BORDER_WIDTH)
                +tab.title
                onClick { this@GoonTabsheet.selectTab(tabId) }
            },
            contentDiv = Div(className = tw {
                +"flex flex-wrap justify-center items-center"
                +"gap-5"
            }) {
                setPanelBackground(PanelBackground.BROWN)
                tab.build(this)
            }
        )
    }

    private fun selectTab(id: String) {
        tabs.forEach { tab ->
            val selected = id == tab.id

            // Tab's border
            tab.tabDiv.setPanelBackground(
                panel = if (!selected) PanelBackground.BROWN
                else PanelBackground.BROWN_W_CORNERNS,
                borderWidth = TAB_BORDER_WIDTH,
            )

            // Show/hide content
            if (selected) tab.contentDiv.removeCssClass("hidden")
            else tab.contentDiv.addCssClass("hidden")
        }
    }

    init {
        val tabs = this.tabs
        div(className = tw {
            +"flex"
        }) {
            tabs.forEach { tab -> add(tab.tabDiv) }
        }
        tabs.forEach { tab -> add(tab.contentDiv) }

        val selectedByDefault = this.tabs.filter { it.goonTab.selectedByDefault }.let {
            if (it.isEmpty()) {
                GoonLog["GoonTabsheet"].warn("Default tabs not selected")
                this.tabs.first()
            } else if (it.size > 1) {
                GoonLog["GoonTabsheet"].warn("Default tabs multiple selected tabs")
                it.first()
            } else it.single()
        }
        selectTab(selectedByDefault.id)
    }

    companion object {
        private val TAB_ID = "tab-id"
        private val TAB_BORDER_WIDTH = "8px 8px 0px 8px"
    }
}
