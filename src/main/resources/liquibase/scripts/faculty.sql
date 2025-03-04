--liquibase formatted sql

--changeset ayakovlev:1
CREATE INDEX faculty_index ON faculty (name, color);