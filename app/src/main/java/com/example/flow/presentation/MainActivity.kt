package com.example.flow.presentation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.flow.databinding.ActivityMainBinding
import com.example.flow.presentation.coroutines.CoroutinesActivity
import com.example.flow.presentation.flow.FlowActivity
import com.example.flow.presentation.flowext.FlowExtActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    private fun setupViews() = with(binding) {
        btFlow.setOnClickListener {
            startActivity(Intent(baseContext, FlowActivity::class.java))
        }

        btFlowExt.setOnClickListener {
            startActivity(Intent(baseContext, FlowExtActivity::class.java))
        }

        btCoroutines.setOnClickListener {
            startActivity(Intent(baseContext, CoroutinesActivity::class.java))
        }
    }
}