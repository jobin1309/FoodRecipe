package com.example.foody_modern_food_recipe_app.ui.authActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.foody_modern_food_recipe_app.R
import com.example.foody_modern_food_recipe_app.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivitySignUpBinding.inflate(layoutInflater)


        firebaseAuth = FirebaseAuth.getInstance()

        setContentView(binding.root)

        binding.ToLogin.setOnClickListener {
            val intent = Intent(this, loginActivity::class.java)
            startActivity(intent)
        }

        binding.SignUpbtn.setOnClickListener {
            val email = binding.emailEt.text.toString();
            val password = binding.passET.text.toString();
            val confirmPass = binding.confirmPassEt.text.toString();

            if (email.isNotEmpty() && password.isNotEmpty() && confirmPass.isNotEmpty()) {
                if (isPasswordValid(password) && password == confirmPass) {
                    firebaseAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener {
                            if (it.isSuccessful) {
                                Toast.makeText(this, "Account Created", Toast.LENGTH_SHORT)
                                    .show()
                                val intent = Intent(this, loginActivity::class.java)
                                startActivity(intent)
                            } else {

                                binding.emailEt.setText("")
                                binding.passET.setText("")
                                binding.confirmPassEt.setText("")

                                Toast.makeText(
                                    this,
                                    it.exception.toString(),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                } else {
                    Toast.makeText(
                        this,
                        "Password is not valid or is not matching",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                Toast.makeText(
                    this,
                    "Please fill out the fields",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }


    }

    private fun isPasswordValid(password: String): Boolean {
        val passwordRegex = Regex("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$")
        return password.matches(passwordRegex)
    }
}