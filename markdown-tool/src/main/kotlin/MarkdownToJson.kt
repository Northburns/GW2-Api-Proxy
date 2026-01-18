@file:JvmName("MarkdownToJson")

package northburns.gw2

import com.vladsch.flexmark.ext.gfm.strikethrough.StrikethroughExtension
import com.vladsch.flexmark.ext.macros.MacrosExtension
import com.vladsch.flexmark.ext.tables.TablesExtension
import com.vladsch.flexmark.ext.xwiki.macros.MacroExtension
import com.vladsch.flexmark.ext.yaml.front.matter.YamlFrontMatterExtension
import com.vladsch.flexmark.html.HtmlRenderer
import com.vladsch.flexmark.parser.Parser
import com.vladsch.flexmark.util.ast.Document
import com.vladsch.flexmark.util.data.MutableDataSet
import northburns.gw2.util.FileUtil
import java.nio.file.Files
import kotlin.io.path.extension
import kotlin.io.path.isRegularFile
import kotlin.io.path.name
import kotlin.io.path.readText
import kotlin.io.path.relativeTo
import kotlin.io.path.walk
import kotlin.io.path.writeText


fun main() {
    val contentDir = FileUtil.projectDir.resolve("content")
    val outDir = FileUtil.projectDir.resolve("site/src/jsMain/resources/content")

    val options = MutableDataSet()
    options.set(
        Parser.EXTENSIONS, listOf(
            TablesExtension.create(),
            StrikethroughExtension.create(),
            YamlFrontMatterExtension.create(),
            MacrosExtension.create(), // https://github.com/vsch/flexmark-java/wiki/Macros-Extension
            MacroExtension.create(), // https://github.com/vsch/flexmark-java/wiki/Extensions#xwiki-macro-extension
        )
    );
    val parser = Parser.builder(options).build()
    val renderer: HtmlRenderer = HtmlRenderer.builder(options).build()
    val r = MdJsonRenderer()

    println("===============================================")

    contentDir.walk()
        .filter { it.isRegularFile() }
        .forEach { file ->
            val relative = file.parent.relativeTo(contentDir).normalize()
            val dir = outDir.resolve(relative).normalize()
            dir.toFile().mkdirs()

            if (file.extension == "md") {
                val i = file.readText(Charsets.UTF_8)
                val dIn: Document = parser.parse(i)
                val json = r.render(dIn)
                val jsonFileName = file.name + ".json"
                val jsonFile = dir.resolve(jsonFileName)
                jsonFile.writeText(json)
            } else {
                Files.copy(file, dir)
            }
            println(relative)
            println(dir)

            //outDir.resolve(jsonFileName).writeText(json)
        }

//    outDir.resolve("index.json").writeText(
//        JsonMd.json.encodeToString(index)
//    )

    println("===============================================")

}
