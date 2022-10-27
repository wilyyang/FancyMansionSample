package com.cheesejuice.fancymansionsample

class Const {
    companion object{
        // Const
        const val TAG = "FancyMansion"

        const val ID_NOT_FOUND = -1L
        const val END_SLIDE_ID = -1L
        const val EDIT_PLAY = "EDIT_PLAY"
        const val NOT_SUPPORT_COND_ID_2 = -1L
        const val FIRST_SLIDE = 1L

        const val SLIDE_TYPE_NORMAL = 1
        const val SLIDE_TYPE_START = 2
        const val SLIDE_TYPE_END = 3

        // Pref
        const val PREF_SETTING = "PREF_SETTING"
        const val PREF_SAVE_SLIDE_ID = "PREF_SAVE_SLIDE_ID"

        const val PREF_PREFIX_BOOK = "book_"
        const val PREF_PREFIX_COUNT = "count_"

        const val PREF_MAKE_SAMPLE = "PREF_MAKE_SAMPLE"
    }
}

enum class CondOp(
    val opName : String,
    val check : (Int, Int) -> Boolean
){
    OVER("over", { n1, n2 -> n1 > n2 }),
    BELOW("under", { n1, n2 -> n1 < n2 }),
    EQUAL("equal", { n1, n2 -> n1 == n2 }),
    NOT("not", { n1, n2 -> n1 != n2 });

    companion object {
        fun from(type: String?): CondOp = values().find { it.opName == type } ?: EQUAL
    }
}

enum class CondNext(
    val relName : String,
    val check : (Boolean, Boolean) -> Boolean
){
    AND("and", { p1, p2 -> p1 && p2 }),
    OR("or", { p1, p2 -> p1 || p2 });

    companion object {
        fun from(type: String?): CondNext = values().find { it.relName == type } ?: OR
    }
}