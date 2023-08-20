INSERT INTO customerprofiles(first_name, last_name, street, house_number, postal_code, residence, telephone_number, email_address, bank_account_number)
VALUES
('Neil', 'Young', 'Country Lane', '1120', '2230', 'Albuquerque', '0612121212', 'neil.young@harvestmoon.com', '76CASH0123456789'),
('Jon', 'Bonjovi', 'Lost Highway', '7800','5588', 'Sayreville', '0677119966', 'jon.bonjovi@badmedicine.com', '12ROCK0135792468'),
('Barry', 'Hay', 'Radarweg', '179', '7754', 'Willemstad', '0658972311', 'barry.hay@goingtotherun.com', '83EARS0987654321'),
('Eddie', 'Vedder', 'Long Road', '10', '2208','Seattle', '0657993254', 'eddie.vedder@stillalive.com', '10EVEN05544332211');

INSERT INTO horses(name, type_of_feed, type_of_bedding, name_of_vet, residence_of_vet, telephone_of_vet, customer_profile_id)
VALUES
('Crazy Horse', 'oats', 'straw', 'Dr John', 'New Orleans', '0612345678', 1),
('Steel Horse', 'hay', 'shavings', 'Dr Hook', 'Trenton', '0611223344', 2),
('Smiling Lady', 'hay', 'shavings', 'Dr Dre', 'Willemstad', '0655667788', 3),
('Jeremy', 'oats', 'straw', 'Dr Alban', 'Seattle', '0613572468', 4),
('Black', 'oats', 'straw', 'Dr Alban', 'Seattle', '0613572468', 4);

INSERT INTO stalls(name, size, type, is_occupied, horse_id)
VALUES
('The Good', '3 x 3.5', 'kleine binnenstal', true, 1),
('The Bad', '3 x 3.5', 'kleine binnenstal', true, 2),
('The Ugly', '3 x 3.5', 'kleine binnenstal',true, 3),
('The Mule', '3 x 4', 'grote binnenstal', false, null),
('The Dead Pool', '3 x 4', 'grote binnenstal', false, null),
('Young Guns I', '3 x 3', 'kleine buitenstal', true, 4),
('Young Guns II', '3 x 3', 'kleine buitenstal', false, null),
('Young Guns III', '3 x 3', 'kleine buitenstal', false, null),
('Gran Torino', '3 x 4', 'grote buitenstal', false, null),
('El Camino', '3 x 4', 'grote buitenstal', false, null),
('Road House', '3 x 4', 'grote buitenstal', false, null);


INSERT INTO subscriptions(name, price, type_of_care, type_of_stall)
VALUES
('Lucky Luke abonnement', 490.95, 'halfpension', 'kleine binnenstal'),
('Jolly Jumper abonnement', 515.95, 'volpension', 'kleine binnenstal'),
('Joe Dalton abonnement', 505.95, 'halfpension', 'grote binnenstal'),
('Jack Dalton abonnement' , 530.95, 'volpension', 'grote binnenstal'),
('William Dalton abonnement', 454.95, 'halfpension', 'kleine buitenstal'),
('Averell Dalton abonnement', 479.95, 'volpension', 'kleine buitenstal'),
('Ma Dalton abonnement', 499.95, 'halfpension', 'grote buitenstal'),
('Rataplan abonnement', 489.95, 'volpension', 'grote buitenstal');

INSERT INTO users(username, password)
VALUES
('dirtyHarry', 'GoAhead!');

INSERT INTO owners(first_name, last_name, email_address, user_id)
VALUES
('Clint', 'Eastwood', 'make@)myday.com', 1);

INSERT INTO enrollments(start_date, expire_date, duration, is_ongoing, cancellation_requested, subscription_id,
customer_profile_id, horse_id)
VALUES
('12-05-2023', '12-05-2024', 1, true, false, 1, 1, 1),
('23-04-2023', '23-04-2024', 2, true, false, 2, 2, null),
('18-09-2022', '18-09-2023', 9, true, false, 2, 1, null),
('01-01-2023', '01-01-2024', 6, true, true, 1, 2, null),
('15-02-2023', '15-02-2024', 5, false, false, 2, 2, null),
('01-07-2023', '01-07-2024', 0, false, false, 3, 1, null);