package northburns.gw2.ksp

public interface SiteResource {
    val res: dynamic

    val cssUrl
        get() = "url($res)"
}
