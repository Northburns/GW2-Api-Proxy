package northburns.gw2.ksp

import kotlin.reflect.KClass

@Target(AnnotationTarget.FILE)
@Retention(AnnotationRetention.SOURCE)
annotation class ReSelect2Generate(
    val n: Int,
    //val name: String,
    //val baseClassFQN: String,
    /**
     * Special: Use Nothing for unbdounded
     */
    //val typeParamTypes: Array<KClass<*>>,
    //val typeParamNames: Array<String>,
    //val typeParamVariance : Array<String>,
)
