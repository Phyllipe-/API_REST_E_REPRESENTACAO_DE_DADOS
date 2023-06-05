package mdcc.sd.rest.blackjack

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class GameActivity : AppCompatActivity() {

    private var cartasDoCroupier = ArrayList<ImageView>()
    private var cartasDoJogador = ArrayList<ImageView>()
    private var jogador = Jogador()
    private var croupier = Jogador()
    private var mapaDeCartas = MapeadorDeCartas()

    private val SITUACAO_VITORIA_JOGADOR: String = "Você Ganhou!"
    private val SITUACAO_VITORIA_CROUPIER: String = "O Croupier Venceu!"
    private val SITUACAO_EMPATE: String = "Empate!"
    private val SITUACAO_ESTOUROU_PONTUACAO: String = "Estourou"
    private val PONTUACAO: String = "PONTUACAO: "

    private lateinit var listaDeCartas: ArrayList<Int>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        listaDeCartas = mapaDeCartas.getIDs(this)
        cartasDoCroupier = arrayListOf(findViewById(R.id.croupierCarta1),
            findViewById(R.id.croupierCarta2),
            findViewById(R.id.croupierCarta3),
            findViewById(R.id.croupierCarta4),
            findViewById(R.id.croupierCarta5))
        cartasDoJogador = arrayListOf(findViewById(R.id.jogadorCarta1),
            findViewById(R.id.jogadorCarta2),
            findViewById(R.id.jogadorCarta3),
            findViewById(R.id.jogadorCarta4),
            findViewById(R.id.jogadorCarta5))



        val random = Random()

        var primeiraJogadaCroupier = true
        var numeroAleatorio = 0
        var identificadorDaCarta = 0

        var contador = 0
        var contadorDoCroupier = 0
        var primeiraCartaCroupier = 0
        var store = 0


        val totalPontos: TextView = findViewById(R.id.totalPontos)
        val passar: Button = findViewById(R.id.passar)
        val comprarCarta: Button = findViewById(R.id.comprarCarta)
        val novoJogo: Button = findViewById(R.id.novoJogo)

        for (i in 0 until 4) {
            numeroAleatorio = random.nextInt(listaDeCartas.size)
            identificadorDaCarta = listaDeCartas[numeroAleatorio]
            val nomeDaCarta = resources.getResourceEntryName(identificadorDaCarta)

            if (i % 2 == 0) {
                jogador.adicionarCarta(nomeDaCarta)
                val valorDaCarta = buscarValorCarta(nomeDaCarta)
                totalPontos.text = PONTUACAO + jogador.getTotal(valorDaCarta)
                cartasDoJogador[contador].setImageResource(identificadorDaCarta)
            } else {
                croupier.adicionarCarta(nomeDaCarta)

                if (primeiraJogadaCroupier) {
                    primeiraCartaCroupier = identificadorDaCarta
                    primeiraJogadaCroupier = false
                }
                val valorDaCarta = buscarValorCarta(nomeDaCarta)
                croupier.getTotal(valorDaCarta)

                if (contador == 0) {
                    cartasDoCroupier[contador].setImageResource(R.drawable.back)
                    store = identificadorDaCarta
                } else {
                    cartasDoCroupier[contador].setImageResource(identificadorDaCarta)
                }
                contador++
                contadorDoCroupier++
            }
            listaDeCartas.remove(identificadorDaCarta)
        }

        passar.setOnClickListener {
            while (croupier.getTotal(0) < 17) {
                numeroAleatorio = random.nextInt(listaDeCartas.size)
                identificadorDaCarta = listaDeCartas[numeroAleatorio]
                val name = resources.getResourceEntryName(identificadorDaCarta)
                listaDeCartas.remove(identificadorDaCarta)
                croupier.adicionarCarta(name)
                val cardValue = buscarValorCarta(name)
                croupier.getTotal(cardValue)
                cartasDoCroupier[contadorDoCroupier].setImageResource(identificadorDaCarta)
                contadorDoCroupier++
            }
            cartasDoCroupier[0].setImageResource(store)
            declararVencedor()
            fimDeJogoOpcoes()
        }

        comprarCarta.setOnClickListener {
            numeroAleatorio = random.nextInt(listaDeCartas.size)
            identificadorDaCarta = listaDeCartas[numeroAleatorio]
            val name = resources.getResourceEntryName(identificadorDaCarta)
            listaDeCartas.remove(numeroAleatorio)
            jogador.adicionarCarta(name)
            val cardValue = buscarValorCarta(name)
            totalPontos.text = PONTUACAO + jogador.getTotal(cardValue)
            cartasDoJogador[contador].setImageResource(identificadorDaCarta)
            contador++
            if (jogador.getTotal(0) > 21) {
                totalPontos.text = SITUACAO_ESTOUROU_PONTUACAO
                fimDeJogoOpcoes()
            }
        }

        novoJogo.setOnClickListener {
            resetarJogo()
        }
    }

    private fun buscarValorCarta(carta: String): Int {
        when (carta) {
            "clubs10", "diamonds10", "hearts10", "spades10" -> return 10
            "clubs2", "diamonds2", "hearts2", "spades2" -> return 2
            "clubs3", "diamonds3", "hearts3", "spades3" -> return 3
            "clubs4", "diamonds4", "hearts4", "spades4" -> return 4
            "clubs5", "diamonds5", "hearts5", "spades5" -> return 5
            "clubs6", "diamonds6", "hearts6", "spades6" -> return 6
            "clubs7", "diamonds7", "hearts7", "spades7" -> return 7
            "clubs8", "diamonds8", "hearts8", "spades8" -> return 8
            "clubs9", "diamonds9", "hearts9", "spades9" -> return 9
            "clubs_ace", "diamonds_ace", "hearts_ace", "spades_ace" -> return 1
            "clubs_jack", "diamonds_jack", "hearts_jack", "spades_jack" -> return 10
            "clubs_queen", "diamonds_queen", "hearts_queen", "spades_queen" -> return 10
            "clubs_king", "diamonds_king", "hearts_king", "spades_king" -> return 10
        }
        return 0
    }

    private fun declararVencedor() {
        val totalPontos: TextView = findViewById(R.id.totalPontos)
        when {
            jogador.getTotal(0) == 21 -> totalPontos.text = SITUACAO_VITORIA_JOGADOR
            jogador.getTotal(0) < 21 && jogador.getTotal(0) > croupier.getTotal(0) -> totalPontos.text = SITUACAO_VITORIA_JOGADOR
            jogador.getTotal(0) == croupier.getTotal(0) -> totalPontos.text = SITUACAO_EMPATE
            jogador.getTotal(0) > 21 -> totalPontos.text = SITUACAO_VITORIA_CROUPIER

            croupier.getTotal(0) == 21 -> totalPontos.text = SITUACAO_VITORIA_CROUPIER
            croupier.getTotal(0) < 21 && croupier.getTotal(0) > jogador.getTotal(0) -> totalPontos.text = SITUACAO_VITORIA_CROUPIER
            croupier.getTotal(0) == 21 && jogador.getTotal(0) == 21 -> totalPontos.text = SITUACAO_VITORIA_CROUPIER
            croupier.getTotal(0) > 21 -> totalPontos.text = SITUACAO_VITORIA_JOGADOR
        }
    }

    private fun resetarJogo() {
        finish()
        startActivity(intent)
    }

    private fun fimDeJogoOpcoes() {
        val novoJogo: Button = findViewById(R.id.novoJogo)
        val botoes: LinearLayout = findViewById(R.id.botoes)

        novoJogo.visibility = View.VISIBLE
        botoes.visibility = View.INVISIBLE
    }
}
