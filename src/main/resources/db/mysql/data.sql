-- Vets (Using predefined UUIDs with UUID_TO_BIN)
INSERT IGNORE INTO vets (id, first_name, last_name) VALUES (UUID_TO_BIN('00000000-0000-0000-0000-000000000001'), 'James', 'Carter');
INSERT IGNORE INTO vets (id, first_name, last_name) VALUES (UUID_TO_BIN('00000000-0000-0000-0000-000000000002'), 'Helen', 'Leary');
INSERT IGNORE INTO vets (id, first_name, last_name) VALUES (UUID_TO_BIN('00000000-0000-0000-0000-000000000003'), 'Linda', 'Douglas');
INSERT IGNORE INTO vets (id, first_name, last_name) VALUES (UUID_TO_BIN('00000000-0000-0000-0000-000000000004'), 'Rafael', 'Ortega');
INSERT IGNORE INTO vets (id, first_name, last_name) VALUES (UUID_TO_BIN('00000000-0000-0000-0000-000000000005'), 'Henry', 'Stevens');
INSERT IGNORE INTO vets (id, first_name, last_name) VALUES (UUID_TO_BIN('00000000-0000-0000-0000-000000000006'), 'Sharon', 'Jenkins');

-- Specialties (Using predefined UUIDs with UUID_TO_BIN)
INSERT IGNORE INTO specialties (id, name) VALUES (UUID_TO_BIN('10000000-0000-0000-0000-000000000001'), 'radiology');
INSERT IGNORE INTO specialties (id, name) VALUES (UUID_TO_BIN('10000000-0000-0000-0000-000000000002'), 'surgery');
INSERT IGNORE INTO specialties (id, name) VALUES (UUID_TO_BIN('10000000-0000-0000-0000-000000000003'), 'dentistry');

-- Vet Specialties (Using predefined UUIDs with UUID_TO_BIN)
INSERT IGNORE INTO vet_specialties (vet_id, specialty_id) VALUES (UUID_TO_BIN('00000000-0000-0000-0000-000000000002'), UUID_TO_BIN('10000000-0000-0000-0000-000000000001')); -- Helen Leary - radiology
INSERT IGNORE INTO vet_specialties (vet_id, specialty_id) VALUES (UUID_TO_BIN('00000000-0000-0000-0000-000000000003'), UUID_TO_BIN('10000000-0000-0000-0000-000000000002')); -- Linda Douglas - surgery
INSERT IGNORE INTO vet_specialties (vet_id, specialty_id) VALUES (UUID_TO_BIN('00000000-0000-0000-0000-000000000003'), UUID_TO_BIN('10000000-0000-0000-0000-000000000003')); -- Linda Douglas - dentistry
INSERT IGNORE INTO vet_specialties (vet_id, specialty_id) VALUES (UUID_TO_BIN('00000000-0000-0000-0000-000000000004'), UUID_TO_BIN('10000000-0000-0000-0000-000000000002')); -- Rafael Ortega - surgery
INSERT IGNORE INTO vet_specialties (vet_id, specialty_id) VALUES (UUID_TO_BIN('00000000-0000-0000-0000-000000000005'), UUID_TO_BIN('10000000-0000-0000-0000-000000000001')); -- Henry Stevens - radiology

-- Types (Using predefined UUIDs with UUID_TO_BIN)
INSERT IGNORE INTO types (id, name) VALUES (UUID_TO_BIN('20000000-0000-0000-0000-000000000001'), 'cat');
INSERT IGNORE INTO types (id, name) VALUES (UUID_TO_BIN('20000000-0000-0000-0000-000000000002'), 'dog');
INSERT IGNORE INTO types (id, name) VALUES (UUID_TO_BIN('20000000-0000-0000-0000-000000000003'), 'lizard');
INSERT IGNORE INTO types (id, name) VALUES (UUID_TO_BIN('20000000-0000-0000-0000-000000000004'), 'snake');
INSERT IGNORE INTO types (id, name) VALUES (UUID_TO_BIN('20000000-0000-0000-0000-000000000005'), 'bird');
INSERT IGNORE INTO types (id, name) VALUES (UUID_TO_BIN('20000000-0000-0000-0000-000000000006'), 'hamster');

-- Owners (Using predefined UUIDs with UUID_TO_BIN)
INSERT IGNORE INTO owners (id, first_name, last_name, address, city, telephone) VALUES (UUID_TO_BIN('30000000-0000-0000-0000-000000000001'), 'George', 'Franklin', '110 W. Liberty St.', 'Madison', '6085551023');
INSERT IGNORE INTO owners (id, first_name, last_name, address, city, telephone) VALUES (UUID_TO_BIN('30000000-0000-0000-0000-000000000002'), 'Betty', 'Davis', '638 Cardinal Ave.', 'Sun Prairie', '6085551749');
INSERT IGNORE INTO owners (id, first_name, last_name, address, city, telephone) VALUES (UUID_TO_BIN('30000000-0000-0000-0000-000000000003'), 'Eduardo', 'Rodriquez', '2693 Commerce St.', 'McFarland', '6085558763');
INSERT IGNORE INTO owners (id, first_name, last_name, address, city, telephone) VALUES (UUID_TO_BIN('30000000-0000-0000-0000-000000000004'), 'Harold', 'Davis', '563 Friendly St.', 'Windsor', '6085553198');
INSERT IGNORE INTO owners (id, first_name, last_name, address, city, telephone) VALUES (UUID_TO_BIN('30000000-0000-0000-0000-000000000005'), 'Peter', 'McTavish', '2387 S. Fair Way', 'Madison', '6085552765');
INSERT IGNORE INTO owners (id, first_name, last_name, address, city, telephone) VALUES (UUID_TO_BIN('30000000-0000-0000-0000-000000000006'), 'Jean', 'Coleman', '105 N. Lake St.', 'Monona', '6085552654');
INSERT IGNORE INTO owners (id, first_name, last_name, address, city, telephone) VALUES (UUID_TO_BIN('30000000-0000-0000-0000-000000000007'), 'Jeff', 'Black', '1450 Oak Blvd.', 'Monona', '6085555387');
INSERT IGNORE INTO owners (id, first_name, last_name, address, city, telephone) VALUES (UUID_TO_BIN('30000000-0000-0000-0000-000000000008'), 'Maria', 'Escobito', '345 Maple St.', 'Madison', '6085557683');
INSERT IGNORE INTO owners (id, first_name, last_name, address, city, telephone) VALUES (UUID_TO_BIN('30000000-0000-0000-0000-000000000009'), 'David', 'Schroeder', '2749 Blackhawk Trail', 'Madison', '6085559435');
INSERT IGNORE INTO owners (id, first_name, last_name, address, city, telephone) VALUES (UUID_TO_BIN('30000000-0000-0000-0000-000000000010'), 'Carlos', 'Estaban', '2335 Independence La.', 'Waunakee', '6085555487');

-- Pets (Using predefined UUIDs with UUID_TO_BIN and linking to owner/type UUIDs)
INSERT IGNORE INTO pets (id, name, birth_date, type_id, owner_id) VALUES (UUID_TO_BIN('40000000-0000-0000-0000-000000000001'), 'Leo', '2000-09-07', UUID_TO_BIN('20000000-0000-0000-0000-000000000001'), UUID_TO_BIN('30000000-0000-0000-0000-000000000001')); -- Leo (cat) / George Franklin
INSERT IGNORE INTO pets (id, name, birth_date, type_id, owner_id) VALUES (UUID_TO_BIN('40000000-0000-0000-0000-000000000002'), 'Basil', '2002-08-06', UUID_TO_BIN('20000000-0000-0000-0000-000000000006'), UUID_TO_BIN('30000000-0000-0000-0000-000000000002')); -- Basil (hamster) / Betty Davis
INSERT IGNORE INTO pets (id, name, birth_date, type_id, owner_id) VALUES (UUID_TO_BIN('40000000-0000-0000-0000-000000000003'), 'Rosy', '2001-04-17', UUID_TO_BIN('20000000-0000-0000-0000-000000000002'), UUID_TO_BIN('30000000-0000-0000-0000-000000000003')); -- Rosy (dog) / Eduardo Rodriquez
INSERT IGNORE INTO pets (id, name, birth_date, type_id, owner_id) VALUES (UUID_TO_BIN('40000000-0000-0000-0000-000000000004'), 'Jewel', '2000-03-07', UUID_TO_BIN('20000000-0000-0000-0000-000000000002'), UUID_TO_BIN('30000000-0000-0000-0000-000000000003')); -- Jewel (dog) / Eduardo Rodriquez
INSERT IGNORE INTO pets (id, name, birth_date, type_id, owner_id) VALUES (UUID_TO_BIN('40000000-0000-0000-0000-000000000005'), 'Iggy', '2000-11-30', UUID_TO_BIN('20000000-0000-0000-0000-000000000003'), UUID_TO_BIN('30000000-0000-0000-0000-000000000004')); -- Iggy (lizard) / Harold Davis
INSERT IGNORE INTO pets (id, name, birth_date, type_id, owner_id) VALUES (UUID_TO_BIN('40000000-0000-0000-0000-000000000006'), 'George', '2000-01-20', UUID_TO_BIN('20000000-0000-0000-0000-000000000004'), UUID_TO_BIN('30000000-0000-0000-0000-000000000005')); -- George (snake) / Peter McTavish
INSERT IGNORE INTO pets (id, name, birth_date, type_id, owner_id) VALUES (UUID_TO_BIN('40000000-0000-0000-0000-000000000007'), 'Samantha', '1995-09-04', UUID_TO_BIN('20000000-0000-0000-0000-000000000001'), UUID_TO_BIN('30000000-0000-0000-0000-000000000006')); -- Samantha (cat) / Jean Coleman
INSERT IGNORE INTO pets (id, name, birth_date, type_id, owner_id) VALUES (UUID_TO_BIN('40000000-0000-0000-0000-000000000008'), 'Max', '1995-09-04', UUID_TO_BIN('20000000-0000-0000-0000-000000000001'), UUID_TO_BIN('30000000-0000-0000-0000-000000000006')); -- Max (cat) / Jean Coleman
INSERT IGNORE INTO pets (id, name, birth_date, type_id, owner_id) VALUES (UUID_TO_BIN('40000000-0000-0000-0000-000000000009'), 'Lucky', '1999-08-06', UUID_TO_BIN('20000000-0000-0000-0000-000000000005'), UUID_TO_BIN('30000000-0000-0000-0000-000000000007')); -- Lucky (bird) / Jeff Black
INSERT IGNORE INTO pets (id, name, birth_date, type_id, owner_id) VALUES (UUID_TO_BIN('40000000-0000-0000-0000-000000000010'), 'Mulligan', '1997-02-24', UUID_TO_BIN('20000000-0000-0000-0000-000000000002'), UUID_TO_BIN('30000000-0000-0000-0000-000000000008')); -- Mulligan (dog) / Maria Escobito
INSERT IGNORE INTO pets (id, name, birth_date, type_id, owner_id) VALUES (UUID_TO_BIN('40000000-0000-0000-0000-000000000011'), 'Freddy', '2000-03-09', UUID_TO_BIN('20000000-0000-0000-0000-000000000005'), UUID_TO_BIN('30000000-0000-0000-0000-000000000009')); -- Freddy (bird) / David Schroeder
INSERT IGNORE INTO pets (id, name, birth_date, type_id, owner_id) VALUES (UUID_TO_BIN('40000000-0000-0000-0000-000000000012'), 'Lucky', '2000-06-24', UUID_TO_BIN('20000000-0000-0000-0000-000000000002'), UUID_TO_BIN('30000000-0000-0000-0000-000000000010')); -- Lucky (dog) / Carlos Estaban
INSERT IGNORE INTO pets (id, name, birth_date, type_id, owner_id) VALUES (UUID_TO_BIN('40000000-0000-0000-0000-000000000013'), 'Sly', '2002-06-08', UUID_TO_BIN('20000000-0000-0000-0000-000000000001'), UUID_TO_BIN('30000000-0000-0000-0000-000000000010')); -- Sly (cat) / Carlos Estaban

-- Visits (Using predefined UUIDs with UUID_TO_BIN and linking to pet UUIDs)
INSERT IGNORE INTO visits (id, pet_id, visit_date, description) VALUES (UUID_TO_BIN('50000000-0000-0000-0000-000000000001'), UUID_TO_BIN('40000000-0000-0000-0000-000000000007'), '2010-03-04', 'rabies shot'); -- Samantha
INSERT IGNORE INTO visits (id, pet_id, visit_date, description) VALUES (UUID_TO_BIN('50000000-0000-0000-0000-000000000002'), UUID_TO_BIN('40000000-0000-0000-0000-000000000008'), '2011-03-04', 'rabies shot'); -- Max
INSERT IGNORE INTO visits (id, pet_id, visit_date, description) VALUES (UUID_TO_BIN('50000000-0000-0000-0000-000000000003'), UUID_TO_BIN('40000000-0000-0000-0000-000000000008'), '2009-06-04', 'neutered'); -- Max
INSERT IGNORE INTO visits (id, pet_id, visit_date, description) VALUES (UUID_TO_BIN('50000000-0000-0000-0000-000000000004'), UUID_TO_BIN('40000000-0000-0000-0000-000000000007'), '2008-09-04', 'spayed'); -- Samantha
