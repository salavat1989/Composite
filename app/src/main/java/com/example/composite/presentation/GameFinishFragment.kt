package com.example.composite.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.composite.R
import com.example.composite.databinding.FragmentGameFinishBinding
import com.example.composite.domain.entyti.GameResult
import java.lang.RuntimeException
import kotlin.math.max

class GameFinishFragment : Fragment() {
    private val args: GameFinishFragmentArgs by navArgs()
    private var _binding: FragmentGameFinishBinding? = null
    private val binding
        get() = _binding ?: throw RuntimeException("FragmentGameFinishBinding is null")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGameFinishBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        parseArgs()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.button.setOnClickListener {
            retryGame()
        }
//        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,
//            object : OnBackPressedCallback(true) {
//                override fun handleOnBackPressed() {
//                    retryGame()
//                }
//            })
        args.gameResult.let {
            val imageId = if (it.isWin) {
                R.drawable.win_smile
            } else {
                R.drawable.sad_smile
            }
            binding.imageView.setImageResource(imageId)

            binding.tvQuestionCount.text = it.countOfQuestions.toString()
            binding.tvRightAnswersCount.text = it.countOfRightAnswers.toString()
            binding.tvPercent.text = String.format(
                "%.3f",
                (100 * it.countOfRightAnswers.toDouble() / max(it.countOfQuestions, 1))
            )
        }
    }

    private fun retryGame() {
//        requireActivity().supportFragmentManager.popBackStack(
//            GameFragment.FRAGMENT_NAME,
//            FragmentManager.POP_BACK_STACK_INCLUSIVE
//        )
        findNavController().popBackStack()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

//    private fun parseArgs() {
//        gameResult = requireArguments().getParcelable<GameResult>(GAME_RESULT_KEY) as GameResult
//    }

    companion object {
        private const val GAME_RESULT_KEY = "game_result"
        fun newInstance(gameResult: GameResult): GameFinishFragment {
            val args = Bundle().apply {
                putParcelable(GAME_RESULT_KEY, gameResult)
            }

            val fragment = GameFinishFragment()
            fragment.arguments = args
            return fragment
        }
    }
}