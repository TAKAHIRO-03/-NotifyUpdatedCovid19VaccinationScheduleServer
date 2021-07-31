CREATE TABLE IF NOT EXISTS public.covid19_vaccination_schedule (
	id BIGSERIAL NOT NULL PRIMARY KEY,
	covid19_vaccination_venue_id BIGINT NOT NULL REFERENCES covid19_vaccination_schedule(id) ON DELETE CASCADE ON UPDATE CASCADE,
	availability_date DATE NOT NULL,
	availability_count INT NOT NULL,
	created_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	updated_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

ALTER TABLE public.covid19_vaccination_schedule OWNER TO nucvsuser;

CREATE TABLE IF NOT EXISTS public.covid19_vaccination_venue (
	id BIGSERIAL NOT NULL PRIMARY KEY,
	prefectures VARCHAR(255) NOT NULL,
	district VARCHAR(255) NOT NULL,
	venue VARCHAR(255) NOT NULL
);

ALTER TABLE public.covid19_vaccination_venue OWNER TO nucvsuser;
