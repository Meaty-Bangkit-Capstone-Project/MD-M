package com.damar.meaty.customview

import android.animation.*
import android.view.View
import com.damar.meaty.databinding.*

class AnimationUtil {
    companion object {
        fun playLoginAnimation(binding: ActivityLoginBinding) {
            val textImageAnimator = ObjectAnimator.ofFloat(binding.fieldTextImage, View.ALPHA, 1f).setDuration(520)
            val usernameAnimator = ObjectAnimator.ofFloat(binding.fieldUsername, View.ALPHA, 1f).setDuration(500)
            val passwordAnimator = ObjectAnimator.ofFloat(binding.fieldPassword, View.ALPHA, 1f).setDuration(480)
            val loginButtonAnimator = ObjectAnimator.ofFloat(binding.btnLogin, View.ALPHA, 1f).setDuration(460)
            val textView1Animator = ObjectAnimator.ofFloat(binding.textView2, View.ALPHA, 1f).setDuration(440)
            val textView2Animator = ObjectAnimator.ofFloat(binding.txtSignUp, View.ALPHA, 1f).setDuration(420)

            val togetherAnimator = AnimatorSet().apply {
                playTogether(textView1Animator, textView2Animator)
            }
            AnimatorSet().apply {
                playSequentially(textImageAnimator, usernameAnimator, passwordAnimator, loginButtonAnimator, togetherAnimator)
                start()
            }
        }

        fun playRegisAnimation(binding: ActivityRegisterBinding) {
            val textImageAnimator = ObjectAnimator.ofFloat(binding.fieldTextImage, View.ALPHA, 1f).setDuration(500)
            val nameAnimator = ObjectAnimator.ofFloat(binding.fieldName, View.ALPHA, 1f).setDuration(460)
            val usernameAnimator = ObjectAnimator.ofFloat(binding.fieldUsername, View.ALPHA, 1f).setDuration(420)
            val genderAnimator = ObjectAnimator.ofFloat(binding.fieldGender, View.ALPHA, 1f).setDuration(380)
            val domisiliAnimator = ObjectAnimator.ofFloat(binding.fieldDomisili, View.ALPHA, 1f).setDuration(340)
            val ageAnimator = ObjectAnimator.ofFloat(binding.fieldAge, View.ALPHA, 1f).setDuration(300)
            val workAnimator = ObjectAnimator.ofFloat(binding.fieldWork, View.ALPHA, 1f).setDuration(260)
            val passwordAnimator = ObjectAnimator.ofFloat(binding.fieldPassword, View.ALPHA, 1f).setDuration(220)
            val registerButtonAnimator = ObjectAnimator.ofFloat(binding.btnRegister, View.ALPHA, 1f).setDuration(180)
            val textView1Animator = ObjectAnimator.ofFloat(binding.textView2, View.ALPHA, 1f).setDuration(140)
            val textView2Animator = ObjectAnimator.ofFloat(binding.txtLogin, View.ALPHA, 1f).setDuration(100)

            val togetherAnimator = AnimatorSet().apply {
                playTogether(textView1Animator, textView2Animator)
            }
            AnimatorSet().apply {
                playSequentially(textImageAnimator, nameAnimator, usernameAnimator, genderAnimator, domisiliAnimator, ageAnimator, workAnimator, passwordAnimator, registerButtonAnimator, togetherAnimator)
                start()
            }
        }

        fun playSettingAnimation(binding: ActivitySettingBinding) {
            val textImageAnimator = ObjectAnimator.ofFloat(binding.fieldTextImage, View.ALPHA, 1f).setDuration(620)
            val setLanguageAnimator = ObjectAnimator.ofFloat(binding.fieldSettingLanguage, View.ALPHA, 1f).setDuration(600)
            val setDiplayAnimator = ObjectAnimator.ofFloat(binding.fieldSettingDisplay, View.ALPHA, 1f).setDuration(600)

            val togetherAnimator = AnimatorSet().apply {
                playTogether(setLanguageAnimator, setDiplayAnimator)
            }

            val imgAnimator = ObjectAnimator.ofPropertyValuesHolder(
                binding.ivCreatePhoto,
                PropertyValuesHolder.ofFloat(View.ALPHA, 0f, 1f),
                PropertyValuesHolder.ofFloat(View.TRANSLATION_Y, binding.ivCreatePhoto.height.toFloat(), 0f),
                PropertyValuesHolder.ofFloat(View.SCALE_X, 0.5f, 1f),
                PropertyValuesHolder.ofFloat(View.SCALE_Y, 0.5f, 1f)
            ).apply {
                duration = 800
                startDelay = 180
            }

            AnimatorSet().apply {
                playSequentially(textImageAnimator, togetherAnimator, imgAnimator)
                start()
            }
        }


        fun playHomeAnimation(binding: FragmentHomeBinding) {
            val imgAnimator = ObjectAnimator.ofFloat(binding.frameLayout, View.ALPHA, 1f).setDuration(480)
            val fram2Animator = ObjectAnimator.ofFloat(binding.frameLayout2, View.ALPHA, 1f).setDuration(460)
            val itemAnimator = ObjectAnimator.ofFloat(binding.bRecyclerView, View.ALPHA, 1f).setDuration(400)
            val field1Animator = ObjectAnimator.ofFloat(binding.field1, View.ALPHA, 1f).setDuration(400)
            val field2Animator = ObjectAnimator.ofFloat(binding.field2, View.ALPHA, 1f).setDuration(400)

            val togetherAnimator = AnimatorSet().apply {
                playTogether(itemAnimator, field1Animator, field2Animator)
            }

            AnimatorSet().apply {
                playSequentially(imgAnimator, fram2Animator, togetherAnimator)
                start()
            }
        }

        fun playArtikelAllAnimation(binding: FragmentAllBinding) {
            val itemAnimator = ObjectAnimator.ofFloat(binding.bRecyclerView, View.ALPHA, 1f).setDuration(600)

            AnimatorSet().apply {
                playSequentially(itemAnimator)
                start()
            }
        }

        fun playArtikelGayaHidupAnimation(binding: FragmentGayaHidupBinding) {
            val itemAnimator = ObjectAnimator.ofFloat(binding.bRecyclerView, View.ALPHA, 1f).setDuration(600)

            AnimatorSet().apply {
                playSequentially(itemAnimator)
                start()
            }
        }

        fun playArtikelRecipeAnimation(binding: FragmentRecipeBinding) {
            val itemAnimator = ObjectAnimator.ofFloat(binding.bRecyclerView, View.ALPHA, 1f).setDuration(600)

            AnimatorSet().apply {
                playSequentially(itemAnimator)
                start()
            }
        }

        fun playArtikelTipsAnimation(binding: FragmentTipsBinding) {
            val itemAnimator = ObjectAnimator.ofFloat(binding.bRecyclerView, View.ALPHA, 1f).setDuration(600)

            AnimatorSet().apply {
                playSequentially(itemAnimator)
                start()
            }
        }

        fun playAddScanAnimation(binding: FragmentAddScanBinding) {
            val imgScanAnimator = ObjectAnimator.ofFloat(binding.ivCreatePhoto, View.ALPHA, 1f).setDuration(520)
            val descriptionAnimator = ObjectAnimator.ofFloat(binding.edAddDescription, View.ALPHA, 1f).setDuration(460)
            val buttonCameraAnimator = ObjectAnimator.ofFloat(binding.buttonCamera, View.ALPHA, 1f).setDuration(400)
            val buttonGalleryAnimator = ObjectAnimator.ofFloat(binding.buttonGallery, View.ALPHA, 1f).setDuration(400)
            val buttonUploadAnimator = ObjectAnimator.ofFloat(binding.buttonAdd, View.ALPHA, 1f).setDuration(380)
            val refresgAnimator = ObjectAnimator.ofFloat(binding.refresh, View.ALPHA, 1f).setDuration(400)
            val cekAnimator = ObjectAnimator.ofFloat(binding.buttonCekHasil, View.ALPHA, 1f).setDuration(400)

            val togetherAnimator1 = AnimatorSet().apply {
                playTogether(buttonGalleryAnimator, buttonCameraAnimator)
            }

            val togetherAnimator2 = AnimatorSet().apply {
                playTogether(refresgAnimator, cekAnimator)
            }

            val togetherAnimator3 = AnimatorSet().apply {
                playTogether(togetherAnimator2, descriptionAnimator)
            }


            AnimatorSet().apply {
                playSequentially(imgScanAnimator, descriptionAnimator, togetherAnimator1, buttonUploadAnimator, cekAnimator, refresgAnimator)
                start()
            }
        }

        fun playRiwayatAnimation(binding: FragmentRiwayatBinding) {
            val itemAnimator = ObjectAnimator.ofFloat(binding.rvStory, View.ALPHA, 1f).setDuration(500)

            AnimatorSet().apply {
                playSequentially(itemAnimator)
                start()
            }
        }
    }
}