package northburns.gw2.ksp.reselect2

import arrow.core.Some
import arrow.core.Tuple4
import arrow.core.Tuple5
import arrow.core.Tuple6
import arrow.core.Tuple7
import arrow.core.Tuple8
import arrow.core.Tuple9
import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.KModifier.PRIVATE
import com.squareup.kotlinpoet.LambdaTypeName
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.TypeVariableName
import com.squareup.kotlinpoet.asTypeName
import com.squareup.kotlinpoet.typeNameOf

class ReSelectNGeneratorContext(
    packageName: String,
    n: Int,
) {

    private val ns = (1..n)

    val clazzName = "ReSelect$n"

    private val slicerNames = ns.map { it.toString() }
    private val valueNames = ns.map { "v$it" }
    private val typeNames = ns.map { "T$it" }
    private val hashNames = ns.map { "hash$it" }

    private val typeSVariable = TypeVariableName("S", Any::class)
    private val typeTNVariables = ns.map { j ->
        TypeVariableName("T$j")
    }
    private val typeRVariable = TypeVariableName("R")

    private val slicerTypes = typeTNVariables.map { typeTN ->
        LambdaTypeName.get(
            parameters = listOf(
                ParameterSpec.builder(name = "", type = typeSVariable).build()
            ),
            returnType = typeTN,
        )
    }
    val slicerParams = slicerNames.zip(slicerTypes)
        .map { (name, type) ->
            ParameterSpec.builder(name, type).build()
        }


    private val factoryName = "result"
    private val factoryType = LambdaTypeName.get(
        parameters = typeTNVariables.map { typeTN ->
            ParameterSpec.builder(name = "", type = typeTN).build()
        },
        returnType = typeRVariable,
    )

    val fileSpec: FileSpec by lazy {
        FileSpec.builder(packageName, "reselect$n")
            .addAnnotation(
                AnnotationSpec.builder(Suppress::class)
                    .addMember("%S", "unused")
                    .addMember("%S", "PrivatePropertyName")
                    .addMember("%S", "PrivatePropertyNames")
                    .addMember("%S", "LocalVariableName")
                    .addMember("%S", "RedundantVisibilityModifier")
                    .addMember("%S", "UnusedReceiverParameter")
                    .addMember("%S", "UnnecessaryVariable")
                    .addMember("%S", "RemoveRedundantQualifierName")
                    .build()
            )
            .addImport(
                "northburns.gw2.site.util.bind",
                "goonBind", "_internalNullHelper", "NullRenderer"
            )
            .addFunction(reselectFunction)
            .addFunction(reselectFromObservableStateFunction)
            .addFunction(goonBindFunction)
            .addFunctions(nonNullRenderFunctions)
            .addType(clazz)
            .build()
    }

    val reselectFunction by lazy {
        FunSpec.builder("reselect")
            .addTypeVariable(typeSVariable)
            .addTypeVariables(typeTNVariables)
            .addParameters(slicerParams)
            .returns(
                ClassName(packageName, clazzName).parameterizedBy(
                    typeSVariable,
                    *typeTNVariables.toTypedArray(),
                    tupletype(n),
                )
            )
            .addStatement(
                ns.joinToString(
                    prefix = "return $clazzName(",
                    separator = ", ",
                    postfix = ", result = ${tupleConstructorRef(n)})"
                ) { i -> "`$i` = `$i`" }
            )
            .build()
    }

    val reselectFromObservableStateFunction by lazy {
        reselectFunction.toBuilder("reselect")
            .receiver(
                ClassName("io.kvision.state", "ObservableState")
                    .parameterizedBy(TypeVariableName("S"))
            )
            .build()
    }

    val classBindGoonRenderInvoker = ClassName(
        "northburns.gw2.site.util.bind",
        "BindGoonRenderInvoker"
    )
    val typeW = TypeVariableName(
        "W",
        ClassName(
            "io.kvision.core",
            "Component",
        )
    )

    val goonBindFunction by lazy {
        val typeGoonState = ClassName("northburns.gw2.site.app", "GoonState")

        FunSpec.builder("goonBindReselect")
            .addTypeVariable(typeW)
            .addTypeVariables(typeTNVariables)
            .addParameters(
                slicerNames.zip(typeTNVariables).map { (name, typeTN) ->
                    val t = LambdaTypeName.get(
                        parameters = listOf(
                            ParameterSpec.builder(name = "", type = typeGoonState).build()
                        ),
                        returnType = typeTN,
                    )
                    ParameterSpec.builder(name, t).build()
                }
            )
            .receiver(typeW)
            .returns(
                classBindGoonRenderInvoker.parameterizedBy(
                    tupletype(n),
                    typeW,
                )
            )
            .addStatement(
                slicerNames.joinToString(
                    prefix = "return goonBind { reselect(",
                    postfix = ") }",
                    separator = ", ",
                ) { i -> "`$i` = `$i`" }
            )
            .build()
    }

    val nonNullRenderFunctions by lazy {
        ns.map { nullableIndex ->

            val slicerName = slicerNames[nullableIndex - 1]
            FunSpec.builder("renderNull$nullableIndex")
                .addTypeVariable(typeW)
                .addTypeVariables(typeTNVariables)
                .receiver(
                    classBindGoonRenderInvoker.parameterizedBy(
                        tupletype(n) { i, t ->
                            if (i != nullableIndex - 1) t
                            else t.copy(nullable = true)
                        },
                        typeW,
                    )
                )
                .returns(
                    classBindGoonRenderInvoker.parameterizedBy(
                        tupletype(n),
                        typeW,
                    )
                )
                .addParameter(
                    ParameterSpec.builder(
                        slicerName,
                        LambdaTypeName.get(
                            receiver = typeW,
                            parameters = listOf(typeTNVariables[nullableIndex - 1].copy(nullable = true)).toTypedArray(),
                            returnType = typeNameOf<Unit>()
                        )
                    ).defaultValue("NullRenderer.loading()").build()
                )
                .addStatement("return _internalNullHelper({ it.component${nullableIndex}() }, `$slicerName`)")

                .build()

        }
    }

    val clazz: TypeSpec by lazy {
        TypeSpec.classBuilder(clazzName)
            .addTypeVariable(typeSVariable)
            .addTypeVariables(typeTNVariables)
            .addTypeVariable(typeRVariable)
            .primaryConstructor(
                FunSpec.constructorBuilder()
                    .addParameters(slicerParams)
                    .addParameter(factoryName, factoryType)
                    .build()
            )
            .addProperties(
                slicerNames.zip(slicerTypes)
                    .map { (name, type) ->
                        PropertySpec.builder(name, type, PRIVATE)
                            .initializer(name).build()
                    })
            .addProperty(
                PropertySpec.builder(factoryName, factoryType, PRIVATE)
                    .initializer(factoryName).build()
            )
            .addProperties(
                hashNames.map {
                    PropertySpec.builder(
                        it,
                        ClassName.bestGuess("northburns.gw2.site.util.reselect.ReSelectBase.HashHolder"),
                        PRIVATE,
                    ).initializer("HashHolder()").build()
                }
            )
            .superclass(
                ClassName.bestGuess("northburns.gw2.site.util.reselect.ReSelectBase")
                    .parameterizedBy(typeSVariable, typeRVariable)
            )
            .addFunction(
                FunSpec.builder("select")
                    .addModifiers(KModifier.OVERRIDE)
                    .addParameter("s", typeSVariable)
                    .returns(typeRVariable)
                    .addCode(
                        CodeBlock.builder()
                            .apply {
                                ns.forEach { i ->
                                    addStatement("val v$i = `${slicerNames[i - 1]}`(s)")
                                }
                                ns.forEach { i ->
                                    addStatement("val c$i = ${hashNames[i - 1]}.from(v$i)")
                                }
                            }
                            .addStatement(
                                ns.joinToString(
                                    prefix = "val changed = ",
                                    separator = " || ",
                                ) { i -> "c$i" }
                            )
                            .addStatement(
                                ns.joinToString(
                                    prefix = "return changed store { $factoryName(",
                                    separator = ", ",
                                    postfix = ") }"
                                ) { i -> "v$i" }
                            )
                            .build())
                    .build()

            )
            .build()
    }

    private fun tupletype(
        number: Int,
        paramMod: (n: Int, TypeName) -> TypeName = { _, n -> n },
    ): TypeName {
        val clazz = tupleClass(number)
        return clazz.asTypeName().parameterizedBy(
            typeTNVariables.mapIndexed(paramMod),
        )
    }

    companion object {
        private fun tupleClass(number: Int) = when (number) {
            1 -> Some::class
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

        private fun tupleConstructorRef(number: Int): String {
            // if (number == 1) return "{ it }"
            val clazz = tupleClass(number)
            return "::${clazz.simpleName}"
        }

    }
}
