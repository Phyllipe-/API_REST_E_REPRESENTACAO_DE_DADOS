package mdcc.sd.rest.blackjack

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

/**
 *
 */
class MainActivity : AppCompatActivity() {
    private lateinit var imageViewPrincipal: ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imageViewPrincipal = findViewById(R.id.imageViewPrincipal) // Obter a referência do ImageView

        imageViewPrincipal.setOnClickListener {
            val intent = Intent(this@MainActivity, GameActivity::class.java)
            startActivity(intent)
        }
    }
}