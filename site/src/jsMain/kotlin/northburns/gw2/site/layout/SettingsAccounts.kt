package northburns.gw2.site.layout

import io.kvision.form.select.select
import io.kvision.form.text.textInput
import io.kvision.html.p
import io.kvision.panel.SimplePanel
import io.kvision.state.bindTo
import kotlinx.coroutines.launch
import northburns.gw2.client.myclient.PlayerApiKey
import northburns.gw2.client.myclient.PlayerId
import northburns.gw2.client.myclient.log.GoonLog
import northburns.gw2.site.Gw2GoonManager
import northburns.gw2.site.app.Account
import northburns.gw2.site.app.AccountData
import northburns.gw2.site.app.CustomAccount
import northburns.gw2.site.app.Gw2MeAccount
import northburns.gw2.site.layout.components.GoonButton
import northburns.gw2.site.layout.components.goonButton
import northburns.gw2.site.util.ValueHolder.Companion.asState
import northburns.gw2.site.util.gw2me.Gw2MeUserSettings
import northburns.gw2.site.util.gw2me.Gw2MeUserSettings.Gw2MeCustomAccount

class SettingsAccounts(
    private val initial: AccountData,
) : SimplePanel() {

    private val rowsContainer = SimplePanel()
    private val accountrows = mutableListOf<AccountRow>()

    private fun addRow(account: Account) {
        AccountRow(account, initial.partyAccountIds).also { r ->
            rowsContainer.add(r)
            accountrows.add(r)
            GoonLog["DUDE"].error(accountrows.size)
            r.addAfterDestroyHook { accountrows.remove(r) }
        }
    }

    init {

        p("Accounts + metadata")

        add(rowsContainer)

        initial.accounts.forEach { account -> addRow(account) }

        goonButton(text = "Add account") {
            addRow(CustomAccount("", "👤", PlayerApiKey("")))
        }

        goonButton(GoonButton.GoonButtonStyle.ACTION, "Save") { save() }
    }

    private fun save() {
        GoonLog["SettingsAccounts"].info("Saving accounts, count: ", accountrows.size, rowsContainer.getChildren().size)
        val custom = mutableListOf<Gw2MeCustomAccount>()
        val emojis = mutableMapOf<String, String>()
        val party = arrayOfNulls<String>(5)
        accountrows.forEach { row ->
            val accountName = row.accountName.getState()
            when (row.account) {
                is Gw2MeAccount -> emojis[accountName] = row.accountEmoji.getState()
                is CustomAccount -> Gw2MeCustomAccount(
                    name = accountName,
                    emoji = row.accountEmoji.getState(),
                    apiKey = row.apiKey.getState(),
                ).also { custom.add(it) }
            }
            row.partyIndex.getState().toIntOrNull()?.let {
                party[it - 1] = accountName
            }
        }

        val extra = Gw2MeUserSettings(
            custom = custom,
            emojisForAccounts = emojis,
            partyAccounts = party.filterNotNull(),
        )
        Gw2GoonManager.launch {
            Gw2GoonManager.gw2me.saveSettings(extra, initial)
        }
    }

    private class AccountRow(
        val account: Account,
        partyAccountIds: List<PlayerId>,
    ) : SimplePanel() {

        val partyIndex = partyAccountIds.indexOf(account.id).let { i ->
            if (i == -1) "" else (i + 1).toString()
        }.asState()
        val accountName = account.name.asState()
        val accountEmoji = account.emoji.asState()
        val apiKey = when (account) {
            is CustomAccount -> account.apiKey.key
            is Gw2MeAccount -> "From gw2.me"
        }.asState()

        private val logger = GoonLog["AccountRow"]

        init {

            select(
                options = listOf(
                    "" to "",
                    "1" to "1",
                    "2" to "2",
                    "3" to "3",
                    "4" to "4",
                    "5" to "5",
                ),
            ).bindTo(partyIndex)

            textInput {
                disabled = when (account) {
                    is CustomAccount -> false
                    is Gw2MeAccount -> true
                }
            }.bindTo(accountName)

            textInput {
            }.bindTo(accountEmoji)

            textInput {
                readonly = when (account) {
                    is CustomAccount -> false
                    is Gw2MeAccount -> true
                }
            }.bindTo(apiKey)

            val canDelete = when (account) {
                is CustomAccount -> true
                is Gw2MeAccount -> false
            }
            if (canDelete)
                goonButton(GoonButton.GoonButtonStyle.ACTION, "Remove account") {
                    this@AccountRow.parent?.remove(this@AccountRow)
                }
        }

    }

}
