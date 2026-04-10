package northburns.gw2.site.util.tailwind

/**
 * <https://tailwindcss.com/docs/responsive-design>
 *
 * | Prefix | Min width | CSS |
 * |---|--:|:--|
 * | sm | 40rem (640px) | @media (width >= 40rem) { ... } |
 * | md | 48rem (768px) | @media (width >= 48rem) { ... } |
 * | lg | 64rem (1024px) | @media (width >= 64rem) { ... } |
 * | xl | 80rem (1280px) | @media (width >= 80rem) { ... } |
 * | 2xl | 96rem (1536px) | @media (width >= 96rem) { ... } |
 */
enum class TwBreakpoint {
    sm, md, lg, xl, `2xl`
}