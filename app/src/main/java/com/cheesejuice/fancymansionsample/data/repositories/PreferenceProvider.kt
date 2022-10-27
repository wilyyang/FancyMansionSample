package com.cheesejuice.fancymansionsample.data.repositories

import android.content.Context
import com.cheesejuice.fancymansionsample.CondNext
import com.cheesejuice.fancymansionsample.CondOp
import com.cheesejuice.fancymansionsample.Const
import com.cheesejuice.fancymansionsample.data.models.Condition

class PreferenceProvider constructor(private val context: Context){
    // Setting Pref
    fun isSampleMake():Boolean{
        val pref = context.getSharedPreferences(Const.PREF_SETTING, Context.MODE_PRIVATE)
        if(!pref.getBoolean(Const.PREF_MAKE_SAMPLE, false)){
            val editor = pref.edit()
            editor.putBoolean(Const.PREF_MAKE_SAMPLE, true)
            editor.commit()
            return false
        }
        return true
    }

    fun deleteBookPref(bookId: Long){
        context.deleteSharedPreferences(Const.PREF_PREFIX_BOOK+bookId)
    }

    // Reading Book Info
    fun getSaveSlideId(bookId: Long): Long{
        val pref = context.getSharedPreferences(
            Const.PREF_PREFIX_BOOK+bookId,
            Context.MODE_PRIVATE
        )
        return pref.getLong(Const.PREF_SAVE_SLIDE_ID, Const.ID_NOT_FOUND)
    }

    fun setSaveSlideId(bookId: Long, slideId: Long){
        val pref = context.getSharedPreferences(
            Const.PREF_PREFIX_BOOK+bookId,
            Context.MODE_PRIVATE
        )
        val editor = pref.edit()
        editor.putLong(Const.PREF_SAVE_SLIDE_ID, slideId)
        editor.commit()
    }

    // Check Condition
    fun checkConditions(bookId: Long, conditions: MutableList<Condition>): Boolean{
        var result = true
        var nextLogic = CondNext.AND
        for(condition in conditions){
            result = nextLogic.check(result, checkCondition(bookId, condition))
            nextLogic = CondNext.from(condition.conditionNext)
            if(result && nextLogic == CondNext.OR) break
        }
        return result
    }

    fun checkCondition(bookId: Long, condition: Condition): Boolean =
        condition.run{
            val count1 = getIdCount(bookId, conditionId1)
            val count2 = if(conditionId2== Const.NOT_SUPPORT_COND_ID_2) conditionCount else getIdCount(bookId, conditionId2)
            CondOp.from(conditionOp).check(count1, count2)
        }

    // Book Count
    private fun getIdCount(bookId: Long, slideId: Long):Int{
        val pref = context.getSharedPreferences(Const.PREF_PREFIX_BOOK+bookId,
            Context.MODE_PRIVATE
        )
        return pref.getInt(Const.PREF_PREFIX_COUNT+slideId, 0)
    }

    private fun setIdCount(bookId: Long, slideId: Long, count: Int){
        val pref = context.getSharedPreferences(Const.PREF_PREFIX_BOOK+bookId,
            Context.MODE_PRIVATE
        )
        val editor = pref.edit()
        editor.putInt(Const.PREF_PREFIX_COUNT+slideId, count)
        editor.commit()
    }

    fun incrementIdCount(bookId: Long, id: Long) {
        val count = getIdCount(bookId, id) + 1
        setIdCount(bookId, id, count)
    }
}