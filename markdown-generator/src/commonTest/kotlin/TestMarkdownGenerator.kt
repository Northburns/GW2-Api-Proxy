import northburns.gw2.markdown.GoonMarkElementType
import northburns.gw2.markdown.GoonMarkGenerator
import northburns.gw2.markdown.GoonMarkGeneratorContext
import northburns.gw2.markdown.GoonMarkNode
import org.intellij.markdown.flavours.commonmark.CommonMarkFlavourDescriptor
import org.intellij.markdown.html.GeneratingProvider
import org.intellij.markdown.html.HtmlGenerator
import org.intellij.markdown.parser.MarkdownParser
import kotlin.test.Test

class TestMarkdownGenerator {
    @Test
    fun test1() {
        val src = """
            Hello *World*!
            
            ## Let's go
            
            * Why
            * It's
              * sooooo
            * cool?
            
            
        """.trimIndent()


        val flavour = CommonMarkFlavourDescriptor()
        val parser = MarkdownParser(flavour)

        val parsedTree = parser.buildMarkdownTreeFromString(src)

        val generator = HtmlGenerator(src, parsedTree, flavour, true)

        val html = generator.generateHtml()

        println(html)

        // kvG is a field that can be reused
        // Pass a project specific generatorContext to it
        val kvG = GoonMarkGenerator(TestGeneratorProvider())

        // At use site, call generate
        val root = TestNode(GoonMarkElementType.ELEMENT_MARKDOWN_FILE, "").apply {
            kvG.generate(src)
        }

        println(root)
    }

    private data class TestNode(
        val type: GoonMarkElementType,
        val txt: String,
        val children: MutableList<TestNode> = mutableListOf<TestNode>(),
    )

    private class TestGeneratorProvider : GoonMarkGeneratorContext<TestNode> {
        override fun render(node: GoonMarkNode): TestNode {
            return TestNode(node.type, node.txt()).also { tn->
                tn.children.addAll(node.children.map { render(it) })
            }
        }

    }

}