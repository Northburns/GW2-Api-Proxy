package northburns.gw2.generator

import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.LambdaTypeName
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.asTypeName
import northburns.gw2.markdown.GoonMarkElementType
import northburns.gw2.markdown.GoonMarkGenerator
import northburns.gw2.markdown.GoonMarkGeneratorContext
import northburns.gw2.markdown.GoonMarkNode
import java.io.File

class MarkdownAstSiteGenerator(
    val mdg: GoonMarkGenerator<GoonMarkNode>,
    val outputDir: File,
) {

    fun run(
        md: String,
        name: String,
    ) {
        val tree: GoonMarkNode = mdg.generate(md)
        val filename = name.replace("/", "_")

        val fileSpec = FileSpec.builder("northburns.gw2.site.generated.md", "$filename.kt")

        val params = mutableMapOf(
            "node" to GoonMarkNode::class.asTypeName(),
            "type" to GoonMarkElementType::class.asTypeName(),
        )
        val sb = StringBuilder()
        fun treeToString(node: GoonMarkNode, indent: String = "") {
            sb.append(indent)
            sb.append("%node:T(")
            sb.append("type = %type:T.${node.type.name}, ")
            sb.append("markdownText = { md }, ")
            sb.append("startOffset = ${node.startOffset}, ")
            sb.append("endOffset = ${node.endOffset}, ")
            sb.append("children = listOf(")
            if (node.children.isNotEmpty()) sb.append("\n")
            node.children.forEach { child ->
                treeToString(child, "$indent  ")
                sb.append(",\n")
            }
            if (node.children.isNotEmpty()) sb.append(indent)
            sb.append("),\n")
            /*
                    val type: GoonMarkElementType,
                    private val markdownText: () -> String,
                    private val startOffset: Int,
                    private val endOffset: Int,
                    val children: List<GoonMarkNode>,
             */
            sb.append(")")
        }
        treeToString(tree)

        fileSpec.addProperty(
            PropertySpec.builder(filename, LambdaTypeName.get(returnType = GoonMarkNode::class.asTypeName()))
                .initializer(
                    CodeBlock.builder()
                        .beginControlFlow("")
                        .addStatement("val md = %S", md)
                        .addNamed(sb.toString(), params)
                        .endControlFlow()
                        .build()
                )
                .build()
        )

        fileSpec
            .build()
            .writeTo(outputDir)
    }

    companion object {
        @JvmStatic
        fun main(vararg args: String) {
            val inputDir = File(args[0])
            val outputDir = File(args[1])

            val generator = MarkdownAstSiteGenerator(
                mdg = GoonMarkGenerator(
                    object : GoonMarkGeneratorContext<GoonMarkNode> {
                        override fun render(node: GoonMarkNode) = node
                    }),
                outputDir = outputDir,
            )


            println("Clear output: $outputDir")
            if (outputDir.exists())
                outputDir.deleteRecursively()
            outputDir.mkdirs()

            println("Walking files")
            inputDir.walk()
                .filter { it.extension == "md" && it.nameWithoutExtension != "README" }
                .forEach { file ->
                    val md = file.readText()
                    val name = file.relativeTo(inputDir).invariantSeparatorsPath.removeSuffix(".md")
                    println("$file: rel $name")

                    generator.run(md, name)
                }


            println("SUCCESS: $inputDir -> $outputDir")
        }


    }
}