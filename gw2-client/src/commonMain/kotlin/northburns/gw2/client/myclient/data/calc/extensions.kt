package northburns.gw2.client.myclient.data.calc

import com.gw2tb.gw2api.types.v2.GW2v2Character

fun GW2v2Character.getActiveBuildTab() = buildTabs?.get(activeBuildTab.toInt() - 1)
