INSERT INTO customerprofiles(first_name, last_name, street, house_number, postal_code, residence, telephone_number, email_address)
VALUES
('Neil', 'Young', 'Country Lane', '1120', '2230', 'Albuquerque', '0612121212', 'neil.young@harvestmoon.com'),
('Jon', 'Bonjovi', 'Lost Highway', '7800','5588', 'Sayreville', '0677119966', 'jon.bonjovi@badmedicine.com'),
('Barry', 'Hay', 'Zeeweg', '179', '7754', 'Willemstad', '0658972311', 'barry.hay@goingtotherun.com'),
('Eddie', 'Vedder', 'Long Road', '10', '2208','Seattle', '0657993254', 'eddie.vedder@stillalive.com');

INSERT INTO horses(name, type_of_feed, type_of_bedding, name_of_vet, residence_of_vet, telephone_of_vet ,customer_profile_id)
VALUES
('Crazy Horse', 'oats', 'straw', 'Dr John', 'New Orleans', '0612345678', 1),
('Steel Horse', 'hay', 'shavings', 'Dr Hook', 'Trenton', '0611223344', 2),
('Smiling Lady', 'hay', 'shavings', 'Dr Dre', 'Willemstad', '0655667788', 3),
('Jeremy', 'oats', 'straw', 'Dr Alban', 'Seattle', '0613572468', 4),
('Black', 'oats', 'straw', 'Dr Alban', 'Seattle', '0613572468', 4);

INSERT INTO stalls(name, size, type, horse_id)
VALUES
('The Good', '3 x 3.5', 'indoor stall', 1),
('The Bad', '3 x 3.5', 'indoor stall', 2),
('The Ugly', '3 x 3.5', 'indoor stall', 3),
('Buitenstal 1', '3 x 3', 'outdoor stall', 4),
('Buitenstal 2', '3 x 3', 'outdoor stall', 5),
('Buitenstal 3', '3 x 3', 'outdoor stall', null);


INSERT INTO subscriptions(price, type_of_care, type_of_stall)
VALUES
(490.95, 'basic care', 'small indoor box'),
(515.95, 'extra care', 'small indoor box'),
(505.95, 'basic care', 'large indoor box'),
(530.95, 'extra care', 'large indoor box'),
(454.95, 'basic care', 'small outdoor box'),
(479.95, 'extra care', 'large outdoor box');

INSERT INTO users(username, password)
VALUES
('dirtyHarry', 'GoAhead!');

INSERT INTO owners(first_name, last_name, email_address, user_id)
VALUES
('Clint', 'Eastwood', 'make@)myday.com', 1);

INSERT INTO customerprofiles_subscriptions(customer_profile_id, subscription_id)
VALUES
(2, 3);