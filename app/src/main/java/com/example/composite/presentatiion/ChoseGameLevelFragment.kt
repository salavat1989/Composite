package com.example.composite.presentatiion

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.composite.databinding.FragmentChoseLevelBinding
import java.lang.RuntimeException

class ChoseGameLevelFragment:Fragment() {
    private var _binding : FragmentChoseLevelBinding? = null
    private val binding
    get() = _binding?:throw RuntimeException("FragmentChoseLevelBinding is null")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChoseLevelBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}