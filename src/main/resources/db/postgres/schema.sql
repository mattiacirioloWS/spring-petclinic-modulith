-- Enable UUID generation function if not already enabled
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS vets (
  id         UUID PRIMARY KEY DEFAULT gen_random_uuid(), -- Changed to UUID
  first_name TEXT,
  last_name  TEXT
);
CREATE INDEX ON vets (last_name);

CREATE TABLE IF NOT EXISTS specialties (
  id   UUID PRIMARY KEY DEFAULT gen_random_uuid(), -- Changed to UUID
  name TEXT
);
CREATE INDEX ON specialties (name);

CREATE TABLE IF NOT EXISTS vet_specialties (
  vet_id       UUID NOT NULL REFERENCES vets (id), -- Changed to UUID
  specialty_id UUID NOT NULL REFERENCES specialties (id), -- Changed to UUID
  PRIMARY KEY (vet_id, specialty_id) -- Use PRIMARY KEY instead of UNIQUE for join table PK
);

CREATE TABLE IF NOT EXISTS types (
  id   UUID PRIMARY KEY DEFAULT gen_random_uuid(), -- Changed to UUID
  name TEXT
);
CREATE INDEX ON types (name);

CREATE TABLE IF NOT EXISTS owners (
  id         UUID PRIMARY KEY DEFAULT gen_random_uuid(), -- Changed to UUID
  first_name TEXT,
  last_name  TEXT,
  address    TEXT,
  city       TEXT,
  telephone  TEXT
);
CREATE INDEX ON owners (last_name);

CREATE TABLE IF NOT EXISTS pets (
  id         UUID PRIMARY KEY DEFAULT gen_random_uuid(), -- Changed to UUID
  name       TEXT,
  birth_date DATE,
  type_id    UUID NOT NULL REFERENCES types (id), -- Changed to UUID
  owner_id   UUID REFERENCES owners (id) -- Changed to UUID
);
CREATE INDEX ON pets (name);
CREATE INDEX ON pets (owner_id);

CREATE TABLE IF NOT EXISTS visits (
  id          UUID PRIMARY KEY DEFAULT gen_random_uuid(), -- Changed to UUID
  pet_id      UUID REFERENCES pets (id), -- Changed to UUID
  visit_date  DATE,
  description TEXT
);
CREATE INDEX ON visits (pet_id);
