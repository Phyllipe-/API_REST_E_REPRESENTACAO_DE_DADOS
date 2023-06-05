package mdcc.sd.rest.blackjack

class Jogador {
    private var totalDePontos = 0
    private var listaDeCartas = ArrayList<String>()

    fun getTotal(valorDaCarta: Int): Int{
        totalDePontos = totalDePontos + valorDaCarta
        return totalDePontos
    }

    fun adicionarCarta(carta: String){
        listaDeCartas.add(carta)
    }
}