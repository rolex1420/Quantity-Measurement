-- Quantity Measurement Database Schema
-- Compatible with H2, MySQL, and PostgreSQL

-- Main table for storing quantity measurement entities
CREATE TABLE IF NOT EXISTS quantity_measurement_entity (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    operand1_value DECIMAL(20, 10) NOT NULL,
    operand1_unit VARCHAR(50) NOT NULL,
    operand1_measurement_type VARCHAR(50) NOT NULL,
    operand2_value DECIMAL(20, 10),
    operand2_unit VARCHAR(50),
    operand2_measurement_type VARCHAR(50),
    operation VARCHAR(50) NOT NULL,
    result_value DECIMAL(20, 10),
    result_unit VARCHAR(50),
    result_measurement_type VARCHAR(50),
    has_error BOOLEAN NOT NULL DEFAULT FALSE,
    error_message TEXT,
    timestamp TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Indexes for performance optimization
CREATE INDEX IF NOT EXISTS idx_operation ON quantity_measurement_entity(operation);
CREATE INDEX IF NOT EXISTS idx_measurement_type ON quantity_measurement_entity(operand1_measurement_type);
CREATE INDEX IF NOT EXISTS idx_timestamp ON quantity_measurement_entity(timestamp);
CREATE INDEX IF NOT EXISTS idx_has_error ON quantity_measurement_entity(has_error);

-- History table for audit trail (optional for future use)
CREATE TABLE IF NOT EXISTS quantity_measurement_history (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    entity_id BIGINT NOT NULL,
    action VARCHAR(50) NOT NULL, -- INSERT, UPDATE, DELETE
    old_value TEXT,
    new_value TEXT,
    changed_by VARCHAR(100),
    changed_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (entity_id) REFERENCES quantity_measurement_entity(id)
);

-- Index for history table
CREATE INDEX IF NOT EXISTS idx_history_entity_id ON quantity_measurement_history(entity_id);
CREATE INDEX IF NOT EXISTS idx_history_action ON quantity_measurement_history(action);
CREATE INDEX IF NOT EXISTS idx_history_changed_at ON quantity_measurement_history(changed_at);
