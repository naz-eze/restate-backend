CREATE TABLE IF NOT EXISTS agents (
    id INT(20) NOT NULL AUTO_INCREMENT,
    created_time TIMESTAMP DEFAULT now(),
    updated_time TIMESTAMP DEFAULT now() ON UPDATE now(),
    email VARCHAR(255) NOT NULL UNIQUE,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255),
    phone_number VARCHAR(255),
    device_id VARCHAR(255),
    is_email_verified BIT DEFAULT 0,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS leads (
    id INT(20) NOT NULL AUTO_INCREMENT,
    created_time TIMESTAMP DEFAULT now(),
    updated_time TIMESTAMP DEFAULT now() ON UPDATE now(),
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    phone_number VARCHAR(255) NOT NULL,
    address VARCHAR(255) NOT NULL,
    lead_status VARCHAR(255) NOT NULL,
    lead_type VARCHAR(255) NOT NULL,
    motivation VARCHAR(255),
    property_type VARCHAR(255),
    years_in_property VARCHAR(255),
    agent_id INT(20),
    PRIMARY KEY (id),
    FOREIGN KEY (agent_id) REFERENCES agents (id)
);

CREATE TABLE IF NOT EXISTS comments (
    id INT(20) NOT NULL AUTO_INCREMENT,
    created_time TIMESTAMP DEFAULT now(),
    updated_time TIMESTAMP DEFAULT now() ON UPDATE now(),
    comment_content VARCHAR(255) NOT NULL,
    lead_id INT(20),
    PRIMARY KEY (id),
    FOREIGN KEY (lead_id) REFERENCES leads (id)
);