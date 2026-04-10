package northburns.gw2.site.layout.style

import io.kvision.core.StyledComponent
import northburns.gw2.site.generated.resources.`Res_modules_imgs_kenney_ui-pack-adventure`
import northburns.gw2.site.generated.resources.`Res_modules_imgs_kenney_ui-pack_Grey`
import northburns.gw2.site.generated.resources.`Res_modules_imgs_kenney_ui-pack_Yellow`

object PanelBackgrounds {


    fun StyledComponent.setPanelBackground(
        panel: PanelBackground,
        borderWidth: String = "8px",
    ) {
        setStyle("border-image-source", panel.source)
        setStyle("border-image-repeat", "round")
        setStyle("border-image-slice", "${panel.slice} fill")
        setStyle("border-image-outset", "0px")
        setStyle("border-image-width", borderWidth)
        setStyle("border-width", borderWidth)
    }

    enum class PanelBackground(
        val source: String,
        val slice: String,
    ) {
        METAL(
            source = `Res_modules_imgs_kenney_ui-pack-adventure`.panel_grey.cssUrl,
            slice = "16.3%",
        ),

        BROWN(
            source = `Res_modules_imgs_kenney_ui-pack-adventure`.panel_brown.cssUrl,
            slice = "25%",
        ),

        BROWN_DAMAGED(
            source = `Res_modules_imgs_kenney_ui-pack-adventure`.panel_brown_damaged.cssUrl,
            slice = "16.3%",
        ),

        BROWN_W_CORNERNS(
            source = `Res_modules_imgs_kenney_ui-pack-adventure`.panel_brown_corners_a.cssUrl,
            slice = "25%",
        ),

        BUTTON_GREY(
            source = `Res_modules_imgs_kenney_ui-pack_Grey`.button_square_depth_flat.cssUrl,
            slice = "20%",
        ),
        BUTTON_GREY_HOVER(
            source = `Res_modules_imgs_kenney_ui-pack_Grey`.button_square_depth_gloss.cssUrl,
            slice = "20%",
        ),
        BUTTON_GREY_ACTIVE(
            source = `Res_modules_imgs_kenney_ui-pack_Grey`.button_square_gloss.cssUrl,
            slice = "20%",
        ),


        BUTTON_YELLOW(
            source = `Res_modules_imgs_kenney_ui-pack_Yellow`.button_square_depth_flat.cssUrl,
            slice = "20%",
        ),
        BUTTON_YELLOW_HOVER(
            source = `Res_modules_imgs_kenney_ui-pack_Yellow`.button_square_depth_gloss.cssUrl,
            slice = "20%",
        ),
        BUTTON_YELLOW_ACTIVE(
            source = `Res_modules_imgs_kenney_ui-pack_Yellow`.button_square_gloss.cssUrl,
            slice = "20%",
        ),

    }


}