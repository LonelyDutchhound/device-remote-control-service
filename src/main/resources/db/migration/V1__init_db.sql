CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TYPE power_status_enum AS ENUM (
    'ON', 'OFF'
    );

CREATE TYPE program_status_enum AS ENUM (
    'STARTING', 'RUNNING', 'FINISHED', 'ERROR'
    );

CREATE TABLE IF NOT EXISTS washing_program
(
    id          uuid PRIMARY KEY DEFAULT uuid_generate_v1(),
    name        VARCHAR(255) NOT NULL UNIQUE,
    temperature SMALLINT CHECK (temperature >= 0),
    duration    BIGINT       NOT NULL CHECK (duration > 0),
    spin_speed  SMALLINT
);


CREATE TABLE IF NOT EXISTS washing_machine
(
    id    uuid PRIMARY KEY DEFAULT uuid_generate_v1(),
    model VARCHAR(255) UNIQUE
);

CREATE TABLE IF NOT EXISTS washing_machine_program
(
    machine_id uuid,
    program_id uuid
);

CREATE TABLE IF NOT EXISTS device
(
    id           uuid PRIMARY KEY DEFAULT uuid_generate_v1(),
    device_id    uuid REFERENCES washing_machine (id) NOT NULL,
    power_status power_status_enum                    NOT NULL,
    created_at   timestamptz      DEFAULT CURRENT_TIMESTAMP,
    deleted_at   timestamptz
);

CREATE TABLE IF NOT EXISTS device_activity
(
    id             uuid PRIMARY KEY DEFAULT uuid_generate_v1(),
    device_id      uuid REFERENCES device (id) NOT NULL,
    program_id     uuid REFERENCES washing_program (id),
    program_status program_status_enum
);
