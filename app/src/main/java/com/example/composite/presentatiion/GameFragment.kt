package com.example.composite.presentatiion

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.composite.R
import com.example.composite.databinding.FragmentGameBinding
import com.example.composite.domain.entyti.GameResult
import com.example.composite.domain.entyti.GameSettings
import com.example.composite.domain.entyti.Level

class GameFragment : Fragment() {
    private lateinit var level :Level
    private var _binding: FragmentGameBinding? = null
    val binding
        get() = _binding ?: throw RuntimeException("FragmentGameBinding is null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseArgs()
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.textViewAnswer1.setOnClickListener {
            launchGameFinish(GameResult(true,10,10, GameSettings(10,10,10.0,10)))
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private fun launchGameFinish(gameResult: GameResult){
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.main_fragment, GameFinishFragment.newInstance(gameResult))
            .commit()
    }
    private fun parseArgs(){
        level = requireArguments().getParcelable<Level>(LEVEL_KEY) as Level
    }
    companion object{
        private const val LEVEL_KEY = "levelKey"
        fun newInstance(level: Level): GameFragment{
            val args = Bundle().apply {
                putParcelable(LEVEL_KEY, level)
            }

            val fragment = GameFragment()
            fragment.arguments = args
            return fragment
        }
    }
}