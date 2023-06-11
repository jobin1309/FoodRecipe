package com.example.foody_modern_food_recipe_app.ui.authActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import com.example.foody_modern_food_recipe_app.R
import com.example.foody_modern_food_recipe_app.databinding.ActivityLoginBinding
import com.example.foody_modern_food_recipe_app.ui.mainActivity.MainActivity
import com.example.foody_modern_food_recipe_app.ui.mainActivity.fragments.recipesFragments.RecipesFragments
import com.google.firebase.auth.FirebaseAuth

class loginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding;
    private lateinit var firebaseAuth: FirebaseAuth;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.signupbtn.setOnClickListener {
           val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        binding.loginbtn.setOnClickListener {

            val email = binding.editEmail.text.toString()
            val password = binding.editPassword.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            binding.editEmail.setText("")
                            binding.editPassword.setText("")
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                        } else {
                            Log.d("Login btn", "Crashed")
                            Toast.makeText(
                                this,
                                "Password or email not recognized",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            } else {
                Toast.makeText(
                    this,
                    "Please input email and password",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }
    }



}