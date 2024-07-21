INSERT INTO users (username, password, email, enabled)
VALUES
('dirtyharry', '$2a$12$WBWNCazStJW.XImY1iMmreBg2MinjUD7KJADELS9Pb6T4F9ony0WS', 'make@myday.com', TRUE),
('foreveryoung', '$2a$12$eD6u67c59rwGghmlYmykWeDjyScFK4c.L1Q6a.MgGS90ooTR8jSx6','neil.young@harvestmoon.com', TRUE),
('jonbonjovi', '$2a$12$2KZ6dAR213r4h6TUig4va.yMOxw/ZTbTunqarDmpkuH501CnzxQK2', 'jon.bonjovi@badmedicine.com', TRUE),
('tinaturner', '$2a$12$0ErQCe4NlkEg2smn3dCOM.WPWzduZ7ILSOtiNk6sy5n7dGVtsKvoG', 'tina.turner@rolling.com', TRUE),
('mickjagger', '$2a$12$1NZyhVbRLyrC8aaphOoCZOr4E2vodIBZoXkKZK16AyvGFqEVPTXXa', 'mick@jagger.com', TRUE),
('barryhay', '$2a$12$9uhKNB1r4PSuSzX.JQqQ/eNswBskYxx9LnsLHEsC0bfPukkuWX/Qe', 'barry@hay.com', TRUE),
('eddievedder', '$2a$12$vKbsbIwPUdvpRc4INyJbteeTyB2vEhHAs6rQyMdMqQ57OIfLzJxPa', 'eddie@vedder.com', TRUE),
('rockybalboa', '$2a$12$tdQw6ktgi7wt0jccZt1x9u6fJEpt0xSzPw/JHyAJIXIqDkpCcAwSG', 'rocky@balboa.com', TRUE),
('axlrose1', '$2a$12$3nJaLTa8XFZwckLqeK5pHuJGf7bWRsYGz4FGPXsqG147UOLdciwDC', 'axl@rose.com', TRUE);

INSERT INTO authorities (username, authority)
VALUES
('dirtyharry', 'ROLE_ADMIN'),
('foreveryoung', 'ROLE_USER'),
('dirtyharry', 'ROLE_USER'),
('jonbonjovi', 'ROLE_USER'),
('tinaturner', 'ROLE_USER'),
('mickjagger', 'ROLE_USER'),
('barryhay', 'ROLE_USER'),
('eddievedder', 'ROLE_USER'),
('rockybalboa', 'ROLE_USER'),
('axlrose1', 'ROLE_USER');

INSERT INTO customerprofiles(first_name, last_name, street, house_number, postal_code, residence, telephone_number,
email_address, bank_account_number, user_username)
VALUES
('Neil', 'Young', 'Country Lane', '1120', '2230', 'Albuquerque', '0612121212', 'neil.young@harvestmoon.com', '76CASH0123456789', 'foreveryoung'),
('Jon', 'Bonjovi', 'Lost Highway', '7800','5588', 'Sayreville', '0677119966', 'jon.bonjovi@badmedicine.com', '12ROCK0135792468', 'jonbonjovi'),
('Barry', 'Hay', 'Radarweg', '179', '7754', 'Willemstad', '0658972311', 'barry.hay@goingtotherun.com', '83EARS0987654321', 'barryhay'),
('Eddie', 'Vedder', 'Long Road', '10', '2208','Seattle', '0657993254', 'eddie.vedder@stillalive.com', '10EVEN05544332211', 'eddievedder'),
('Mick', 'Jagger', 'Sympathy Road', '12', '3333', 'Richmond', '0677336611', 'mick.jagger@movelikeme.com', '12HORS0639008652', 'mickjagger'),
('Rocky', 'Balboa', 'Stair Way', '118A', '1876', 'Philadelphia', '0629983321', 'rocky.balboa@adrian.com', '88BOKS0777444999', 'rockybalboa'),
('Axl', 'Rose', 'Road to Nowhere', '1', '8854', 'Paradise City', '0691827364', 'axl.rose@youcouldbemine.com', '43ROSE0843223859', 'axlrose1'),
('Tina', 'Turner', 'Hard Road', '12', '1234', 'Nutbush', '0612345678', 'tina@turner.com', '43ROLL1843223823', 'tinaturner');

INSERT INTO file_uploads(file_name, content_type, url)
VALUES
('steelhorse.jpg', 'image/jpeg', 'http://localhost:8080/download/steelhorse.jpg');

INSERT INTO horses(name, horse_number, type_of_feed, type_of_bedding, name_of_vet, residence_of_vet, telephone_of_vet,
preferred_subscription, customer_profile_id, passport_file_name)
VALUES
('Crazy Horse', 'NL012345678912', 'haver', 'stro', 'Dr John', 'New Orleans', '0612345678', 'activated', 1, 'steelhorse.jpg'),
('Steel Horse', 'NL011212121212', 'hooi', 'houtvezel', 'Dr Hook', 'Trenton', '0611223344', 'activated', 2, 'steelhorse.jpg'),
('Smiling Lady', 'NL014759830927', 'hooi', 'houtvezel', 'Dr Dre', 'Willemstad', '0655667788', 'Jolly Jumper abonnement', 3, 'steelhorse.jpg'),
('Jeremy', 'NL099887766543','haver',  'stro', 'Dr Alban', 'Seattle', '0613572468', 'activated', 4, 'steelhorse.jpg'),
('Black', 'NL098765432123','haver',  'stro', 'Dr Alban', 'Seattle', '0613572468', 'Joe Dalton abonnement', 4, 'steelhorse.jpg'),
('Jumpin Jack', 'NL011887766999', 'hooi', 'stro', 'Dr Robert', 'London', '0666776677', 'activated', 5, 'steelhorse.jpg'),
('Angie', 'NL033667766922', 'haver', 'houtvezel', 'Dr Robert', 'London', '0666776677', 'activated', 5, 'steelhorse.jpg'),
('Wild Horse', 'NL011887766999', 'hooi', 'stro', 'Dr Robert', 'London', '0666776677', 'activated', 5, 'steelhorse.jpg'),
('Savage Horse', 'NL024247767456', 'hooi', 'stro', 'Dr Robert', 'London', '0666776677', 'Averell Dalton abonnement', 5, 'steelhorse.jpg'),
('Italian Stallion', 'NL076547766123', 'haver', 'houtvezel', 'Dr Strange', 'Philadelphia', '0612883479', 'activated', 6, 'steelhorse.jpg'),
('Sweet Horse O Mine', 'NL089247764781', 'haver', 'stro', 'Dr John', 'New Orleans', '0612345678', 'activated', 7, 'steelhorse.jpg'),
('Proud Merrie', 'NL082247864733', 'hooi', 'houtvezel', 'Dr Phil', 'Memphis', '0612345678', 'activated', 8, 'steelhorse.jpg');

INSERT INTO stalls(name, size, type, is_occupied, horse_id)
VALUES
('The Good', '3 x 3.5', 'kleine binnenstal', true, 1),
('The Bad', '3 x 3.5', 'kleine binnenstal', true, 2),
('The Ugly', '3 x 3.5', 'kleine binnenstal', false, null),
('The Bold', '3 x 3.5', 'kleine binnenstal', false, null),
('The Beautiful', '3 x 3.5', 'kleine binnenstal', false, null),
('The Mule', '3 x 4', 'grote binnenstal', false, null),
('The Dead Pool', '3 x 4', 'grote binnenstal', true, 4),
('Heartbreak Ridge', '3 x 4', 'grote binnenstal', false, null),
('Los Pollos', '3 x 4', 'grote binnenstal', false, null),
('Young Guns I', '3 x 3', 'kleine buitenstal', true, 6),
('Young Guns II', '3 x 3', 'kleine buitenstal', true, 7),
('Young Guns III', '3 x 3', 'kleine buitenstal', false, null),
('The Quick', '3 x 3', 'kleine buitenstal', false, null),
('The Dead', '3 x 3', 'kleine buitenstal', false, null),
('Gran Torino', '3 x 4', 'grote buitenstal', true, 8),
('El Camino', '3 x 4', 'grote buitenstal', true, 11),
('Road House', '3 x 4', 'grote buitenstal', true, 10);

INSERT INTO subscriptions(name, price, type_of_care, type_of_stall)
VALUES
('Lucky Luke abonnement', 490.95, 'halfpension', 'kleine binnenstal'),
('Jolly Jumper abonnement', 515.95, 'volpension', 'kleine binnenstal'),
('Joe Dalton abonnement', 505.95, 'halfpension', 'grote binnenstal'),
('Jack Dalton abonnement' , 530.95, 'volpension', 'grote binnenstal'),
('William Dalton abonnement', 454.95, 'halfpension', 'kleine buitenstal'),
('Averell Dalton abonnement', 479.95, 'volpension', 'kleine buitenstal'),
('Ma Dalton abonnement', 489.95, 'halfpension', 'grote buitenstal'),
('Rataplan abonnement', 499.95, 'volpension', 'grote buitenstal');

INSERT INTO enrollments(start_date, expire_date, duration, is_ongoing, cancellation_requested, horse_number,
subscription_price, customer_number, subscription_id, customer_profile_id, horse_id)
VALUES
('12-05-2023', '12-05-2024', 1, true, false, 'NL012345678912', 515.95, 1001, 2, 1, 1),
('01-04-2023', '01-04-2024', 2, true, true, 'NL011212121212', 490.95, 1002, 1, 2, 2),
('01-09-2022', '01-09-2023', 9, true, false, 'NL099887766543', 505.95, 1004, 3, 4, 4),
('12-02-2023', '12-02-2024', 5, true, true, 'NL011887766999', 454.95, 1005, 5, 5, 6),
('12-02-2023', '12-02-2024', 5, true, false, 'NL033667766922', 479.95, 1005, 6, 5, 7),
('01-07-2023', '01-07-2024', 1, true, false, 'NL011887766999', 499.95, 1005, 8, 5, 8),
('01-04-2023', '01-04-2024', 12, false, false, 'NL082247864733', 479.95, 1008, 6, 8, 12),
('01-09-2023', '01-09-2024', 0, true, false, 'NL024247767456', 489.95, 1005, 7, 6, 10),
('01-01-2023', '01-01-2024', 6, true, true, 'NL098765432123', 499.95, 1004, 8, 7, 11);