-- Vets (Using predefined UUIDs)
INSERT INTO vets (id, first_name, last_name) VALUES ('00000000-0000-0000-0000-000000000001', 'James', 'Carter');
INSERT INTO vets (id, first_name, last_name) VALUES ('00000000-0000-0000-0000-000000000002', 'Helen', 'Leary');
INSERT INTO vets (id, first_name, last_name) VALUES ('00000000-0000-0000-0000-000000000003', 'Linda', 'Douglas');
INSERT INTO vets (id, first_name, last_name) VALUES ('00000000-0000-0000-0000-000000000004', 'Rafael', 'Ortega');
INSERT INTO vets (id, first_name, last_name) VALUES ('00000000-0000-0000-0000-000000000005', 'Henry', 'Stevens');
INSERT INTO vets (id, first_name, last_name) VALUES ('00000000-0000-0000-0000-000000000006', 'Sharon', 'Jenkins');

-- Specialties (Using predefined UUIDs)
INSERT INTO specialties (id, name) VALUES ('10000000-0000-0000-0000-000000000001', 'radiology');
INSERT INTO specialties (id, name) VALUES ('10000000-0000-0000-0000-000000000002', 'surgery');
INSERT INTO specialties (id, name) VALUES ('10000000-0000-0000-0000-000000000003', 'dentistry');

-- Vet Specialties (Using predefined UUIDs)
INSERT INTO vet_specialties (vet_id, specialty_id) VALUES ('00000000-0000-0000-0000-000000000002', '10000000-0000-0000-0000-000000000001'); -- Helen Leary - radiology
INSERT INTO vet_specialties (vet_id, specialty_id) VALUES ('00000000-0000-0000-0000-000000000003', '10000000-0000-0000-0000-000000000002'); -- Linda Douglas - surgery
INSERT INTO vet_specialties (vet_id, specialty_id) VALUES ('00000000-0000-0000-0000-000000000003', '10000000-0000-0000-0000-000000000003'); -- Linda Douglas - dentistry
INSERT INTO vet_specialties (vet_id, specialty_id) VALUES ('00000000-0000-0000-0000-000000000004', '10000000-0000-0000-0000-000000000002'); -- Rafael Ortega - surgery
INSERT INTO vet_specialties (vet_id, specialty_id) VALUES ('00000000-0000-0000-0000-000000000005', '10000000-0000-0000-0000-000000000001'); -- Henry Stevens - radiology

-- Types (Using predefined UUIDs)
INSERT INTO types (id, name) VALUES ('20000000-0000-0000-0000-000000000001', 'cat');
INSERT INTO types (id, name) VALUES ('20000000-0000-0000-0000-000000000002', 'dog');
INSERT INTO types (id, name) VALUES ('20000000-0000-0000-0000-000000000003', 'lizard');
INSERT INTO types (id, name) VALUES ('20000000-0000-0000-0000-000000000004', 'snake');
INSERT INTO types (id, name) VALUES ('20000000-0000-0000-0000-000000000005', 'bird');
INSERT INTO types (id, name) VALUES ('20000000-0000-0000-0000-000000000006', 'hamster');

-- Owners (Using predefined UUIDs)
INSERT INTO owners (id, first_name, last_name, address, city, telephone) VALUES ('30000000-0000-0000-0000-000000000001', 'George', 'Franklin', '110 W. Liberty St.', 'Madison', '6085551023');
INSERT INTO owners (id, first_name, last_name, address, city, telephone) VALUES ('30000000-0000-0000-0000-000000000002', 'Betty', 'Davis', '638 Cardinal Ave.', 'Sun Prairie', '6085551749');
INSERT INTO owners (id, first_name, last_name, address, city, telephone) VALUES ('30000000-0000-0000-0000-000000000003', 'Eduardo', 'Rodriquez', '2693 Commerce St.', 'McFarland', '6085558763');
INSERT INTO owners (id, first_name, last_name, address, city, telephone) VALUES ('30000000-0000-0000-0000-000000000004', 'Harold', 'Davis', '563 Friendly St.', 'Windsor', '6085553198');
INSERT INTO owners (id, first_name, last_name, address, city, telephone) VALUES ('30000000-0000-0000-0000-000000000005', 'Peter', 'McTavish', '2387 S. Fair Way', 'Madison', '6085552765');
INSERT INTO owners (id, first_name, last_name, address, city, telephone) VALUES ('30000000-0000-0000-0000-000000000006', 'Jean', 'Coleman', '105 N. Lake St.', 'Monona', '6085552654');
INSERT INTO owners (id, first_name, last_name, address, city, telephone) VALUES ('30000000-0000-0000-0000-000000000007', 'Jeff', 'Black', '1450 Oak Blvd.', 'Monona', '6085555387');
INSERT INTO owners (id, first_name, last_name, address, city, telephone) VALUES ('30000000-0000-0000-0000-000000000008', 'Maria', 'Escobito', '345 Maple St.', 'Madison', '6085557683');
INSERT INTO owners (id, first_name, last_name, address, city, telephone) VALUES ('30000000-0000-0000-0000-000000000009', 'David', 'Schroeder', '2749 Blackhawk Trail', 'Madison', '6085559435');
INSERT INTO owners (id, first_name, last_name, address, city, telephone) VALUES ('30000000-0000-0000-0000-000000000010', 'Carlos', 'Estaban', '2335 Independence La.', 'Waunakee', '6085555487');

-- Pets (Using predefined UUIDs and linking to owner/type UUIDs)
INSERT INTO pets (id, name, birth_date, type_id, owner_id) VALUES ('40000000-0000-0000-0000-000000000001', 'Leo', DATE '2010-09-07', '20000000-0000-0000-0000-000000000001', '30000000-0000-0000-0000-000000000001'); -- Leo (cat) / George Franklin
INSERT INTO pets (id, name, birth_date, type_id, owner_id) VALUES ('40000000-0000-0000-0000-000000000002', 'Basil', DATE '2012-08-06', '20000000-0000-0000-0000-000000000006', '30000000-0000-0000-0000-000000000002'); -- Basil (hamster) / Betty Davis
INSERT INTO pets (id, name, birth_date, type_id, owner_id) VALUES ('40000000-0000-0000-0000-000000000003', 'Rosy', DATE '2011-04-17', '20000000-0000-0000-0000-000000000002', '30000000-0000-0000-0000-000000000003'); -- Rosy (dog) / Eduardo Rodriquez
INSERT INTO pets (id, name, birth_date, type_id, owner_id) VALUES ('40000000-0000-0000-0000-000000000004', 'Jewel', DATE '2010-03-07', '20000000-0000-0000-0000-000000000002', '30000000-0000-0000-0000-000000000003'); -- Jewel (dog) / Eduardo Rodriquez
INSERT INTO pets (id, name, birth_date, type_id, owner_id) VALUES ('40000000-0000-0000-0000-000000000005', 'Iggy', DATE '2010-11-30', '20000000-0000-0000-0000-000000000003', '30000000-0000-0000-0000-000000000004'); -- Iggy (lizard) / Harold Davis
INSERT INTO pets (id, name, birth_date, type_id, owner_id) VALUES ('40000000-0000-0000-0000-000000000006', 'George', DATE '2010-01-20', '20000000-0000-0000-0000-000000000004', '30000000-0000-0000-0000-000000000005'); -- George (snake) / Peter McTavish
INSERT INTO pets (id, name, birth_date, type_id, owner_id) VALUES ('40000000-0000-0000-0000-000000000007', 'Samantha', DATE '2012-09-04', '20000000-0000-0000-0000-000000000001', '30000000-0000-0000-0000-000000000006'); -- Samantha (cat) / Jean Coleman
INSERT INTO pets (id, name, birth_date, type_id, owner_id) VALUES ('40000000-0000-0000-0000-000000000008', 'Max', DATE '2012-09-04', '20000000-0000-0000-0000-000000000001', '30000000-0000-0000-0000-000000000006'); -- Max (cat) / Jean Coleman
INSERT INTO pets (id, name, birth_date, type_id, owner_id) VALUES ('40000000-0000-0000-0000-000000000009', 'Lucky', DATE '2011-08-06', '20000000-0000-0000-0000-000000000005', '30000000-0000-0000-0000-000000000007'); -- Lucky (bird) / Jeff Black
INSERT INTO pets (id, name, birth_date, type_id, owner_id) VALUES ('40000000-0000-0000-0000-000000000010', 'Mulligan', DATE '2007-02-24', '20000000-0000-0000-0000-000000000002', '30000000-0000-0000-0000-000000000008'); -- Mulligan (dog) / Maria Escobito
INSERT INTO pets (id, name, birth_date, type_id, owner_id) VALUES ('40000000-0000-0000-0000-000000000011', 'Freddy', DATE '2010-03-09', '20000000-0000-0000-0000-000000000005', '30000000-0000-0000-0000-000000000009'); -- Freddy (bird) / David Schroeder
INSERT INTO pets (id, name, birth_date, type_id, owner_id) VALUES ('40000000-0000-0000-0000-000000000012', 'Lucky', DATE '2010-06-24', '20000000-0000-0000-0000-000000000002', '30000000-0000-0000-0000-000000000010'); -- Lucky (dog) / Carlos Estaban
INSERT INTO pets (id, name, birth_date, type_id, owner_id) VALUES ('40000000-0000-0000-0000-000000000013', 'Sly', DATE '2012-06-08', '20000000-0000-0000-0000-000000000001', '30000000-0000-0000-0000-000000000010'); -- Sly (cat) / Carlos Estaban

-- Visits (Using predefined UUIDs and linking to pet UUIDs)
INSERT INTO visits (id, pet_id, visit_date, description) VALUES ('50000000-0000-0000-0000-000000000001', '40000000-0000-0000-0000-000000000007', DATE '2013-01-01', 'rabies shot'); -- Samantha
INSERT INTO visits (id, pet_id, visit_date, description) VALUES ('50000000-0000-0000-0000-000000000002', '40000000-0000-0000-0000-000000000008', DATE '2013-01-02', 'rabies shot'); -- Max
INSERT INTO visits (id, pet_id, visit_date, description) VALUES ('50000000-0000-0000-0000-000000000003', '40000000-0000-0000-0000-000000000008', DATE '2013-01-03', 'neutered'); -- Max
INSERT INTO visits (id, pet_id, visit_date, description) VALUES ('50000000-0000-0000-0000-000000000004', '40000000-0000-0000-0000-000000000007', DATE '2013-01-04', 'spayed'); -- Samantha
