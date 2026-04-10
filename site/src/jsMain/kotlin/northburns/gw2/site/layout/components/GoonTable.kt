package northburns.gw2.site.layout.components

import io.kvision.core.Component
import io.kvision.core.Container
import io.kvision.core.onEvent
import io.kvision.state.bind
import io.kvision.tabulator.Align
import io.kvision.tabulator.ColumnDefinition
import io.kvision.tabulator.Formatter
import io.kvision.tabulator.Layout
import io.kvision.tabulator.ResponsiveLayout
import io.kvision.tabulator.SortingDir
import io.kvision.tabulator.SortingDir.ASC
import io.kvision.tabulator.SortingDir.DESC
import io.kvision.tabulator.TableType
import io.kvision.tabulator.Tabulator
import io.kvision.tabulator.TabulatorOptions
import io.kvision.tabulator.VAlign
import io.kvision.tabulator.js.Tabulator.CellComponent
import io.kvision.tabulator.js.Tabulator.ColumnComponent
import io.kvision.tabulator.js.Tabulator.RowComponent
import northburns.gw2.client.myclient.log.GoonLog
import northburns.gw2.site.Gw2GoonManager.goonStore
import northburns.gw2.site.app.GoonState

class GoonTable<T : Any>(
    private val id: (T) -> String,
    columns: ColumnBuilder.() -> Unit,
    private val build: GoonTable<T>.() -> Unit = {},
) {

    private val cols: List<Column<*>> = mutableListOf<Column<Any>>().also { list ->
        ColumnBuilder(list)
            .column(key = "id", title = "id", value = id, visible = false)
            .apply(columns)
    }
    private val colsByKey = cols.associateBy { it.key }

    private val tabulator = Tabulator<dynamic>(
        data = listOf(),
        dataUpdateOnEdit = false,
        options = TabulatorOptions(
            // https://tabulator.info/docs/6.3/reactivity
            // manipulate data by manipulating js array (doesn't watch nested properties of rows, which is fine)
            reactiveData = true,
            // https://tabulator.info/docs/6.3/layout#resize-table
            // Redraw data if container's size changes
            autoResize = true,

            autoColumns = false,

            // https://tabulator.info/docs/6.3/layout#layout
            // FITDATA = if no width/minWidth, column sized to data. Possible scrollbar
            // FITDATAFILL = as FITDATA, but rows are at least the table's width
            // FITDATASTRETCH = as FITDATAFILL, but instead of stretching "empty", stretches last column
            // FITDATATABLE = as FITDATA, but resizes table to match columns' total width
            // FITCOLUMNS = as FITDATA, but extra space is distributed to columns (use widthGrow/widthShrink)
            layout = Layout.FITDATATABLE,

            // https://tabulator.info/docs/6.3/layout#layoutcolumnsonnewdata
            layoutColumnsOnNewData = true,

            responsiveLayout = ResponsiveLayout.COLLAPSE,
            responsiveLayoutCollapseStartOpen = true,

            movableColumns = false,

            // TODO selectablerows feature

            resizableRows = false,

            // initialSort ?
            // initialFilter ?

            // https://tabulator.info/docs/6.3/print#style
            // OH MY! YES! INTERESTING!
            // TODO check the documentation for details!
            printAsHtml = true,


            invalidOptionWarnings = true,

            // TODO dataTree, does that interest us?

            columnHeaderVertAlign = VAlign.BOTTOM,

            // TODO rowContextMenu / rowClickMenu interesting!

            // https://tabulator.info/docs/6.3/layout#row-header
            // rowHeader = true,

            // https://tabulator.info/docs/6.3/group
            // groupBy =

            columns = cols.map { it.coldef },
        ),
        types = setOf(
            TableType.BORDERED,
            TableType.SMALL,
            TableType.STRIPED,
        ),
        className = "goon-table",
    )
    lateinit var jsTabulator: io.kvision.tabulator.js.Tabulator

    fun addTo(c: Container): GoonTable<T> = this.also { c.add(tabulator) }
    fun <X> bind(sub: (GoonState)->X, block: GoonTable<T>.(X)->Unit): GoonTable<T> {
        tabulator.bind(goonStore, sub = sub,removeChildren = false, runImmediately = true) {
            block(it)
        }
        return this
    }

    inner class Column<V : Any>(
        val key: String,
        val value: (T) -> V,
        val coldef: ColumnDefinition<T>,
    )

    inner class ColumnBuilder internal constructor(
        private val built: MutableList<Column<Any>>,
    ) {
        fun <V : Any> column(
            title: String,
            key: String = title,
            visible: Boolean? = null,
            align: Align? = null,
            hozAlign: Align? = null,
            vertAlign: VAlign? = null,
            headerHozAlign: Align? = null,
            headerVertical: Boolean? = null,
            widthGrow: Int? = null,
            widthShrink: Int? = null,
            sorter: Comparator<V>? = null,
            formatter: Formatter? = null,
            formatterFunction: ((V) -> String)? = null,
            formatterComponent: ((V) -> Component)? = null,
            topCalc: ColumnCalculator<V, *>? = null,
            bottomCalc: ColumnCalculator<V, *>? = null,
            value: (T) -> V,
        ): ColumnBuilder {
            val gt = this@GoonTable
            // https://tabulator.info/docs/6.3/columns
            val coldef = ColumnDefinition<T>(
                title = title,
                field = key,
                visible = visible,
                align = align,
                headerVertical = headerVertical,
                widthGrow = widthGrow,
                widthShrink = widthShrink,

                headerSort = sorter != null, // TODO headerSortTristate?
                sorterFunction = if (sorter == null) null else
                    { a: Any?, b: Any?, _: RowComponent, _: RowComponent, _: ColumnComponent, dir: SortingDir, _: Any? ->
                        @Suppress("UNCHECKED_CAST")
                        if (a == null && b == null) 0
                        else if (a == null) 1
                        else if (b == null) -1
                        else sorter.compare(a as V, b as V).let { result ->
                            when (dir) {
                                ASC -> result
                                DESC -> -result
                            }
                        }
                    },

                formatter = formatter,
                formatterFunction = if (formatterFunction == null) null else
                    { cell: CellComponent, _: Any?, _: (callback: () -> Unit) -> Unit ->
                        GoonLog["GoonTable"].e { log("TODO formatterFunction",cell.getValue()) }
//                        @Suppress("UNCHECKED_CAST") val t: V = cell.getValue() as V
//                        formatterFunction(t)
                        TODO()
                    },
                formatterComponentFunction = if (formatterComponent == null) null else
                    { _: CellComponent, _: (callback: () -> Unit) -> Unit, data: T ->
                        GoonLog["GoonTable"].e { log("TODO formatterComponentFunction",data) }
//                        formatterComponent(data)
                        TODO()
                    },

                variableHeight = false,

                // TODO editable feature

                topCalcFunc = if (topCalc == null) null else { _: dynamic, _: dynamic, _: dynamic ->
                    topCalc.calc(gt.getColumnValues(key))
                },
                topCalcFormatter = topCalc?.formatter,
                // TODO topCalc formatter function+component


                bottomCalcFunc = if (bottomCalc == null) null else { _: dynamic, _: dynamic, _: dynamic ->
                    bottomCalc.calc(gt.getColumnValues(key))
                },
                bottomCalcFormatter = bottomCalc?.formatter,
                // TODO bottomCalc formatter function+component

                // TODO titleFormatter?

                // TODO headerFilter feature

                // TODO htmlOutput =
                // TODO print / formatterPrint

                hozAlign = hozAlign,
                vertAlign = vertAlign,
                headerHozAlign = headerHozAlign,
            )
            built.add(Column(key, value, coldef))
            return this
        }
    }

    data class ColumnCalculator<T : Any, V : Any>(
        val calc: (Collection<T>) -> V,

        val formatter: Formatter? = null,
        val formatterFunction: ((V) -> String)? = null,
        val formatterComponent: ((V) -> Component)? = null,
    )

    private val dataBackup = mutableMapOf<String, T>()

    fun upsert(data: List<T>) {
        dataBackup += data.associateBy { id(it) }
        actAfterInitialized {
            // https://tabulator.info/docs/6.3/update#alter-update
            jsTabulator.updateOrAddData(data.map { convert(it) }.toTypedArray())
        }
    }

    fun replace(data: List<T>) {
        dataBackup.clear()
        dataBackup += data.associateBy { id(it) }
        actAfterInitialized {
            // https://tabulator.info/docs/6.3/update#alter-replace
            jsTabulator.replaceData(data.map { convert(it) }.toTypedArray(), null, null)
        }

    }

    private fun convert(t: T): dynamic {
        val d = js("{}")
        cols.forEach { col ->
            d[col.key] = col.value(t)
        }
        return d
    }

    private fun <V : Any> getColumnValues(columnKey: String): Collection<V> {
        return dataBackup.map { (_, v) ->
            @Suppress("UNCHECKED_CAST")
            colsByKey.getValue(columnKey).value(v) as V
        }

    }


    private var actions = mutableListOf<() -> Unit>()
    private fun actAfterInitialized(block: () -> Unit) {
        if (::jsTabulator.isInitialized) block()
        else actions.add(block)
    }

    init {
        // Validations
        require("id" in colsByKey) { "There must be a column with key='id'" }
        val columnKeysDefinedMultipleTimes = cols.groupBy { it.key }.values
            .filter { it.size > 1 }.map { it.first().key }
        require(columnKeysDefinedMultipleTimes.isEmpty()) { "Following keys defined multiple times: $columnKeysDefinedMultipleTimes" }

        tabulator.onEvent { // or tabulator.addAfterInsertHook should be fine too
            tableBuiltTabulator = { e ->
                jsTabulator = tabulator.jsTabulator!!
                actions.forEach { action -> action() }
                actions.clear()

                // Work
                build()
            }
        }

    }


    companion object {

    }
}
