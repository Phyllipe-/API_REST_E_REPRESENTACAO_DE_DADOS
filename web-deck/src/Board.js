import React from 'react';
import Card from './Card';
import Player from './Player';

const Board = ({ player, dealer }) => {
  return (
    <div className="board">
      <Player name="Player" score={player.score} />
      <div className="cards">
        {player.cards.map((card, index) => (
          <Card key={index} value={card} />
        ))}
      </div>
      <Player name="Dealer" score={dealer.score} />
      <div className="cards">
        {dealer.cards.map((card, index) => (
          <Card key={index} value={card} />
        ))}
      </div>
    </div>
  );
};

export default Board;
