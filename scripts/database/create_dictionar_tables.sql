DROP TABLE IF EXISTS word;

CREATE SEQUENCE SEQ_WORD_ID;
CREATE TABLE word (
--  uuid                                    VARCHAR(255) PRIMARY KEY,
  id                                      NUMERIC(38,0) PRIMARY KEY DEFAULT NEXTVAL('SEQ_WORD_ID'),
  lang                                    NUMERIC(3,0) NOT NULL,
  word                                    VARCHAR(100) NOT NULL,
  sortedWordChars                         VARCHAR(100),
  wordWithoutDiacritics                   VARCHAR(100),
  sortedWordCharsWithoutDiacritics        VARCHAR(100),
  wordLength                              NUMERIC(3,0) NOT NULL,
  date_created                            TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  date_updated                            TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  UNIQUE (id)
);