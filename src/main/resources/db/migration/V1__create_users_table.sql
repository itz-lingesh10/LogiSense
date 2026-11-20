-- V1: Create users table and case-insensitive email index
-- BIGINT identity primary keys provide 64-bit auto-incrementing safety
CREATE TABLE users (
    id BIGINT GENERATED ALWAYS AS IDENTITY,
    email VARCHAR(255) NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    full_name VARCHAR(150) NOT NULL,
    phone VARCHAR(30) NULL,
    role VARCHAR(20) NOT NULL,
    enabled BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT now(),

    CONSTRAINT pk_users PRIMARY KEY (id),
    CONSTRAINT chk_users_role CHECK (role IN ('ADMIN', 'DISPATCHER', 'DRIVER', 'CUSTOMER'))
);

-- Case-insensitive unique index prevents duplicate email registrations regardless of case
CREATE UNIQUE INDEX uq_users_lower_email ON users (lower(email));
