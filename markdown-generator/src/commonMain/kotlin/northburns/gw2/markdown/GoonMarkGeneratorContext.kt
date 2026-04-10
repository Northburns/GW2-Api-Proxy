package northburns.gw2.markdown


/**
 * @param W is the generated type returned by [GoonMarkGenerator.generate]
 */
interface GoonMarkGeneratorContext<W : Any> {

    //fun generator(type: GoonMarkElementType): GeneratingProvider<W>
    //fun add(parent: W, child: W)

    fun render(node: GoonMarkNode): W

}
