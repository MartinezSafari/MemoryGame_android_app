package org.thereachtrust.memorygame_android_app

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.TextView
import android.widget.TimePicker
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), GameFragment.GameFragmentListener
{
    var gridSize= 6

    private var thisSecondTap = false
    private lateinit var tile1: Tile
    private lateinit var tile2: Tile

    var gameActive = true
    val foundTiles: ArrayList<Tile> = ArrayList()



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
            foundTiles.add(tile1)
            foundTiles.add(tile2)

            if(foundTiles.size == gridSize * gridSize){
                // won game
                Toast.makeText(this, "YOU WON !!", Toast.LENGTH_LONG).show()
            }

        }
        else{
                tile1.tileStatus= Status.UNKOWN
                tile2.tileStatus= Status.UNKOWN

                tile1.updateTile()
                tile2.updateTile()

        }
        gameActive= true
    }

    private fun restartGame()
    {
        gameActive= true
        thisSecondTap= false
        foundTiles.clear()

        val frag= supportFragmentManager.findFragmentByTag("game")
        if (frag != null)
        {
            supportFragmentManager.beginTransaction()
                .remove(frag).commit()
        }
        supportFragmentManager.beginTransaction()
            .add(R.id.gameLayout, GameFragment.newInstance(gridSize),
                "game").commit()


    }


   override fun makeTiles(): ArrayList<Tile>{
        val tilesArray: ArrayList<Tile> = ArrayList()

        val totalGrid= gridSize * gridSize
        val halfGrid= totalGrid/2

        for (i in 1..totalGrid)
        {

            var num = i
            if (num > halfGrid)
                num -= halfGrid

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

        restartGame()
        restartButton.setOnClickListener{
            restartGame()
        }
    }
}