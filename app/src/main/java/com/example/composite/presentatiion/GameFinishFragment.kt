package com.example.composite.presentatiion

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.composite.databinding.FragmentChoseLevelBinding
import com.example.composite.databinding.FragmentGameFinishBinding
import com.example.composite.domain.entyti.GameResult
import java.lang.RuntimeException

class GameFinishFragment:Fragment() {
    private var _binding : FragmentGameFinishBinding? = null
    private val binding
        get() = _binding?:throw RuntimeException("FragmentGameFinishBinding is null")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGameFinishBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.button.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    companion object{
        private const val GAME_RESULT_KEY ="game_result"
        fun newInstance(gameResult:GameResult): GameFinishFragment{
            val args = Bundle().apply {
                putParcelable(GAME_RESULT_KEY,gameResult)
            }

            val fragment = GameFinishFragment()
            fragment.arguments = args
            return fragment
        }
    }
}