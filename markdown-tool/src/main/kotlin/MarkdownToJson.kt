@file:JvmName("MarkdownToJson")

package northburns.gw2

import com.vladsch.flexmark.ext.gfm.strikethrough.StrikethroughExtension
import com.vladsch.flexmark.ext.tables.TablesExtension
import com.vladsch.flexmark.parser.Parser
import com.vladsch.flexmark.util.ast.Document
import com.vladsch.flexmark.util.data.MutableDataSet
import northburns.gw2.markdown.model.JsonMd


fun main() {
    val contentDir = FileUtil.findDir("content")
    val outDir = FileUtil.findDir("content-out")

    val options = MutableDataSet()
    options.set(Parser.EXTENSIONS, listOf(TablesExtension.create(), StrikethroughExtension.create()));
    val parser = Parser.builder(options).build()

    contentDir.walk()
        .filter { it.extension == "md" }
        .forEach { file ->
            val i = file.readText(Charsets.UTF_8)
            println(i)
            val dIn: Document = parser.parse(i)
            val dOut = MarkDownMapper.document(dIn)
            println(dOut)
            val dJson = JsonMd.json.encodeToString(dOut)
            println(dJson)
        }

}
