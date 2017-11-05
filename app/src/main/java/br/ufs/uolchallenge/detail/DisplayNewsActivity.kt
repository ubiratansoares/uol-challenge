package br.ufs.uolchallenge.detail

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import br.ufs.uolchallenge.R
import kotlinx.android.synthetic.main.activity_display_news.*

class DisplayNewsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_news)
        setSupportActionBar(toolbar)
    }

    companion object {
        val VISUALIZATION_URL = "visualization.url"
        val SHARE_URL = "share.url"
    }
}
