INSERT INTO stalls(name, size, type)
VALUES
('The Good', '3 x 3.5', 'indoor stall'),
('The Bad', '3 x 3.5', 'indoor stall'),
('The Ugly', '3 x 3.5', 'indoor stall');

INSERT INTO horses(name, type_of_feed, type_of_bedding, name_of_vet, residence_of_vet, telephone_of_vet)
VALUES
('Crazy Horse', 'oats', 'straw', 'Dr John', 'New Orleans', '0612345678'),
('Steel Horse', 'hay', 'shavings', 'Dr Hook', 'Trenton', '0611223344'),
('Smiling Lady', 'hay', 'shavings', 'Dr Dre', 'Willemstad', '0655667788'),
('Jeremy', 'oats', 'straw', 'Dr Alban', 'Seattle', '0613572468'),
('Black', 'oats', 'straw', 'Dr Alban', 'Seattle', '0613572468');

INSERT INTO subscriptions(price, type_of_care, type_of_stall)
VALUES
(490.95, 'basic care', 'small inside box'),
(515.95, 'extra care', 'small inside box'),
(505.95, 'basic care', 'large inside box'),
(530.95, 'extra care', 'large inside box'),
(454.95, 'basic care', 'small outside box'),
(479.95, 'extra care', 'large outside box');