package com.example.composite.presentation

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.composite.R
import com.example.composite.databinding.FragmentGameBinding
import com.example.composite.domain.entyti.GameResult
import com.example.composite.domain.entyti.Level

class GameFragment : Fragment() {
    private val viewModel: GameViewModel by lazy {
        ViewModelProvider(
            this,
            GameViewModelFactory(requireActivity().application,level)
        )[GameViewModel::class.java]
    }
    private val tvAnswersList: List<TextView> by lazy {
        mutableListOf<TextView>().apply {
            add(binding.textViewAnswer1)
            add(binding.textViewAnswer2)
            add(binding.textViewAnswer3)
            add(binding.textViewAnswer4)
            add(binding.textViewAnswer5)
            add(binding.textViewAnswer6)
        }
    }
    private lateinit var level: Level
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

        observeViewModel()
        setAnswerClickListeners()
    }

    private fun setAnswerClickListeners() {
        for (tv in tvAnswersList) {
            tv.setOnClickListener {
                viewModel.checkAnswer(tv.text.toString().toInt())
            }
        }
    }

    private fun observeViewModel() {
        viewModel.question.observe(viewLifecycleOwner) {
            binding.textViewSum.text = it.sum.toString()
            binding.textViewVisibleNumber.text = it.visibleNumber.toString()
            for ((index, tv) in tvAnswersList.withIndex()) {
                tv.text = it.answers[index].toString()
            }
        }
        viewModel.leftTimeString.observe(viewLifecycleOwner) {
            binding.textViewTime.text = it
        }
        viewModel.rightAnswersProgress.observe(viewLifecycleOwner) {
            binding.textViewGameStatus.text = it
        }
        viewModel.percentOfRightAnswers.observe(viewLifecycleOwner) {
            binding.progressBar.setProgress(it, true)
        }
        viewModel.percentOfRightAnswersMin.observe(viewLifecycleOwner) {
            binding.progressBar.secondaryProgress = it
        }
        viewModel.gameResult.observe(viewLifecycleOwner) {
            launchGameFinish(it)
        }
        viewModel.enoughRightCount.observe(viewLifecycleOwner) {
            binding.textViewGameStatus.setTextColor(getColorByState(it))
        }
        viewModel.enoughRightPercent.observe(viewLifecycleOwner) {
            binding.progressBar.progressTintList = ColorStateList.valueOf(getColorByState(it))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getColorByState(state: Boolean): Int {
        val colorId = if (state) {
            android.R.color.holo_green_light
        } else {
            android.R.color.holo_red_light
        }
        return getColorByResId(colorId)
    }

    private fun getColorByResId(resId: Int): Int {
        return ContextCompat.getColor(requireContext(), resId)
    }

    private fun launchGameFinish(gameResult: GameResult) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.main_fragment, GameFinishFragment.newInstance(gameResult))
            .addToBackStack("game")
            .commit()
    }

    private fun parseArgs() {
        level = requireArguments().getParcelable<Level>(LEVEL_KEY) as Level
    }

    companion object {
        const val FRAGMENT_NAME = "gameFragment"
        private const val LEVEL_KEY = "levelKey"
        fun newInstance(level: Level): GameFragment {
            val args = Bundle().apply {
                putParcelable(LEVEL_KEY, level)
            }

            val fragment = GameFragment()
            fragment.arguments = args
            return fragment
        }
    }
}