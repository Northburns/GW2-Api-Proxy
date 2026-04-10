package northburns.gw2.ksp

import arrow.core.Tuple4
import arrow.core.Tuple5
import arrow.core.Tuple6
import arrow.core.Tuple7
import arrow.core.Tuple8
import arrow.core.Tuple9
import com.google.devtools.ksp.KspExperimental
import com.google.devtools.ksp.getAnnotationsByType
import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSDeclaration
import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.LambdaTypeName
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.TypeVariableName
import com.squareup.kotlinpoet.asTypeName
import com.squareup.kotlinpoet.ksp.writeTo

@Deprecated("just delete this")
class ReSelectGenerator(
    private val codeGenerator: CodeGenerator
) : SymbolProcessor {

    init {
        println("ReSelectGenerator")
    }

    @OptIn(KspExperimental::class)
    override fun process(resolver: Resolver): List<KSAnnotated> {

        resolver.getSymbolsWithAnnotation(ReSelectGenerate::class.qualifiedName!!)
            .filterIsInstance<KSDeclaration>()
            .forEach { declaration ->

                val originatingKSFiles = listOfNotNull(declaration.containingFile)
                val packageName = declaration.packageName.asString()
                val reselectName = declaration.simpleName.asString()
                val baseName = reselectName + "Base"
                val n = declaration.getAnnotationsByType(ReSelectGenerate::class)
                    .single().n

                val fileAnnotation = AnnotationSpec.builder(Suppress::class)
                    .addMember("%S", "unused")
                    .addMember("%S", "PrivatePropertyName")
                    .addMember("%S", "PrivatePropertyNames")
                    .addMember("%S", "LocalVariableName")
                    .addMember("%S", "RedundantVisibilityModifier")
                    .addMember("%S", "UnusedReceiverParameter")
                    .addMember("%S", "UnnecessaryVariable")
                    .build()

                val fileImpl = FileSpec.builder(packageName, "ReSelectImplementation")
                    .addAnnotation(fileAnnotation)
                val fileFunSource = FileSpec.builder(packageName, "ReSelectFunSource")
                    .addAnnotation(fileAnnotation)

                (1..n).forEach { i ->
                    val typeVariables = (0..i).map { j ->
                        TypeVariableName("T$j")
                    }
                    val propertyNames = (0..i).map { j ->
                        "`$j`"
                    }
                    val propertyTypes: List<TypeName> = (0..i).map { j ->
                        LambdaTypeName.get(
                            receiver = null,
                            parameters = listOf(
                                ParameterSpec.builder(name = "", type = TypeVariableName("S")).build()
                            ),
                            returnType = typeVariables[j],
                        )
                    }
                    val resultName = "result"
                    val resultType = LambdaTypeName.get(
                        receiver = null,
                        parameters = (1..i).map { j ->
                            ParameterSpec.builder(name = "", type = TypeVariableName("T$j")).build()
                        },
                        returnType = TypeVariableName("R"),
                    )
                    val typeName = "ReSelect$i"
                    val factoryName = "reSelect$i"

                    fun tupleClass(number: Int) = when (number) {
                        2 -> Pair::class
                        3 -> Triple::class
                        4 -> Tuple4::class
                        5 -> Tuple5::class
                        6 -> Tuple6::class
                        7 -> Tuple7::class
                        8 -> Tuple8::class
                        9 -> Tuple9::class
                        else -> throw IllegalArgumentException("Unsupported tuple-n: $number")
                    }

                    fun tupletype(number: Int): TypeName {
                        @Suppress("UnnecessaryVariable", "RedundantSuppression") val t = typeVariables
                        if (number == 1) return t[1]
                        val clazz = tupleClass(number)
                        return clazz.asTypeName().parameterizedBy(t.drop(1))
                    }

                    fun tupleConstructorRef(number: Int): String {
                        if (number == 1) return "{ it }"
                        val clazz = tupleClass(number)
                        return "::${clazz.simpleName}"
                    }

                    fileImpl.addType(
                        TypeSpec.classBuilder(typeName)
                            .addTypeVariable(TypeVariableName("S", Any::class))
                            .apply {
                                (1..i).forEach { j ->
                                    addTypeVariable(typeVariables[j])
                                }
                            }
                            .addTypeVariable(TypeVariableName("R"))
                            .primaryConstructor(
                                FunSpec.constructorBuilder()
                                    .apply {
                                        (1..i).forEach { j ->
                                            addParameter(
                                                ParameterSpec.builder(propertyNames[j], propertyTypes[j])
                                                    .build()
                                            )
                                        }
                                    }
                                    .addParameter(
                                        ParameterSpec.builder(resultName, resultType)
                                            .build()
                                    )
                                    .build()
                            )
                            .apply {
                                (1..i).forEach { j ->
                                    addProperty(
                                        PropertySpec.builder(propertyNames[j], propertyTypes[j])
                                            .initializer(propertyNames[j])
                                            .addModifiers(KModifier.PRIVATE)
                                            .build()
                                    )
                                }
                            }
                            .addProperty(
                                PropertySpec.builder(resultName, resultType)
                                    .initializer(resultName)
                                    .addModifiers(KModifier.PRIVATE)
                                    .build()
                            )
                            .superclass(
                                ClassName(packageName, baseName).parameterizedBy(
                                    TypeVariableName("S"), TypeVariableName("R")
                                )
                            )
                            .apply {
                                (1..i).forEach { j ->
                                    addProperty(
                                        PropertySpec.builder("hash$j", ClassName(packageName, "HashHolder"))
                                            .initializer("HashHolder()")
                                            .addModifiers(KModifier.PRIVATE)
                                            .build()
                                    )
                                }
                            }
                            .addFunction(
                                FunSpec.builder("select")
                                    .addModifiers(KModifier.OVERRIDE)
                                    .addParameter("s", TypeVariableName("S"))
                                    .returns(TypeVariableName("R"))
                                    .addCode(
                                        CodeBlock.builder()
                                            .apply {
                                                (1..i).forEach { j ->
                                                    addStatement("val v$j = `$j`(s)")
                                                }
                                                (1..i).forEach { j ->
                                                    addStatement("val c$j = hash$j.from(v$j)")
                                                }
                                            }
                                            .addStatement(
                                                (1..i).joinToString(
                                                    prefix = "val changed = ",
                                                    separator = " || ",
                                                ) { j -> "c$j" }
                                            )
                                            .addStatement(
                                                (1..i).joinToString(
                                                    prefix = "return changed store { result(",
                                                    separator = ", ",
                                                    postfix = ") }"
                                                ) { j -> "v$j" }
                                            )
                                            .build())
                                    .build()
                            )

                            .addType(
                                TypeSpec.companionObjectBuilder()
                                    .addFunction(
                                        FunSpec.builder(factoryName)
                                            .addTypeVariable(TypeVariableName("S", Any::class))
                                            .apply {
                                                (1..i).forEach { j ->
                                                    addTypeVariable(typeVariables[j])
                                                }
                                            }
                                            .apply {
                                                (1..i).forEach { j ->
                                                    addParameter(
                                                        ParameterSpec.builder(propertyNames[j], propertyTypes[j])
                                                            .build()
                                                    )
                                                }
                                            }
                                            .returns(
                                                ClassName(packageName, typeName).parameterizedBy(
                                                    TypeVariableName("S", Any::class),
                                                    *typeVariables.drop(1).toTypedArray(),
                                                    tupletype(i),
                                                )
                                            )
                                            .addStatement(
                                                (1..i).joinToString(
                                                    prefix = "return $typeName(",
                                                    separator = ", ",
                                                    postfix = ", result = ${tupleConstructorRef(i)})"
                                                ) { j -> "`$j` = `$j`" }
                                            )
                                            .build()
                                    )
                                    .build())
                            .build()
                    )



                    buildReselectFun(
                        i,
                        ClassName("io.kvision.state", "ObservableState")
                            .parameterizedBy(TypeVariableName("S")),
                        typeVariables,
                        propertyNames,
                        propertyTypes,
                        { tupletype(it) },
                        resultType,
                        packageName,
                        reselectName
                    ).also(fileFunSource::addFunction)
                }

                fileImpl
                    .build()
                    .writeTo(codeGenerator, false, originatingKSFiles)
                fileFunSource
                    .build()
                    .writeTo(codeGenerator, false, originatingKSFiles)
            }

        return emptyList()
    }

}

private fun buildReselectFun(
    i: Int,
    receiver: TypeName,
    typeVariables: List<TypeVariableName>,
    propertyNames: List<String>,
    propertyTypes: List<TypeName>,
    tupleType: (Int) -> TypeName,
    resultType: LambdaTypeName,
    packageName: String,
    reselectName: String
): FunSpec {
    return FunSpec.builder("reselect")
        .addTypeVariable(TypeVariableName("S", Any::class))
        .apply {
            (1..i).forEach { j ->
                addTypeVariable(typeVariables[j])
            }
        }
        .receiver(receiver)
        .apply {
            (1..i).forEach { j ->
                addParameter(
                    ParameterSpec.builder(propertyNames[j], propertyTypes[j])
                        .build()
                )
            }
        }
        .returns(
            ClassName(packageName, reselectName)
                .parameterizedBy(TypeVariableName("S"))
                .plusParameter(tupleType(i))
        )
        .addCode(
            CodeBlock.builder()
                .add(
                    (1..i).joinToString(
                        prefix = "return ReSelect$i.reSelect$i(",
                        separator = ", ",
                        postfix = ")"
                    ) { j -> propertyNames[j] }
                )
                .build())
        .build()
}