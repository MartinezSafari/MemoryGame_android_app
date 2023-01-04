package org.thereachtrust.memorygame_android_app

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class GameFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val frag= inflater.inflate(R.layout.fragment_game, container,
            false)
        return frag
    }

    companion object {

        fun newInstance(): GameFragment
        {
            return GameFragment()


        }
    }
}