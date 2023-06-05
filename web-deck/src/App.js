import React, { useEffect, useState } from 'react';
import { Button } from '@material-ui/core';
import axios from 'axios';
import Board from './Board';



const App = () => {
  const [player, setPlayer] = useState({ cards: [], score: 0 });
  const [dealer, setDealer] = useState({ cards: [], score: 0 });

  const dealCards = async () => {
    try {
      const response = await axios.get('http://127.0.0.1:8081/api/deal');
      const cards = response.data;
      setPlayer({ cards, score: 0 });
      setDealer({ cards: [cards[0]], score: 0 });
    } catch (error) {
      console.error(error);
    }
  };

  useEffect(() => {
    const calculateScore = async () => {
      try {
        const response = await axios.get(`http://127.0.0.1:8081/api/score?cards=${player.cards.join(',')}`);
        const score = response.data;
        setPlayer((prevPlayer) => ({ ...prevPlayer, score }));
      } catch (error) {
        console.error(error);
      }
    };

    calculateScore();
  }, [player.cards]);

  return (
    <div className="app">
      <h1>Jogo de 21</h1>
      <Board player={player} dealer={dealer} />
      <Button variant="contained" color="primary" onClick={dealCards}>
        Distribuir cartas
      </Button>
    </div>
  );
};

export default App;
