package mdcc.sd.rest.deck.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class GameController {

    private RestTemplate restTemplate;

    public GameController() {
        this.restTemplate = new RestTemplate();
    }

    @GetMapping("/cards")
    public ResponseEntity<String> getCards() {
        String apiUrl = "https://deckofcardsapi.com/api/deck/new/draw/?count=1";
        ResponseEntity<String> response = restTemplate.getForEntity(apiUrl, String.class);
        return response;
    }

    @GetMapping("/deal")
    public ResponseEntity<List<String>> dealCards() {
        String apiUrl = "https://deckofcardsapi.com/api/deck/new/draw/?count=2";
        ResponseEntity<DeckResponse> response = restTemplate.getForEntity(apiUrl, DeckResponse.class);

        List<String> cards = new ArrayList<>();
        cards.add(response.getBody().getCards().get(0).getValue());
        cards.add(response.getBody().getCards().get(1).getValue());

        return ResponseEntity.ok(cards);
    }

    @GetMapping("/score")
    public ResponseEntity<Integer> calculateScore(@RequestParam("cards") List<String> cards) {
        int score = 0;

        for (String card : cards) {
            if (card.equals("KING") || card.equals("QUEEN") || card.equals("JACK")) {
                score += 10;
            } else if (card.equals("ACE")) {
                score += 1; // Initial value of 1, can be changed to 11 later if applicable
            } else {
                score += Integer.parseInt(card);
            }
        }

        return ResponseEntity.ok(score);
    }

    // Implement other routes and logic of the game here

    // Inner class to deserialize the response from the Deck of Cards API
    private static class DeckResponse {
        private List<Card> cards;

        public List<Card> getCards() {
            return cards;
        }

        public void setCards(List<Card> cards) {
            this.cards = cards;
        }
    }

    // Inner class to represent a card
    private static class Card {
        private String value;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}

