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
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.composite.databinding.FragmentGameBinding
import com.example.composite.domain.entyti.GameResult
import com.example.composite.domain.entyti.Level

class GameFragment : Fragment() {
    private val args: GameFragmentArgs by navArgs()
    private val viewModel: GameViewModel by lazy {
        ViewModelProvider(
            this,
            GameViewModelFactory(requireActivity().application,args.level)
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
//    private lateinit var level: Level
    private var _binding: FragmentGameBinding? = null
    val binding
        get() = _binding ?: throw RuntimeException("FragmentGameBinding is null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        parseArgs()
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
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        observeViewModel()
    }


    private fun observeViewModel() {
        viewModel.gameResult.observe(viewLifecycleOwner) {
            launchGameFinish(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



    private fun launchGameFinish(gameResult: GameResult) {
//        requireActivity().supportFragmentManager.beginTransaction()
//            .replace(R.id.main_fragment, GameFinishFragment.newInstance(gameResult))
//            .addToBackStack("game")
//            .commit()
        findNavController().navigate(GameFragmentDirections.actionGameFragmentToGameFinishFragment(gameResult))
    }

//    private fun parseArgs() {
//        level = requireArguments().getParcelable<Level>(LEVEL_KEY) as Level
//    }

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