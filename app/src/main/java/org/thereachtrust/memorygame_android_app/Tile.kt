package org.thereachtrust.memorygame_android_app

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Color
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
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
        val objAnime1 = ObjectAnimator.ofFloat(this,
            "scaleX", 1f, 0f)

        val objAnime2 = ObjectAnimator.ofFloat(this,
            "scaleX", 0f, 1f)

        objAnime1.duration= 200
        objAnime2.duration= 200

        objAnime1.interpolator= DecelerateInterpolator()
        objAnime2.interpolator= AccelerateInterpolator()

        objAnime1.addListener(object : AnimatorListenerAdapter()
        {
            override fun onAnimationEnd(animation: Animator?) {
                super.onAnimationEnd(animation)

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

                objAnime2.start()
            }
        })
        objAnime1.start()

    }
}