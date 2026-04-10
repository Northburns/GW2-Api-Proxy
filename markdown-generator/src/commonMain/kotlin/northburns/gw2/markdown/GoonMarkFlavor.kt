package northburns.gw2.markdown

import org.intellij.markdown.flavours.commonmark.CommonMarkFlavourDescriptor

/**
 * TODO looks empty for now, but we add our own tag types for macros at some point!
 */
internal class GoonMarkFlavor() : CommonMarkFlavourDescriptor(useSafeLinks = true, absolutizeAnchorLinks = false) {

}
