package northburns.gw2.client.myclient.utils

fun <A, B, AA, BB> Pair<A, B>.map(a: (A) -> AA, b: (B) -> BB): Pair<AA, BB> = Pair(a(first), b(second))
