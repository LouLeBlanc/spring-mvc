DROP TABLE IF EXISTS book;

CREATE TABLE book (
	id INT NOT NULL AUTO_INCREMENT,
	category_name VARCHAR(100) NOT NULL,
	isbn VARCHAR(13) NOT NULL,
	title VARCHAR(200) NOT NULL,
	publisher VARCHAR(100),
	price DECIMAL(4,2) NOT NULL,
	version INT NOT NULL DEFAULT 0,
	UNIQUE uq_isbn_1 ( isbn ),
	PRIMARY KEY ( id )
);