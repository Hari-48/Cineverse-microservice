INSERT INTO GENRE (name) VALUES
('Action'),
('Comedy'),
('Drama'),
('Thriller'),
('Horror'),
('Sci-Fi'),
('Fantasy'),
('Romance'),
('Adventure'),
('Mystery'),
('Documentary'),
('Animation'),
('Crime'),
('Historical'),
('Western'),
('Musical'),
('War'),
('Biography'),
('Family'),
('Sport'),
('Superhero'),
('Psychological'),
('Disaster'),
('Noir'),
('Experimental');


select * from GENRE


-------------------------------------------------------------

INSERT INTO MOVIE (id, title, description, release_date, language, status, created_at, updated_at, genre_id) VALUES
(1, 'Interstellar', 'Explorers travel through a wormhole in space.', '2014-11-07', 'English', 'Released', NOW(), NOW(), 1),
(2, 'Inception', 'A thief steals corporate secrets through dreams.', '2010-07-16', 'English', 'Released', NOW(), NOW(), 1),
(3, 'The Notebook', 'A love story through time.', '2004-06-25', 'English', 'Released', NOW(), NOW(), 5),
(4, 'Avengers: Endgame', 'Final battle against Thanos.', '2019-04-26', 'English', 'Released', NOW(), NOW(), 3),
(5, 'Joker', 'Origin story of the Joker.', '2019-10-04', 'English', 'Released', NOW(), NOW(), 2),
(6, 'Coco', 'A boy explores the land of the dead.', '2017-10-20', 'English', 'Released', NOW(), NOW(), 10),
(7, 'Get Out', 'A disturbing family secret.', '2017-02-24', 'English', 'Released', NOW(), NOW(), 6),
(8, 'Frozen', 'A magical story of two sisters.', '2013-11-27', 'English', 'Released', NOW(), NOW(), 10),
(9, 'Titanic', 'A shipwreck love story.', '1997-12-19', 'English', 'Released', NOW(), NOW(), 5),
(10, 'The Dark Knight', 'Batman faces Joker.', '2008-07-18', 'English', 'Released', NOW(), NOW(), 3);


select * from MOVIE
-------------------------------------------------------------------------------

INSERT INTO CAST (id, name, role) VALUES
(1, 'Matthew McConaughey', 'Cooper'),
(2, 'Anne Hathaway', 'Brand'),
(3, 'Leonardo DiCaprio', 'Dom Cobb'),
(4, 'Kate Winslet', 'Rose'),
(5, 'Heath Ledger', 'Joker'),
(6, 'Christian Bale', 'Batman'),
(7, 'Bradley Cooper', 'Jackson Maine'),
(8, 'Lady Gaga', 'Ally'),
(9, 'Tom Holland', 'Peter Parker'),
(10, 'Scarlett Johansson', 'Natasha Romanoff');

select * from CAST

-------------------------------------------------------------

INSERT INTO POSTER  (id, url, IS_ACTIVE, movie_id) VALUES
(1, 'https://example.com/posters/interstellar.jpg', TRUE, 1),
(2, 'https://example.com/posters/inception.jpg', TRUE, 2),
(3, 'https://example.com/posters/notebook.jpg', TRUE, 3),
(4, 'https://example.com/posters/avengers.jpg', TRUE, 4),
(5, 'https://example.com/posters/joker.jpg', TRUE, 5),
(6, 'https://example.com/posters/coco.jpg', TRUE, 6),
(7, 'https://example.com/posters/getout.jpg', TRUE, 7),
(8, 'https://example.com/posters/frozen.jpg', TRUE, 8),
(9, 'https://example.com/posters/titanic.jpg', TRUE, 9),
(10, 'https://example.com/posters/darkknight.jpg', TRUE, 10);



-------------------------------------------------------------------

INSERT INTO SHOW_TIME  (
    THEATRE_NAME,
    DATE,
    TIME,
    SHOW_NAME,
    LANGUAGE,
    MOVIE_ID
) VALUES
('INOX Megaplex', '2025-06-01', '10:00:00', 'MORNING', 'Hindi', 1),
('PVR Icon',      '2025-06-01', '13:00:00', 'NOON',    'English', 1),
('Cinepolis',     '2025-06-01', '16:30:00', 'EVENING', 'Tamil', 2),
('PVR Cinemas',   '2025-06-01', '21:00:00', 'NIGHT',   'English', 3);
--------------------------------------------------------------------