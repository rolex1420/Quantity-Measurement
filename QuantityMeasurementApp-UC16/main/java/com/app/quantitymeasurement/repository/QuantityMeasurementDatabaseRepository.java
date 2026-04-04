package com.app.quantitymeasurement.repository;

import com.app.quantitymeasurement.entity.QuantityMeasurementEntity;
import com.app.quantitymeasurement.entity.QuantityDTO;
import com.app.quantitymeasurement.exception.DatabaseException;
import com.app.quantitymeasurement.util.ConnectionPool;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class QuantityMeasurementDatabaseRepository implements IQuantityMeasurementRepository {
    private final ConnectionPool connectionPool;
    private static final String INSERT_SQL = 
        "INSERT INTO quantity_measurement_entity " +
        "(operand1_value, operand1_unit, operand1_measurement_type, " +
        "operand2_value, operand2_unit, operand2_measurement_type, " +
        "operation, result_value, result_unit, result_measurement_type, " +
        "has_error, error_message, timestamp) " +
        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    
    private static final String SELECT_ALL_SQL = 
        "SELECT operand1_value, operand1_unit, operand1_measurement_type, " +
        "operand2_value, operand2_unit, operand2_measurement_type, " +
        "operation, result_value, result_unit, result_measurement_type, " +
        "has_error, error_message, timestamp " +
        "FROM quantity_measurement_entity ORDER BY timestamp DESC";
    
    private static final String SELECT_BY_OPERATION_SQL = 
        SELECT_ALL_SQL + " WHERE operation = ?";
    
    private static final String SELECT_BY_TYPE_SQL = 
        SELECT_ALL_SQL + " WHERE operand1_measurement_type = ?";
    
    private static final String COUNT_SQL = 
        "SELECT COUNT(*) FROM quantity_measurement_entity";
    
    private static final String DELETE_ALL_SQL = 
        "DELETE FROM quantity_measurement_entity";

    public QuantityMeasurementDatabaseRepository() {
        this.connectionPool = ConnectionPool.getInstance();
        initializeDatabase();
    }

    private void initializeDatabase() {
        try (Connection connection = connectionPool.getConnection()) {
            executeSchema(connection);
            System.out.println("Database schema initialized successfully");
        } catch (SQLException e) {
            throw DatabaseException.schemaCreationFailed(e);
        }
    }

    private void executeSchema(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.execute(
                "CREATE TABLE IF NOT EXISTS quantity_measurement_entity (" +
                "id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
                "operand1_value DECIMAL(20, 10) NOT NULL, " +
                "operand1_unit VARCHAR(50) NOT NULL, " +
                "operand1_measurement_type VARCHAR(50) NOT NULL, " +
                "operand2_value DECIMAL(20, 10), " +
                "operand2_unit VARCHAR(50), " +
                "operand2_measurement_type VARCHAR(50), " +
                "operation VARCHAR(50) NOT NULL, " +
                "result_value DECIMAL(20, 10), " +
                "result_unit VARCHAR(50), " +
                "result_measurement_type VARCHAR(50), " +
                "has_error BOOLEAN NOT NULL DEFAULT FALSE, " +
                "error_message TEXT, " +
                "timestamp TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP, " +
                "created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP)"
            );
            
            statement.execute("CREATE INDEX IF NOT EXISTS idx_operation ON quantity_measurement_entity(operation)");
            statement.execute("CREATE INDEX IF NOT EXISTS idx_measurement_type ON quantity_measurement_entity(operand1_measurement_type)");
            statement.execute("CREATE INDEX IF NOT EXISTS idx_timestamp ON quantity_measurement_entity(timestamp)");
            statement.execute("CREATE INDEX IF NOT EXISTS idx_has_error ON quantity_measurement_entity(has_error)");
        }
    }

    @Override
    public void save(QuantityMeasurementEntity entity) {
        String sql = INSERT_SQL;
        
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            
            setInsertParameters(statement, entity);
            int rowsAffected = statement.executeUpdate();
            
            if (rowsAffected == 0) {
                throw new DatabaseException("Failed to save entity - no rows affected");
            }
        } catch (SQLException e) {
            throw DatabaseException.queryFailed(sql, e);
        }
    }

    private void setInsertParameters(PreparedStatement statement, QuantityMeasurementEntity entity) throws SQLException {
        statement.setBigDecimal(1, entity.getOperand1() != null ? 
            java.math.BigDecimal.valueOf(entity.getOperand1().getValue()) : null);
        statement.setString(2, entity.getOperand1() != null ? entity.getOperand1().getUnit() : null);
        statement.setString(3, entity.getOperand1() != null ? entity.getOperand1().getMeasurementType() : null);
        
        statement.setBigDecimal(4, entity.getOperand2() != null ? 
            java.math.BigDecimal.valueOf(entity.getOperand2().getValue()) : null);
        statement.setString(5, entity.getOperand2() != null ? entity.getOperand2().getUnit() : null);
        statement.setString(6, entity.getOperand2() != null ? entity.getOperand2().getMeasurementType() : null);
        
        statement.setString(7, entity.getOperation());
        statement.setBigDecimal(8, entity.getResult() != null ? 
            java.math.BigDecimal.valueOf(entity.getResult().getValue()) : null);
        statement.setString(9, entity.getResult() != null ? entity.getResult().getUnit() : null);
        statement.setString(10, entity.getResult() != null ? entity.getResult().getMeasurementType() : null);
        
        statement.setBoolean(11, entity.hasError());
        statement.setString(12, entity.getErrorMessage());
        statement.setTimestamp(13, Timestamp.valueOf(entity.getTimestamp()));
    }

    @Override
    public List<QuantityMeasurementEntity> getAllMeasurements() {
        return executeQuery(SELECT_ALL_SQL);
    }

    @Override
    public List<QuantityMeasurementEntity> getMeasurementsByOperation(String operation) {
        return executeQuery(SELECT_BY_OPERATION_SQL, operation);
    }

    @Override
    public List<QuantityMeasurementEntity> getMeasurementsByType(String measurementType) {
        return executeQuery(SELECT_BY_TYPE_SQL, measurementType);
    }

    @Override
    public int getTotalCount() {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(COUNT_SQL);
             ResultSet resultSet = statement.executeQuery()) {
            
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
            return 0;
        } catch (SQLException e) {
            throw DatabaseException.queryFailed(COUNT_SQL, e);
        }
    }

    @Override
    public void clearAll() {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_ALL_SQL)) {
            
            int rowsAffected = statement.executeUpdate();
            System.out.println("Cleared " + rowsAffected + " records from database");
        } catch (SQLException e) {
            throw DatabaseException.queryFailed(DELETE_ALL_SQL, e);
        }
    }

    private List<QuantityMeasurementEntity> executeQuery(String sql, Object... parameters) {
        List<QuantityMeasurementEntity> entities = new ArrayList<>();
        
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            
            for (int i = 0; i < parameters.length; i++) {
                statement.setObject(i + 1, parameters[i]);
            }
            
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    entities.add(mapResultSetToEntity(resultSet));
                }
            }
        } catch (SQLException e) {
            throw DatabaseException.queryFailed(sql, e);
        }
        
        return entities;
    }

    private QuantityMeasurementEntity mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        QuantityDTO operand1 = mapOperand(resultSet, "operand1");
        QuantityDTO operand2 = mapOperand(resultSet, "operand2");
        QuantityDTO result = mapResult(resultSet);
        
        boolean hasError = resultSet.getBoolean("has_error");
        String operation = resultSet.getString("operation");
        
        if (hasError) {
            String errorMessage = resultSet.getString("error_message");
            if (operand2 != null) {
                return new QuantityMeasurementEntity(operand1, operand2, operation, errorMessage);
            } else {
                return new QuantityMeasurementEntity(operand1, operation, errorMessage);
            }
        } else {
            if (operand2 != null) {
                return new QuantityMeasurementEntity(operand1, operand2, operation, result);
            } else {
                return new QuantityMeasurementEntity(operand1, operation, result);
            }
        }
    }

    private QuantityDTO mapOperand(ResultSet resultSet, String prefix) throws SQLException {
        Double value = resultSet.getObject(prefix + "_value", Double.class);
        if (value == null) return null;
        
        String unit = resultSet.getString(prefix + "_unit");
        String measurementType = resultSet.getString(prefix + "_measurement_type");
        
        return new QuantityDTO(value, unit, measurementType);
    }

    private QuantityDTO mapResult(ResultSet resultSet) throws SQLException {
        Double value = resultSet.getObject("result_value", Double.class);
        if (value == null) return null;
        
        String unit = resultSet.getString("result_unit");
        String measurementType = resultSet.getString("result_measurement_type");
        
        return new QuantityDTO(value, unit, measurementType);
    }

    @Override
    public String getPoolStatistics() {
        return connectionPool.getPoolStatistics().toString();
    }

    @Override
    public void releaseResources() {
        connectionPool.closeAllConnections();
    }
}
