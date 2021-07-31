CREATE TABLE IF NOT EXISTS covid19_vaccination_schedule (
	id bigint NOT NULL PRIMARY KEY,
	covid19_vaccination_venue_id VARCHAR(255) NOT NULL,
	availability_date DATE NOT NULL,
	availability_count INT NOT NULL,
	created_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	updated_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS covid19_vaccination_venue (
	id bigint NOT NULL PRIMARY KEY,
	prefectures VARCHAR(255) NOT NULL,
	district VARCHAR(255) NOT NULL,
	venue VARCHAR(255) NOT NULL
);