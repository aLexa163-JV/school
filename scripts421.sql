--ограничения таблицы student--
ALTER TABLE student
    ADD CONSTRAINT age_constraint CHECK (age > 16),--Возраст студента не может быть меньше 16 лет--
    ALTER COLUMN name TEXT NOT NULL,               --Имена студентов должны быть уникальными и не равны нулю--
    ADD CONSTRAINT default_age SET DEFAULT 20;     --При создании студента без возраста ему автоматически должно присваиваться 20 лет--
--ограничения таблицы faculty--
ALTER TABLE faculty
    ADD CONSTRAINT name_color UNIQUE (name, color);--Пара “значение названия” - “цвет факультета” должна быть уникальной--