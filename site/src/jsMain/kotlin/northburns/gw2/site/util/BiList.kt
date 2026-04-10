package northburns.gw2.site.util

class BiList<T> {
    val listA: MutableList<T> = mutableListOf()
    val listB: MutableList<T> = mutableListOf()

    fun add(a: T, b: T) {
        listA.add(a)
        listB.add(b)
    }

    fun getJoined(): List<T> = listA + listB
}
