package mdcc.sd.rest.blackjack

import android.content.Context


class MapeadorDeCartas {
    fun getIDs(context: Context): ArrayList<Int> {
        val res = ArrayList<Int>()
        val fields = R.drawable::class.java.declaredFields


        for (i in 0 until fields.size) {
            try {
                val resourceId = fields[i].getInt(R.drawable::class.java)

                val name = context.resources.getResourceEntryName(resourceId)

                if (name.matches(Regex("(clubs|joker|spades|diamonds|hearts).*"))) {
                    res.add(resourceId)
                }
            }
            catch (e: Exception) {
                var mes = e.localizedMessage
                continue
            }
        }

        return res
    }
}