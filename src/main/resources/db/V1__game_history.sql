-- TODO intuisco a cosa possa servire ma nel tutorial non viene fatta menzione 
-- della necessità di questo file :\
-- la spiegazione dettagliata di una migrazione è forse out of scope per il training?

CREATE TYPE move AS ENUM (
  'Rock',
  'Paper',
  'Scissors'
);

CREATE TYPE match_result AS ENUM (
  'UserWins',
  'Draw',
  'CpuWins',
  'DumbUser'
);

CREATE CAST (varchar AS move) WITH INOUT AS ASSIGNMENT;
CREATE CAST (varchar AS match_result) WITH INOUT AS ASSIGNMENT;

CREATE TABLE history (
  id uuid PRIMARY KEY,
  computer_move move NOT NULL,
  user_move move NOT NULL,
  result match_result NOT NULL,
  occurred_at timestamptz NOT NULL
);
