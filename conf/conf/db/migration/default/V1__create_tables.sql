DROP TABLE IF EXISTS bar;
CREATE TABLE bar (
  id SERIAL PRIMARY KEY,
  title VARCHAR(250) NOT NULL,
  country VARCHAR(3) NOT NULL,
  year_realsed Int NOT NULL,
  genre VARCHAR(250),
  ranking INT,
  original_title VARCHAR(250),
  french_release DATE,
  synopsis VARCHAR(250),
  CONSTRAINT check_ranking
      CHECK (ranking BETWEEN 0 and 10),

);