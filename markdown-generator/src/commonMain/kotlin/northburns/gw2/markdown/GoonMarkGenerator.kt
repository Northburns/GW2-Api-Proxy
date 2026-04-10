package northburns.gw2.markdown

import org.intellij.markdown.ast.ASTNode
import org.intellij.markdown.parser.MarkdownParser

/**
 * Very much like [org.intellij.markdown.html.HtmlGenerator], but generates
 * a KVision container builder
 */
class GoonMarkGenerator<W : Any>(
    internal val generatorContext: GoonMarkGeneratorContext<W>,
) {
    private val flavour = GoonMarkFlavor()
    private val parser = MarkdownParser(flavour)

    /**
     * It might be beneficial to split this into two:
     * One method that generates the gmTree, and one that calls render.
     * If parsing is slow (which it might be, caching the AST Tree might be good idea ),
     * and the rendering is done multiple times.
     * But we'll cross that bridge when we get there, this is fine for now.
     */
    fun generate(markdownText: String): W {
        val parsedTree: ASTNode = parser.buildMarkdownTreeFromString(markdownText)
        val gmTree = GoonMarkNode.fromASTNode(parsedTree) { markdownText }
        //GeneratingVisitor(markdownText, DefaultTagRenderer()).visitNode(gmTree)
        return generate(gmTree)
    }

    fun generate(tree: GoonMarkNode): W {
        return generatorContext.render(tree)
    }


}
