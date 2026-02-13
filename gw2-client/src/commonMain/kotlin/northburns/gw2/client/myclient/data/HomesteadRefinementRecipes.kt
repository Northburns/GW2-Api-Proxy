package northburns.gw2.client.myclient.data

import com.gw2tb.gw2api.types.GW2ItemId
import kotlinx.serialization.Serializable

object HomesteadRefinementRecipes {

    @Serializable
    data class HomesteadRefinementRecipe(
        val item: GW2ItemId,
        val count: Int,
        val materials: Int,
    )

    /**
     * Each is 100 trades per week. Values are Pair of "in" items, "out" fibers.
     * Total of 800 trades per week.
     */
    val farm = listOf(
        HomesteadRefinementRecipe(item = Gw2Ids.Zucchini, count = 2, materials = 1),
        HomesteadRefinementRecipe(item = Gw2Ids.Yam, count = 8, materials = 1),
        HomesteadRefinementRecipe(item = Gw2Ids.`Vanillla Bean`, count = 2, materials = 1),
        HomesteadRefinementRecipe(item = Gw2Ids.Turnip, count = 12, materials = 1),
        HomesteadRefinementRecipe(item = Gw2Ids.`Thyme Leaf`, count = 1, materials = 1),
        HomesteadRefinementRecipe(item = Gw2Ids.`Tarragon Leaves`, count = 1, materials = 1),
        HomesteadRefinementRecipe(item = Gw2Ids.`Sugar Pumpkin`, count = 8, materials = 1),
        HomesteadRefinementRecipe(item = Gw2Ids.Strawberry, count = 1, materials = 1),
        HomesteadRefinementRecipe(item = Gw2Ids.`Spinach Leaf`, count = 1, materials = 1),
        HomesteadRefinementRecipe(item = Gw2Ids.`Snow Truffle`, count = 2, materials = 1),
        HomesteadRefinementRecipe(item = Gw2Ids.`Sesame Seed`, count = 2, materials = 1),
        HomesteadRefinementRecipe(item = Gw2Ids.Seaweed, count = 1, materials = 2),
        HomesteadRefinementRecipe(item = Gw2Ids.`Sawgill Mushroom`, count = 6, materials = 1),
        HomesteadRefinementRecipe(item = Gw2Ids.`Sage Leaf`, count = 1, materials = 2),
        HomesteadRefinementRecipe(item = Gw2Ids.`Saffron Thread`, count = 1, materials = 2),
        HomesteadRefinementRecipe(item = Gw2Ids.Rutabaga, count = 1, materials = 1),
        HomesteadRefinementRecipe(item = Gw2Ids.`Rosemary Sprig`, count = 1, materials = 1),
        HomesteadRefinementRecipe(item = Gw2Ids.Raspberry, count = 1, materials = 2),
        HomesteadRefinementRecipe(item = Gw2Ids.`Prickly Pear`, count = 6, materials = 1),
        HomesteadRefinementRecipe(item = Gw2Ids.Potato, count = 4, materials = 1),
        HomesteadRefinementRecipe(item = Gw2Ids.`Portobello Mushroom`, count = 3, materials = 1),
        HomesteadRefinementRecipe(item = Gw2Ids.`Pile of Flax Seeds`, count = 1, materials = 2),
        HomesteadRefinementRecipe(item = Gw2Ids.`Pile of Allspice Berries`, count = 1, materials = 1),
        HomesteadRefinementRecipe(item = Gw2Ids.`Passion Fruit`, count = 5, materials = 1),
        HomesteadRefinementRecipe(item = Gw2Ids.Parsnip, count = 7, materials = 1),
        HomesteadRefinementRecipe(item = Gw2Ids.`Parsley Leaf`, count = 1, materials = 1),
        HomesteadRefinementRecipe(item = Gw2Ids.`Orrian Truffle`, count = 1, materials = 2),
        HomesteadRefinementRecipe(item = Gw2Ids.`Oregano Leaf`, count = 10, materials = 1),
        HomesteadRefinementRecipe(item = Gw2Ids.Onion, count = 1, materials = 2),
        HomesteadRefinementRecipe(item = Gw2Ids.Omnomberry, count = 1, materials = 1),
        HomesteadRefinementRecipe(item = Gw2Ids.Nopal, count = 6, materials = 1),
        HomesteadRefinementRecipe(item = Gw2Ids.Mushroom, count = 1, materials = 1),
        HomesteadRefinementRecipe(item = Gw2Ids.`Mint Leaf`, count = 7, materials = 1),
        HomesteadRefinementRecipe(item = Gw2Ids.`Lotus Root`, count = 4, materials = 1),
        HomesteadRefinementRecipe(item = Gw2Ids.Lemongrass, count = 4, materials = 1),
        HomesteadRefinementRecipe(item = Gw2Ids.Leek, count = 7, materials = 1),
        HomesteadRefinementRecipe(item = Gw2Ids.`Kale Leaf`, count = 1, materials = 1),
        HomesteadRefinementRecipe(item = Gw2Ids.`Head of Lettuce`, count = 1, materials = 2),
        HomesteadRefinementRecipe(item = Gw2Ids.`Head of Garlic`, count = 1, materials = 1),
        HomesteadRefinementRecipe(item = Gw2Ids.`Head of Cauliflower`, count = 8, materials = 1),
        HomesteadRefinementRecipe(item = Gw2Ids.`Head of Cabbage`, count = 10, materials = 1),
        HomesteadRefinementRecipe(item = Gw2Ids.`Handful of Red Lentils`, count = 1, materials = 1),
        HomesteadRefinementRecipe(item = Gw2Ids.`Green Onion`, count = 6, materials = 1),
        HomesteadRefinementRecipe(item = Gw2Ids.Grape, count = 8, materials = 1),
        HomesteadRefinementRecipe(item = Gw2Ids.`Ghost Pepper`, count = 1, materials = 1),
        HomesteadRefinementRecipe(item = Gw2Ids.`Dill Sprig`, count = 10, materials = 1),
        HomesteadRefinementRecipe(item = Gw2Ids.Clove, count = 1, materials = 1),
        HomesteadRefinementRecipe(item = Gw2Ids.`Chili Pepper`, count = 2, materials = 1),
        HomesteadRefinementRecipe(item = Gw2Ids.`Cayenne Pepper`, count = 1, materials = 1),
        HomesteadRefinementRecipe(item = Gw2Ids.`Cassava Root`, count = 1, materials = 1),
        HomesteadRefinementRecipe(item = Gw2Ids.Carrot, count = 1, materials = 1),
        HomesteadRefinementRecipe(item = Gw2Ids.`Butternut Squash`, count = 7, materials = 1),
        HomesteadRefinementRecipe(item = Gw2Ids.Blueberry, count = 2, materials = 1),
        HomesteadRefinementRecipe(item = Gw2Ids.Blackberry, count = 1, materials = 1),
        HomesteadRefinementRecipe(item = Gw2Ids.`Black Peppercorn`, count = 1, materials = 2),
        HomesteadRefinementRecipe(item = Gw2Ids.Beet, count = 15, materials = 1),
        HomesteadRefinementRecipe(item = Gw2Ids.`Bay Leaf`, count = 8, materials = 1),
        HomesteadRefinementRecipe(item = Gw2Ids.`Asparagus Spear`, count = 1, materials = 1),
        HomesteadRefinementRecipe(item = Gw2Ids.Artichoke, count = 7, materials = 1),
    )

    /**
     * Each is 250 trades per week. Values are Pair of "in" items, "out" fibers.
     * Total of 800 trades per week.
     */
    val forge = listOf(
        HomesteadRefinementRecipe(item = Gw2Ids.`Orichalcum Ore`, count = 1, materials = 2),
        HomesteadRefinementRecipe(item = Gw2Ids.`Mithril Ore`, count = 1, materials = 1),
        HomesteadRefinementRecipe(item = Gw2Ids.`Platinum Ore`, count = 1, materials = 1),
        HomesteadRefinementRecipe(item = Gw2Ids.`Gold Ore`, count = 2, materials = 1),
        HomesteadRefinementRecipe(item = Gw2Ids.`Silver Ore`, count = 5, materials = 1),
        HomesteadRefinementRecipe(item = Gw2Ids.`Iron Ore`, count = 1, materials = 1),
        HomesteadRefinementRecipe(item = Gw2Ids.`Copper Ore`, count = 2, materials = 1),
    )

    /**
     * Each is 250 trades per week. Values are Pair of "in" items, "out" fibers.
     * Total of 800 trades per week.
     */
    val mill = listOf(
        HomesteadRefinementRecipe(item = Gw2Ids.`Green Wood Log`, count = 5, materials = 1),
        HomesteadRefinementRecipe(item = Gw2Ids.`Soft Wood Log`, count = 3, materials = 1),
        HomesteadRefinementRecipe(item = Gw2Ids.`Seasoned Wood Log`, count = 1, materials = 1),
        HomesteadRefinementRecipe(item = Gw2Ids.`Hard Wood Log`, count = 1, materials = 1),
        HomesteadRefinementRecipe(item = Gw2Ids.`Elder Wood Log`, count = 1, materials = 1),
        HomesteadRefinementRecipe(item = Gw2Ids.`Ancient Wood Log`, count = 1, materials = 2),
    )
}