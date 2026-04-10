package northburns.gw2.ksp

@Retention(AnnotationRetention.SOURCE)
@Target(AnnotationTarget.FILE)
annotation class GenerateDirJsImportEnum(
    val base: String,
    val dir : String,
)
