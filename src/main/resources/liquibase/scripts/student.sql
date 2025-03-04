--liquibase formatted sql

--changeset ayakovlev:1
CREATE INDEX student_name_index ON student (name);