package northburns.gw2.site.layout.components

import io.kvision.core.Container
import io.kvision.html.H1
import io.kvision.html.H2
import io.kvision.html.H3
import io.kvision.html.H4
import io.kvision.html.H5
import io.kvision.html.H6
import io.kvision.html.Image
import io.kvision.html.Ul
import northburns.gw2.client.myclient.data.Gw2Image
import northburns.gw2.site.util.tailwind.tw

object CommonComponents {

    fun createGoonH1(init: (H1.() -> Unit)) = H1(className = tw {
        +"text-6xl font-medium"
        +"border-be-4 border-be-black"
        +"mb-3"
    }, init = init)

    fun Container.goonH1(init: (H1.() -> Unit)) = createGoonH1(init).also { add(it) }
    fun Container.goonH1(h1: String) = createGoonH1 { +h1 }.also { add(it) }


    fun createGoonH2(init: (H2.() -> Unit)) = H2(className = tw {
        +"text-5xl font-medium"
        +"border-be-2 border-be-black"
        +"mb-3"
    }, init = init)

    fun Container.goonH2(init: (H2.() -> Unit)) = createGoonH2(init).also { add(it) }
    fun Container.goonH2(h2: String) = createGoonH2 { +h2 }.also { add(it) }


    fun createGoonH3(init: (H3.() -> Unit)) = H3(className = tw {
        +"text-4xl font-medium"
        +"mb-2"
    }, init = init)

    fun Container.goonH3(init: (H3.() -> Unit)) = createGoonH3(init).also { add(it) }
    fun Container.goonH3(h3: String) = createGoonH3 { +h3 }.also { add(it) }


    fun createGoonH4(init: (H4.() -> Unit)) = H4(className = tw {
        +"text-3xl font-medium"
        +"mb-2"
    }, init = init)

    fun Container.goonH4(init: (H4.() -> Unit)) = createGoonH4(init).also { add(it) }
    fun Container.goonH4(h4: String) = createGoonH4 { +h4 }.also { add(it) }


    fun createGoonH5(init: (H5.() -> Unit)) = H5(className = tw {
        +"text-2xl font-medium"
        +"mb-1"
    }, init = init)

    fun Container.goonH5(init: (H5.() -> Unit)) = createGoonH5(init).also { add(it) }
    fun Container.goonH5(h5: String) = createGoonH5 { +h5 }.also { add(it) }


    fun createGoonH6(init: (H6.() -> Unit)) = H6(className = tw {
        +"text-xl font-medium"
        +"mb-1"
    }, init = init)

    fun Container.goonH6(init: (H6.() -> Unit)) = createGoonH6(init).also { add(it) }
    fun Container.goonH6(h6: String) = createGoonH6 { +h6 }.also { add(it) }

    fun createGoonUl(init: (Ul.() -> Unit)) = Ul(className = tw {
        +"list-disc list-inside mb-2"
    }, init = init)

    fun Container.goonUl(init: Ul.() -> Unit) = createGoonUl(init).also { add(it) }

    fun createGoonImage(img: Gw2Image) = Image(
        TODO()
    )

    fun Container.goonImage(img: Gw2Image) = createGoonImage(img)
}