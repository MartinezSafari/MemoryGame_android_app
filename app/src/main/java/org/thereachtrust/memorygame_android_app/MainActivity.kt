package org.thereachtrust.memorygame_android_app

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity(), GameFragment.GameFragmentListener
{
    var thisSecondTap= false
    lateinit var tile1: Tile
    lateinit var tile2: Tile

    var gameActive = true

    override fun tileTapped(tile: Tile, index: Int)
    {
        if (!gameActive || tile.tileStatus == Status.FOUND
            || tile.tileStatus == Status.FLIPPED)
            return

        tile.tileStatus= Status.FLIPPED
        tile.updateTile()

        if (!thisSecondTap){
            tile1= tile
            thisSecondTap= true
        }
        else
        {
            tile2= tile
            thisSecondTap= false
            gameActive= false

            Handler().postDelayed(
                {
            compare()},
                1000)

        }
    }
    fun compare()
    {
        if (tile1.value == tile2.value){
            tile1.tileStatus= Status.FOUND
            tile2.tileStatus= Status.FOUND

            tile1.updateTile()
            tile2.updateTile()

        }
        else{
                tile1.tileStatus= Status.UNKOWN
                tile2.tileStatus= Status.UNKOWN

                tile1.updateTile()
                tile2.updateTile()

        }
        gameActive= true
    }

   override fun makeTiles(): ArrayList<Tile>{
        val tilesArray: ArrayList<Tile> = ArrayList()
        for (i in 1..16){

            var num= i
            if (num> 8)
                num-=8

            val newTile= Tile(this, num)
           newTile.updateTile()

            tilesArray.add(newTile)
        }
       tilesArray.shuffle()
        return tilesArray
    }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .add(R.id.gameLayout, GameFragment.newInstance(), "game").commit()
    }
}