package org.thereachtrust.memorygame_android_app

import android.content.Context
import android.graphics.Color
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView


enum class Status
{
    UNKOWN, FLIPPED, FOUND
}

data class Tile(var myContext: Context, var value: Int): AppCompatTextView(myContext)
{

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int)
    {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val width= measuredWidth
        setMeasuredDimension(width, width)
    }
    var tileStatus: Status= Status.UNKOWN

    fun updateTile()
    {
        when(tileStatus){
            Status.UNKOWN-> {
                this@Tile.text= "?"
                this@Tile.setBackgroundColor(Color.DKGRAY)
            }
            Status.FLIPPED-> {
                this@Tile.text= this@Tile.value.toString()
                this@Tile.setBackgroundColor(Color.YELLOW)
            }
            Status.FOUND->
            {
                this@Tile.text= "100%"
                this@Tile.setBackgroundColor(Color.GREEN)
            }

        }
    }
}