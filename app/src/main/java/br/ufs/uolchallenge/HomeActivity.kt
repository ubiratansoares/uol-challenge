package br.ufs.uolchallenge

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import br.ufs.uolchallenge.presentation.NewsFeedViewModel

class HomeActivity : AppCompatActivity() {

    val presenter = NewsFeedViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
    }

}
