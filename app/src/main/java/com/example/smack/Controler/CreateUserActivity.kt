package com.example.smack.Controler

import android.content.Context
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import com.example.smack.R
import com.example.smack.Services.AuthService
import com.example.smack.Services.UserDataService
import com.example.smack.Services.UserDataService.name
import kotlinx.android.synthetic.main.activity_create_user.*
import java.util.*

class CreateUserActivity : AppCompatActivity() {

    var userAvatar = "proileDefault"
    var avatarColor = "[0.5, 0.5, 0.5, 1]"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_user)
    }

    fun generateUserAvatar(view: View) {
        val random = Random()
        val color = random.nextInt(2)
        val avatar = random.nextInt(28)

        if(color == 0) {
            userAvatar = "light$avatar"
        } else {
            userAvatar = "dark$avatar"
        }

        val resourceId = resources.getIdentifier(userAvatar, "drawable", packageName)
        createAvatarImageView.setImageResource(resourceId)
    }

    fun generateColorClicked(view: View){
        val random = Random()
        val r = random.nextInt(255)
        val b = random.nextInt(255)
        val g = random.nextInt(255)

        createAvatarImageView.setBackgroundColor(Color.rgb(r,g,b))

        val savedR = r.toDouble()
        val savedB = b.toDouble()
        val savedG = g.toDouble()

        avatarColor = "[$savedR, $savedG, $savedB, 1]"
    }

    fun createUserClicked(view:  View) {

        val userName = createUserNameText.text.toString()
        val email = createEmailText.text.toString()
        val password = createPasswordText.text.toString()

        AuthService.registerUser(this, email, password) {registerSucces ->
            if(registerSucces) {
                AuthService.loginUser(this, email, password){loginSucces ->
                    if(loginSucces){
                        AuthService.createUser(this, userName, email, userAvatar, avatarColor) {createSuccess ->
                            if(createSuccess){
                                println(UserDataService.avatarName)
                                println(UserDataService.avatarColor)
                                println(UserDataService.name)
                                finish()
                            }
                        }
                    }

                }
            }

        }
    }
}
