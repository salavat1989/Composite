package com.example.composite.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.composite.R
import com.example.composite.databinding.FragmentChoseLevelBinding
import com.example.composite.domain.entyti.Level
import java.lang.RuntimeException

class ChoseGameLevelFragment : Fragment() {
    private var _binding: FragmentChoseLevelBinding? = null
    private val binding
        get() = _binding ?: throw RuntimeException("FragmentChoseLevelBinding is null")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChoseLevelBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonEasyLevel.setOnClickListener {
            launchGameFragment(Level.EASY)
        }
        binding.buttonEasyLevel.setOnClickListener {
            launchGameFragment(Level.EASY)
        }
        binding.buttonMediumLevel.setOnClickListener {
            launchGameFragment(Level.MEDIUM)
        }
        binding.buttonHardLevel.setOnClickListener {
            launchGameFragment(Level.HARD)
        }
        binding.buttonTestLevel.setOnClickListener {
            launchGameFragment(Level.TEST)
        }
    }

    private fun launchGameFragment(level: Level) {
        val fragment = GameFragment.newInstance(level)
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.main_fragment, fragment)
            .addToBackStack(GameFragment.FRAGMENT_NAME)
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}