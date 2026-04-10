package northburns.gw2.site.layout.components

import io.kvision.core.Component
import io.kvision.core.Container
import io.kvision.core.VerticalAlign
import io.kvision.html.Align
import io.kvision.html.TAG
import io.kvision.html.Table
import io.kvision.html.Tag
import io.kvision.html.Tbody
import io.kvision.html.Td
import io.kvision.html.Th
import io.kvision.html.Thead
import io.kvision.html.td
import io.kvision.html.th
import io.kvision.html.tr

fun <T : Any> Container.simpleTable(
    data: List<T>,
    options: SimpleTableOptions<T>,
) = SimpleTable(data, options).also { add(it) }

class SimpleTableOptions<T : Any>(
    val columns: List<SimpleTableColumn<T>>,
    val footer: Boolean = false,
)

class SimpleTableColumn<T : Any>(
    val align: Align? = null,

    val header: (() -> String)? = null,
    val headerInit: (Th.() -> Unit)? = null,

    val cell: ((i: Int, t: T) -> String)? = null,
    val cellInit: (Td.(i: Int, t: T) -> Unit)? = null,

    val footer: (() -> String)? = null,
    val footerInit: (Td.() -> Unit)? = null,

    )

class SimpleTable<T : Any>(
    val data: List<T>,
    val options: SimpleTableOptions<T>,
    private val table: Table = Table(className = "table table-striped table-sm table-responsive"),
) : Component by table {

    init {
        table.setAttribute("border", "1") // TODO remove this and do styling
        table.setStyle("margin-bottom", "0px")

        Thead {
            tr {
                options.columns.forEach { column ->
                    th(content = column.header?.invoke(), align = column.align).apply {
                        verticalAlign = VerticalAlign.BOTTOM
                        column.headerInit?.invoke(this)
                    }
                }
            }
        }.also(table::add)

        Tbody(className = "table-group-divider") {
            data.forEachIndexed { rowIndex, row ->
                tr {
                    options.columns.forEach { column ->
                        td(content = column.cell?.invoke(rowIndex, row), align = column.align).apply {
                            verticalAlign = VerticalAlign.MIDDLE
                            column.cellInit?.invoke(this, rowIndex, row)
                        }
                    }
                }
            }
        }.also(table::add)

        if (options.footer)
            Tag(TAG.TFOOT, className = "table-group-divider") {
                tr {
                    options.columns.forEach { column ->
                        td(content = column.footer?.invoke(), align = column.align).apply {
                            verticalAlign = VerticalAlign.TOP
                            column.footerInit?.invoke(this)
                        }
                    }
                }
            }.also(table::add)
    }

}