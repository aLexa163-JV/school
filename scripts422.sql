CREATE TABLE public.cars (
    cars_id int NULL,
    cars_brand varchar NULL,
    cars_model varchar NULL,
    cars_amount numeric NULL
);
ALTER TABLE cars
    ALTER COLUMN cars_brand SET NOT NULL,
    ADD CONSTRAINT model_unique UNIQUE(cars_model),
    ADD CONSTRAINT amount_constraint CHECK (cars_amount>0)
    ADD PRIMARY KEY (cars_id);

CREATE TABLE public.person (
    person_id int NULL,
    person_name varchar NULL,
    person_age int NULL,
    person_driver_licenses BOOLEAN NULL
);
ALTER TABLE person
    ALTER COLUMN person_name SET NOT NULL,
    ADD CONSTRAINT age_constraint CHECK (person_age>0),
    ALTER COLUMN driver_licenses SET NOT NULL;

ALTER TABLE cars ADD FOREIGN KEY(person_id) REFERENCES person(person_id);