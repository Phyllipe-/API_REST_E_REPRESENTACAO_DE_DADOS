import React from 'react';

const Player = ({ name, score }) => {
  return (
    <div className="player">
      <h2>{name}</h2>
      <p>Score: {score}</p>
    </div>
  );
};

export default Player;
