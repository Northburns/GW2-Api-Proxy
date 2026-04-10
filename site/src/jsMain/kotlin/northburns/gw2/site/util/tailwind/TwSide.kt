package northburns.gw2.site.util.tailwind

/**
 * Names:
 *
 * * s = start
 * * e = end
 * * t = top
 * * r = top-right
 * * b = bottom-right
 * * l = top-left
 * * ss = start-start
 * * se = start-end
 * * ee = end-end
 * * es = end-start
 * * tl = top-left
 * * tr = top-right
 * * br = bottom-right
 * * bl = bottom-left
 *
 * Assuming Left-to-right:
 *
 * ```
 * ss,se,ee,es
 * | tl, l | t | tr, r |
 * |     s |   |     e |
 * |    bl |   | br, b |
 * ```
 */
enum class TwSide {
    s,
    e,
    t,
    r,
    b,
    l,
    ss,
    se,
    ee,
    es,
    tl,
    tr,
    br,
    bl,
}