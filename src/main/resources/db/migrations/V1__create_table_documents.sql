CREATE TABLE documents(
    id UUID PRIMARY KEY,
    mrz VARCHAR(255),
    name VARCHAR(255) NOT NULL,
    number VARCHAR(50),
    type VARCHAR(50),
    nationality VARCHAR(100),
    sex VARCHAR(20),

    birth_date DATE,
    expiry_date DATE
);