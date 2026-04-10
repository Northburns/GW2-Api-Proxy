package northburns.gw2.client.myclient.data

import com.gw2tb.gw2api.types.GW2AchievementCategoryId
import com.gw2tb.gw2api.types.GW2AchievementId
import com.gw2tb.gw2api.types.GW2CurrencyId
import com.gw2tb.gw2api.types.GW2ItemId
import com.gw2tb.gw2api.types.GW2ProfessionId
import com.gw2tb.gw2api.types.GW2SpecializationId

@Suppress("unused", "RemoveRedundantBackticks", "ObjectPropertyName", "SpellCheckingInspection")
object Gw2Ids {

    // Currency ids
    val `Coin` = GW2CurrencyId(1)
    val `Karma` = GW2CurrencyId(2)
    val `Laurel` = GW2CurrencyId(3)
    val `Gem` = GW2CurrencyId(4)
    val `Ascalonian Tear` = GW2CurrencyId(5)
    val `Shard of Zhaitan` = GW2CurrencyId(6)
    val `Fractal Relic` = GW2CurrencyId(7)
    val `Seal of Beetletun` = GW2CurrencyId(9)
    val `Manifesto of the Moletariate` = GW2CurrencyId(10)
    val `Deadly Bloom` = GW2CurrencyId(11)
    val `Symbol of Koda` = GW2CurrencyId(12)
    val `Flame Legion Charr Carving` = GW2CurrencyId(13)
    val `Knowledge Crystal` = GW2CurrencyId(14)
    val `Badge of Honor` = GW2CurrencyId(15)
    val `Guild Commendation` = GW2CurrencyId(16)
    val `Transmutation Charge` = GW2CurrencyId(18)
    val `Airship Part` = GW2CurrencyId(19)
    val `Ley Line Crystal` = GW2CurrencyId(20)
    val `Lump of Aurillium` = GW2CurrencyId(22)
    val `Spirit Shard` = GW2CurrencyId(23)
    val `Pristine Fractal Relic` = GW2CurrencyId(24)
    val `Geode` = GW2CurrencyId(25)
    val `WvW Skirmish Claim Ticket` = GW2CurrencyId(26)
    val `Bandit Crest` = GW2CurrencyId(27)
    val `Magnetite Shard` = GW2CurrencyId(28)
    val `Provisioner Token` = GW2CurrencyId(29)
    val `PvP League Ticket` = GW2CurrencyId(30)
    val `Proof of Heroics` = GW2CurrencyId(31)
    val `Unbound Magic` = GW2CurrencyId(32)
    val `Ascended Shards of Glory` = GW2CurrencyId(33)
    val `Trade Contract` = GW2CurrencyId(34)
    val `Elegy Mosaic` = GW2CurrencyId(35)
    val `Testimony of Desert Heroics` = GW2CurrencyId(36)
    val `Exalted Key` = GW2CurrencyId(37)
    val `Machete` = GW2CurrencyId(38)
    val `Gaeting Crystal (PoF)` = GW2CurrencyId(39)
    val `Bandit Skeleton Key` = GW2CurrencyId(40)
    val `Pact Crowbar` = GW2CurrencyId(41)
    val `Vial of Chak Acid` = GW2CurrencyId(42)
    val `Zephyrite Lockpick` = GW2CurrencyId(43)
    val `Trader's Key` = GW2CurrencyId(44)
    val `Volatile Magic` = GW2CurrencyId(45)
    val `PvP Tournament Voucher` = GW2CurrencyId(46)
    val `Racing Medallion` = GW2CurrencyId(47)
    val `Mistborn Key` = GW2CurrencyId(49)
    val `Festival Token` = GW2CurrencyId(50)
    val `Cache Key` = GW2CurrencyId(51)
    val `Red Prophet Shard` = GW2CurrencyId(52)
    val `Green Prophet Shard` = GW2CurrencyId(53)
    val `Blue Prophet Crystal` = GW2CurrencyId(54)
    val `Green Prophet Crystal` = GW2CurrencyId(55)
    val `Red Prophet Crystal` = GW2CurrencyId(56)
    val `Blue Prophet Shard` = GW2CurrencyId(57)
    val `War Supplies` = GW2CurrencyId(58)
    val `Unstable Fractal Essence` = GW2CurrencyId(59)
    val `Tyrian Defense Seal` = GW2CurrencyId(60)
    val `Research Note` = GW2CurrencyId(61)
    val `Unusual Coin` = GW2CurrencyId(62)
    val `Astral Acclaim` = GW2CurrencyId(63)
    val `Jade Sliver` = GW2CurrencyId(64)
    val `Testimony of Jade Heroics` = GW2CurrencyId(65)
    val `Ancient Coin` = GW2CurrencyId(66)
    val `Canach Coins` = GW2CurrencyId(67)
    val `Imperial Favor` = GW2CurrencyId(68)
    val `Tales of Dungeon Delving` = GW2CurrencyId(69)
    val `Legendary Insight` = GW2CurrencyId(70)
    val `Jade Miner'val s Keycard` = GW2CurrencyId(71)
    val `Static Charge` = GW2CurrencyId(72)
    val `Pinch of Stardust` = GW2CurrencyId(73)
    val `Calcified Gasp` = GW2CurrencyId(75)
    val `Ursus Oblige` = GW2CurrencyId(76)
    val `Gaeting Crystal (JW)` = GW2CurrencyId(77)
    val `Fine Rift Essence` = GW2CurrencyId(78)
    val `Rare Rift Essence` = GW2CurrencyId(79)
    val `Masterwork Rift Essence` = GW2CurrencyId(80)
    val `Antiquated Ducat` = GW2CurrencyId(81)
    val `Testimony of Castoran Heroics` = GW2CurrencyId(82)
    val `Aether-Rich Sap` = GW2CurrencyId(83)

    // Item ids
    val `Refined Homestead Fiber` = GW2ItemId(102306)
    val `Zucchini` = GW2ItemId(12330)
    val `Yam` = GW2ItemId(12329)
    val `Vanillla Bean` = GW2ItemId(12234)
    val `Turnip` = GW2ItemId(12162)
    val `Thyme Leaf` = GW2ItemId(12248)
    val `Tarragon Leaves` = GW2ItemId(12506)
    val `Sugar Pumpkin` = GW2ItemId(12538)
    val `Strawberry` = GW2ItemId(12253)
    val `Spinach Leaf` = GW2ItemId(12241)
    val `Snow Truffle` = GW2ItemId(12144)
    val `Sesame Seed` = GW2ItemId(12342)
    val `Seaweed` = GW2ItemId(12509)
    val `Sawgill Mushroom` = GW2ItemId(73504)
    val `Sage Leaf` = GW2ItemId(12243)
    val `Saffron Thread` = GW2ItemId(12547)
    val `Rutabaga` = GW2ItemId(12535)
    val `Rosemary Sprig` = GW2ItemId(12335)
    val `Raspberry` = GW2ItemId(12254)
    val `Prickly Pear` = GW2ItemId(66522)
    val `Potato` = GW2ItemId(12135)
    val `Portobello Mushroom` = GW2ItemId(12334)
    val `Pile of Flax Seeds` = GW2ItemId(74090)
    val `Pile of Allspice Berries` = GW2ItemId(73096)
    val `Passion Fruit` = GW2ItemId(36731)
    val `Parsnip` = GW2ItemId(12507)
    val `Parsley Leaf` = GW2ItemId(12246)
    val `Orrian Truffle` = GW2ItemId(12545)
    val `Oregano Leaf` = GW2ItemId(12244)
    val `Onion` = GW2ItemId(12142)
    val `Omnomberry` = GW2ItemId(12128)
    val `Nopal` = GW2ItemId(66524)
    val `Mushroom` = GW2ItemId(12147)
    val `Mint Leaf` = GW2ItemId(12536)
    val `Lotus Root` = GW2ItemId(12510)
    val `Lemongrass` = GW2ItemId(12546)
    val `Leek` = GW2ItemId(12508)
    val `Kale Leaf` = GW2ItemId(12333)
    val `Head of Lettuce` = GW2ItemId(12238)
    val `Head of Garlic` = GW2ItemId(12163)
    val `Head of Cauliflower` = GW2ItemId(12532)
    val `Head of Cabbage` = GW2ItemId(12332)
    val `Handful of Red Lentils` = GW2ItemId(82866)
    val `Green Onion` = GW2ItemId(12533)
    val `Grape` = GW2ItemId(12341)
    val `Ghost Pepper` = GW2ItemId(12544)
    val `Dill Sprig` = GW2ItemId(12336)
    val `Clove` = GW2ItemId(12534)
    val `Chili Pepper` = GW2ItemId(12331)
    val `Cayenne Pepper` = GW2ItemId(12504)
    val `Cassava Root` = GW2ItemId(73113)
    val `Carrot` = GW2ItemId(12134)
    val `Butternut Squash` = GW2ItemId(12511)
    val `Blueberry` = GW2ItemId(12255)
    val `Blackberry` = GW2ItemId(12537)
    val `Black Peppercorn` = GW2ItemId(12236)
    val `Beet` = GW2ItemId(12161)
    val `Bay Leaf` = GW2ItemId(12247)
    val `Asparagus Spear` = GW2ItemId(12505)
    val `Artichoke` = GW2ItemId(12512)

    val `Orichalcum Ore` = GW2ItemId(19701)
    val `Mithril Ore` = GW2ItemId(19700)
    val `Platinum Ore` = GW2ItemId(19702)
    val `Gold Ore` = GW2ItemId(19698)
    val `Silver Ore` = GW2ItemId(19703)
    val `Iron Ore` = GW2ItemId(19699)
    val `Copper Ore` = GW2ItemId(19697)

    val `Green Wood Log` = GW2ItemId(19723)
    val `Soft Wood Log` = GW2ItemId(19726)
    val `Seasoned Wood Log` = GW2ItemId(19727)
    val `Hard Wood Log` = GW2ItemId(19724)
    val `Elder Wood Log` = GW2ItemId(19722)
    val `Ancient Wood Log` = GW2ItemId(19725)

    val `Shared Inventory Slot` = GW2ItemId(67071)
    val `Bank Tab Expansion` = GW2ItemId(19995)
    val `Material Storage Expander` = GW2ItemId(42932)

    object VisionIds {

        val `Vision I - Awakening` = GW2AchievementId(4762)
        val `Visions of Istan` = GW2AchievementId(4765)
        val `Visions of Sandswept Isles` = GW2AchievementId(4774)
        val `Visions of Kourna` = GW2AchievementId(4760)
        val `Visions of Jahai` = GW2AchievementId(4770)
        val `Visions of Thunderhead Peaks` = GW2AchievementId(4764)
        val `Visions of Dragonfall` = GW2AchievementId(4757)

        val `Daybreak`= GW2AchievementId(3988)
        val `A Bug in the System`= GW2AchievementId(4093)
        val `Long Live the Lich`= GW2AchievementId(4195)
        val `A Star to Guide Us`= GW2AchievementId(4359)
        val `All or Nothing`= GW2AchievementId(4544)
        val `War Eternal`= GW2AchievementId(4689)

        /**
         * Istan - Complete 30
         */
        val `Daybreak Achievements` = listOf(
            GW2AchievementId(3962),
            GW2AchievementId(3955),
            GW2AchievementId(4040),
            GW2AchievementId(4009),
            GW2AchievementId(3994),
            GW2AchievementId(3996),
            GW2AchievementId(3997),
            GW2AchievementId(3984),
            GW2AchievementId(3976),
            GW2AchievementId(4002),
            GW2AchievementId(4022),
            GW2AchievementId(4014),
            GW2AchievementId(3982),
            GW2AchievementId(4032),
            GW2AchievementId(3986),
            GW2AchievementId(3975),
            GW2AchievementId(3965),
            GW2AchievementId(4039),
            GW2AchievementId(3999),
            GW2AchievementId(3978),
            GW2AchievementId(4027),
            GW2AchievementId(4041),
            GW2AchievementId(4034),
            GW2AchievementId(3959),
            GW2AchievementId(3983),
            GW2AchievementId(4023),
            GW2AchievementId(3958),
            GW2AchievementId(4013),
            GW2AchievementId(4007),
            GW2AchievementId(3969),
            GW2AchievementId(4042),
            GW2AchievementId(3985),
            GW2AchievementId(3989),
            GW2AchievementId(3992),
            GW2AchievementId(3960),
            GW2AchievementId(4008),
            GW2AchievementId(3995),
            GW2AchievementId(3991),
            GW2AchievementId(4031),
            GW2AchievementId(3968),
            GW2AchievementId(4019),
            GW2AchievementId(3981),
            GW2AchievementId(4029),
            GW2AchievementId(4026),
        )

        /**
         * Sandswept Isles - Complete 35
         */
        val `A Bug in the System Achievements` = listOf(
            GW2AchievementId(4146),
            GW2AchievementId(4115),
            GW2AchievementId(4092),
            GW2AchievementId(4091),
            GW2AchievementId(4098),
            GW2AchievementId(4148),
            GW2AchievementId(4133),
            GW2AchievementId(4105),
            GW2AchievementId(4121),
            GW2AchievementId(4136),
            GW2AchievementId(4143),
            GW2AchievementId(4095),
            GW2AchievementId(4124),
            GW2AchievementId(4147),
            GW2AchievementId(4135),
            GW2AchievementId(4139),
            GW2AchievementId(4114),
            GW2AchievementId(4122),
            GW2AchievementId(4149),
            GW2AchievementId(4142),
            GW2AchievementId(4132),
            GW2AchievementId(4127),
            GW2AchievementId(4100),
            GW2AchievementId(4117),
            GW2AchievementId(4130),
            GW2AchievementId(4126),
            GW2AchievementId(4137),
            GW2AchievementId(4120),
            GW2AchievementId(4140),
            GW2AchievementId(4145),
            GW2AchievementId(4101),
            GW2AchievementId(4119),
            GW2AchievementId(4110),
            GW2AchievementId(4129),
            GW2AchievementId(4090),
            GW2AchievementId(4131),
            GW2AchievementId(4102),
            GW2AchievementId(4144),
            GW2AchievementId(4106),
            GW2AchievementId(4112),
            GW2AchievementId(4103),
            GW2AchievementId(4141),
            GW2AchievementId(4108),
            GW2AchievementId(4125),
            GW2AchievementId(4096),
            GW2AchievementId(4134),
            GW2AchievementId(4150),
        )

        /**
         * Kourna - Complete 38
         */
        val `Long Live the Lich Achievements` = listOf(
            GW2AchievementId(4236),
            GW2AchievementId(4214),
            GW2AchievementId(4230),
            GW2AchievementId(4201),
            GW2AchievementId(4271),
            GW2AchievementId(4247),
            GW2AchievementId(4238),
            GW2AchievementId(4259),
            GW2AchievementId(4234),
            GW2AchievementId(4246),
            GW2AchievementId(4269),
            GW2AchievementId(4227),
            GW2AchievementId(4194),
            GW2AchievementId(4182),
            GW2AchievementId(4198),
            GW2AchievementId(4192),
            GW2AchievementId(4203),
            GW2AchievementId(4216),
            GW2AchievementId(4199),
            GW2AchievementId(4256),
            GW2AchievementId(4262),
            GW2AchievementId(4250),
            GW2AchievementId(4272),
            GW2AchievementId(4208),
            GW2AchievementId(4204),
            GW2AchievementId(4264),
            GW2AchievementId(4231),
            GW2AchievementId(4243),
            GW2AchievementId(4217),
            GW2AchievementId(4223),
            GW2AchievementId(4253),
            GW2AchievementId(4209),
            GW2AchievementId(4220),
            GW2AchievementId(4188),
            GW2AchievementId(4213),
            GW2AchievementId(4255),
            GW2AchievementId(4228),
            GW2AchievementId(4206),
            GW2AchievementId(4261),
            GW2AchievementId(4258),
            GW2AchievementId(4186),
            GW2AchievementId(4235),
            GW2AchievementId(4207),
            GW2AchievementId(4251),
            GW2AchievementId(4233),
            GW2AchievementId(4265),
            GW2AchievementId(4205),
            GW2AchievementId(4270),
        )

        /**
         * Jahai Bluffs - Complete 38
         */
        val `A Star to Guide Us Achievements` = listOf(
            GW2AchievementId(4342),
            GW2AchievementId(4346),
            GW2AchievementId(4392),
            GW2AchievementId(4372),
            GW2AchievementId(4358),
            GW2AchievementId(4347),
            GW2AchievementId(4378),
            GW2AchievementId(4424),
            GW2AchievementId(4389),
            GW2AchievementId(4401),
            GW2AchievementId(4384),
            GW2AchievementId(4369),
            GW2AchievementId(4400),
            GW2AchievementId(4393),
            GW2AchievementId(4365),
            GW2AchievementId(4343),
            GW2AchievementId(4370),
            GW2AchievementId(4353),
            GW2AchievementId(4428),
            GW2AchievementId(4418),
            GW2AchievementId(4360),
            GW2AchievementId(4381),
            GW2AchievementId(4414),
            GW2AchievementId(4377),
            GW2AchievementId(4430),
            GW2AchievementId(4349),
            GW2AchievementId(4363),
            GW2AchievementId(4427),
            GW2AchievementId(4434),
            GW2AchievementId(4413),
            GW2AchievementId(4421),
            GW2AchievementId(4371),
            GW2AchievementId(4425),
            GW2AchievementId(4390),
            GW2AchievementId(4394),
            GW2AchievementId(4352),
            GW2AchievementId(4350),
            GW2AchievementId(4385),
            GW2AchievementId(4403),
            GW2AchievementId(4417),
            GW2AchievementId(4351),
            GW2AchievementId(4375),
            GW2AchievementId(4374),
            GW2AchievementId(4373),
            GW2AchievementId(4366),
            GW2AchievementId(4387),
            GW2AchievementId(4419),
            GW2AchievementId(4348),
            GW2AchievementId(4433),
            GW2AchievementId(4426),
            GW2AchievementId(4398),
            GW2AchievementId(4406),
            GW2AchievementId(4376),
            GW2AchievementId(4362),
        )

        /**
         * Thunderhead Peaks - Complete 30
         */
        val `All or Nothing Achievements` = listOf(
            GW2AchievementId(4538),
            GW2AchievementId(4550),
            GW2AchievementId(4569),
            GW2AchievementId(4586),
            GW2AchievementId(4493),
            GW2AchievementId(4576),
            GW2AchievementId(4594),
            GW2AchievementId(4617),
            GW2AchievementId(4513),
            GW2AchievementId(4497),
            GW2AchievementId(4588),
            GW2AchievementId(4618),
            GW2AchievementId(4567),
            GW2AchievementId(4510),
            GW2AchievementId(4553),
            GW2AchievementId(4605),
            GW2AchievementId(4591),
            GW2AchievementId(4499),
            GW2AchievementId(4596),
            GW2AchievementId(4522),
            GW2AchievementId(4516),
            GW2AchievementId(4507),
            GW2AchievementId(4564),
            GW2AchievementId(4590),
            GW2AchievementId(4498),
            GW2AchievementId(4604),
            GW2AchievementId(4621),
            GW2AchievementId(4559),
            GW2AchievementId(4616),
            GW2AchievementId(4600),
            GW2AchievementId(4515),
            GW2AchievementId(4545),
            GW2AchievementId(4579),
            GW2AchievementId(4601),
            GW2AchievementId(4577),
            GW2AchievementId(4506),
            GW2AchievementId(4503),
            GW2AchievementId(4532),
            GW2AchievementId(4539),
        )

        /**
         * Dragonfall - Complete 18
         */
        val `War Eternal Achievements` = listOf(
            GW2AchievementId(4673),
            GW2AchievementId(4753),
            GW2AchievementId(4744),
            GW2AchievementId(4700),
            GW2AchievementId(4747),
            GW2AchievementId(4740),
            GW2AchievementId(4723),
            GW2AchievementId(4715),
            GW2AchievementId(4669),
            GW2AchievementId(4731),
            GW2AchievementId(4721),
            GW2AchievementId(4697),
            GW2AchievementId(4687),
            GW2AchievementId(4708),
            GW2AchievementId(4722),
            GW2AchievementId(4663),
            GW2AchievementId(4737),
            GW2AchievementId(4705),
        )
    }

    val `Eternity` = GW2ItemId(30689)

    object AchievementCategories {
        val `No Quarter` = GW2AchievementCategoryId(253)
    }

    object GuardianIds {
        val Guardian = GW2ProfessionId("Guardian")
        val Luminary = GW2SpecializationId(81) // Guardian - elite
        val Dragonhunter = GW2SpecializationId(27) // Guardian - elite
        val Firebrand = GW2SpecializationId(62) // Guardian - elite
        val Willbender = GW2SpecializationId(65) // Guardian - elite

        val Virtues = GW2SpecializationId(46) // Guardian
        val Valor = GW2SpecializationId(13) // Guardian
        val Radiance = GW2SpecializationId(16) // Guardian
        val Zeal = GW2SpecializationId(42) // Guardian
        val Honor = GW2SpecializationId(49) // Guardian
    }

    object WarriorIds {
        val Warrior = GW2ProfessionId("Warrior")
        val Berserker = GW2SpecializationId(18) // Warrior - elite
        val Bladesworn = GW2SpecializationId(68) // Warrior - elite
        val Spellbreaker = GW2SpecializationId(61) // Warrior - elite
        val Paragon = GW2SpecializationId(74) // Warrior - elite

        val Strength = GW2SpecializationId(4) // Warrior
        val Tactics = GW2SpecializationId(11) // Warrior
        val Defense = GW2SpecializationId(22) // Warrior
        val Arms = GW2SpecializationId(36) // Warrior
        val Discipline = GW2SpecializationId(51) // Warrior
    }

    object EngineerIds {
        val Engineer = GW2ProfessionId("Engineer")
        val Amalgam = GW2SpecializationId(75) // Engineer - elite
        val Mechanist = GW2SpecializationId(70) // Engineer - elite
        val Holosmith = GW2SpecializationId(57) // Engineer - elite
        val Scrapper = GW2SpecializationId(43) // Engineer - elite

        val Explosives = GW2SpecializationId(6) // Engineer
        val Alchemy = GW2SpecializationId(29) // Engineer
        val Firearms = GW2SpecializationId(38) // Engineer
        val Inventions = GW2SpecializationId(47) // Engineer
        val Tools = GW2SpecializationId(21) // Engineer
    }

    object RangerIds {
        val Ranger = GW2ProfessionId("Ranger")
        val Galeshot = GW2SpecializationId(78) // Ranger - elite
        val Untamed = GW2SpecializationId(72) // Ranger - elite
        val Soulbeast = GW2SpecializationId(55) // Ranger - elite
        val Druid = GW2SpecializationId(5) // Ranger - elite

        val Marksmanship = GW2SpecializationId(8) // Ranger
        val `Nature Magic` = GW2SpecializationId(25) // Ranger
        val Skirmishing = GW2SpecializationId(30) // Ranger
        val Beastmastery = GW2SpecializationId(32) // Ranger
        val `Wilderness Survival` = GW2SpecializationId(33) // Ranger
    }

    object ThiefIds {
        val Thief = GW2ProfessionId("Thief")
        val Antiquary = GW2SpecializationId(77) // Thief - elite
        val Specter = GW2SpecializationId(71) // Thief - elite
        val Deadeye = GW2SpecializationId(58) // Thief - elite
        val Daredevil = GW2SpecializationId(7) // Thief - elite

        val Trickery = GW2SpecializationId(44) // Thief
        val Acrobatics = GW2SpecializationId(54) // Thief
        val `Shadow Arts` = GW2SpecializationId(20) // Thief
        val `Critical Strikes` = GW2SpecializationId(35) // Thief
        val `Deadly Arts` = GW2SpecializationId(28) // Thief
    }

    object ElementalistIds {
        val Elementalist = GW2ProfessionId("Elementalist")
        val Evoker = GW2SpecializationId(80) // Elementalist - elite
        val Catalyst = GW2SpecializationId(67) // Elementalist - elite
        val Tempest = GW2SpecializationId(48) // Elementalist - elite
        val Weaver = GW2SpecializationId(56) // Elementalist - elite

        val Water = GW2SpecializationId(17) // Elementalist
        val Earth = GW2SpecializationId(26) // Elementalist
        val Fire = GW2SpecializationId(31) // Elementalist
        val Arcane = GW2SpecializationId(37) // Elementalist
        val Air = GW2SpecializationId(41) // Elementalist
    }

    object MesmerIds {
        val Mesmer = GW2ProfessionId("Mesmer")
        val Troubadour = GW2SpecializationId(73) // Mesmer - elite
        val Mirage = GW2SpecializationId(59) // Mesmer - elite
        val Chronomancer = GW2SpecializationId(40) // Mesmer - elite
        val Virtuoso = GW2SpecializationId(66) // Mesmer - elite

        val Dueling = GW2SpecializationId(1) // Mesmer
        val Domination = GW2SpecializationId(10) // Mesmer
        val Inspiration = GW2SpecializationId(23) // Mesmer
        val Chaos = GW2SpecializationId(45) // Mesmer
        val Illusions = GW2SpecializationId(24) // Mesmer
    }

    object NecromancerIds {
        val Necromancer = GW2ProfessionId("Necromancer")
        val Ritualist = GW2SpecializationId(76) // Necromancer - elite
        val Harbinger = GW2SpecializationId(64) // Necromancer - elite
        val Scourge = GW2SpecializationId(60) // Necromancer - elite
        val Reaper = GW2SpecializationId(34) // Necromancer - elite

        val `Death Magic` = GW2SpecializationId(2) // Necromancer
        val Spite = GW2SpecializationId(53) // Necromancer
        val `Blood Magic` = GW2SpecializationId(19) // Necromancer
        val Curses = GW2SpecializationId(39) // Necromancer
        val `Soul Reaping` = GW2SpecializationId(50) // Necromancer
    }

    object RevenantIds {
        val Revenant = GW2ProfessionId("Revenant")
        val Conduit = GW2SpecializationId(79) // Revenant - elite
        val Vindicator = GW2SpecializationId(69) // Revenant - elite
        val Herald = GW2SpecializationId(52) // Revenant - elite
        val Renegade = GW2SpecializationId(63) // Revenant - elite

        val Invocation = GW2SpecializationId(3) // Revenant
        val Retribution = GW2SpecializationId(9) // Revenant
        val Corruption = GW2SpecializationId(14) // Revenant
        val Devastation = GW2SpecializationId(15) // Revenant
        val Salvation = GW2SpecializationId(12) // Revenant
    }

    val professionSpecializations = mapOf(
        GuardianIds.Guardian to listOf(
            GuardianIds.Zeal,
            GuardianIds.Radiance,
            GuardianIds.Valor,
            GuardianIds.Honor,
            GuardianIds.Virtues,
        ),
        RevenantIds.Revenant to listOf(
            RevenantIds.Corruption,
            RevenantIds.Retribution,
            RevenantIds.Salvation,
            RevenantIds.Invocation,
            RevenantIds.Devastation,
        ),
        WarriorIds.Warrior to listOf(
            WarriorIds.Strength,
            WarriorIds.Arms,
            WarriorIds.Defense,
            WarriorIds.Tactics,
            WarriorIds.Discipline,
        ),
        EngineerIds.Engineer to listOf(
            EngineerIds.Explosives,
            EngineerIds.Firearms,
            EngineerIds.Inventions,
            EngineerIds.Alchemy,
            EngineerIds.Tools,
        ),
        RangerIds.Ranger to listOf(
            RangerIds.Marksmanship,
            RangerIds.Skirmishing,
            RangerIds.`Wilderness Survival`,
            RangerIds.`Nature Magic`,
            RangerIds.Beastmastery,
        ),
        ThiefIds.Thief to listOf(
            ThiefIds.`Deadly Arts`,
            ThiefIds.`Critical Strikes`,
            ThiefIds.`Shadow Arts`,
            ThiefIds.Acrobatics,
            ThiefIds.Trickery,
        ),
        ElementalistIds.Elementalist to listOf(
            ElementalistIds.Fire,
            ElementalistIds.Air,
            ElementalistIds.Earth,
            ElementalistIds.Water,
            ElementalistIds.Arcane,
        ),
        MesmerIds.Mesmer to listOf(
            MesmerIds.Domination,
            MesmerIds.Dueling,
            MesmerIds.Chaos,
            MesmerIds.Inspiration,
            MesmerIds.Illusions,
        ),
        NecromancerIds.Necromancer to listOf(
            NecromancerIds.Spite,
            NecromancerIds.Curses,
            NecromancerIds.`Death Magic`,
            NecromancerIds.`Blood Magic`,
            NecromancerIds.`Soul Reaping`,
        ),
    )

    val professionElites = mapOf(
        GuardianIds.Guardian to listOf(
            GuardianIds.Dragonhunter,
            GuardianIds.Firebrand,
            GuardianIds.Willbender,
            GuardianIds.Luminary,
        ),
        RevenantIds.Revenant to listOf(
            RevenantIds.Herald,
            RevenantIds.Renegade,
            RevenantIds.Vindicator,
            RevenantIds.Conduit,
        ),
        WarriorIds.Warrior to listOf(
            WarriorIds.Berserker,
            WarriorIds.Spellbreaker,
            WarriorIds.Bladesworn,
            WarriorIds.Paragon,
        ),
        EngineerIds.Engineer to listOf(
            EngineerIds.Scrapper,
            EngineerIds.Holosmith,
            EngineerIds.Mechanist,
            EngineerIds.Amalgam,
        ),
        RangerIds.Ranger to listOf(
            RangerIds.Druid,
            RangerIds.Soulbeast,
            RangerIds.Untamed,
            RangerIds.Galeshot,
        ),
        ThiefIds.Thief to listOf(
            ThiefIds.Daredevil,
            ThiefIds.Deadeye,
            ThiefIds.Specter,
            ThiefIds.Antiquary,
        ),
        ElementalistIds.Elementalist to listOf(
            ElementalistIds.Tempest,
            ElementalistIds.Weaver,
            ElementalistIds.Catalyst,
            ElementalistIds.Evoker,
        ),
        MesmerIds.Mesmer to listOf(
            MesmerIds.Chronomancer,
            MesmerIds.Mirage,
            MesmerIds.Virtuoso,
            MesmerIds.Troubadour,
        ),
        NecromancerIds.Necromancer to listOf(
            NecromancerIds.Reaper,
            NecromancerIds.Scourge,
            NecromancerIds.Harbinger,
            NecromancerIds.Ritualist,
        ),
    )

    val wellKnownItemNames = mapOf(
        "Vision" to GW2ItemId(91048),
    )

    fun findWellKnownItemByName(name: String): GW2ItemId {
        return wellKnownItemNames[name] ?: throw IllegalArgumentException("Unknown item name $name")
    }
}
