

use validation_source;

CREATE TABLE IF NOT EXISTS employees_source (
    id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL
);

INSERT INTO employees_source(first_name, last_name, email)
VALUES
	('Dibakar', 'Dutta', 'dibakard'),
	('Manikandan', 'Elumalai', 'manikandane');
