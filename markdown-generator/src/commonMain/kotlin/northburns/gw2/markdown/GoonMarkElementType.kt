package northburns.gw2.markdown

import org.intellij.markdown.IElementType
import org.intellij.markdown.MarkdownElementType
import org.intellij.markdown.MarkdownElementTypes
import org.intellij.markdown.MarkdownTokenTypes

enum class GoonMarkElementType(
    val elementType: IElementType,
) {
    ELEMENT_MARKDOWN_FILE(MarkdownElementTypes.MARKDOWN_FILE),
    ELEMENT_UNORDERED_LIST(MarkdownElementTypes.UNORDERED_LIST),
    ELEMENT_ORDERED_LIST(MarkdownElementTypes.ORDERED_LIST),
    ELEMENT_LIST_ITEM(MarkdownElementTypes.LIST_ITEM),
    ELEMENT_BLOCK_QUOTE(MarkdownElementTypes.BLOCK_QUOTE),
    ELEMENT_CODE_FENCE(MarkdownElementTypes.CODE_FENCE),
    ELEMENT_CODE_BLOCK(MarkdownElementTypes.CODE_BLOCK),
    ELEMENT_CODE_SPAN(MarkdownElementTypes.CODE_SPAN),
    ELEMENT_HTML_BLOCK(MarkdownElementTypes.HTML_BLOCK),
    ELEMENT_PARAGRAPH(MarkdownElementTypes.PARAGRAPH),
    ELEMENT_EMPH(MarkdownElementTypes.EMPH),
    ELEMENT_STRONG(MarkdownElementTypes.STRONG),
    ELEMENT_LINK_DEFINITION(MarkdownElementTypes.LINK_DEFINITION),
    ELEMENT_LINK_LABEL(MarkdownElementTypes.LINK_LABEL),
    ELEMENT_LINK_DESTINATION(MarkdownElementTypes.LINK_DESTINATION),
    ELEMENT_LINK_TITLE(MarkdownElementTypes.LINK_TITLE),
    ELEMENT_LINK_TEXT(MarkdownElementTypes.LINK_TEXT),
    ELEMENT_INLINE_LINK(MarkdownElementTypes.INLINE_LINK),
    ELEMENT_FULL_REFERENCE_LINK(MarkdownElementTypes.FULL_REFERENCE_LINK),
    ELEMENT_SHORT_REFERENCE_LINK(MarkdownElementTypes.SHORT_REFERENCE_LINK),
    ELEMENT_IMAGE(MarkdownElementTypes.IMAGE),
    ELEMENT_AUTOLINK(MarkdownElementTypes.AUTOLINK),
    ELEMENT_SETEXT_1(MarkdownElementTypes.SETEXT_1),
    ELEMENT_SETEXT_2(MarkdownElementTypes.SETEXT_2),
    ELEMENT_ATX_1(MarkdownElementTypes.ATX_1),
    ELEMENT_ATX_2(MarkdownElementTypes.ATX_2),
    ELEMENT_ATX_3(MarkdownElementTypes.ATX_3),
    ELEMENT_ATX_4(MarkdownElementTypes.ATX_4),
    ELEMENT_ATX_5(MarkdownElementTypes.ATX_5),
    ELEMENT_ATX_6(MarkdownElementTypes.ATX_6),
    TOKEN_TEXT(MarkdownTokenTypes.TEXT),
    TOKEN_CODE_LINE(MarkdownTokenTypes.CODE_LINE),
    TOKEN_BLOCK_QUOTE(MarkdownTokenTypes.BLOCK_QUOTE),
    TOKEN_HTML_BLOCK_CONTENT(MarkdownTokenTypes.HTML_BLOCK_CONTENT),
    TOKEN_SINGLE_QUOTE(MarkdownTokenTypes.SINGLE_QUOTE),
    TOKEN_DOUBLE_QUOTE(MarkdownTokenTypes.DOUBLE_QUOTE),
    TOKEN_LPAREN(MarkdownTokenTypes.LPAREN),
    TOKEN_RPAREN(MarkdownTokenTypes.RPAREN),
    TOKEN_LBRACKET(MarkdownTokenTypes.LBRACKET),
    TOKEN_RBRACKET(MarkdownTokenTypes.RBRACKET),
    TOKEN_LT(MarkdownTokenTypes.LT),
    TOKEN_GT(MarkdownTokenTypes.GT),
    TOKEN_COLON(MarkdownTokenTypes.COLON),
    TOKEN_EXCLAMATION_MARK(MarkdownTokenTypes.EXCLAMATION_MARK),
    TOKEN_HARD_LINE_BREAK(MarkdownTokenTypes.HARD_LINE_BREAK),
    TOKEN_EOL(MarkdownTokenTypes.EOL),
    TOKEN_LINK_ID(MarkdownTokenTypes.LINK_ID),
    TOKEN_ATX_HEADER(MarkdownTokenTypes.ATX_HEADER),
    TOKEN_ATX_CONTENT(MarkdownTokenTypes.ATX_CONTENT),
    TOKEN_SETEXT_1(MarkdownTokenTypes.SETEXT_1),
    TOKEN_SETEXT_2(MarkdownTokenTypes.SETEXT_2),
    TOKEN_SETEXT_CONTENT(MarkdownTokenTypes.SETEXT_CONTENT),
    TOKEN_EMPH(MarkdownTokenTypes.EMPH),
    TOKEN_BACKTICK(MarkdownTokenTypes.BACKTICK),
    TOKEN_ESCAPED_BACKTICKS(MarkdownTokenTypes.ESCAPED_BACKTICKS),
    TOKEN_LIST_BULLET(MarkdownTokenTypes.LIST_BULLET),
    TOKEN_URL(MarkdownTokenTypes.URL),
    TOKEN_HORIZONTAL_RULE(MarkdownTokenTypes.HORIZONTAL_RULE),
    TOKEN_LIST_NUMBER(MarkdownTokenTypes.LIST_NUMBER),
    TOKEN_FENCE_LANG(MarkdownTokenTypes.FENCE_LANG),
    TOKEN_CODE_FENCE_START(MarkdownTokenTypes.CODE_FENCE_START),
    TOKEN_CODE_FENCE_CONTENT(MarkdownTokenTypes.CODE_FENCE_CONTENT),
    TOKEN_CODE_FENCE_END(MarkdownTokenTypes.CODE_FENCE_END),
    TOKEN_LINK_TITLE(MarkdownTokenTypes.LINK_TITLE),
    TOKEN_AUTOLINK(MarkdownTokenTypes.AUTOLINK),
    TOKEN_EMAIL_AUTOLINK(MarkdownTokenTypes.EMAIL_AUTOLINK),
    TOKEN_HTML_TAG(MarkdownTokenTypes.HTML_TAG),
    TOKEN_BAD_CHARACTER(MarkdownTokenTypes.BAD_CHARACTER),
    TOKEN_WHITE_SPACE(MarkdownTokenTypes.WHITE_SPACE),
    ;

    companion object {
        val byElementType = hashMapOf(*entries.map { it.elementType to it }.toTypedArray())
        fun get(elementType: IElementType) =
            requireNotNull(byElementType[elementType]) {
                "No GoonMarkElementType found for: $elementType"
            }
    }
}