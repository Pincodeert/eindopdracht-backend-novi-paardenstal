INSERT INTO customerprofiles(first_name, last_name, street, house_number, postal_code, residence, telephone_number, email_address, bank_account_number)
VALUES
('Neil', 'Young', 'Country Lane', '1120', '2230', 'Albuquerque', '0612121212', 'neil.young@harvestmoon.com', '76CASH0123456789'),
('Jon', 'Bonjovi', 'Lost Highway', '7800','5588', 'Sayreville', '0677119966', 'jon.bonjovi@badmedicine.com', '12ROCK0135792468'),
('Barry', 'Hay', 'Radarweg', '179', '7754', 'Willemstad', '0658972311', 'barry.hay@goingtotherun.com', '83EARS0987654321'),
('Eddie', 'Vedder', 'Long Road', '10', '2208','Seattle', '0657993254', 'eddie.vedder@stillalive.com', '10EVEN05544332211'),
('Mick', 'Jagger', 'Sympathy Road', '12', '3333', 'Richmond', '0677336611', 'mick.jagger@movelikeme.com', '12HORS0639008652'),
('Rocky', 'Balboa', 'Stair Way', '118A', '1876', 'Philadelphia', '0629983321', 'rocky.balboa@adrian.com', '88BOKS0777444999'),
('Axl', 'Rose', 'Road to Nowhere', '1', '8854', 'Paradise City', '0691827364', 'axl.rose@youcouldbemine.com', '43ROSE0843223859');

INSERT INTO horses(name, horse_number, type_of_feed, type_of_bedding, name_of_vet, residence_of_vet, telephone_of_vet, preferred_subscription, customer_profile_id)
VALUES
('Crazy Horse', 'NL012345678912', 'oats', 'straw', 'Dr John', 'New Orleans', '0612345678', 'Lucky Luke abonnement', 1),
('Steel Horse', 'NL011212121212', 'hay', 'shavings', 'Dr Hook', 'Trenton', '0611223344', 'Jolly Jumper abonnement', 2),
('Smiling Lady', 'NL014759830927', 'hay', 'shavings', 'Dr Dre', 'Willemstad', '0655667788', 'Jolly Jumper abonnement', 3),
('Jeremy', 'NL099887766543','oats',  'straw', 'Dr Alban', 'Seattle', '0613572468', 'Joe Dalton abonnement', 4),
('Black', 'NL098765432123','oats',  'straw', 'Dr Alban', 'Seattle', '0613572468', 'Joe Dalton abonnement', 4),
('Jumpin Jack', 'NL011887766999', 'hay', 'straw', 'Dr Robert', 'London', '0666776677', 'William Dalton abonnement', 5),
('Angie', 'NL033667766922', 'oats', 'shavings', 'Dr Robert', 'London', '0666776677', 'William Dalton abonnement', 5),
('Wild Horse', 'NL011887766999', 'hay', 'straw', 'Dr Robert', 'London', '0666776677', 'Averell Dalton abonnement', 5),
('Savage Horse', 'NL024247767456', 'hay', 'straw', 'Dr Robert', 'London', '0666776677', 'Averell Dalton abonnement', 5),
('Italian Stallion', 'NL076547766123', 'oats', 'shavings', 'Dr Strange', 'Philadelphia', '0612883479', 'Ma Dalton abonnement', 6),
('Sweet Horse O Mine', 'NL089247764781', 'oats', 'straw', 'Dr John', 'New Orleans', '0612345678', 'Averell Dalton abonnement', 7);

INSERT INTO stalls(name, size, type, is_occupied, horse_id)
VALUES
('The Good', '3 x 3.5', 'kleine binnenstal', true, 1),
('The Bad', '3 x 3.5', 'kleine binnenstal', true, 2),
('The Ugly', '3 x 3.5', 'kleine binnenstal', false, null),
('The Bold', '3 x 3.5', 'kleine binnenstal', false, null),
('The Beautiful', '3 x 3.5', 'kleine binnenstal', false, null),
('The Mule', '3 x 4', 'grote binnenstal', true, 4),
('The Dead Pool', '3 x 4', 'grote binnenstal', true, 5),
('Heartbreak Ridge', '3 x 4', 'grote binnenstal', false, null),
('Los Pollos', '3 x 4', 'grote binnenstal', false, null),
('Young Guns I', '3 x 3', 'kleine buitenstal', true, 6),
('Young Guns II', '3 x 3', 'kleine buitenstal', true, 7),
('Young Guns III', '3 x 3', 'kleine buitenstal', true, 11),
('The Quick', '3 x 3', 'kleine buitenstal', false, null),
('The Dead', '3 x 3', 'kleine buitenstal', false, null),
('Gran Torino', '3 x 4', 'grote buitenstal', true, 8),
('El Camino', '3 x 4', 'grote buitenstal', true, 9),
('Road House', '3 x 4', 'grote buitenstal', true, 10);


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

-- password = "password" (dit comment is een security lek, zet dit nooit in je code.
-- Als je hier je plaintext password niet meer weet, moet je een nieuw password encrypted)
INSERT INTO users (username, password, email, enabled)
VALUES
('foreveryoung', '$2a$12$eD6u67c59rwGghmlYmykWeDjyScFK4c.L1Q6a.MgGS90ooTR8jSx6','neil.young@harvestmoon.com', TRUE),
('dirtyharry', '$2a$12$WBWNCazStJW.XImY1iMmreBg2MinjUD7KJADELS9Pb6T4F9ony0WS', 'make@myday.com', TRUE);

INSERT INTO authorities (username, authority)
VALUES
('foreveryoung', 'ROLE_USER'),
('dirtyharry', 'ROLE_USER'),
('dirtyharry', 'ROLE_ADMIN');


INSERT INTO enrollments(start_date, expire_date, duration, is_ongoing, cancellation_requested, horse_number, subscription_id,
customer_profile_id, horse_id)
VALUES
('12-05-2023', '12-05-2024', 1, true, false, 'NL012345678912', 1, 1, 1),
('23-04-2023', '23-04-2024', 2, false, false, 'NL011212121212', 2, 2, 2),
('18-09-2022', '18-09-2023', 9, true, false, 'NL099887766543', 3, 4, 4),
('01-01-2023', '01-01-2024', 6, true, true, 'NL098765432123', 3, 4, 5),
('15-02-2023', '15-02-2024', 5, true, true, 'NL011887766999', 5, 5, 6),
('15-02-2023', '15-02-2024', 5, true, false, 'NL033667766922', 5, 5, 7),
('01-07-2023', '01-07-2024', 1, true, false, 'NL011887766999', 8, 5, 8),
('01-09-2023', '01-09-2024', 0, false, false, 'NL024247767456', 8, 5, 9);