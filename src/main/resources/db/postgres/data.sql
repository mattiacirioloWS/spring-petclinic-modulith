-- Vets (Using predefined UUIDs)
INSERT INTO vets (id, first_name, last_name) VALUES ('00000000-0000-0000-0000-000000000001', 'James', 'Carter') ON CONFLICT (id) DO NOTHING;
INSERT INTO vets (id, first_name, last_name) VALUES ('00000000-0000-0000-0000-000000000002', 'Helen', 'Leary') ON CONFLICT (id) DO NOTHING;
INSERT INTO vets (id, first_name, last_name) VALUES ('00000000-0000-0000-0000-000000000003', 'Linda', 'Douglas') ON CONFLICT (id) DO NOTHING;
INSERT INTO vets (id, first_name, last_name) VALUES ('00000000-0000-0000-0000-000000000004', 'Rafael', 'Ortega') ON CONFLICT (id) DO NOTHING;
INSERT INTO vets (id, first_name, last_name) VALUES ('00000000-0000-0000-0000-000000000005', 'Henry', 'Stevens') ON CONFLICT (id) DO NOTHING;
INSERT INTO vets (id, first_name, last_name) VALUES ('00000000-0000-0000-0000-000000000006', 'Sharon', 'Jenkins') ON CONFLICT (id) DO NOTHING;

-- Specialties (Using predefined UUIDs)
INSERT INTO specialties (id, name) VALUES ('10000000-0000-0000-0000-000000000001', 'radiology') ON CONFLICT (id) DO NOTHING;
INSERT INTO specialties (id, name) VALUES ('10000000-0000-0000-0000-000000000002', 'surgery') ON CONFLICT (id) DO NOTHING;
INSERT INTO specialties (id, name) VALUES ('10000000-0000-0000-0000-000000000003', 'dentistry') ON CONFLICT (id) DO NOTHING;

-- Vet Specialties (Using predefined UUIDs)
INSERT INTO vet_specialties (vet_id, specialty_id) VALUES ('00000000-0000-0000-0000-000000000002', '10000000-0000-0000-0000-000000000001') ON CONFLICT (vet_id, specialty_id) DO NOTHING; -- Helen Leary - radiology
INSERT INTO vet_specialties (vet_id, specialty_id) VALUES ('00000000-0000-0000-0000-000000000003', '10000000-0000-0000-0000-000000000002') ON CONFLICT (vet_id, specialty_id) DO NOTHING; -- Linda Douglas - surgery
INSERT INTO vet_specialties (vet_id, specialty_id) VALUES ('00000000-0000-0000-0000-000000000003', '10000000-0000-0000-0000-000000000003') ON CONFLICT (vet_id, specialty_id) DO NOTHING; -- Linda Douglas - dentistry
INSERT INTO vet_specialties (vet_id, specialty_id) VALUES ('00000000-0000-0000-0000-000000000004', '10000000-0000-0000-0000-000000000002') ON CONFLICT (vet_id, specialty_id) DO NOTHING; -- Rafael Ortega - surgery
INSERT INTO vet_specialties (vet_id, specialty_id) VALUES ('00000000-0000-0000-0000-000000000005', '10000000-0000-0000-0000-000000000001') ON CONFLICT (vet_id, specialty_id) DO NOTHING; -- Henry Stevens - radiology

-- Types (Using predefined UUIDs)
INSERT INTO types (id, name) VALUES ('20000000-0000-0000-0000-000000000001', 'cat') ON CONFLICT (id) DO NOTHING;
INSERT INTO types (id, name) VALUES ('20000000-0000-0000-0000-000000000002', 'dog') ON CONFLICT (id) DO NOTHING;
INSERT INTO types (id, name) VALUES ('20000000-0000-0000-0000-000000000003', 'lizard') ON CONFLICT (id) DO NOTHING;
INSERT INTO types (id, name) VALUES ('20000000-0000-0000-0000-000000000004', 'snake') ON CONFLICT (id) DO NOTHING;
INSERT INTO types (id, name) VALUES ('20000000-0000-0000-0000-000000000005', 'bird') ON CONFLICT (id) DO NOTHING;
INSERT INTO types (id, name) VALUES ('20000000-0000-0000-0000-000000000006', 'hamster') ON CONFLICT (id) DO NOTHING;

-- Owners (Using predefined UUIDs)
INSERT INTO owners (id, first_name, last_name, address, city, telephone) VALUES ('30000000-0000-0000-0000-000000000001', 'George', 'Franklin', '110 W. Liberty St.', 'Madison', '6085551023') ON CONFLICT (id) DO NOTHING;
INSERT INTO owners (id, first_name, last_name, address, city, telephone) VALUES ('30000000-0000-0000-0000-000000000002', 'Betty', 'Davis', '638 Cardinal Ave.', 'Sun Prairie', '6085551749') ON CONFLICT (id) DO NOTHING;
INSERT INTO owners (id, first_name, last_name, address, city, telephone) VALUES ('30000000-0000-0000-0000-000000000003', 'Eduardo', 'Rodriquez', '2693 Commerce St.', 'McFarland', '6085558763') ON CONFLICT (id) DO NOTHING;
INSERT INTO owners (id, first_name, last_name, address, city, telephone) VALUES ('30000000-0000-0000-0000-000000000004', 'Harold', 'Davis', '563 Friendly St.', 'Windsor', '6085553198') ON CONFLICT (id) DO NOTHING;
INSERT INTO owners (id, first_name, last_name, address, city, telephone) VALUES ('30000000-0000-0000-0000-000000000005', 'Peter', 'McTavish', '2387 S. Fair Way', 'Madison', '6085552765') ON CONFLICT (id) DO NOTHING;
INSERT INTO owners (id, first_name, last_name, address, city, telephone) VALUES ('30000000-0000-0000-0000-000000000006', 'Jean', 'Coleman', '105 N. Lake St.', 'Monona', '6085552654') ON CONFLICT (id) DO NOTHING;
INSERT INTO owners (id, first_name, last_name, address, city, telephone) VALUES ('30000000-0000-0000-0000-000000000007', 'Jeff', 'Black', '1450 Oak Blvd.', 'Monona', '6085555387') ON CONFLICT (id) DO NOTHING;
INSERT INTO owners (id, first_name, last_name, address, city, telephone) VALUES ('30000000-0000-0000-0000-000000000008', 'Maria', 'Escobito', '345 Maple St.', 'Madison', '6085557683') ON CONFLICT (id) DO NOTHING;
INSERT INTO owners (id, first_name, last_name, address, city, telephone) VALUES ('30000000-0000-0000-0000-000000000009', 'David', 'Schroeder', '2749 Blackhawk Trail', 'Madison', '6085559435') ON CONFLICT (id) DO NOTHING;
INSERT INTO owners (id, first_name, last_name, address, city, telephone) VALUES ('30000000-0000-0000-0000-000000000010', 'Carlos', 'Estaban', '2335 Independence La.', 'Waunakee', '6085555487') ON CONFLICT (id) DO NOTHING;

-- Pets (Using predefined UUIDs and linking to owner/type UUIDs)
INSERT INTO pets (id, name, birth_date, type_id, owner_id) VALUES ('40000000-0000-0000-0000-000000000001', 'Leo', '2000-09-07', '20000000-0000-0000-0000-000000000001', '30000000-0000-0000-0000-000000000001') ON CONFLICT (id) DO NOTHING; -- Leo (cat) / George Franklin
INSERT INTO pets (id, name, birth_date, type_id, owner_id) VALUES ('40000000-0000-0000-0000-000000000002', 'Basil', '2002-08-06', '20000000-0000-0000-0000-000000000006', '30000000-0000-0000-0000-000000000002') ON CONFLICT (id) DO NOTHING; -- Basil (hamster) / Betty Davis
INSERT INTO pets (id, name, birth_date, type_id, owner_id) VALUES ('40000000-0000-0000-0000-000000000003', 'Rosy', '2001-04-17', '20000000-0000-0000-0000-000000000002', '30000000-0000-0000-0000-000000000003') ON CONFLICT (id) DO NOTHING; -- Rosy (dog) / Eduardo Rodriquez
INSERT INTO pets (id, name, birth_date, type_id, owner_id) VALUES ('40000000-0000-0000-0000-000000000004', 'Jewel', '2000-03-07', '20000000-0000-0000-0000-000000000002', '30000000-0000-0000-0000-000000000003') ON CONFLICT (id) DO NOTHING; -- Jewel (dog) / Eduardo Rodriquez
INSERT INTO pets (id, name, birth_date, type_id, owner_id) VALUES ('40000000-0000-0000-0000-000000000005', 'Iggy', '2000-11-30', '20000000-0000-0000-0000-000000000003', '30000000-0000-0000-0000-000000000004') ON CONFLICT (id) DO NOTHING; -- Iggy (lizard) / Harold Davis
INSERT INTO pets (id, name, birth_date, type_id, owner_id) VALUES ('40000000-0000-0000-0000-000000000006', 'George', '2000-01-20', '20000000-0000-0000-0000-000000000004', '30000000-0000-0000-0000-000000000005') ON CONFLICT (id) DO NOTHING; -- George (snake) / Peter McTavish
INSERT INTO pets (id, name, birth_date, type_id, owner_id) VALUES ('40000000-0000-0000-0000-000000000007', 'Samantha', '1995-09-04', '20000000-0000-0000-0000-000000000001', '30000000-0000-0000-0000-000000000006') ON CONFLICT (id) DO NOTHING; -- Samantha (cat) / Jean Coleman
INSERT INTO pets (id, name, birth_date, type_id, owner_id) VALUES ('40000000-0000-0000-0000-000000000008', 'Max', '1995-09-04', '20000000-0000-0000-0000-000000000001', '30000000-0000-0000-0000-000000000006') ON CONFLICT (id) DO NOTHING; -- Max (cat) / Jean Coleman
INSERT INTO pets (id, name, birth_date, type_id, owner_id) VALUES ('40000000-0000-0000-0000-000000000009', 'Lucky', '1999-08-06', '20000000-0000-0000-0000-000000000005', '30000000-0000-0000-0000-000000000007') ON CONFLICT (id) DO NOTHING; -- Lucky (bird) / Jeff Black
INSERT INTO pets (id, name, birth_date, type_id, owner_id) VALUES ('40000000-0000-0000-0000-000000000010', 'Mulligan', '1997-02-24', '20000000-0000-0000-0000-000000000002', '30000000-0000-0000-0000-000000000008') ON CONFLICT (id) DO NOTHING; -- Mulligan (dog) / Maria Escobito
INSERT INTO pets (id, name, birth_date, type_id, owner_id) VALUES ('40000000-0000-0000-0000-000000000011', 'Freddy', '2000-03-09', '20000000-0000-0000-0000-000000000005', '30000000-0000-0000-0000-000000000009') ON CONFLICT (id) DO NOTHING; -- Freddy (bird) / David Schroeder
INSERT INTO pets (id, name, birth_date, type_id, owner_id) VALUES ('40000000-0000-0000-0000-000000000012', 'Lucky', '2000-06-24', '20000000-0000-0000-0000-000000000002', '30000000-0000-0000-0000-000000000010') ON CONFLICT (id) DO NOTHING; -- Lucky (dog) / Carlos Estaban
INSERT INTO pets (id, name, birth_date, type_id, owner_id) VALUES ('40000000-0000-0000-0000-000000000013', 'Sly', '2002-06-08', '20000000-0000-0000-0000-000000000001', '30000000-0000-0000-0000-000000000010') ON CONFLICT (id) DO NOTHING; -- Sly (cat) / Carlos Estaban

-- Visits (Using predefined UUIDs and linking to pet UUIDs)
INSERT INTO visits (id, pet_id, visit_date, description) VALUES ('50000000-0000-0000-0000-000000000001', '40000000-0000-0000-0000-000000000007', '2010-03-04', 'rabies shot') ON CONFLICT (id) DO NOTHING; -- Samantha
INSERT INTO visits (id, pet_id, visit_date, description) VALUES ('50000000-0000-0000-0000-000000000002', '40000000-0000-0000-0000-000000000008', '2011-03-04', 'rabies shot') ON CONFLICT (id) DO NOTHING; -- Max
INSERT INTO visits (id, pet_id, visit_date, description) VALUES ('50000000-0000-0000-0000-000000000003', '40000000-0000-0000-0000-000000000008', '2009-06-04', 'neutered') ON CONFLICT (id) DO NOTHING; -- Max
INSERT INTO visits (id, pet_id, visit_date, description) VALUES ('50000000-0000-0000-0000-000000000004', '40000000-0000-0000-0000-000000000007', '2008-09-04', 'spayed') ON CONFLICT (id) DO NOTHING; -- Samantha
