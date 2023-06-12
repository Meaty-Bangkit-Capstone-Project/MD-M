package com.damar.meaty.splash

import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import com.damar.meaty.R
import com.damar.meaty.customview.AnimationUtil
import com.damar.meaty.databinding.ActivitySplashBinding
import com.damar.meaty.login.LoginActivity

class SplashActivity : AppCompatActivity() {
    private val SPLASH_DELAY: Long = 1500

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val imageViewSplash: ImageView = binding.imageViewSplash
        val animation = AnimationUtils.loadAnimation(this, R.anim.bounce_anim)
        imageViewSplash.startAnimation(animation)

        playTextAnimation()

        supportActionBar?.hide()

        Handler().postDelayed({
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }, SPLASH_DELAY)
    }

    private fun playTextAnimation() {
//        val textViewAnimator = ObjectAnimator.ofFloat(binding.textView, View.ALPHA, 0f, 1f)
//        textViewAnimator.duration = 1300
//        textViewAnimator.start()

        val textView2Animator = ObjectAnimator.ofFloat(binding.textView2, View.ALPHA, 0f, 1f)
        textView2Animator.duration = 1300 // Durasi animasi dalam milidetik
        textView2Animator.start()
    }
}