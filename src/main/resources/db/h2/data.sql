-- Generate UUIDs for reuse
SET @vet1 = UUID();
SET @vet2 = UUID();
SET @vet3 = UUID();
SET @vet4 = UUID();
SET @vet5 = UUID();
SET @vet6 = UUID();

SET @specialty1 = UUID();
SET @specialty2 = UUID();
SET @specialty3 = UUID();

SET @type1 = UUID();
SET @type2 = UUID();
SET @type3 = UUID();
SET @type4 = UUID();
SET @type5 = UUID();
SET @type6 = UUID();

SET @owner1 = UUID();
SET @owner2 = UUID();
SET @owner3 = UUID();
SET @owner4 = UUID();
SET @owner5 = UUID();
SET @owner6 = UUID();
SET @owner7 = UUID();
SET @owner8 = UUID();
SET @owner9 = UUID();
SET @owner10 = UUID();

SET @pet1 = UUID();
SET @pet2 = UUID();
SET @pet3 = UUID();
SET @pet4 = UUID();
SET @pet5 = UUID();
SET @pet6 = UUID();
SET @pet7 = UUID();
SET @pet8 = UUID();
SET @pet9 = UUID();
SET @pet10 = UUID();
SET @pet11 = UUID();
SET @pet12 = UUID();
SET @pet13 = UUID();

-- Insert data using generated UUIDs
INSERT INTO vets VALUES (@vet1, 'James', 'Carter');
INSERT INTO vets VALUES (@vet2, 'Helen', 'Leary');
INSERT INTO vets VALUES (@vet3, 'Linda', 'Douglas');
INSERT INTO vets VALUES (@vet4, 'Rafael', 'Ortega');
INSERT INTO vets VALUES (@vet5, 'Henry', 'Stevens');
INSERT INTO vets VALUES (@vet6, 'Sharon', 'Jenkins');

INSERT INTO specialties VALUES (@specialty1, 'radiology');
INSERT INTO specialties VALUES (@specialty2, 'surgery');
INSERT INTO specialties VALUES (@specialty3, 'dentistry');

INSERT INTO vet_specialties VALUES (@vet2, @specialty1);
INSERT INTO vet_specialties VALUES (@vet3, @specialty2);
INSERT INTO vet_specialties VALUES (@vet3, @specialty3);
INSERT INTO vet_specialties VALUES (@vet4, @specialty2);
INSERT INTO vet_specialties VALUES (@vet5, @specialty1);

INSERT INTO types VALUES (@type1, 'cat');
INSERT INTO types VALUES (@type2, 'dog');
INSERT INTO types VALUES (@type3, 'lizard');
INSERT INTO types VALUES (@type4, 'snake');
INSERT INTO types VALUES (@type5, 'bird');
INSERT INTO types VALUES (@type6, 'hamster');

INSERT INTO owners VALUES (@owner1, 'George', 'Franklin', '110 W. Liberty St.', 'Madison', '6085551023');
INSERT INTO owners VALUES (@owner2, 'Betty', 'Davis', '638 Cardinal Ave.', 'Sun Prairie', '6085551749');
INSERT INTO owners VALUES (@owner3, 'Eduardo', 'Rodriquez', '2693 Commerce St.', 'McFarland', '6085558763');
INSERT INTO owners VALUES (@owner4, 'Harold', 'Davis', '563 Friendly St.', 'Windsor', '6085553198');
INSERT INTO owners VALUES (@owner5, 'Peter', 'McTavish', '2387 S. Fair Way', 'Madison', '6085552765');
INSERT INTO owners VALUES (@owner6, 'Jean', 'Coleman', '105 N. Lake St.', 'Monona', '6085552654');
INSERT INTO owners VALUES (@owner7, 'Jeff', 'Black', '1450 Oak Blvd.', 'Monona', '6085555387');
INSERT INTO owners VALUES (@owner8, 'Maria', 'Escobito', '345 Maple St.', 'Madison', '6085557683');
INSERT INTO owners VALUES (@owner9, 'David', 'Schroeder', '2749 Blackhawk Trail', 'Madison', '6085559435');
INSERT INTO owners VALUES (@owner10, 'Carlos', 'Estaban', '2335 Independence La.', 'Waunakee', '6085555487');

INSERT INTO pets VALUES (@pet1, 'Leo', '2010-09-07', @type1, @owner1);
INSERT INTO pets VALUES (@pet2, 'Basil', '2012-08-06', @type6, @owner2);
INSERT INTO pets VALUES (@pet3, 'Rosy', '2011-04-17', @type2, @owner3);
INSERT INTO pets VALUES (@pet4, 'Jewel', '2010-03-07', @type2, @owner3);
INSERT INTO pets VALUES (@pet5, 'Iggy', '2010-11-30', @type3, @owner4);
INSERT INTO pets VALUES (@pet6, 'George', '2010-01-20', @type4, @owner5);
INSERT INTO pets VALUES (@pet7, 'Samantha', '2012-09-04', @type1, @owner6);
INSERT INTO pets VALUES (@pet8, 'Max', '2012-09-04', @type1, @owner6);
INSERT INTO pets VALUES (@pet9, 'Lucky', '2011-08-06', @type5, @owner7);
INSERT INTO pets VALUES (@pet10, 'Mulligan', '2007-02-24', @type2, @owner8);
INSERT INTO pets VALUES (@pet11, 'Freddy', '2010-03-09', @type5, @owner9);
INSERT INTO pets VALUES (@pet12, 'Lucky', '2010-06-24', @type2, @owner10);
INSERT INTO pets VALUES (@pet13, 'Sly', '2012-06-08', @type1, @owner10);

INSERT INTO visits VALUES (default, @pet7, @vet1, '2013-01-01', 'rabies shot');
INSERT INTO visits VALUES (default, @pet8, @vet1, '2013-01-02', 'rabies shot');
INSERT INTO visits VALUES (default, @pet8, @vet3, '2013-01-03', 'neutered');
INSERT INTO visits VALUES (default, @pet7, @vet2, '2013-01-04', 'spayed');
